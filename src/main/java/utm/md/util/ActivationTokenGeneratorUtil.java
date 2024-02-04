package utm.md.util;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Optional;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ActivationTokenGeneratorUtil {

    private final Logger log = LoggerFactory.getLogger(ActivationTokenGeneratorUtil.class);
    private static final TimeBasedOneTimePasswordGenerator totp = new TimeBasedOneTimePasswordGenerator();

    public Optional<String> getOtpKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(totp.getAlgorithm());

            int macLengthInBytes = Mac.getInstance(totp.getAlgorithm()).getMacLength();
            keyGenerator.init(macLengthInBytes * 8);

            Key key = keyGenerator.generateKey();
            return Optional.of(totp.generateOneTimePasswordString(key, Instant.now()));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("Error generating otp code");
        }
        return Optional.empty();
    }
}
