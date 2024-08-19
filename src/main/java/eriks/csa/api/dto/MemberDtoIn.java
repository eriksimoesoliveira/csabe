package eriks.csa.api.dto;

import eriks.csa.domain.obj.Member;

public class MemberDtoIn {
    public String steamId;
    public String faceitNick;
    public boolean isSecondaryAccount;
    public long joinDate;
    public String country;
    public boolean hasAngel;
    public String primaryAccountFaceitNick;

    public Member toDomain() {
        return new Member(steamId, faceitNick, isSecondaryAccount, joinDate, country, hasAngel, primaryAccountFaceitNick);
    }
}
