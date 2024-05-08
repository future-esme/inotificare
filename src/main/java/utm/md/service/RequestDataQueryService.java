package utm.md.service;

import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import utm.md.domain.Notification_;
import utm.md.domain.RequestData;
import utm.md.domain.RequestData_;
import utm.md.domain.Template_;
import utm.md.repository.RequestDataRepository;
import utm.md.service.criteria.RequestDataCriteria;

/**
 * Service for executing complex queries for {@link RequestData} entities in the database.
 * The main input is a {@link RequestDataCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RequestData} or a {@link Page} of {@link RequestData} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RequestDataQueryService extends QueryService<RequestData> {

    private final Logger log = LoggerFactory.getLogger(RequestDataQueryService.class);

    private final RequestDataRepository requestDataRepository;

    public RequestDataQueryService(RequestDataRepository requestDataRepository) {
        this.requestDataRepository = requestDataRepository;
    }

    /**
     * Return a {@link List} of {@link RequestData} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RequestData> findByCriteria(RequestDataCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RequestData> specification = createSpecification(criteria);
        return requestDataRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link RequestData} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RequestData> findByCriteria(RequestDataCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RequestData> specification = createSpecification(criteria);
        return requestDataRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RequestDataCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RequestData> specification = createSpecification(criteria);
        return requestDataRepository.count(specification);
    }

    /**
     * Function to convert {@link RequestDataCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RequestData> createSpecification(RequestDataCriteria criteria) {
        Specification<RequestData> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), RequestData_.id));
            }
            if (criteria.getChannel() != null) {
                specification = specification.and(buildSpecification(criteria.getChannel(), RequestData_.channel));
            }
            if (criteria.getRecipients() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRecipients(), RequestData_.recipients));
            }
            if (criteria.getRecipientType() != null) {
                specification = specification.and(buildSpecification(criteria.getRecipientType(), RequestData_.recipientType));
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildSpecification(criteria.getPriority(), RequestData_.priority));
            }
            if (criteria.getContent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContent(), RequestData_.content));
            }
            if (criteria.getMessageStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getMessageStatus(), RequestData_.messageStatus));
            }
            if (criteria.getCreatedTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedTime(), RequestData_.createdTime));
            }
            if (criteria.getTemplateIdId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTemplateIdId(),
                            root -> root.join(RequestData_.templateId, JoinType.LEFT).get(Template_.id)
                        )
                    );
            }
            if (criteria.getNotificationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNotificationId(),
                            root -> root.join(RequestData_.notifications, JoinType.LEFT).get(Notification_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
