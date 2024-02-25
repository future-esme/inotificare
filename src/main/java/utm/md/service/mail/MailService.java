package utm.md.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tech.jhipster.config.JHipsterProperties;
import utm.md.domain.Notification;

/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    public MailService(JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender) {
        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(Notification notification) {
        log.debug("Send email to {}", notification.getRecipient().getLogin());

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());
            message.setTo("carte7respect@gmail.com");
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject("test");
            message.setText("test", false);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to recipient '{}'", notification.getRecipient().getLogin());
        } catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", notification.getRecipient().getLogin(), e);
        }
    }
}
