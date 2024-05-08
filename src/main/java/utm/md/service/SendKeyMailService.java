package utm.md.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tech.jhipster.config.JHipsterProperties;
import utm.md.worker.NotificationEventPublisher;

@Service
public class SendKeyMailService {

    private final Logger log = LoggerFactory.getLogger(SendKeyMailService.class);
    private final NotificationEventPublisher notificationEventPublisher;
    private static final String ACTIVATION_KEY_MAIL_SUBJECT = "Cheie unica de activare cont";
    private static final String ACTIVATION_CHANNEL_MAIL_SUBJECT = "Cod unic de verificare email";
    private static final String ACTIVATION_KEY_MAIL_BODY =
        "Buna ziua, mai jos aveti cheia unica de activare a contului in sistemul. Cheia de activare : %s";
    private static final String ACTIVATION_CHANNEL_MAIL_BODY =
        "Buna ziua, mai jos aveti codul unic de verificare a acestui email ca canal de notificare. Cod de activare : %s";

    public SendKeyMailService(NotificationEventPublisher notificationEventPublisher) {
        this.notificationEventPublisher = notificationEventPublisher;
    }

    public void sendEmailActivationKey(String email, String activationKey) {
        log.debug("Send email with activationKey to {} for activate account", email);
        notificationEventPublisher.publishEmail(email, ACTIVATION_KEY_MAIL_BODY.formatted(activationKey), ACTIVATION_KEY_MAIL_SUBJECT);
    }

    public void sendEmailValidateChannel(String email, String activationKey) {
        log.debug("Send email with activationKey to {} for activate channel", email);
        notificationEventPublisher.publishEmail(
            email,
            ACTIVATION_CHANNEL_MAIL_BODY.formatted(activationKey),
            ACTIVATION_CHANNEL_MAIL_SUBJECT
        );
    }
}
