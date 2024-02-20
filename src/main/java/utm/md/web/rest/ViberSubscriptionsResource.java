package utm.md.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import utm.md.domain.ViberSubscriptions;
import utm.md.repository.ViberSubscriptionsRepository;
import utm.md.service.ViberSubscriptionsQueryService;
import utm.md.service.ViberSubscriptionsService;
import utm.md.service.criteria.ViberSubscriptionsCriteria;
import utm.md.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link utm.md.domain.ViberSubscriptions}.
 */
@RestController
@RequestMapping("/api/viber-subscriptions")
public class ViberSubscriptionsResource {

    private final Logger log = LoggerFactory.getLogger(ViberSubscriptionsResource.class);

    private static final String ENTITY_NAME = "viberSubscriptions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ViberSubscriptionsService viberSubscriptionsService;

    private final ViberSubscriptionsRepository viberSubscriptionsRepository;

    private final ViberSubscriptionsQueryService viberSubscriptionsQueryService;

    public ViberSubscriptionsResource(
        ViberSubscriptionsService viberSubscriptionsService,
        ViberSubscriptionsRepository viberSubscriptionsRepository,
        ViberSubscriptionsQueryService viberSubscriptionsQueryService
    ) {
        this.viberSubscriptionsService = viberSubscriptionsService;
        this.viberSubscriptionsRepository = viberSubscriptionsRepository;
        this.viberSubscriptionsQueryService = viberSubscriptionsQueryService;
    }

    /**
     * {@code POST  /viber-subscriptions} : Create a new viberSubscriptions.
     *
     * @param viberSubscriptions the viberSubscriptions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new viberSubscriptions, or with status {@code 400 (Bad Request)} if the viberSubscriptions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ViberSubscriptions> createViberSubscriptions(@Valid @RequestBody ViberSubscriptions viberSubscriptions)
        throws URISyntaxException {
        log.debug("REST request to save ViberSubscriptions : {}", viberSubscriptions);
        if (viberSubscriptions.getId() != null) {
            throw new BadRequestAlertException("A new viberSubscriptions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ViberSubscriptions result = viberSubscriptionsService.save(viberSubscriptions);
        return ResponseEntity
            .created(new URI("/api/viber-subscriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /viber-subscriptions/:id} : Updates an existing viberSubscriptions.
     *
     * @param id the id of the viberSubscriptions to save.
     * @param viberSubscriptions the viberSubscriptions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated viberSubscriptions,
     * or with status {@code 400 (Bad Request)} if the viberSubscriptions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the viberSubscriptions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ViberSubscriptions> updateViberSubscriptions(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody ViberSubscriptions viberSubscriptions
    ) throws URISyntaxException {
        log.debug("REST request to update ViberSubscriptions : {}, {}", id, viberSubscriptions);
        if (viberSubscriptions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, viberSubscriptions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!viberSubscriptionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ViberSubscriptions result = viberSubscriptionsService.update(viberSubscriptions);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, viberSubscriptions.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /viber-subscriptions/:id} : Partial updates given fields of an existing viberSubscriptions, field will ignore if it is null
     *
     * @param id the id of the viberSubscriptions to save.
     * @param viberSubscriptions the viberSubscriptions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated viberSubscriptions,
     * or with status {@code 400 (Bad Request)} if the viberSubscriptions is not valid,
     * or with status {@code 404 (Not Found)} if the viberSubscriptions is not found,
     * or with status {@code 500 (Internal Server Error)} if the viberSubscriptions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ViberSubscriptions> partialUpdateViberSubscriptions(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody ViberSubscriptions viberSubscriptions
    ) throws URISyntaxException {
        log.debug("REST request to partial update ViberSubscriptions partially : {}, {}", id, viberSubscriptions);
        if (viberSubscriptions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, viberSubscriptions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!viberSubscriptionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ViberSubscriptions> result = viberSubscriptionsService.partialUpdate(viberSubscriptions);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, viberSubscriptions.getId().toString())
        );
    }

    /**
     * {@code GET  /viber-subscriptions} : get all the viberSubscriptions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of viberSubscriptions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ViberSubscriptions>> getAllViberSubscriptions(
        ViberSubscriptionsCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ViberSubscriptions by criteria: {}", criteria);

        Page<ViberSubscriptions> page = viberSubscriptionsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /viber-subscriptions/count} : count all the viberSubscriptions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countViberSubscriptions(ViberSubscriptionsCriteria criteria) {
        log.debug("REST request to count ViberSubscriptions by criteria: {}", criteria);
        return ResponseEntity.ok().body(viberSubscriptionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /viber-subscriptions/:id} : get the "id" viberSubscriptions.
     *
     * @param id the id of the viberSubscriptions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the viberSubscriptions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ViberSubscriptions> getViberSubscriptions(@PathVariable("id") UUID id) {
        log.debug("REST request to get ViberSubscriptions : {}", id);
        Optional<ViberSubscriptions> viberSubscriptions = viberSubscriptionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(viberSubscriptions);
    }

    /**
     * {@code DELETE  /viber-subscriptions/:id} : delete the "id" viberSubscriptions.
     *
     * @param id the id of the viberSubscriptions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteViberSubscriptions(@PathVariable("id") UUID id) {
        log.debug("REST request to delete ViberSubscriptions : {}", id);
        viberSubscriptionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
