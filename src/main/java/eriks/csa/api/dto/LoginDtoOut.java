package eriks.csa.api.dto;

import eriks.csa.domain.obj.Login;

public class LoginDtoOut {
    public final String userId;
    public final String token;

    public LoginDtoOut(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public static LoginDtoOut fromDomain(Login login) {
        return new LoginDtoOut(login.userId, login.token);
    }
}
