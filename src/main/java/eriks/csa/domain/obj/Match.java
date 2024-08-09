package eriks.csa.domain.obj;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Match extends PanacheEntityBase {
    @Id
    public String id;
    public String player1;
    public String player2;
    public String player3;
    public String player4;
    public String player5;
    public String map;
    public Long date;
    public MatchResult result;
    public String enemyTeam;
    public String score;

    public Match() {
    }

    public Match(String id, String player1, String player2, String player3, String player4, String player5, String map, Long date, MatchResult result, String enemyTeam, String score) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.player4 = player4;
        this.player5 = player5;
        this.map = map;
        this.date = date;
        this.result = result;
        this.enemyTeam = enemyTeam;
        this.score = score;
    }

    public enum MatchResult {
        VICTORY,
        DEFEAT
    }
}

