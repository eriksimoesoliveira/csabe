package eriks.csa.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class PackOpen extends PanacheEntity {
    public String userId;
    public String userName;
    public String packId;
    public String packType;
    public String packOrigin;
    public Long timestamp;

    public PackOpen(String userId, String userName, String packId, String packType, String packOrigin, Long timestamp) {
        this.userId = userId;
        this.userName = userName;
        this.packId = packId;
        this.packType = packType;
        this.packOrigin = packOrigin;
        this.timestamp = timestamp;
    }

    public PackOpen() {
    }
}
