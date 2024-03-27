package utm.md.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utm.md.domain.RequestData;
import utm.md.repository.RequestDataRepository;

/**
 * Service Implementation for managing {@link utm.md.domain.RequestData}.
 */
@Service
@Transactional
public class RequestDataService {

    private final Logger log = LoggerFactory.getLogger(RequestDataService.class);

    private final RequestDataRepository requestDataRepository;

    public RequestDataService(RequestDataRepository requestDataRepository) {
        this.requestDataRepository = requestDataRepository;
    }

    /**
     * Save a requestData.
     *
     * @param requestData the entity to save.
     * @return the persisted entity.
     */
    public RequestData save(RequestData requestData) {
        log.debug("Request to save RequestData : {}", requestData);
        return requestDataRepository.save(requestData);
    }

    /**
     * Update a requestData.
     *
     * @param requestData the entity to save.
     * @return the persisted entity.
     */
    public RequestData update(RequestData requestData) {
        log.debug("Request to update RequestData : {}", requestData);
        return requestDataRepository.save(requestData);
    }

    /**
     * Partially update a requestData.
     *
     * @param requestData the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RequestData> partialUpdate(RequestData requestData) {
        log.debug("Request to partially update RequestData : {}", requestData);

        return requestDataRepository
            .findById(requestData.getId())
            .map(existingRequestData -> {
                if (requestData.getChannel() != null) {
                    existingRequestData.setChannel(requestData.getChannel());
                }
                if (requestData.getRecipients() != null) {
                    existingRequestData.setRecipients(requestData.getRecipients());
                }
                if (requestData.getRecipientType() != null) {
                    existingRequestData.setRecipientType(requestData.getRecipientType());
                }
                if (requestData.getPriority() != null) {
                    existingRequestData.setPriority(requestData.getPriority());
                }
                if (requestData.getContent() != null) {
                    existingRequestData.setContent(requestData.getContent());
                }
                if (requestData.getMessageStatus() != null) {
                    existingRequestData.setMessageStatus(requestData.getMessageStatus());
                }
                if (requestData.getCreatedTime() != null) {
                    existingRequestData.setCreatedTime(requestData.getCreatedTime());
                }

                return existingRequestData;
            })
            .map(requestDataRepository::save);
    }

    /**
     * Get all the requestData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RequestData> findAll(Pageable pageable) {
        log.debug("Request to get all RequestData");
        return requestDataRepository.findAll(pageable);
    }

    /**
     *  Get all the requestData where Notification is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RequestData> findAllWhereNotificationIsNull() {
        log.debug("Request to get all requestData where Notification is null");
        return StreamSupport
            .stream(requestDataRepository.findAll().spliterator(), false)
            .filter(requestData -> requestData.getNotifications() == null)
            .toList();
    }

    /**
     * Get one requestData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RequestData> findOne(UUID id) {
        log.debug("Request to get RequestData : {}", id);
        return requestDataRepository.findById(id);
    }

    /**
     * Delete the requestData by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete RequestData : {}", id);
        requestDataRepository.deleteById(id);
    }
}
