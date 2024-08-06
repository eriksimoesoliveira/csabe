package eriks.csa.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eriks.csa.domain.CSAService;
import jakarta.inject.Inject;

abstract class AuthenticatedResource {

    @Inject
    ObjectMapper objectWriter;
    @Inject
    CSAService service;

    protected <T> T deserialize(String payload, Class<T> toValueType) throws JsonProcessingException {
        String sanitizedPayload = "";
        if (payload.startsWith("\"") && payload.endsWith("\"")) {
            sanitizedPayload = payload.substring(1, payload.length() - 1);
        }
        sanitizedPayload = sanitizedPayload.replace("\\", "");
        return objectWriter.readValue(sanitizedPayload, toValueType);
    }
}
