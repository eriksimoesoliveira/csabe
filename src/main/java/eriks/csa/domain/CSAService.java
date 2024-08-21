package eriks.csa.domain;

import eriks.csa.domain.obj.*;
import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CSAService {

    @Transactional
    public Login login(String userId, String password) {
        GameUser user = GameUser.findById(userId);
        if (!password.equals(user.password)) {
            throw new UnauthorizedException("Invalid password");
        }
        long now = Instant.now().toEpochMilli();
        String token = UUID.randomUUID().toString();
        long tokenExpiration = Instant.now().plus( 30, ChronoUnit.MINUTES).toEpochMilli();
        Login login = new Login(userId, now, token, tokenExpiration);
        Login.save(login);
        new LoginAttempt(userId, user.userName, now).persist();
        return login;
    }

    @Transactional
    public Login signUp(String userName, String password) {
        long now = Instant.now().toEpochMilli();
        String token = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        long tokenExpiration = Instant.now().plus( 30, ChronoUnit.MINUTES).toEpochMilli();
        new GameUser(userId, userName, password, now).persist();
        Login login = new Login(userId, now, token, tokenExpiration);
        Login.save(login);
        new LoginAttempt(userId, userName, now).persist();
        return login;
    }

    @Transactional
    public boolean validateToken(String token) {
        Login login = Login.findByField("token", token);
        if (!token.equals(login.token)) {
            throw new UnauthorizedException("Token Invalid");
        }
        if (login.tokenExpiration < Instant.now().toEpochMilli()) {
            throw new UnauthorizedException("Token Expired");
        }
        refreshToken(login);
        return true;
    }

    @Transactional
    public void refreshToken(Login login) {
        login.tokenExpiration = Instant.now().plus( 30, ChronoUnit.MINUTES).toEpochMilli();
        login.persist();
    }

    @Transactional
    public void savePackOpen(PackOpen packOpen) {
        packOpen.persist();
    }

    @Transactional
    public void updateAlbumValue(AlbumValue albumValue) {
        AlbumValue found = AlbumValue.find("userId", albumValue.userId).firstResult();
        if (found != null) {
            found.value = albumValue.value;
            found.lastUpdate = Instant.now().toEpochMilli();
            found.persist();
        } else {
            albumValue.persist();
        }
    }

    public String buildFriendlyMetrics() {
        String ret = "";

        ret += "ALBUM VALUES\n\n";
        List<AlbumValue> albumValues = AlbumValue.list("order by value desc");
        for (AlbumValue albumValue : albumValues) {
            ret += albumValue.userId + "\t" + albumValue.userName + "\t" + NumberFormat.getCurrencyInstance().format(albumValue.value) + "\t" + convertMillisToDateTime(albumValue.lastUpdate) + "\n";
        }
        ret += "\nPACKS\n\n";
        List<PackOpen> packageOpens = PackOpen.list("order by timestamp");
        for (PackOpen pack : packageOpens) {
            ret += pack.userId + "\t" + pack.userName + "\t" + pack.packOrigin + "\t" + pack.packType + "\t" + convertMillisToDateTime(pack.timestamp) + "\t" + pack.packId + "\n";
        }
        ret += "\nLOGINS\n\n";
        List<LoginAttempt> logins = LoginAttempt.list("order by timestamp");
        for (LoginAttempt login : logins) {
            ret += login.userId + "\t" + login.userName + "\t" + convertMillisToDateTime(login.timestamp) + "\n";
        }

        return ret;
    }

    public static String convertMillisToDateTime(long millis) {
        Instant instant = Instant.ofEpochMilli(millis);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.of("America/Edmonton"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    @Transactional
    public List<OPAPackage> getPackagesByUser(String userName) {
        List<OPAPackage> unclaimedByOwner = OPAPackage.findUnclaimedByOwner(userName);
        unclaimedByOwner.forEach(pack -> {
            pack.isClaimed = true;
            pack.persist();
        });
        return unclaimedByOwner;
    }

    public List<OPAPackage> getAllPackages() {
        return OPAPackage.listAll();
    }

    @Transactional
    public void userCleanUp(String userId) {
        AlbumValue.delete("userId", userId);
        PackOpen.delete("userId", userId);
        LoginAttempt.delete("userId", userId);
    }
}
