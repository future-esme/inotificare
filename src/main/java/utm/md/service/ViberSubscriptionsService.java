package utm.md.service;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utm.md.domain.ViberSubscriptions;
import utm.md.repository.ViberSubscriptionsRepository;

/**
 * Service Implementation for managing {@link utm.md.domain.ViberSubscriptions}.
 */
@Service
@Transactional
public class ViberSubscriptionsService {

    private final Logger log = LoggerFactory.getLogger(ViberSubscriptionsService.class);

    private final ViberSubscriptionsRepository viberSubscriptionsRepository;

    public ViberSubscriptionsService(ViberSubscriptionsRepository viberSubscriptionsRepository) {
        this.viberSubscriptionsRepository = viberSubscriptionsRepository;
    }

    /**
     * Save a viberSubscriptions.
     *
     * @param viberSubscriptions the entity to save.
     * @return the persisted entity.
     */
    public ViberSubscriptions save(ViberSubscriptions viberSubscriptions) {
        log.debug("Request to save ViberSubscriptions : {}", viberSubscriptions);
        return viberSubscriptionsRepository.save(viberSubscriptions);
    }

    /**
     * Update a viberSubscriptions.
     *
     * @param viberSubscriptions the entity to save.
     * @return the persisted entity.
     */
    public ViberSubscriptions update(ViberSubscriptions viberSubscriptions) {
        log.debug("Request to update ViberSubscriptions : {}", viberSubscriptions);
        return viberSubscriptionsRepository.save(viberSubscriptions);
    }

    /**
     * Partially update a viberSubscriptions.
     *
     * @param viberSubscriptions the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ViberSubscriptions> partialUpdate(ViberSubscriptions viberSubscriptions) {
        log.debug("Request to partially update ViberSubscriptions : {}", viberSubscriptions);

        return viberSubscriptionsRepository
            .findById(viberSubscriptions.getId())
            .map(existingViberSubscriptions -> {
                if (viberSubscriptions.getUserId() != null) {
                    existingViberSubscriptions.setUserId(viberSubscriptions.getUserId());
                }
                if (viberSubscriptions.getUserName() != null) {
                    existingViberSubscriptions.setUserName(viberSubscriptions.getUserName());
                }
                if (viberSubscriptions.getStatus() != null) {
                    existingViberSubscriptions.setStatus(viberSubscriptions.getStatus());
                }
                if (viberSubscriptions.getCreatedTime() != null) {
                    existingViberSubscriptions.setCreatedTime(viberSubscriptions.getCreatedTime());
                }
                if (viberSubscriptions.getLastUpdatedTime() != null) {
                    existingViberSubscriptions.setLastUpdatedTime(viberSubscriptions.getLastUpdatedTime());
                }

                return existingViberSubscriptions;
            })
            .map(viberSubscriptionsRepository::save);
    }

    /**
     * Get all the viberSubscriptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ViberSubscriptions> findAll(Pageable pageable) {
        log.debug("Request to get all ViberSubscriptions");
        return viberSubscriptionsRepository.findAll(pageable);
    }

    /**
     * Get one viberSubscriptions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ViberSubscriptions> findOne(UUID id) {
        log.debug("Request to get ViberSubscriptions : {}", id);
        return viberSubscriptionsRepository.findById(id);
    }

    /**
     * Delete the viberSubscriptions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete ViberSubscriptions : {}", id);
        viberSubscriptionsRepository.deleteById(id);
    }
}
