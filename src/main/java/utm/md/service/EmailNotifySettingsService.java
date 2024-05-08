package utm.md.service;

import static utm.md.domain.enumeration.Channel.EMAIL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import utm.md.domain.enumeration.NotifyChannelStatusEnum;
import utm.md.repository.ChannelsTokenRepository;
import utm.md.repository.NotifySettingsRepository;

@Service
public class EmailNotifySettingsService {

    private final Logger log = LoggerFactory.getLogger(EmailNotifySettingsService.class);
    private final NotifySettingsRepository notifySettingsRepository;
    private final ChannelsTokenRepository channelsTokenRepository;

    public EmailNotifySettingsService(NotifySettingsRepository notifySettingsRepository, ChannelsTokenRepository channelsTokenRepository) {
        this.notifySettingsRepository = notifySettingsRepository;
        this.channelsTokenRepository = channelsTokenRepository;
    }

    public void handleCallback(String token) {
        log.debug("Handle email callback");
        handleMessageCallback(token);
    }

    private void handleMessageCallback(String token) {
        log.debug("Handle email callback message event");
        var existentTokenOptional = channelsTokenRepository.findTokenAndActive(token, EMAIL.name());
        if (existentTokenOptional.isPresent()) {
            log.debug("Valid attempt to activate notify settings for email channel");
            var notifySettingsOptional = notifySettingsRepository.findByUserInternalIdAndChannel(
                existentTokenOptional.get().getUserId(),
                EMAIL
            );
            if (notifySettingsOptional.isPresent()) {
                notifySettingsOptional.get().setStatus(NotifyChannelStatusEnum.ON);
                notifySettingsRepository.save(notifySettingsOptional.get());
            }
        }
    }
}
