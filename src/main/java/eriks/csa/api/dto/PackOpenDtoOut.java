package eriks.csa.api.dto;

import eriks.csa.domain.obj.PackOpen;

public class PackOpenDtoOut {
    public String userId;
    public String userName;
    public String packId;
    public String packType;
    public String packOrigin;
    public Long timestamp;

    public PackOpenDtoOut(String userId, String userName, String packId, String packType, String packOrigin, Long timestamp) {
        this.userId = userId;
        this.userName = userName;
        this.packId = packId;
        this.packType = packType;
        this.packOrigin = packOrigin;
        this.timestamp = timestamp;
    }

    public static PackOpenDtoOut fromDomain(PackOpen packOpen) {
        return new PackOpenDtoOut(packOpen.userId, packOpen.userName, packOpen.packId, packOpen.packType, packOpen.packOrigin, packOpen.timestamp);
    }
}
