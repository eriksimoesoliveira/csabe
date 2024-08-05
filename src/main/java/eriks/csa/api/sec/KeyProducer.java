package eriks.csa.api.sec;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@ApplicationScoped
public class KeyProducer {

    @Produces
    @ApplicationScoped
    public SecretKey produceKey() {
        String secret = System.getenv("CSA_SECRET"); // Retrieve the secret key from environment variable
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }
}