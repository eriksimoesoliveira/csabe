package eriks.csa.domain.obj;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;

@Entity
public class Login extends PanacheEntityBase {
    @Id
    public String userId;
    public long timestamp;
    @Column
    public String token;
    @Column
    public Long tokenExpiration;

    public Login(String userId, long timestamp, String token, Long tokenExpiration) {
        this.userId = userId;
        this.timestamp = timestamp;
        this.token = token;
        this.tokenExpiration = tokenExpiration;
    }

    // Default constructor
    public Login() {
    }

    @Transactional
    public static Login findById(String userId) {
        return Login.find("userId", userId).firstResult();
    }

    @Transactional
    public static Login findByField(String field, Object value) {
        return Login.find(field, value).firstResult();
    }

    @Transactional
    public static void save(Login login) {
        Login found = findById(login.userId);
        if (found != null) {
            found.tokenExpiration = login.tokenExpiration;
            found.token = login.token;
            found.timestamp = login.timestamp;
            found.persist();
        } else {
            login.persist();
        }
    }
}
