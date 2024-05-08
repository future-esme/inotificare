package utm.md.service;

import static java.util.Objects.nonNull;
import static utm.md.domain.enumeration.Channel.EMAIL;
import static utm.md.util.ActivationTokenGeneratorUtil.getOtpKey;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utm.md.config.ApplicationProperties;
import utm.md.domain.ChannelUserCredentials;
import utm.md.domain.ChannelsToken;
import utm.md.domain.NotifySettings;
import utm.md.domain.User;
import utm.md.domain.enumeration.Channel;
import utm.md.domain.enumeration.NotifyChannelStatusEnum;
import utm.md.repository.ChannelUserCredentialsRepository;
import utm.md.repository.ChannelsTokenRepository;
import utm.md.repository.NotifySettingsRepository;
import utm.md.repository.UserRepository;
import utm.md.security.SecurityUtils;
import utm.md.web.rest.errors.BadRequestAlertException;
import utm.md.web.rest.errors.NotFoundAlertException;

/**
 * Service Implementation for managing {@link utm.md.domain.NotifySettings}.
 */
@Service
@Transactional
public class NotifySettingsService {

    private final Logger log = LoggerFactory.getLogger(NotifySettingsService.class);

    private final NotifySettingsRepository notifySettingsRepository;
    private final ChannelUserCredentialsRepository credentialsRepository;
    private final ChannelsTokenRepository channelsTokenRepository;
    private final UserRepository userRepository;
    private final SendKeyMailService mailService;
    private final MinioService minioService;
    private final ApplicationProperties applicationProperties;

    public NotifySettingsService(
        NotifySettingsRepository notifySettingsRepository,
        ChannelUserCredentialsRepository credentialsRepository,
        ChannelsTokenRepository channelsTokenRepository,
        UserRepository userRepository,
        SendKeyMailService mailService,
        MinioService minioService,
        ApplicationProperties applicationProperties
    ) {
        this.notifySettingsRepository = notifySettingsRepository;
        this.credentialsRepository = credentialsRepository;
        this.channelsTokenRepository = channelsTokenRepository;
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.minioService = minioService;
        this.applicationProperties = applicationProperties;
    }

    /**
     * Save a notifySettings.
     *
     * @param notifySettings the entity to save.
     * @return the persisted entity.
     */
    public NotifySettings save(NotifySettings notifySettings) {
        log.debug("Request to save NotifySettings : {}", notifySettings);
        return notifySettingsRepository.save(notifySettings);
    }

    /**
     * Update a notifySettings.
     *
     * @param notifySettings the entity to save.
     * @return the persisted entity.
     */
    public NotifySettings update(NotifySettings notifySettings) {
        log.debug("Request to update NotifySettings : {}", notifySettings);
        return notifySettingsRepository.save(notifySettings);
    }

    /**
     * Get all the notifySettings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NotifySettings> findAll(Pageable pageable) {
        log.debug("Request to get all NotifySettings");
        return notifySettingsRepository.findAll(pageable);
    }

    /**
     * Get one notifySettings by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NotifySettings> findOne(UUID id) {
        log.debug("Request to get NotifySettings : {}", id);
        return notifySettingsRepository.findById(id);
    }

    /**
     * Delete the notifySettings by id.
     *
     * @param id the id of the entity.
     */
    public User delete(UUID id) {
        log.debug("Request to delete NotifySettings : {}", id);
        notifySettingsRepository.deleteById(id);
        var userLogin = SecurityUtils.getCurrentUserLogin().get();
        return userRepository.findOneByLogin(userLogin).get();
    }

    public User addNotifySettings(String channel, String email) {
        log.debug("Request to add new channel of notification for current user");
        var userLogin = SecurityUtils.getCurrentUserLogin().get();
        var user = userRepository.findOneByLogin(userLogin).get();
        var credentials = createCredentials(channel, email);
        var newNotifySettings = new NotifySettings();
        newNotifySettings.setStatus(NotifyChannelStatusEnum.OFF);
        newNotifySettings.setChannel(credentials.getChannel());
        newNotifySettings.setUserInternal(user);
        newNotifySettings.setCredentials(credentials);
        notifySettingsRepository.save(newNotifySettings);
        generateAndSaveToken(newNotifySettings);
        return userRepository.findOneWithNotifySettingsByLogin(userLogin).get();
    }

    public User changeStatusNotifySettings(UUID id) {
        var notifySettings = notifySettingsRepository.findById(id);
        if (notifySettings.isPresent()) {
            var userLogin = SecurityUtils.getCurrentUserLogin().get();
            if (userLogin.equals(notifySettings.get().getUserInternal().getLogin())) {
                var currentStatus = notifySettings.get().getStatus();
                notifySettings
                    .get()
                    .setStatus(
                        (currentStatus.equals(NotifyChannelStatusEnum.ON)) ? NotifyChannelStatusEnum.OFF : NotifyChannelStatusEnum.ON
                    );
                notifySettingsRepository.save(notifySettings.get());
                return userRepository.findOneByLogin(userLogin).get();
            }
        }
        throw new BadRequestAlertException("Invalid change status request", "notifySettings", "badRequest");
    }

    public byte[] getQrCode(Channel channel) {
        log.debug("Get qr code for bot from {}", channel.name());
        String objectName = null;
        switch (channel) {
            case TELEGRAM -> objectName = applicationProperties.minio().qrCode().telegram();
            case VIBER -> objectName = applicationProperties.minio().qrCode().viber();
        }
        if (nonNull(objectName)) {
            return minioService.getObjectByPath(objectName);
        } else {
            throw new NotFoundAlertException("Not found qr code for this channel", "notifySettings", "notFound");
        }
    }

    private ChannelUserCredentials createCredentials(String channel, String email) {
        log.debug("Create credentials for notify settings current user, channel : {}", channel);
        var credentials = new ChannelUserCredentials();
        if (EMAIL.name().equals(channel)) {
            credentials.setChatId(email);
        }
        credentials.setChannel(Channel.valueOf(channel));
        return credentialsRepository.save(credentials);
    }

    private void generateAndSaveToken(NotifySettings notifySettings) {
        log.debug("Generate token for activation channel : {}", notifySettings.getChannel().name());
        var channelsToken = new ChannelsToken();
        channelsToken.setToken(String.valueOf(getOtpKey(10)));
        channelsToken.setChannel(notifySettings.getChannel());
        channelsToken.setUserId(notifySettings.getUserInternal().getId());
        channelsToken.createdTime(Instant.now());
        channelsToken.setExpirationTime(Instant.now().atZone(ZoneId.of("Europe/Chisinau")).plusMinutes(5).toInstant());
        channelsToken.setNotifySettings(notifySettings);
        channelsTokenRepository.save(channelsToken);
        if (EMAIL.equals(notifySettings.getChannel())) {
            mailService.sendEmailValidateChannel(notifySettings.getCredentials().getChatId(), channelsToken.getToken());
        }
    }
}
