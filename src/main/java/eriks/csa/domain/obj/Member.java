package eriks.csa.domain.obj;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.util.Optional;

@Entity
public class Member extends PanacheEntity {
    public String faceitNick;
    public String steamId;
    public boolean isSecondaryAccount;
    public long joinDate;
    public String country;
    public boolean hasAngel;

    @Column(nullable = true)
    public String primaryAccountFaceitNick;

    public Member(String faceitNick, String steamId, boolean isSecondaryAccount, long joinDate, String country, boolean hasAngel, String primaryAccountFaceitNick) {
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
