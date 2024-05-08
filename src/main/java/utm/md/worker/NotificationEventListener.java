package utm.md.worker;

import static utm.md.config.RabbitMqConstants.*;

import java.util.Arrays;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import utm.md.service.dto.NotificationChannelDTO;
import utm.md.service.dto.NotificationShortDTO;
import utm.md.service.dto.NotificationSubjectDTO;

@Component
public class NotificationEventListener {

    private final Logger log = LoggerFactory.getLogger(NotificationEventListener.class);

    @Autowired
    private Environment env;

    private final RabbitTemplate rabbitTemplate;

    public NotificationEventListener(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Async
    @EventListener
    public void handleEvent(NotificationChannelDTO event) {
        log.info("try to sent notification to rabbit " + event);
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains("rabbit")) {
            log.info("sent notification to rabbit " + event);
            sendToRabbit(event);
        }
    }

    private void sendToRabbit(NotificationChannelDTO event) {
        String exchange;
        String routingKey;
        NotificationShortDTO eventToSend;
        switch (event.getChannel()) {
            case TELEGRAM -> {
                exchange = TELEGRAM_EXCHANGE;
                routingKey = TELEGRAM_ROUTING_KEY;
                eventToSend = new NotificationShortDTO(event.getReceiver(), event.getMessage());
            }
            case VIBER -> {
                exchange = VIBER_EXCHANGE;
                routingKey = VIBER_ROUTING_KEY;
                eventToSend = new NotificationShortDTO(event.getReceiver(), event.getMessage());
            }
            case EMAIL -> {
                exchange = MAIL_EXCHANGE;
                routingKey = MAIL_ROUTING_KEY;
                eventToSend = new NotificationSubjectDTO(event.getReceiver(), event.getMessage(), event.getSubject());
            }
            default -> {
                log.warn("channel is not yet available");
                return;
            }
        }
        rabbitTemplate.convertAndSend(exchange, routingKey, eventToSend);
    }
}
