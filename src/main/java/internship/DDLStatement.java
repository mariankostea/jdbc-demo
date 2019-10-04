package internship;

import internship.config.DatasourceConfiguration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class DDLStatement {

    public static void main(String[] args) {
        final var datasource = DatasourceConfiguration.getDataSource();

        try (Connection connection = datasource.getConnection(); Statement statement = connection.createStatement()) {
            final var student = getStudentDDL();
            final var addressBook = getAddressBookDDL();

            //var query = "DROP TABLE student";
            //var query1 = "DROP TABLE address_book";

            var resultCount = statement.executeUpdate(student);
            System.out.println(resultCount);

            resultCount = statement.executeUpdate(addressBook);
            System.out.println(resultCount);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static String getAddressBookDDL() {
        return "CREATE TABLE address_book (" +
                " student_id INT NOT NULL PRIMARY KEY," +
                " country VARCHAR(50) NOT NULL," +
                " city VARCHAR(50) NOT NULL," +
                " email VARCHAR(20) NOT NULL," +
                " phone_number VARCHAR(50) NOT NULL)";
    }

    private static String getStudentDDL() {
        return "CREATE TABLE student (" +
                " id INT NOT NULL PRIMARY KEY," +
                " first_name VARCHAR(50) NOT NULL," +
                " last_name VARCHAR(50) NOT NULL," +
                " group_name VARCHAR(20) NOT NULL)";
    }
}
