package utm.md.service;

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

/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class SendAuthenticationKeyMailService {

    private final Logger log = LoggerFactory.getLogger(SendAuthenticationKeyMailService.class);
    private static final String ACTIVATION_KEY_MAIL_SUBJECT = "Cheie unica de activare cont";
    private static final String ACTIVATION_KEY_MAIL_BODY =
        "Buna ziua, mai jos aveti cheia unica de activare a contului in sistemul. Cheia de activare : %s";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    public SendAuthenticationKeyMailService(JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender) {
        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String email, String activationKey) {
        log.debug("Send mail to {} with authentication key", email);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());
            message.setTo(email);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(ACTIVATION_KEY_MAIL_SUBJECT);
            message.setText(ACTIVATION_KEY_MAIL_BODY.formatted(activationKey), false);
            javaMailSender.send(mimeMessage);
            log.debug("Send mail to {} with authentication key", email);
        } catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to email '{}', error message: {}", email, e.getMessage());
        }
    }
}
