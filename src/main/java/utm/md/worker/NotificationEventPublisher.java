package utm.md.worker;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import utm.md.domain.enumeration.Channel;
import utm.md.service.dto.NotificationChannelDTO;

@Component
public class NotificationEventPublisher implements InitializingBean {

    private static ApplicationEventPublisher publisher;

    private final ApplicationEventPublisher applicationEventPublisher;

    public NotificationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        publisher = applicationEventPublisher;
    }

    public void publishViber(String receiver, String content) {
        publisher.publishEvent(new NotificationChannelDTO(receiver, content, Channel.VIBER));
    }

    public void publishTelegram(String receiver, String content) {
        publisher.publishEvent(new NotificationChannelDTO(receiver, content, Channel.TELEGRAM));
    }

    public void publishEmail(String receiver, String content) {
        publisher.publishEvent(new NotificationChannelDTO(receiver, content, Channel.EMAIL));
    }

    public void publishEmail(String receiver, String content, String subject) {
        publisher.publishEvent(new NotificationChannelDTO(receiver, content, Channel.EMAIL, subject));
    }

    public void publish(NotificationChannelDTO notification) {
        publisher.publishEvent(notification);
    }
}
