package eriks.csa.domain.obj;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class LoginAttempt extends PanacheEntity {
    public String userId;
    public String userName;
    public long timestamp;

    public LoginAttempt() {

    }

    public LoginAttempt(String userId, String userName, long timestamp) {
        this.userId = userId;
        this.userName = userName;
        this.timestamp = timestamp;
    }
}
