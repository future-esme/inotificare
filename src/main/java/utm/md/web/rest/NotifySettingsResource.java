package utm.md.web.rest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import utm.md.domain.NotifySettings;
import utm.md.domain.User;
import utm.md.domain.enumeration.Channel;
import utm.md.service.NotifySettingsQueryService;
import utm.md.service.NotifySettingsService;
import utm.md.service.criteria.NotifySettingsCriteria;

/**
 * REST controller for managing {@link utm.md.domain.NotifySettings}.
 */
@RestController
@RequestMapping("/api/notify-settings")
public class NotifySettingsResource {

    private final Logger log = LoggerFactory.getLogger(NotifySettingsResource.class);

    private final NotifySettingsService notifySettingsService;

    private final NotifySettingsQueryService notifySettingsQueryService;

    public NotifySettingsResource(NotifySettingsService notifySettingsService, NotifySettingsQueryService notifySettingsQueryService) {
        this.notifySettingsService = notifySettingsService;
        this.notifySettingsQueryService = notifySettingsQueryService;
    }

    @GetMapping("/add-channel/{channel}")
    public ResponseEntity<User> createNotifySettings(
        @PathVariable("channel") String channel,
        @RequestParam(value = "email", required = false) String email
    ) {
        log.debug("REST request to save NotifySettings : {}", channel);

        var result = notifySettingsService.addNotifySettings(channel, email);
        return ResponseEntity.status(201).body(result);
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

    @GetMapping("/qr-code/{channel}")
    public ResponseEntity<byte[]> getQrCode(@PathVariable("channel") Channel channel) {
        log.debug("REST request to get qr code for channel {}", channel);
        var image = notifySettingsService.getQrCode(channel);
        return ResponseEntity.ok(image);
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
