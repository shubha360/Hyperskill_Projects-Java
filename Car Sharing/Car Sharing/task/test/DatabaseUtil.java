import org.hyperskill.hstest.exception.outcomes.WrongAnswer;

import java.sql.*;
import java.util.HashMap;

public class DatabaseUtil {

    private Connection connection = null;
    private static final String databaseFilePath = "./src/carsharing/db/carsharing";

    public Connection getConnection() {
        if (connection != null) {
            return connection;
        }
        try {
            connection = DriverManager.getConnection("jdbc:h2:" + databaseFilePath);
        } catch (SQLException ignored) {
            throw new WrongAnswer("Can't connect to the database.");
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignored) {
                System.out.println(ignored.getErrorCode());
                throw new WrongAnswer("Can't close connection to the database.");
            }
            connection = null;
        }
    }

    public ResultSet executeQuery(String query) {
        try {
            return getConnection().createStatement().executeQuery(query);
        } catch (SQLException exception) {
            throw new WrongAnswer("Can't execute query to the database.\n" +
                    "SQL Message:\n" + exception.getMessage());
        }
    }

    public boolean ifTableExist(String tableName) {
        try {
            tableName = tableName.toUpperCase();
            ResultSet resultSet = executeQuery("SHOW TABLES");
            while (resultSet.next()) {
                if (resultSet.getString("TABLE_NAME").equals(tableName)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException exception) {
            throw new WrongAnswer("Can't execute query to the database.\n" +
                    "SQL Message:\n" + exception.getMessage());
        }
    }

    public void ifColumnsExist(String tableName, String[][] columns) {
        try {
            ResultSet resultSet = getConnection()
                    .createStatement()
                    .executeQuery("SHOW COLUMNS FROM " + tableName.toUpperCase());

            HashMap<String, String> correctColumns = new HashMap<>();
            for (String[] column : columns) {
                correctColumns.put(column[0], column[1]);
            }

            while (resultSet.next()) {
                String columnName = resultSet.getString("FIELD");
                if (correctColumns.containsKey(columnName)) {
                    if (!resultSet.getString("TYPE").contains(correctColumns.get(columnName))) {
                        throw new WrongAnswer("In the 'COMPANY' table '" + columnName
                                + "' column should be of " + correctColumns.get(columnName) + " type.");
                    }
                    correctColumns.remove(columnName);
                }
            }
            if (!correctColumns.isEmpty()) {
                throw new WrongAnswer("Can't find in 'COMPANY' table the following columns: " + correctColumns.toString());
            }
        } catch (SQLException exception) {
            throw new WrongAnswer("Can't execute query to the database.\n" +
                    "SQL Message:\n" + exception.getMessage());
        }
    }

    public void clearCompanyTable() {
        try {
            getConnection().createStatement().execute("DELETE FROM COMPANY");
        } catch (SQLException ignored) {
            throw new WrongAnswer("Can't delete rows from the COMPANY table.");
        }
    }

    public void clearCarTable() {
        try {
            getConnection().createStatement().execute("DELETE FROM CAR");
        } catch (SQLException ignored) {
            throw new WrongAnswer("Can't delete rows from the CAR table.");
        }
    }

    public void clearCustomerTable() {
        try {
            getConnection().createStatement().execute("DELETE FROM CUSTOMER");
        } catch (SQLException ignored) {
            throw new WrongAnswer("Can't delete rows from the CUSTOMER table.");
        }
    }

    public void checkCompany(String name) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM COMPANY WHERE NAME = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new WrongAnswer("Can't find '" + name + "' company in the 'COMPANY' table\n" +
                        "Make sure you don't clear the database after starting the program");
            }
        } catch (SQLException ignored) {
            throw new WrongAnswer("Can't select data from the 'COMPANY' table!");
        }
    }

    public void checkCar(String companyName, String carName) {
        try {
            PreparedStatement companyStatement = getConnection().prepareStatement("SELECT * FROM COMPANY WHERE NAME = ?");
            companyStatement.setString(1, companyName);

            PreparedStatement carStatement = getConnection().prepareStatement("SELECT * FROM CAR WHERE NAME = ?");
            carStatement.setString(1, carName);

            ResultSet resultSet = companyStatement.executeQuery();
            if (!resultSet.next()) {
                throw new WrongAnswer("Can't find '" + companyName + "' company in the 'COMPANY' table.");
            }

            int id = resultSet.getInt("ID");

            resultSet = carStatement.executeQuery();
            if (!resultSet.next()) {
                throw new WrongAnswer("Can't find '" + carName + "' car in the 'CAR' table.\n" +
                        "Make sure you don't clear the database after starting the program");
            }
            if (resultSet.getInt("COMPANY_ID") != id) {
                throw new WrongAnswer("'COMPANY_ID' of the '" + carName + "' car is wrong. It should reference to the '" + companyName + "' company ID.");
            }
        } catch (SQLException exception) {
            throw new WrongAnswer("Can't execute query to the database.\n" +
                    "SQL Message:\n" + exception.getMessage());
        }
    }

    public void checkCustomer(String name, String carName) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM CUSTOMER WHERE NAME = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new WrongAnswer("Can't find '" + name + "' customer in the 'CUSTOMER' table.\n" +
                        "Make sure you don't clear the database after starting the program");
            }

            if (carName == null) {
                if (resultSet.getString("RENTED_CAR_ID") != null) {
                    throw new WrongAnswer("'RENTED_CAR_ID' of the '" + name + "' customer is wrong. It should be NULL");
                }
                return;
            }

            PreparedStatement carStatement = getConnection().prepareStatement("SELECT * FROM CAR WHERE NAME = ?");
            carStatement.setString(1, carName);
            ResultSet carResultSet = carStatement.executeQuery();

            if (!carResultSet.next()) {
                throw new WrongAnswer("Can't find '" + carName + "' car in the 'CAR' table.\n" +
                        "Make sure you don't clear the database after starting the program");
            }

            int carId = carResultSet.getInt("ID");

            if (resultSet.getInt("RENTED_CAR_ID") != carId) {
                throw new WrongAnswer("'RENTED_CAR_ID' of the '" + name + "' customer is wrong. It should reference to the '" + carName + "' car ID.");
            }
        } catch (SQLException ignored) {
            throw new WrongAnswer("Can't select data from the 'COMPANY' table!");
        }
    }


    public void checkCompanyColumnProperties() {
        try {
            ResultSet resultSet = getConnection().createStatement().executeQuery("SELECT * FROM INFORMATION_SCHEMA.CONSTRAINTS" +
                    " WHERE COLUMN_LIST = 'ID' AND TABLE_NAME = 'COMPANY' AND CONSTRAINT_TYPE = 'PRIMARY KEY'");

            if (!resultSet.next()) {
                throw new WrongAnswer("Looks like 'ID' column in 'COMPANY' table doesn't have PRIMARY KEY constraint.");
            }

            resultSet = getConnection().createStatement().executeQuery("SELECT * FROM INFORMATION_SCHEMA.CONSTRAINTS" +
                    " WHERE COLUMN_LIST = 'NAME' AND TABLE_NAME = 'COMPANY' AND CONSTRAINT_TYPE = 'UNIQUE'");

            if (!resultSet.next()) {
                throw new WrongAnswer("Looks like 'NAME' column in 'COMPANY' table doesn't have UNIQUE constraint.");
            }

            resultSet = getConnection().createStatement().executeQuery("SELECT  * FROM INFORMATION_SCHEMA.COLUMNS" +
                    " WHERE COLUMN_NAME = 'NAME' AND TABLE_NAME = 'COMPANY' AND IS_NULLABLE = 'NO'");

            if (!resultSet.next()) {
                throw new WrongAnswer("Looks like 'NAME' column in 'COMPANY' table doesn't have NOT NULL constraint.");
            }
        } catch (SQLException exception) {
            throw new WrongAnswer("Can't execute query to the database.\n" +
                    "SQL Message:\n" + exception.getMessage());
        }
    }

    public void checkCarColumnProperties() {
        try {
            ResultSet resultSet = getConnection().createStatement().executeQuery("SELECT * FROM INFORMATION_SCHEMA.CONSTRAINTS" +
                    " WHERE COLUMN_LIST = 'ID' AND TABLE_NAME = 'CAR' AND CONSTRAINT_TYPE = 'PRIMARY KEY'");

            if (!resultSet.next()) {
                throw new WrongAnswer("Looks like 'ID' column in 'CAR' table doesn't have PRIMARY KEY constraint.");
            }

            resultSet = getConnection().createStatement().executeQuery("SELECT * FROM INFORMATION_SCHEMA.COLUMNS" +
                    " WHERE COLUMN_NAME = 'NAME' AND TABLE_NAME = 'CAR' AND IS_NULLABLE = 'NO'");

            if (!resultSet.next()) {
                throw new WrongAnswer("Looks like 'NAME' column in 'CAR' table doesn't have NOT NULL constraint.");
            }

            resultSet = getConnection().createStatement().executeQuery("SELECT * FROM INFORMATION_SCHEMA.COLUMNS" +
                    " WHERE COLUMN_NAME = 'COMPANY_ID' AND TABLE_NAME = 'CAR' AND IS_NULLABLE = 'NO'");

            if (!resultSet.next()) {
                throw new WrongAnswer("Looks like 'COMPANY_ID' column in 'CAR' table doesn't have NOT NULL constraint.");
            }

            resultSet = getConnection().createStatement().executeQuery("SELECT  * FROM INFORMATION_SCHEMA.CONSTRAINTS" +
                    " WHERE COLUMN_LIST = 'COMPANY_ID' AND TABLE_NAME = 'CAR' AND CONSTRAINT_TYPE = 'REFERENTIAL'");

            if (!resultSet.next()) {
                throw new WrongAnswer("Looks like 'COMPANY_ID' column in 'CAR' table is not FOREIGN KEY. It should refer to 'ID' column of the 'COMPANY' table.");
            }

            if (!resultSet.getString("SQL").replace("\"", "").contains("COMPANY(ID)")) {
                throw new WrongAnswer("Looks like 'COMPANY_ID' column in 'CAR' table doesn't refer to 'ID' column of the 'COMPANY' table.");
            }
        } catch (SQLException exception) {
            throw new WrongAnswer("Can't execute query to the database.\n" +
                    "SQL Message:\n" + exception.getMessage());
        }
    }

    public void checkCustomerColumnProperties() {
        try {
            ResultSet resultSet = getConnection().createStatement().executeQuery("SELECT * FROM INFORMATION_SCHEMA.CONSTRAINTS" +
                    " WHERE COLUMN_LIST = 'ID' AND TABLE_NAME = 'CUSTOMER' AND CONSTRAINT_TYPE = 'PRIMARY KEY'");

            if (!resultSet.next()) {
                throw new WrongAnswer("Looks like 'ID' column in 'CUSTOMER' table doesn't have PRIMARY KEY constraint.");
            }

            resultSet = getConnection().createStatement().executeQuery("SELECT * FROM INFORMATION_SCHEMA.COLUMNS" +
                    " WHERE COLUMN_NAME = 'NAME' AND TABLE_NAME = 'CUSTOMER' AND IS_NULLABLE = 'NO'");

            if (!resultSet.next()) {
                throw new WrongAnswer("Looks like 'NAME' column in 'CUSTOMER' table doesn't have NOT NULL constraint.");
            }

            resultSet = getConnection().createStatement().executeQuery("SELECT * FROM INFORMATION_SCHEMA.COLUMNS" +
                    " WHERE COLUMN_NAME = 'RENTED_CAR_ID' AND TABLE_NAME = 'CUSTOMER' AND IS_NULLABLE = 'YES'");

            if (!resultSet.next()) {
                throw new WrongAnswer("Looks like 'RENTED_CAR_ID' column in 'CUSTOMER' table has NOT NULL constraint, but it shouldn't");
            }

            resultSet = getConnection().createStatement().executeQuery("SELECT  * FROM INFORMATION_SCHEMA.CONSTRAINTS" +
                    " WHERE COLUMN_LIST = 'RENTED_CAR_ID' AND TABLE_NAME = 'CUSTOMER' AND CONSTRAINT_TYPE = 'REFERENTIAL'");

            if (!resultSet.next()) {
                throw new WrongAnswer("Looks like 'RENTED_CAR_ID' column in 'CUSTOMER' table is not FOREIGN KEY. It should refer to 'ID' column of the 'CAR' table.");
            }

            if (!resultSet.getString("SQL").replace("\"", "").contains("CAR(ID)")) {
                throw new WrongAnswer("Looks like 'RENTED_CAR_ID' column in 'CUSTOMER' table doesn't refer to 'ID' column of the 'CAR' table.");
            }
        } catch (SQLException exception) {
            throw new WrongAnswer("Can't execute query to the database.\n" +
                    "SQL Message:\n" + exception.getMessage());
        }
    }
}
