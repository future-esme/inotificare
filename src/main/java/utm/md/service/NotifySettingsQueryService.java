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
import utm.md.domain.ChannelUserCredentials_;
import utm.md.domain.NotifySettings;
import utm.md.domain.NotifySettings_;
import utm.md.domain.User_;
import utm.md.repository.NotifySettingsRepository;
import utm.md.service.criteria.NotifySettingsCriteria;

/**
 * Service for executing complex queries for {@link NotifySettings} entities in the database.
 * The main input is a {@link NotifySettingsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NotifySettings} or a {@link Page} of {@link NotifySettings} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NotifySettingsQueryService extends QueryService<NotifySettings> {

    private final Logger log = LoggerFactory.getLogger(NotifySettingsQueryService.class);

    private final NotifySettingsRepository notifySettingsRepository;

    public NotifySettingsQueryService(NotifySettingsRepository notifySettingsRepository) {
        this.notifySettingsRepository = notifySettingsRepository;
    }

    /**
     * Return a {@link List} of {@link NotifySettings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NotifySettings> findByCriteria(NotifySettingsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NotifySettings> specification = createSpecification(criteria);
        return notifySettingsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link NotifySettings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NotifySettings> findByCriteria(NotifySettingsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NotifySettings> specification = createSpecification(criteria);
        return notifySettingsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NotifySettingsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NotifySettings> specification = createSpecification(criteria);
        return notifySettingsRepository.count(specification);
    }

    /**
     * Function to convert {@link NotifySettingsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NotifySettings> createSpecification(NotifySettingsCriteria criteria) {
        Specification<NotifySettings> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), NotifySettings_.id));
            }
            if (criteria.getChannel() != null) {
                specification = specification.and(buildSpecification(criteria.getChannel(), NotifySettings_.channel));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), NotifySettings_.status));
            }
            if (criteria.getCredentialsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCredentialsId(),
                            root -> root.join(NotifySettings_.credentials, JoinType.LEFT).get(ChannelUserCredentials_.id)
                        )
                    );
            }
            if (criteria.getUserInternalId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserInternalId(),
                            root -> root.join(NotifySettings_.userInternal, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
