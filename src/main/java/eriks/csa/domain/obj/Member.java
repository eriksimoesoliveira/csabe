package eriks.csa.domain.obj;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Member extends PanacheEntityBase {
    @Id
    public String steamId;
    public String faceitNick;
    public boolean isSecondaryAccount;
    public long joinDate;
    public String country;
    public boolean hasAngel;

    @Column(nullable = true)
    public String primaryAccountFaceitNick;

    public Member(String steamId, String faceitNick, boolean isSecondaryAccount, long joinDate, String country, boolean hasAngel, String primaryAccountFaceitNick) {
        this.faceitNick = faceitNick;
        this.steamId = steamId;
        this.isSecondaryAccount = isSecondaryAccount;
        this.joinDate = joinDate;
        this.country = country;
        this.hasAngel = hasAngel;
        this.primaryAccountFaceitNick = primaryAccountFaceitNick;
    }

    public Member() {
    }
}
