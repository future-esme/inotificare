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
import utm.md.domain.ChannelUserCredentials;
import utm.md.domain.ChannelUserCredentials_;
import utm.md.repository.ChannelUserCredentialsRepository;
import utm.md.service.criteria.ChannelUserCredentialsCriteria;

/**
 * Service for executing complex queries for {@link ChannelUserCredentials} entities in the database.
 * The main input is a {@link ChannelUserCredentialsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ChannelUserCredentials} or a {@link Page} of {@link ChannelUserCredentials} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ChannelUserCredentialsQueryService extends QueryService<ChannelUserCredentials> {

    private final Logger log = LoggerFactory.getLogger(ChannelUserCredentialsQueryService.class);

    private final ChannelUserCredentialsRepository channelUserCredentialsRepository;

    public ChannelUserCredentialsQueryService(ChannelUserCredentialsRepository channelUserCredentialsRepository) {
        this.channelUserCredentialsRepository = channelUserCredentialsRepository;
    }

    /**
     * Return a {@link List} of {@link ChannelUserCredentials} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ChannelUserCredentials> findByCriteria(ChannelUserCredentialsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ChannelUserCredentials> specification = createSpecification(criteria);
        return channelUserCredentialsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ChannelUserCredentials} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ChannelUserCredentials> findByCriteria(ChannelUserCredentialsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ChannelUserCredentials> specification = createSpecification(criteria);
        return channelUserCredentialsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ChannelUserCredentialsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ChannelUserCredentials> specification = createSpecification(criteria);
        return channelUserCredentialsRepository.count(specification);
    }

    /**
     * Function to convert {@link ChannelUserCredentialsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ChannelUserCredentials> createSpecification(ChannelUserCredentialsCriteria criteria) {
        Specification<ChannelUserCredentials> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ChannelUserCredentials_.id));
            }
            if (criteria.getChatId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChatId(), ChannelUserCredentials_.chatId));
            }
            if (criteria.getChannel() != null) {
                specification = specification.and(buildSpecification(criteria.getChannel(), ChannelUserCredentials_.channel));
            }
        }
        return specification;
    }
}
