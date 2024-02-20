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
import utm.md.domain.RequestData_;
import utm.md.domain.Template;
import utm.md.domain.Template_;
import utm.md.repository.TemplateRepository;
import utm.md.service.criteria.TemplateCriteria;

/**
 * Service for executing complex queries for {@link Template} entities in the database.
 * The main input is a {@link TemplateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Template} or a {@link Page} of {@link Template} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TemplateQueryService extends QueryService<Template> {

    private final Logger log = LoggerFactory.getLogger(TemplateQueryService.class);

    private final TemplateRepository templateRepository;

    public TemplateQueryService(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    /**
     * Return a {@link List} of {@link Template} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Template> findByCriteria(TemplateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Template> specification = createSpecification(criteria);
        return templateRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Template} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Template> findByCriteria(TemplateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Template> specification = createSpecification(criteria);
        return templateRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TemplateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Template> specification = createSpecification(criteria);
        return templateRepository.count(specification);
    }

    /**
     * Function to convert {@link TemplateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Template> createSpecification(TemplateCriteria criteria) {
        Specification<Template> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Template_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Template_.title));
            }
            if (criteria.getBodyRo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBodyRo(), Template_.bodyRo));
            }
            if (criteria.getBodyRu() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBodyRu(), Template_.bodyRu));
            }
            if (criteria.getBodyEn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBodyEn(), Template_.bodyEn));
            }
            if (criteria.getBodyShortRo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBodyShortRo(), Template_.bodyShortRo));
            }
            if (criteria.getBodyShortRu() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBodyShortRu(), Template_.bodyShortRu));
            }
            if (criteria.getBodyShortEn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBodyShortEn(), Template_.bodyShortEn));
            }
            if (criteria.getSubjectRo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubjectRo(), Template_.subjectRo));
            }
            if (criteria.getSubjectRu() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubjectRu(), Template_.subjectRu));
            }
            if (criteria.getSubjectEn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubjectEn(), Template_.subjectEn));
            }
            if (criteria.getRequestDataId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRequestDataId(),
                            root -> root.join(Template_.requestData, JoinType.LEFT).get(RequestData_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
