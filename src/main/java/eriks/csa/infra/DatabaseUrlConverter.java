package eriks.csa.infra;

public class DatabaseUrlConverter {
    public static String convertHerokuDatabaseUrl(String herokuUrl) {
        return herokuUrl.replace("postgres://", "jdbc:postgresql://");
    }
}