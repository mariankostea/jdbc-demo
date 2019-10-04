package internship.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.NoArgsConstructor;

import java.util.Map;
import javax.sql.DataSource;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class DatasourceConfiguration {

    private static DataSource dataSource;

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";

    private static final String USER = "postgres";

    private static final String PASSWORD = "*********";

    static {
        Map<String, String> configurationProperties = Map.of("url", JDBC_URL,
            "username", USER,
            "password", PASSWORD);

        dataSource = configureDataSource(configurationProperties);
    }

    private static DataSource configureDataSource(Map<String, String> configurationProperties) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(JDBC_URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);
        dataSource.setMinimumIdle(1);
        dataSource.setMaximumPoolSize(1);
        return dataSource;

    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
