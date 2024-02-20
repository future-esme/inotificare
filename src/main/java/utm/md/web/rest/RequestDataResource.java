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
import utm.md.domain.RequestData;
import utm.md.repository.RequestDataRepository;
import utm.md.service.RequestDataQueryService;
import utm.md.service.RequestDataService;
import utm.md.service.criteria.RequestDataCriteria;
import utm.md.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link utm.md.domain.RequestData}.
 */
@RestController
@RequestMapping("/api/request-data")
public class RequestDataResource {

    private final Logger log = LoggerFactory.getLogger(RequestDataResource.class);

    private static final String ENTITY_NAME = "requestData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestDataService requestDataService;

    private final RequestDataRepository requestDataRepository;

    private final RequestDataQueryService requestDataQueryService;

    public RequestDataResource(
        RequestDataService requestDataService,
        RequestDataRepository requestDataRepository,
        RequestDataQueryService requestDataQueryService
    ) {
        this.requestDataService = requestDataService;
        this.requestDataRepository = requestDataRepository;
        this.requestDataQueryService = requestDataQueryService;
    }

    /**
     * {@code POST  /request-data} : Create a new requestData.
     *
     * @param requestData the requestData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestData, or with status {@code 400 (Bad Request)} if the requestData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RequestData> createRequestData(@Valid @RequestBody RequestData requestData) throws URISyntaxException {
        log.debug("REST request to save RequestData : {}", requestData);
        if (requestData.getId() != null) {
            throw new BadRequestAlertException("A new requestData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequestData result = requestDataService.save(requestData);
        return ResponseEntity
            .created(new URI("/api/request-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /request-data/:id} : Updates an existing requestData.
     *
     * @param id the id of the requestData to save.
     * @param requestData the requestData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestData,
     * or with status {@code 400 (Bad Request)} if the requestData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RequestData> updateRequestData(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody RequestData requestData
    ) throws URISyntaxException {
        log.debug("REST request to update RequestData : {}, {}", id, requestData);
        if (requestData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RequestData result = requestDataService.update(requestData);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestData.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /request-data/:id} : Partial updates given fields of an existing requestData, field will ignore if it is null
     *
     * @param id the id of the requestData to save.
     * @param requestData the requestData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestData,
     * or with status {@code 400 (Bad Request)} if the requestData is not valid,
     * or with status {@code 404 (Not Found)} if the requestData is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequestData> partialUpdateRequestData(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody RequestData requestData
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequestData partially : {}, {}", id, requestData);
        if (requestData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequestData> result = requestDataService.partialUpdate(requestData);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestData.getId().toString())
        );
    }

    /**
     * {@code GET  /request-data} : get all the requestData.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestData in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RequestData>> getAllRequestData(
        RequestDataCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get RequestData by criteria: {}", criteria);

        Page<RequestData> page = requestDataQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /request-data/count} : count all the requestData.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countRequestData(RequestDataCriteria criteria) {
        log.debug("REST request to count RequestData by criteria: {}", criteria);
        return ResponseEntity.ok().body(requestDataQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /request-data/:id} : get the "id" requestData.
     *
     * @param id the id of the requestData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RequestData> getRequestData(@PathVariable("id") UUID id) {
        log.debug("REST request to get RequestData : {}", id);
        Optional<RequestData> requestData = requestDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requestData);
    }

    /**
     * {@code DELETE  /request-data/:id} : delete the "id" requestData.
     *
     * @param id the id of the requestData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequestData(@PathVariable("id") UUID id) {
        log.debug("REST request to delete RequestData : {}", id);
        requestDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
