package utm.md.service.viber;

import java.time.Instant;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import utm.md.config.ApplicationProperties;
import utm.md.util.RequestUtil;

@Service
public class SendViberNotificationService {

    private final String token;
    private final RestTemplate restTemplate;

    private final ApplicationProperties applicationProperties;

    public SendViberNotificationService(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
        this.token = applicationProperties.getTokens().getViber();
        this.restTemplate = RequestUtil.getRestTemplate();
    }

    public void sendTestNotification() {
        var address = "https://chatapi.viber.com/pa/send_message";
        var notificationBody = new ViberNotificationDTO("", ViberMessageTypeEnum.text, new ViberSenderDTO(""));
        HttpEntity<ViberNotificationDTO> request = new HttpEntity<>(notificationBody, RequestUtil.getHttpHeaders(token));
        var response = restTemplate.exchange(address, HttpMethod.POST, request, String.class);
    }
}