package eriks.csa.api.dto;

public class LoginDtoIn {
    public String userId;
    public String password;

    public LoginDtoIn() {
    }

    public LoginDtoIn(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
