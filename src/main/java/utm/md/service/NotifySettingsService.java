package utm.md.service;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utm.md.domain.NotifySettings;
import utm.md.repository.NotifySettingsRepository;

/**
 * Service Implementation for managing {@link utm.md.domain.NotifySettings}.
 */
@Service
@Transactional
public class NotifySettingsService {

    private final Logger log = LoggerFactory.getLogger(NotifySettingsService.class);

    private final NotifySettingsRepository notifySettingsRepository;

    public NotifySettingsService(NotifySettingsRepository notifySettingsRepository) {
        this.notifySettingsRepository = notifySettingsRepository;
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
     * Partially update a notifySettings.
     *
     * @param notifySettings the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NotifySettings> partialUpdate(NotifySettings notifySettings) {
        log.debug("Request to partially update NotifySettings : {}", notifySettings);

        return notifySettingsRepository
            .findById(notifySettings.getId())
            .map(existingNotifySettings -> {
                if (notifySettings.getChannel() != null) {
                    existingNotifySettings.setChannel(notifySettings.getChannel());
                }
                if (notifySettings.getStatus() != null) {
                    existingNotifySettings.setStatus(notifySettings.getStatus());
                }

                return existingNotifySettings;
            })
            .map(notifySettingsRepository::save);
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
    public void delete(UUID id) {
        log.debug("Request to delete NotifySettings : {}", id);
        notifySettingsRepository.deleteById(id);
    }
}
