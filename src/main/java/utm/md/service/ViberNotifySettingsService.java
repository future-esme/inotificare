package utm.md.service;

import static java.util.Objects.nonNull;
import static utm.md.domain.enumeration.Channel.VIBER;

import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import utm.md.domain.ViberSubscriptions;
import utm.md.domain.enumeration.NotifyChannelStatusEnum;
import utm.md.domain.enumeration.ViberSubscriptionStatus;
import utm.md.repository.ChannelUserCredentialsRepository;
import utm.md.repository.ChannelsTokenRepository;
import utm.md.repository.NotifySettingsRepository;
import utm.md.repository.ViberSubscriptionsRepository;
import utm.md.service.viber.ViberCallbackDTO;

@Service
public class ViberNotifySettingsService {

    private final Logger log = LoggerFactory.getLogger(ViberNotifySettingsService.class);
    private final ViberSubscriptionsRepository viberSubscriptionsRepository;
    private final NotifySettingsRepository notifySettingsRepository;
    private final ChannelsTokenRepository channelsTokenRepository;
    private final ChannelUserCredentialsRepository credentialsRepository;

    public ViberNotifySettingsService(
        ViberSubscriptionsRepository viberSubscriptionsRepository,
        NotifySettingsRepository notifySettingsRepository,
        ChannelsTokenRepository channelsTokenRepository,
        ChannelUserCredentialsRepository credentialsRepository
    ) {
        this.viberSubscriptionsRepository = viberSubscriptionsRepository;
        this.notifySettingsRepository = notifySettingsRepository;
        this.channelsTokenRepository = channelsTokenRepository;
        this.credentialsRepository = credentialsRepository;
    }

    @Async
    public void handleCallback(ViberCallbackDTO callback) {
        log.debug("Handle viber callback");
        if (nonNull(callback)) {
            switch (callback.event()) {
                case subscribed -> handleSubscribedCallback(callback);
                case unsubscribed -> handleUnsubscribedCallback(callback);
                case message -> handleMessageCallback(callback);
            }
        }
    }

    private void handleSubscribedCallback(ViberCallbackDTO callback) {
        log.debug("Handle viber callback subscribed event");
        var viberSubscriptions = new ViberSubscriptions();
        viberSubscriptions.setCreatedTime(Instant.now());
        viberSubscriptions.setLastUpdatedTime(Instant.now());
        viberSubscriptions.setStatus(ViberSubscriptionStatus.WAITING);
        viberSubscriptions.setUserId(callback.user().id());
        viberSubscriptions.setUserName(callback.user().name());
        viberSubscriptionsRepository.save(viberSubscriptions);
    }

    private void handleUnsubscribedCallback(ViberCallbackDTO callbackDTO) {
        log.debug("Handle viber callback unsubscribed event");
        var viberSubscriptionsOptional = viberSubscriptionsRepository.findByUserIdAndStatus(
            callbackDTO.user().id(),
            ViberSubscriptionStatus.SUBSCRIBED
        );
        if (viberSubscriptionsOptional.isPresent()) {
            var viberSubscribed = viberSubscriptionsOptional.get();
            viberSubscribed.setStatus(ViberSubscriptionStatus.UNSUBSCRIBED);
            viberSubscribed.setLastUpdatedTime(Instant.now());
            viberSubscriptionsRepository.save(viberSubscribed);
            notifySettingsRepository.updateByChatId(NotifyChannelStatusEnum.OFF.name(), callbackDTO.user().id(), VIBER.name());
        }
    }

    private void handleMessageCallback(ViberCallbackDTO callbackDTO) {
        log.debug("Handle viber callback message event");
        var viberSubscriptionsOptional = viberSubscriptionsRepository.findByUserIdAndStatus(
            callbackDTO.sender().id(),
            ViberSubscriptionStatus.WAITING
        );
        var existentTokenOptional = channelsTokenRepository.findTokenAndActive(callbackDTO.message().text(), VIBER.name());
        if (existentTokenOptional.isPresent()) {
            log.debug("Valid attempt to activate notify settings for viber channel");
            var notifySettingsOptional = notifySettingsRepository.findByUserInternalIdAndChannel(
                existentTokenOptional.get().getUserId(),
                VIBER
            );
            if (notifySettingsOptional.isPresent()) {
                notifySettingsOptional.get().setStatus(NotifyChannelStatusEnum.ON);
                var credentials = notifySettingsOptional.get().getCredentials();
                credentials.setChatId(callbackDTO.sender().id());
                credentialsRepository.save(credentials);
                notifySettingsRepository.save(notifySettingsOptional.get());
            }
        }
        if (viberSubscriptionsOptional.isPresent()) {
            var viberSubscribed = viberSubscriptionsOptional.get();
            viberSubscribed.setStatus(ViberSubscriptionStatus.SUBSCRIBED);
            viberSubscribed.setLastUpdatedTime(Instant.now());
            viberSubscriptionsRepository.save(viberSubscribed);
        }
    }
}
