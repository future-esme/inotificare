package utm.md.util;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import org.apache.commons.lang3.RandomStringUtils;

public class ActivationTokenGeneratorUtil {

    private ActivationTokenGeneratorUtil() {}

    private static final TimeBasedOneTimePasswordGenerator totp = new TimeBasedOneTimePasswordGenerator();

    public static String getOtpKey(int length) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(totp.getAlgorithm());

            int macLengthInBytes = Mac.getInstance(totp.getAlgorithm()).getMacLength();
            keyGenerator.init(macLengthInBytes * length);

            Key key = keyGenerator.generateKey();
            return totp.generateOneTimePasswordString(key, Instant.now());
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            return RandomStringUtils.randomAlphabetic(8);
        }
    }
}
