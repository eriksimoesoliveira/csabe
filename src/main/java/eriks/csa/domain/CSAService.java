package eriks.csa.domain;

import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CSAService {

    public List<Login> getAllLogins() {
        return Login.listAll();
    }

    @Transactional
    public Login login(String userId, String userName, String password) {
        if (!password.equals(System.getenv("CSA_STATIC_PASSWORD"))) {
            throw new UnauthorizedException("Invalid password");
        }
        long now = Instant.now().toEpochMilli();
        String token = UUID.randomUUID().toString();
        long tokenExpiration = Instant.now().plus( 30, ChronoUnit.MINUTES).toEpochMilli();
        Login newLogin = new Login(userId, userName, password, now, token, tokenExpiration);
        Login.save(newLogin);
        new LoginAttempt(userId, userName, now).persist();
        return newLogin;
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

    public List<PackOpen> getAllPackOpen() {
        return PackOpen.listAll();
    }

    @Transactional
    public void savePackOpen(PackOpen packOpen) {
        packOpen.persist();
    }

    public List<AlbumValue> getAllAlbumValues() {
        return AlbumValue.listAll();
    }

    @Transactional
    public void updateAlbumValue(AlbumValue albumValue) {
        AlbumValue found = AlbumValue.find("userId", albumValue.userId).firstResult();
        if (found != null) {
            found.value = albumValue.value;
            found.persist();
        } else {
            albumValue.persist();
        }
    }

    public String buildFriendlyMetrics() {
        String ret = "";

        ret += "ALBUM VALUES\n\n";
        List<AlbumValue> albumValues = AlbumValue.listAll();
        for (AlbumValue albumValue : albumValues) {
            ret += albumValue.userId + "\t" + albumValue.userName + "\t" + albumValue.value + "\t" + convertMillisToDateTime(albumValue.lastUpdate) + "\n";
        }
        ret += "\nPACKS\n\n";
        List<PackOpen> packageOpens = PackOpen.listAll();
        for (PackOpen pack : packageOpens) {
            ret += pack.userId + "\t" + pack.userName + "\t" + pack.packId + "\t" + pack.packOrigin + "\t" + pack.packType + "\t" + convertMillisToDateTime(pack.timestamp) + "\n";
        }
        ret += "\nLOGINS\n\n";
        List<LoginAttempt> logins = LoginAttempt.listAll();
        for (LoginAttempt login : logins) {
            ret += login.userId + "\t" + login.userName + "\t" + convertMillisToDateTime(login.timestamp) + "\n";
        }

        return ret;
    }

    public static String convertMillisToDateTime(long millis) {
        Instant instant = Instant.ofEpochMilli(millis);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
}
