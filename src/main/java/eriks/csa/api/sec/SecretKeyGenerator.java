package eriks.csa.api.sec;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class SecretKeyGenerator {

    public static void main(String[] args) throws Exception {
        // Generate a secret key
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // You can use 128, 192, or 256-bit keys
        SecretKey secretKey = keyGen.generateKey();

        // Encode the key in Base64
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("Generated Secret Key: " + encodedKey);
    }
}
