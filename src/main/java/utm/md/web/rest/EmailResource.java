package utm.md.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utm.md.service.EmailNotifySettingsService;

@RestController
@RequestMapping("/api/email/callback")
public class EmailResource {

    private final Logger log = LoggerFactory.getLogger(EmailResource.class);
    private final EmailNotifySettingsService emailNotifySettingsService;

    public EmailResource(EmailNotifySettingsService emailNotifySettingsService) {
        this.emailNotifySettingsService = emailNotifySettingsService;
    }

    @GetMapping("")
    public ResponseEntity<Void> callback(@RequestParam("token") String token) {
        log.debug("Received callback from front app");
        emailNotifySettingsService.handleCallback(token);
        return ResponseEntity.ok().build();
    }
}
