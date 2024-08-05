package eriks.csa.api.dto;

public class LoginDtoIn {
    public String userId;
    public String userName;
    public String password;

    public LoginDtoIn() {
    }

    public LoginDtoIn(String userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }
}
