package utm.md.service;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utm.md.domain.ChannelUserCredentials;
import utm.md.domain.NotifySettings;
import utm.md.domain.User;
import utm.md.domain.enumeration.NotifyChannelStatusEnum;
import utm.md.repository.ChannelUserCredentialsRepository;
import utm.md.repository.NotifySettingsRepository;
import utm.md.repository.UserRepository;
import utm.md.security.SecurityUtils;
import utm.md.web.rest.errors.BadRequestAlertException;

/**
 * Service Implementation for managing {@link utm.md.domain.NotifySettings}.
 */
@Service
@Transactional
public class NotifySettingsService {

    private final Logger log = LoggerFactory.getLogger(NotifySettingsService.class);

    private final NotifySettingsRepository notifySettingsRepository;
    private final ChannelUserCredentialsRepository credentialsRepository;
    private final UserRepository userRepository;

    public NotifySettingsService(
        NotifySettingsRepository notifySettingsRepository,
        ChannelUserCredentialsRepository credentialsRepository,
        UserRepository userRepository
    ) {
        this.notifySettingsRepository = notifySettingsRepository;
        this.credentialsRepository = credentialsRepository;
        this.userRepository = userRepository;
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

    public User addNotifySettings(ChannelUserCredentials credentials) {
        log.debug("Request to add new channel of notification for current user");
        var userLogin = SecurityUtils.getCurrentUserLogin().get();
        var user = userRepository.findOneByLogin(userLogin).get();
        credentials = credentialsRepository.save(credentials);
        var newNotifySettings = new NotifySettings();
        newNotifySettings.setStatus(NotifyChannelStatusEnum.ON);
        newNotifySettings.setChannel(credentials.getChannel());
        newNotifySettings.setUserInternal(user);
        newNotifySettings.setCredentials(credentials);
        notifySettingsRepository.save(newNotifySettings);
        return userRepository.findOneByLogin(userLogin).get();
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
}
