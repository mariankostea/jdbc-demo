package internship;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static internship.config.DatasourceConfiguration.getDataSource;

public class Transaction {

    public static void main(String[] args) throws SQLException {
        //nonTransactional();
        transactional();
    }

    private static void nonTransactional() {
        final var dataSource = getDataSource();

        try (Connection connection = dataSource.getConnection()) {
            insertStudent(connection);
            simulateException();
            insertAddress(connection);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void transactional() throws SQLException {
        Connection connection = getDataSource().getConnection();
        try {
            connection.setAutoCommit(false);

            insertStudent(connection);
            //simulateException();
            insertAddress(connection);

            connection.commit();
        } catch (Exception e) {
            System.out.println("Exception caught " + e.getMessage());
        }
    }

    private static void insertStudent(Connection connection) throws SQLException {
        String query = "INSERT INTO student (id, first_name, last_name, group_name) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, 2);
            statement.setString(2, "John");
            statement.setString(3, "Snow");
            statement.setString(4, "TI-182");

            int result = statement.executeUpdate();
            System.out.println(String.format("Inserted %d student", result));
        }
    }

    private static void insertAddress(Connection connection) throws SQLException {
        String query = "INSERT INTO address_book (student_id, country, city, email, phone_number) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, 1);
            statement.setString(2, "Moldova");
            statement.setString(3, "Chisinau");
            statement.setString(4, "john.snow@gmail.com");
            statement.setString(5, "(+373 79 000 000)");

            int result = statement.executeUpdate();
            System.out.println(String.format("Inserted %d address", result));
        }
    }

    private static void simulateException() {
        throw new RuntimeException("An error occurred");
    }
}
