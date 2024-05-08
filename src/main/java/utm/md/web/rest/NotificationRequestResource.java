package utm.md.web.rest;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utm.md.service.RequestDataService;
import utm.md.service.dto.RequestDataApiDTO;
import utm.md.service.dto.RequestDataDTO;
import utm.md.service.dto.SendNotificationResponse;

@RestController
@RequestMapping("/api/send-notification")
public class NotificationRequestResource {

    private final Logger log = LoggerFactory.getLogger(NotificationRequestResource.class);

    private final RequestDataService requestDataService;

    public NotificationRequestResource(RequestDataService requestDataService) {
        this.requestDataService = requestDataService;
    }

    @PostMapping("")
    public ResponseEntity<SendNotificationResponse> sendNotification(@RequestBody RequestDataDTO requestDataDTO) {
        log.info("REST request to send notifications: {}", requestDataDTO);
        var response = requestDataService.handleRequest(requestDataDTO);
        requestDataService.handleRequest(requestDataDTO, response.getRequestData());
        return ResponseEntity.status(response.getInternStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestDataApiDTO> getNotification(@PathVariable("id") UUID id) {
        log.info("REST request to get request: {}", id);
        return ResponseEntity.ok(requestDataService.fetchRequestData(id));
    }
}
