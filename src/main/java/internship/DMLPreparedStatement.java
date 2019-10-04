package internship;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static internship.config.DatasourceConfiguration.getDataSource;

public class DMLPreparedStatement {


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

        final var query = "INSERT INTO student (id, first_name, last_name, group_name) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, 1);
            statement.setString(2, "John");
            statement.setString(3, "Snow");
            statement.setString(4, "TI-182");

            int result = statement.executeUpdate();

            System.out.println(String.format("Inserted %d student", result));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void query() {
        final var dataSource = getDataSource();

        String query = "SELECT id, first_name, last_name, group_name FROM student WHERE id = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, 1);

            for (ResultSet resultSet = statement.executeQuery(); resultSet.next(); ) {
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

        final var query = "UPDATE student set group_name = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, "TI-181");
            statement.setInt(2, 1);

            int result = statement.executeUpdate();

            System.out.println(String.format("Updated %d student", result));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void delete() {
        final var dataSource = getDataSource();

        final var query = "DELETE FROM student WHERE id = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, 1);

            int result = statement.executeUpdate();

            System.out.println(String.format("Deleted %d student", result));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
