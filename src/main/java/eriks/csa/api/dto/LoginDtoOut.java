package eriks.csa.api.dto;

import eriks.csa.domain.Login;

public class LoginDtoOut {
    public final String userId;
    public final String userName;
    public final long timestamp;
    public final String token;

    public LoginDtoOut(String userId, String userName, long timestamp, String token) {
        this.userId = userId;
        this.userName = userName;
        this.timestamp = timestamp;
        this.token = token;
    }

    public static LoginDtoOut fromDomain(Login login) {
        return new LoginDtoOut(login.userId, login.userName, login.timestamp, login.token);
    }
}
