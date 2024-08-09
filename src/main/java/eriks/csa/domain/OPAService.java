package eriks.csa.domain;

import eriks.csa.domain.obj.Match;
import eriks.csa.domain.obj.Member;
import eriks.csa.domain.obj.OPAPackage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.text.DateFormat;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@ApplicationScoped
public class OPAService {

    @Transactional
    public void saveMember(Member member) {
        member.persist();
    }

    public List<Member> getAllMembers() {
        return Member.listAll();
    }

    Locale locale = new Locale("en", "CA");
    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);

    @Transactional
    public void saveOrUpdateMatch(Match match) {
        Optional<Match> existingMatch = Match.findByIdOptional(match.id);

        if (existingMatch.isPresent()) {
            // Atualiza o match existente
            Match updateMatch = existingMatch.get();
            updateMatch.player1 = match.player1;
            updateMatch.player2 = match.player2;
            updateMatch.player3 = match.player3;
            updateMatch.player4 = match.player4;
            updateMatch.player5 = match.player5;
            updateMatch.map = match.map;
            updateMatch.date = match.date;
            updateMatch.result = match.result;
            updateMatch.enemyTeam = match.enemyTeam;
            updateMatch.score = match.score;
            updateMatch.persist();
        } else {
            // Insere um novo match
            match.persist();
            generatePackages(match);
        }

    }

    private void generatePackages(Match match) {
        new OPAPackage(match.id, match.player1, false, false, match.result + " against " + match.enemyTeam + " on " + match.map + " ("+dateFormat.format(match.date)+")", Instant.now()).persist();
        new OPAPackage(match.id, match.player2, false, false, match.result + " against " + match.enemyTeam + " on " + match.map + " ("+dateFormat.format(match.date)+")", Instant.now()).persist();
        new OPAPackage(match.id, match.player3, false, false, match.result + " against " + match.enemyTeam + " on " + match.map + " ("+dateFormat.format(match.date)+")", Instant.now()).persist();
        new OPAPackage(match.id, match.player4, false, false, match.result + " against " + match.enemyTeam + " on " + match.map + " ("+dateFormat.format(match.date)+")", Instant.now()).persist();
        new OPAPackage(match.id, match.player5, false, false, match.result + " against " + match.enemyTeam + " on " + match.map + " ("+dateFormat.format(match.date)+")", Instant.now()).persist();

        if (match.result == Match.MatchResult.VICTORY) {
            new OPAPackage(match.id + "-V", match.player1, false, false, match.result + " against " + match.enemyTeam + " on " + match.map + " ("+dateFormat.format(match.date)+")", Instant.now()).persist();
            new OPAPackage(match.id + "-V", match.player2, false, false, match.result + " against " + match.enemyTeam + " on " + match.map + " ("+dateFormat.format(match.date)+")", Instant.now()).persist();
            new OPAPackage(match.id + "-V", match.player3, false, false, match.result + " against " + match.enemyTeam + " on " + match.map + " ("+dateFormat.format(match.date)+")", Instant.now()).persist();
            new OPAPackage(match.id + "-V", match.player4, false, false, match.result + " against " + match.enemyTeam + " on " + match.map + " ("+dateFormat.format(match.date)+")", Instant.now()).persist();
            new OPAPackage(match.id + "-V", match.player5, false, false, match.result + " against " + match.enemyTeam + " on " + match.map + " ("+dateFormat.format(match.date)+")", Instant.now()).persist();
        }
    }

    public List<Match> getAllMatches() {
        return Match.listAll();
    }
}
