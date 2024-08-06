package eriks.csa.api.dto;

public class SignUpDtoIn {
    public String userName;
    public String password;

    public SignUpDtoIn(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public SignUpDtoIn() {

    }
}
