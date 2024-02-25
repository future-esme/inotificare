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
import utm.md.domain.NotifySettings;
import utm.md.domain.User;
import utm.md.repository.NotifySettingsRepository;
import utm.md.service.NotifySettingsQueryService;
import utm.md.service.NotifySettingsService;
import utm.md.service.criteria.NotifySettingsCriteria;
import utm.md.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link utm.md.domain.NotifySettings}.
 */
@RestController
@RequestMapping("/api/notify-settings")
public class NotifySettingsResource {

    private final Logger log = LoggerFactory.getLogger(NotifySettingsResource.class);

    private static final String ENTITY_NAME = "notifySettings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotifySettingsService notifySettingsService;

    private final NotifySettingsRepository notifySettingsRepository;

    private final NotifySettingsQueryService notifySettingsQueryService;

    public NotifySettingsResource(
        NotifySettingsService notifySettingsService,
        NotifySettingsRepository notifySettingsRepository,
        NotifySettingsQueryService notifySettingsQueryService
    ) {
        this.notifySettingsService = notifySettingsService;
        this.notifySettingsRepository = notifySettingsRepository;
        this.notifySettingsQueryService = notifySettingsQueryService;
    }

    @PostMapping("")
    public ResponseEntity<User> createNotifySettings(@RequestBody ChannelUserCredentials credentials) {
        log.debug("REST request to save NotifySettings : {}", credentials);

        var result = notifySettingsService.addNotifySettings(credentials);
        return ResponseEntity
            .status(201)
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notify-settings/:id} : Updates an existing notifySettings.
     *
     * @param id the id of the notifySettings to save.
     * @param notifySettings the notifySettings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notifySettings,
     * or with status {@code 400 (Bad Request)} if the notifySettings is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notifySettings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NotifySettings> updateNotifySettings(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody NotifySettings notifySettings
    ) {
        log.debug("REST request to update NotifySettings : {}, {}", id, notifySettings);
        if (notifySettings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notifySettings.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notifySettingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NotifySettings result = notifySettingsService.update(notifySettings);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notifySettings.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /notify-settings} : get all the notifySettings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notifySettings in body.
     */
    @GetMapping("")
    public ResponseEntity<List<NotifySettings>> getAllNotifySettings(
        NotifySettingsCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get NotifySettings by criteria: {}", criteria);

        Page<NotifySettings> page = notifySettingsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /notify-settings/:id} : get the "id" notifySettings.
     *
     * @param id the id of the notifySettings to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notifySettings, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NotifySettings> getNotifySettings(@PathVariable("id") UUID id) {
        log.debug("REST request to get NotifySettings : {}", id);
        Optional<NotifySettings> notifySettings = notifySettingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(notifySettings);
    }

    @GetMapping("/change-status/{id}")
    public ResponseEntity<User> changeNotifySettingsStatus(@PathVariable("id") UUID id) {
        log.debug("REST request to get NotifySettings : {}", id);
        var user = notifySettingsService.changeStatusNotifySettings(id);
        return ResponseEntity.ok(user);
    }

    /**
     * {@code DELETE  /notify-settings/:id} : delete the "id" notifySettings.
     *
     * @param id the id of the notifySettings to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteNotifySettings(@PathVariable("id") UUID id) {
        log.debug("REST request to delete NotifySettings : {}", id);
        var user = notifySettingsService.delete(id);
        return ResponseEntity.ok(user);
    }
}
