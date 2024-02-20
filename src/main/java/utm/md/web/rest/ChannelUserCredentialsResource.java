package utm.md.web.rest;

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
import utm.md.domain.ChannelUserCredentials;
import utm.md.repository.ChannelUserCredentialsRepository;
import utm.md.service.ChannelUserCredentialsQueryService;
import utm.md.service.ChannelUserCredentialsService;
import utm.md.service.criteria.ChannelUserCredentialsCriteria;
import utm.md.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link utm.md.domain.ChannelUserCredentials}.
 */
@RestController
@RequestMapping("/api/channel-user-credentials")
public class ChannelUserCredentialsResource {

    private final Logger log = LoggerFactory.getLogger(ChannelUserCredentialsResource.class);

    private static final String ENTITY_NAME = "channelUserCredentials";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChannelUserCredentialsService channelUserCredentialsService;

    private final ChannelUserCredentialsRepository channelUserCredentialsRepository;

    private final ChannelUserCredentialsQueryService channelUserCredentialsQueryService;

    public ChannelUserCredentialsResource(
        ChannelUserCredentialsService channelUserCredentialsService,
        ChannelUserCredentialsRepository channelUserCredentialsRepository,
        ChannelUserCredentialsQueryService channelUserCredentialsQueryService
    ) {
        this.channelUserCredentialsService = channelUserCredentialsService;
        this.channelUserCredentialsRepository = channelUserCredentialsRepository;
        this.channelUserCredentialsQueryService = channelUserCredentialsQueryService;
    }

    /**
     * {@code POST  /channel-user-credentials} : Create a new channelUserCredentials.
     *
     * @param channelUserCredentials the channelUserCredentials to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new channelUserCredentials, or with status {@code 400 (Bad Request)} if the channelUserCredentials has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ChannelUserCredentials> createChannelUserCredentials(@RequestBody ChannelUserCredentials channelUserCredentials)
        throws URISyntaxException {
        log.debug("REST request to save ChannelUserCredentials : {}", channelUserCredentials);
        if (channelUserCredentials.getId() != null) {
            throw new BadRequestAlertException("A new channelUserCredentials cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChannelUserCredentials result = channelUserCredentialsService.save(channelUserCredentials);
        return ResponseEntity
            .created(new URI("/api/channel-user-credentials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /channel-user-credentials/:id} : Updates an existing channelUserCredentials.
     *
     * @param id the id of the channelUserCredentials to save.
     * @param channelUserCredentials the channelUserCredentials to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated channelUserCredentials,
     * or with status {@code 400 (Bad Request)} if the channelUserCredentials is not valid,
     * or with status {@code 500 (Internal Server Error)} if the channelUserCredentials couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ChannelUserCredentials> updateChannelUserCredentials(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody ChannelUserCredentials channelUserCredentials
    ) throws URISyntaxException {
        log.debug("REST request to update ChannelUserCredentials : {}, {}", id, channelUserCredentials);
        if (channelUserCredentials.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, channelUserCredentials.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!channelUserCredentialsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChannelUserCredentials result = channelUserCredentialsService.update(channelUserCredentials);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, channelUserCredentials.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /channel-user-credentials/:id} : Partial updates given fields of an existing channelUserCredentials, field will ignore if it is null
     *
     * @param id the id of the channelUserCredentials to save.
     * @param channelUserCredentials the channelUserCredentials to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated channelUserCredentials,
     * or with status {@code 400 (Bad Request)} if the channelUserCredentials is not valid,
     * or with status {@code 404 (Not Found)} if the channelUserCredentials is not found,
     * or with status {@code 500 (Internal Server Error)} if the channelUserCredentials couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChannelUserCredentials> partialUpdateChannelUserCredentials(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody ChannelUserCredentials channelUserCredentials
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChannelUserCredentials partially : {}, {}", id, channelUserCredentials);
        if (channelUserCredentials.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, channelUserCredentials.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!channelUserCredentialsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChannelUserCredentials> result = channelUserCredentialsService.partialUpdate(channelUserCredentials);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, channelUserCredentials.getId().toString())
        );
    }

    /**
     * {@code GET  /channel-user-credentials} : get all the channelUserCredentials.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of channelUserCredentials in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ChannelUserCredentials>> getAllChannelUserCredentials(
        ChannelUserCredentialsCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ChannelUserCredentials by criteria: {}", criteria);

        Page<ChannelUserCredentials> page = channelUserCredentialsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /channel-user-credentials/count} : count all the channelUserCredentials.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countChannelUserCredentials(ChannelUserCredentialsCriteria criteria) {
        log.debug("REST request to count ChannelUserCredentials by criteria: {}", criteria);
        return ResponseEntity.ok().body(channelUserCredentialsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /channel-user-credentials/:id} : get the "id" channelUserCredentials.
     *
     * @param id the id of the channelUserCredentials to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the channelUserCredentials, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ChannelUserCredentials> getChannelUserCredentials(@PathVariable("id") UUID id) {
        log.debug("REST request to get ChannelUserCredentials : {}", id);
        Optional<ChannelUserCredentials> channelUserCredentials = channelUserCredentialsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(channelUserCredentials);
    }

    /**
     * {@code DELETE  /channel-user-credentials/:id} : delete the "id" channelUserCredentials.
     *
     * @param id the id of the channelUserCredentials to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChannelUserCredentials(@PathVariable("id") UUID id) {
        log.debug("REST request to delete ChannelUserCredentials : {}", id);
        channelUserCredentialsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
