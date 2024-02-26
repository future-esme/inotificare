package utm.md.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utm.md.service.TelegramNotifySettingsService;
import utm.md.service.dto.UpdatesTelegramDTO;

@RestController
@RequestMapping("/telegram/callback")
public class TelegramResource {

    private final Logger log = LoggerFactory.getLogger(TelegramResource.class);
    private final TelegramNotifySettingsService telegramNotifySettingsService;

    public TelegramResource(TelegramNotifySettingsService telegramNotifySettingsService) {
        this.telegramNotifySettingsService = telegramNotifySettingsService;
    }

    @PostMapping("")
    public ResponseEntity<Void> callback(@RequestBody(required = false) UpdatesTelegramDTO objectCallback) {
        log.debug("Received callback from telegram bot, object received : {}", objectCallback);
        telegramNotifySettingsService.handleCallback(objectCallback);
        return ResponseEntity.ok().build();
    }
}
