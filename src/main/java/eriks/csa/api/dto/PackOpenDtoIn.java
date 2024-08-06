package eriks.csa.api.dto;

import eriks.csa.domain.obj.PackOpen;

import java.time.Instant;

public class PackOpenDtoIn {
    public String userId;
    public String userName;
    public String packId;
    public String packType;
    public String packOrigin;

    public PackOpenDtoIn() {
    }

    public PackOpenDtoIn(String userId, String userName, String packId, String packType, String packOrigin) {
        this.userId = userId;
        this.userName = userName;
        this.packId = packId;
        this.packType = packType;
        this.packOrigin = packOrigin;
    }

    public PackOpen toDomain() {
        return new PackOpen(userId, userName, packId, packType, packOrigin, Instant.now().toEpochMilli());
    }
}
