package utm.md.service.viber;

import static java.util.Objects.nonNull;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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
        var notificationBody = new ViberNotificationDTO("", ViberMessageTypeEnum.text, new ViberSenderBotDTO(""), "");
        HttpEntity<ViberNotificationDTO> request = new HttpEntity<>(notificationBody, RequestUtil.getHttpHeaders(token));
        var response = restTemplate.exchange(address, HttpMethod.POST, request, String.class);
    }

    public void handleCallback(ViberCallbackDTO callback) {
        if (nonNull(callback)) {
            if (callback.event().equals(ViberEventTypeEnum.subscribed)) {
                //store user id and name
            } else if (callback.event().equals(ViberEventTypeEnum.unsubscribed)) {
                //delete account
            } else if (callback.event().equals(ViberEventTypeEnum.message)) {
                //check there waiting subscriptions
                //check message.text is a code
                //check to activate and change status in vibersubscriptions
                //else ignore
            }
        }
    }
}
