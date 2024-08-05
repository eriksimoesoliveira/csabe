package eriks.csa.api.dto;

import eriks.csa.domain.AlbumValue;

import java.time.Instant;

public class AlbumValueUpdateIn {
    public String userId;
    public String userName;
    public Double value;

    public AlbumValueUpdateIn() {
    }

    public AlbumValueUpdateIn(String userId, String userName, Double value) {
        this.userId = userId;
        this.userName = userName;
        this.value = value;
    }

    public AlbumValue toDomain() {
        return new AlbumValue(userId, userName, value, Instant.now().toEpochMilli());
    }
}
