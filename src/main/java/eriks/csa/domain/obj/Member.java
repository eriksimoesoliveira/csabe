package eriks.csa.domain.obj;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Member extends PanacheEntity {
    public String faceitNick;
    public String steamId;
    public boolean isSecondaryAccount;
    public long joinDate;
    public String country;

    public Member(String faceitNick, String steamId, boolean isSecondaryAccount, long joinDate, String country) {
        this.faceitNick = faceitNick;
        this.steamId = steamId;
        this.isSecondaryAccount = isSecondaryAccount;
        this.joinDate = joinDate;
        this.country = country;
    }

    public Member() {
    }
}
