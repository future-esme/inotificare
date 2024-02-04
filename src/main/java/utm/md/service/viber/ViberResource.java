package utm.md.service.viber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/viber/callback")
public class ViberResource {

    private final Logger log = LoggerFactory.getLogger(ViberResource.class);

    @PostMapping("")
    public ResponseEntity<Void> callback(@RequestBody(required = false) ViberCallbackDTO objectCallback) {
        log.debug("Received callback from viber bot, object received : {}", objectCallback);
        return ResponseEntity.ok().build();
    }
}
