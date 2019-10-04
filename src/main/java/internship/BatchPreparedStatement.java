package internship;

import internship.config.DatasourceConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BatchPreparedStatement {

    private static final int BATCH_SIZE = 5;

    public static void main(String[] args) {
        insert();
    }

    private static void insert() {
        final var dataSource = DatasourceConfiguration.getDataSource();

        final var query = "INSERT INTO student (id, first_name, last_name, group_name) VALUES (?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            for (int i = 1; i <= 7; i++) {
                statement.setInt(1, i);
                statement.setString(2, "John");
                statement.setString(3, "Snow");
                statement.setString(4, "TI-182");
                statement.addBatch();

                if (i % BATCH_SIZE == 0) {
                    int[] result = statement.executeBatch();
                    statement.clearBatch();
                    System.out.println(String.format("Inserted %d student", result.length));
                }
            }

            int[] result = statement.executeBatch();

            System.out.println(String.format("Inserted %d student", result.length));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
