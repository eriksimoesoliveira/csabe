package eriks.csa.api.dto;

import java.util.List;

public class AngelDtoIn {
    public List<String> oldAngels;
    public List<String> newAngels;

    public AngelDtoIn() {
    }

    public AngelDtoIn(List<String> newAngels, List<String> oldAngels) {
        this.newAngels = newAngels;
        this.oldAngels = oldAngels;
    }
}
