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
import utm.md.domain.Template;
import utm.md.repository.TemplateRepository;

/**
 * Service Implementation for managing {@link utm.md.domain.Template}.
 */
@Service
@Transactional
public class TemplateService {

    private final Logger log = LoggerFactory.getLogger(TemplateService.class);

    private final TemplateRepository templateRepository;

    public TemplateService(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    /**
     * Save a template.
     *
     * @param template the entity to save.
     * @return the persisted entity.
     */
    public Template save(Template template) {
        log.debug("Request to save Template : {}", template);
        return templateRepository.save(template);
    }

    /**
     * Update a template.
     *
     * @param template the entity to save.
     * @return the persisted entity.
     */
    public Template update(Template template) {
        log.debug("Request to update Template : {}", template);
        return templateRepository.save(template);
    }

    /**
     * Partially update a template.
     *
     * @param template the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Template> partialUpdate(Template template) {
        log.debug("Request to partially update Template : {}", template);

        return templateRepository
            .findById(template.getId())
            .map(existingTemplate -> {
                if (template.getTitle() != null) {
                    existingTemplate.setTitle(template.getTitle());
                }
                if (template.getBodyRo() != null) {
                    existingTemplate.setBodyRo(template.getBodyRo());
                }
                if (template.getBodyRu() != null) {
                    existingTemplate.setBodyRu(template.getBodyRu());
                }
                if (template.getBodyEn() != null) {
                    existingTemplate.setBodyEn(template.getBodyEn());
                }
                if (template.getBodyShortRo() != null) {
                    existingTemplate.setBodyShortRo(template.getBodyShortRo());
                }
                if (template.getBodyShortRu() != null) {
                    existingTemplate.setBodyShortRu(template.getBodyShortRu());
                }
                if (template.getBodyShortEn() != null) {
                    existingTemplate.setBodyShortEn(template.getBodyShortEn());
                }
                if (template.getSubjectRo() != null) {
                    existingTemplate.setSubjectRo(template.getSubjectRo());
                }
                if (template.getSubjectRu() != null) {
                    existingTemplate.setSubjectRu(template.getSubjectRu());
                }
                if (template.getSubjectEn() != null) {
                    existingTemplate.setSubjectEn(template.getSubjectEn());
                }

                return existingTemplate;
            })
            .map(templateRepository::save);
    }

    /**
     * Get all the templates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Template> findAll(Pageable pageable) {
        log.debug("Request to get all Templates");
        return templateRepository.findAll(pageable);
    }

    /**
     *  Get all the templates where RequestData is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Template> findAllWhereRequestDataIsNull() {
        log.debug("Request to get all templates where RequestData is null");
        return StreamSupport
            .stream(templateRepository.findAll().spliterator(), false)
            .filter(template -> template.getRequestData() == null)
            .toList();
    }

    /**
     * Get one template by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Template> findOne(UUID id) {
        log.debug("Request to get Template : {}", id);
        return templateRepository.findById(id);
    }

    /**
     * Delete the template by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Template : {}", id);
        templateRepository.deleteById(id);
    }
}
