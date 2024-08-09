package eriks.csa.domain.obj;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class GameUser extends PanacheEntityBase {
    @Id
    public String userId;
    public String userName;
    public String password;
    public long creationTime;

    public GameUser(String userId, String userName, String password, long creationTime) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.creationTime = creationTime;
    }

    public GameUser() {
    }
}
