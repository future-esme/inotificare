package utm.md.service;

import static java.util.Objects.nonNull;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utm.md.domain.Notification;
import utm.md.domain.RequestData;
import utm.md.domain.enumeration.Channel;
import utm.md.domain.enumeration.MessageStatus;
import utm.md.repository.NotificationRepository;
import utm.md.repository.RequestDataRepository;
import utm.md.service.dto.*;
import utm.md.web.rest.errors.NotFoundException;
import utm.md.worker.NotificationEventPublisher;

/**
 * Service Implementation for managing {@link utm.md.domain.RequestData}.
 */
@Service
@Transactional
public class RequestDataService {

    private final Logger log = LoggerFactory.getLogger(RequestDataService.class);

    private final RequestDataRepository requestDataRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationEventPublisher notificationEventPublisher;

    public RequestDataService(
        RequestDataRepository requestDataRepository,
        NotificationRepository notificationRepository,
        NotificationEventPublisher notificationEventPublisher
    ) {
        this.requestDataRepository = requestDataRepository;
        this.notificationRepository = notificationRepository;
        this.notificationEventPublisher = notificationEventPublisher;
    }

    public RequestDataApiDTO fetchRequestData(UUID id) {
        log.info("Fetching request data for id {}", id);
        var requestData = requestDataRepository.findById(id);
        if (requestData.isEmpty()) {
            throw new NotFoundException("Reqeust not found", "RequestData");
        }
        return new RequestDataApiDTO(requestData.get());
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

    public SendNotificationResponse handleRequest(RequestDataDTO requestDataDTO) {
        log.info("Received request to send notifications");
        SendNotificationResponse response = new SendNotificationResponse();
        try {
            var requestDataEntity = createRequestData(requestDataDTO);
            response.setRequestId(requestDataEntity.getId());
            response.setRequestData(requestDataEntity);
            if (
                !nonNull(requestDataDTO.getRecipientType()) ||
                !nonNull(requestDataDTO.getRecipients()) ||
                requestDataDTO.getRecipients().isEmpty()
            ) {
                response.isInvalidRequest();
            } else {
                response.isSuccess();
            }
        } catch (Exception e) {
            response.isInvalidRequest();
        }
        return response;
    }

    @Async
    public void handleRequest(RequestDataDTO requestDataDTO, RequestData requestDataEntity) {
        log.info("Asynchronously process and send notifications");
        List<IUserRequestDataDTO> recipientsData =
            switch (requestDataDTO.getRecipientType()) {
                case DEPARTMENT -> requestDataRepository.findRecipientsByDepartmentIds(requestDataDTO.getRecipients());
                case USER -> requestDataRepository.findRecipientsByUserIds(requestDataDTO.getRecipients());
            };
        var notifications = createNotificationEntities(requestDataEntity, recipientsData);
        updateRequestData(requestDataEntity, MessageStatus.PROCESSED);
        createAndPublishNotifications(recipientsData, requestDataDTO);
        updateRequestData(requestDataEntity, MessageStatus.PENDING);
        updateStatusNotifications(notifications, MessageStatus.PENDING);
        //somewhere will be the confirmation implementation and status tp each message sent to queue will be confirmed individualy
        updateRequestData(requestDataEntity, MessageStatus.SENT);
        updateStatusNotifications(notifications, MessageStatus.SENT);
    }

    private RequestData createRequestData(RequestDataDTO requestDataDTO) {
        var requestData = new RequestData();
        requestData
            .channel(Channel.DEFAULT)
            .recipientType(requestDataDTO.getRecipientType())
            .priority(requestDataDTO.getPriority())
            .content(requestDataDTO.getContent())
            .messageStatus(MessageStatus.UNPROCESSED)
            .createdTime(Instant.now());
        if (nonNull(requestDataDTO.getRecipients())) {
            requestData.recipients(requestDataDTO.getRecipients().toString());
        }
        return requestDataRepository.save(requestData);
    }

    private void updateRequestData(RequestData requestData, MessageStatus status) {
        requestData.setMessageStatus(status);
        requestDataRepository.save(requestData);
    }

    private List<Notification> createNotificationEntities(RequestData requestData, List<IUserRequestDataDTO> recipients) {
        log.info("Create notifications");
        List<Notification> notificationsToSend = new ArrayList<>();
        for (var recipient : recipients) {
            var notification = new Notification();
            notification
                .content(requestData.getContent())
                .status(MessageStatus.PROCESSED)
                .recipientId(UUID.fromString(recipient.getUserId()))
                .requestId(requestData)
                .channelId(UUID.fromString(recipient.getChannelId()));
            notificationsToSend.add(notificationRepository.save(notification));
        }
        return notificationsToSend;
    }

    private void updateStatusNotifications(List<Notification> notificationsToSend, MessageStatus status) {
        log.info("Update notifications status");
        for (var notification : notificationsToSend) {
            notification.status(status);
            notificationRepository.save(notification);
        }
    }

    private void createAndPublishNotifications(List<IUserRequestDataDTO> notifications, RequestDataDTO requestDataDTO) {
        log.info("Publish notification to queue");
        for (var notification : notifications) {
            NotificationChannelDTO notificationQueue;
            if (Channel.EMAIL.name().equals(notification.getChannel())) {
                notificationQueue =
                    new NotificationChannelDTO(
                        notification.getChatId(),
                        requestDataDTO.getContent(),
                        Channel.EMAIL,
                        requestDataDTO.getEmailSubject()
                    );
            } else {
                notificationQueue =
                    new NotificationChannelDTO(
                        notification.getChatId(),
                        requestDataDTO.getContent(),
                        Channel.valueOf(notification.getChannel())
                    );
            }
            notificationEventPublisher.publish(notificationQueue);
        }
    }
}
