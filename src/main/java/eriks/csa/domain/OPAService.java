package eriks.csa.domain;

import eriks.csa.api.client.DiscordClient;
import eriks.csa.api.dto.DiscordMessageDtoOut;
import eriks.csa.domain.obj.Match;
import eriks.csa.domain.obj.Member;
import eriks.csa.domain.obj.OPAPackage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.text.DateFormat;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class OPAService {

    @Inject
    @RestClient
    DiscordClient discordClient;

    @Transactional
    public void saveAngels(List<String> newAngels, List<String> oldAngels) {
        getAllMembers().forEach(member -> {
            if (newAngels.contains(member.faceitNick)) {
                member.hasAngel = true;
            } else if (oldAngels.contains(member.faceitNick)) {
                member.hasAngel = false;
            }
        });
        sendAngelMessageToDiscord();
    }

    public void sendAngelMessageToDiscord() {
        String listOfPlayers = getAllMembers().stream()
                                              .filter(member -> member.hasAngel)
                                              .map(member -> member.faceitNick)
                                              .collect(Collectors.joining(", "));
        DiscordMessageDtoOut message = new DiscordMessageDtoOut("Anjos atuais: "+listOfPlayers);
        discordClient.sendMessage(message);
    }

    @Transactional
    public void saveMember(Member member) {
        Member.findByIdOptional(member.steamId).ifPresentOrElse(
                entity -> {
                    Member existingMember = (Member) entity;
                    existingMember.faceitNick = member.faceitNick;
                    existingMember.isSecondaryAccount = member.isSecondaryAccount;
                    existingMember.joinDate = member.joinDate;
                    existingMember.country = member.country;
                    existingMember.hasAngel = member.hasAngel;
                    existingMember.primaryAccountFaceitNick = member.primaryAccountFaceitNick;
                },
                () -> member.persist()
        );
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
