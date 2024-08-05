package eriks.csa;

import eriks.csa.infra.DatabaseUrlConverter;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Main {
    public static void main(String[] args) {
//        String databaseUrl = System.getenv("DATABASE_URL");
//        if (databaseUrl != null) {
//            String jdbcUrl = DatabaseUrlConverter.convertHerokuDatabaseUrl(databaseUrl);
//            System.setProperty("JDBC_DATABASE_URL", jdbcUrl);
//        }
        Quarkus.run(args);
    }
}