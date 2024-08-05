package eriks.csa.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class AlbumValue extends PanacheEntity {
    public String userId;
    public String userName;
    public Double value;
    public Long lastUpdate;

    public AlbumValue(String userId, String userName, Double value, Long lastUpdate) {
        this.userId = userId;
        this.userName = userName;
        this.value = value;
        this.lastUpdate = lastUpdate;
    }

    public AlbumValue() {
    }
}
