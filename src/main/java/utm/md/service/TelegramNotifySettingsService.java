package utm.md.service;

import static utm.md.domain.enumeration.Channel.TELEGRAM;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import utm.md.domain.enumeration.NotifyChannelStatusEnum;
import utm.md.repository.ChannelUserCredentialsRepository;
import utm.md.repository.ChannelsTokenRepository;
import utm.md.repository.NotifySettingsRepository;
import utm.md.service.dto.UpdatesTelegramDTO;

@Service
public class TelegramNotifySettingsService {

    private final Logger log = LoggerFactory.getLogger(TelegramNotifySettingsService.class);
    private final NotifySettingsRepository notifySettingsRepository;
    private final ChannelsTokenRepository channelsTokenRepository;
    private final ChannelUserCredentialsRepository credentialsRepository;

    public TelegramNotifySettingsService(
        NotifySettingsRepository notifySettingsRepository,
        ChannelsTokenRepository channelsTokenRepository,
        ChannelUserCredentialsRepository credentialsRepository
    ) {
        this.notifySettingsRepository = notifySettingsRepository;
        this.channelsTokenRepository = channelsTokenRepository;
        this.credentialsRepository = credentialsRepository;
    }

    @Async
    public void handleCallback(UpdatesTelegramDTO objectCallback) {
        log.debug("Handle telegram callback");
        handleMessageCallback(objectCallback);
    }

    private void handleMessageCallback(UpdatesTelegramDTO objectCallback) {
        log.debug("Handle telegram callback message event");
        var existentTokenOptional = channelsTokenRepository.findTokenAndActive(objectCallback.message().text(), TELEGRAM.name());
        if (existentTokenOptional.isPresent()) {
            log.debug("Valid attempt to activate notify settings for telegram channel");
            var notifySettingsOptional = notifySettingsRepository.findByUserInternalIdAndChannel(
                existentTokenOptional.get().getUserId(),
                TELEGRAM
            );
            if (notifySettingsOptional.isPresent()) {
                notifySettingsOptional.get().setStatus(NotifyChannelStatusEnum.ON);
                var credentials = notifySettingsOptional.get().getCredentials();
                credentials.setChatId(objectCallback.message().chat().id().toString());
                credentialsRepository.save(credentials);
                notifySettingsRepository.save(notifySettingsOptional.get());
            }
        }
    }
}
