package internship;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static internship.config.DatasourceConfiguration.getDataSource;

public class DMLStatement {

    public static void main(String[] args) {
        insert();
        query();

        update();
        query();

        delete();
        query();
    }

    private static void insert() {
        final var dataSource = getDataSource();

        String query = "INSERT INTO student (id, first_name, last_name, group_name) VALUES (1, 'John', 'Snow', 'TI-182')";
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {

            int result = statement.executeUpdate(query);

            System.out.println(String.format("Inserted %d student", result));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void query() {
        final var dataSource = getDataSource();

        String query = "SELECT id, first_name, last_name, group_name FROM student WHERE id = 1";
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {

            for (ResultSet resultSet = statement.executeQuery(query); resultSet.next(); ) {
                String student = String.format("Read student: id=%d firstName=%s lastName=%s groupName=%s",
                    resultSet.getInt("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("group_name"));

                System.out.println(student);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void update() {
        final var dataSource = getDataSource();

        String query = "UPDATE student set group_name = 'TI-181' WHERE id = 1";
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {

            int result = statement.executeUpdate(query);

            System.out.println(String.format("Updated %d student", result));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void delete() {
        final var dataSource = getDataSource();

        String query = "DELETE FROM student WHERE id = 1";
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {

            int result = statement.executeUpdate(query);

            System.out.println(String.format("Deleted %d student", result));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
