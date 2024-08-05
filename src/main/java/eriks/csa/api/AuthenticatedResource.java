package eriks.csa.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import eriks.csa.api.sec.EncryptionUtil;
import eriks.csa.domain.CSAService;
import io.quarkus.security.UnauthorizedException;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.HttpHeaders;

abstract class AuthenticatedResource {

    @Inject
    ObjectMapper objectWriter;
    @Inject
    CSAService service;

    protected <T> T validate(String payload, HttpHeaders headers, Class<T> toValueType) {
        try {
            String sanitizedPayload = "";
            if (payload.startsWith("\"") && payload.endsWith("\"")) {
                sanitizedPayload = payload.substring(1, payload.length() - 1);
            }
            sanitizedPayload = sanitizedPayload.replace("\\", "");
            String signature = headers.getHeaderString("Signature");
            String encrypted = EncryptionUtil.encrypt(sanitizedPayload);

            if (!signature.equals(encrypted)) {
                throw new UnauthorizedException("Signature mismatch");
            }

            return objectWriter.readValue(sanitizedPayload, toValueType);

        } catch (Exception e) {
            e.printStackTrace();
            throw new UnauthorizedException(e);
        }
    }
}
