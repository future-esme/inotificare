package utm.md.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import utm.md.domain.ViberSubscriptions;
import utm.md.domain.ViberSubscriptions_;
import utm.md.repository.ViberSubscriptionsRepository;
import utm.md.service.criteria.ViberSubscriptionsCriteria;

/**
 * Service for executing complex queries for {@link ViberSubscriptions} entities in the database.
 * The main input is a {@link ViberSubscriptionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ViberSubscriptions} or a {@link Page} of {@link ViberSubscriptions} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ViberSubscriptionsQueryService extends QueryService<ViberSubscriptions> {

    private final Logger log = LoggerFactory.getLogger(ViberSubscriptionsQueryService.class);

    private final ViberSubscriptionsRepository viberSubscriptionsRepository;

    public ViberSubscriptionsQueryService(ViberSubscriptionsRepository viberSubscriptionsRepository) {
        this.viberSubscriptionsRepository = viberSubscriptionsRepository;
    }

    /**
     * Return a {@link List} of {@link ViberSubscriptions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ViberSubscriptions> findByCriteria(ViberSubscriptionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ViberSubscriptions> specification = createSpecification(criteria);
        return viberSubscriptionsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ViberSubscriptions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ViberSubscriptions> findByCriteria(ViberSubscriptionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ViberSubscriptions> specification = createSpecification(criteria);
        return viberSubscriptionsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ViberSubscriptionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ViberSubscriptions> specification = createSpecification(criteria);
        return viberSubscriptionsRepository.count(specification);
    }

    /**
     * Function to convert {@link ViberSubscriptionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ViberSubscriptions> createSpecification(ViberSubscriptionsCriteria criteria) {
        Specification<ViberSubscriptions> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ViberSubscriptions_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserId(), ViberSubscriptions_.userId));
            }
            if (criteria.getUserName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserName(), ViberSubscriptions_.userName));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), ViberSubscriptions_.status));
            }
            if (criteria.getCreatedTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedTime(), ViberSubscriptions_.createdTime));
            }
            if (criteria.getLastUpdatedTime() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastUpdatedTime(), ViberSubscriptions_.lastUpdatedTime));
            }
        }
        return specification;
    }
}
