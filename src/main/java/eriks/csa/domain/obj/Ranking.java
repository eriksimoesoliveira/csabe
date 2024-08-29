package eriks.csa.domain.obj;

public class Ranking {
    public final String userName;
    public final Double value;
    public final Integer openPacks;
    public final Integer totalPacks;

    public Ranking(String userName, Double value, Integer openPacks, Integer totalPacks) {
        this.userName = userName;
        this.value = value;
        this.openPacks = openPacks;
        this.totalPacks = totalPacks;
    }
}
