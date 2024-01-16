package utm.md.service.telegram;

import java.time.Instant;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import utm.md.config.ApplicationProperties;

@Service
public class SendTelegramNotificationService {

    private static final String CHAT_ID = "1196449083";
    private final String token;

    private final ApplicationProperties applicationProperties;

    public SendTelegramNotificationService(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
        this.token = applicationProperties.getTokens().getTelegram();
    }

    public void sendTestNotification() {
        var restTemplate = new RestTemplate();
        var address = "https://api.telegram.org/%s/sendMessage".formatted(token);
        var uriBuilder = UriComponentsBuilder
            .fromHttpUrl(address)
            .queryParam("chat_id", CHAT_ID)
            .queryParam("text", "start aplicatie " + Instant.now().toString())
            .encode()
            .toUriString();
        var response = restTemplate.getForEntity(uriBuilder, String.class);
    }
}
