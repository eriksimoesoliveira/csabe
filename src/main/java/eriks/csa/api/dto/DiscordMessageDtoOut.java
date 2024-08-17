package eriks.csa.api.dto;

public class DiscordMessageDtoOut {
    private String content;

    public DiscordMessageDtoOut() {
    }

    public DiscordMessageDtoOut(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
