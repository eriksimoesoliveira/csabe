package eriks.csa.api.dto;

import eriks.csa.domain.obj.Ranking;

public class RankingDtoOut {
    public String userName;
    public Double value;
    public Integer openPacks;
    public Integer totalPacks;

    public RankingDtoOut(String userName, Double value, Integer openPacks, Integer totalPacks) {
        this.userName = userName;
        this.value = value;
        this.openPacks = openPacks;
        this.totalPacks = totalPacks;
    }

    public static RankingDtoOut fromDomain(Ranking ranking) {
        return new RankingDtoOut(ranking.userName, ranking.value, ranking.openPacks, ranking.totalPacks);
    }
}
