package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DB_Handler {

    static final String JDBC_DRIVER = "org.h2.Driver";
    static String DB_URL = "jdbc:h2:C:\\Users\\SweetHome\\IdeaProjects\\Car Sharing\\Car Sharing\\task\\src\\carsharing\\db\\";
    static final String USERNAME = "sa";
    static final String PASSWORD = "";

    static void createTables() {

        try {
            Class.forName(JDBC_DRIVER);
            try (Connection connection = DriverManager.getConnection(DB_URL)) {

                connection.setAutoCommit(true);
                try (Statement statement = connection.createStatement()) {

                    String sql = "CREATE TABLE IF NOT EXISTS COMPANY (" +
                            "ID INT PRIMARY KEY AUTO_INCREMENT," +
                            "NAME VARCHAR NOT NULL UNIQUE" +
                            ");" +
                            "CREATE TABLE IF NOT EXISTS CAR (" +
                            "ID INT PRIMARY KEY AUTO_INCREMENT," +
                            "NAME VARCHAR NOT NULL UNIQUE," +
                            "COMPANY_ID INT NOT NULL," +
                            "CONSTRAINT fk_companyId FOREIGN KEY (COMPANY_ID)" +
                            "REFERENCES COMPANY(ID)" +
                            ");" +
                            "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
                            "ID INT PRIMARY KEY AUTO_INCREMENT," +
                            "NAME VARCHAR NOT NULL UNIQUE," +
                            "RENTED_CAR_ID INT DEFAULT NULL," +
                            "CONSTRAINT fk_rentId FOREIGN KEY (RENTED_CAR_ID)" +
                            "REFERENCES CAR(ID)" +
                            ");";

                    statement.executeUpdate(sql);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    static void createCompany(String companyName) {

        try {
            Class.forName(JDBC_DRIVER);
            try (Connection connection = DriverManager.getConnection(DB_URL)) {

                connection.setAutoCommit(true);
                try (Statement statement = connection.createStatement()) {

                    String sql = "INSERT INTO COMPANY(NAME) VALUES('" + companyName + "');";
                    statement.executeUpdate(sql);
                    System.out.println("The company was created!");
                    System.out.println();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    static ArrayList<Company> obtainCompanyList() {

        ArrayList<Company> companyList = new ArrayList<>();

        try {
            Class.forName(JDBC_DRIVER);
            try (Connection connection = DriverManager.getConnection(DB_URL)) {

                connection.setAutoCommit(true);
                try (Statement statement = connection.createStatement()) {

                    String countQuery = "SELECT COUNT(*) FROM COMPANY";
                    var res = statement.executeQuery(countQuery);
                    int count = -1;

                    while (res.next()) {
                        count = res.getInt(1);
                    }

                    if (count > 0) {

                        String listQuery = "SELECT * FROM COMPANY";
                        res = statement.executeQuery(listQuery);

                        while (res.next()) {
                            Company company = new Company(res.getInt(1), res.getString(2));
                            companyList.add(company);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return companyList;
    }

    static void createNewCar(String name, int companyId) {

        try {
            Class.forName(JDBC_DRIVER);
            try (Connection connection = DriverManager.getConnection(DB_URL)) {

                connection.setAutoCommit(true);
                try (Statement statement = connection.createStatement()) {

                    String sql = "INSERT INTO CAR(NAME, COMPANY_ID) VALUES('" + name + "', " + companyId + ");";
                    statement.executeUpdate(sql);
                    System.out.println("The car was added!");
                    System.out.println();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    static ArrayList<Car> obtainCarList(int companyId) {

        ArrayList<Car> carList = new ArrayList<>();

        try {
            Class.forName(JDBC_DRIVER);
            try (Connection connection = DriverManager.getConnection(DB_URL)) {

                connection.setAutoCommit(true);
                try (Statement statement = connection.createStatement()) {

                    String countQuery = "SELECT COUNT(*) FROM CAR";
                    var res = statement.executeQuery(countQuery);
                    int count = -1;

                    while (res.next()) {
                        count = res.getInt(1);
                    }

                    if (count > 0) {
                        String listQuery = "SELECT * " +
                                "FROM CAR " +
                                "WHERE COMPANY_ID = " + companyId + ";";
                        res = statement.executeQuery(listQuery);

                        while (res.next()) {
                            carList.add(new Car(res.getInt(1), res.getString(2), companyId));
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return carList;
    }

    static void createNewCustomer(String name) {

        try {
            Class.forName(JDBC_DRIVER);
            try (Connection connection = DriverManager.getConnection(DB_URL)) {

                connection.setAutoCommit(true);
                try (Statement statement = connection.createStatement()) {

                    String sql = "INSERT INTO CUSTOMER(NAME) VALUES('" + name + "');";
                    statement.executeUpdate(sql);
                    System.out.println("The customer was added!");
                    System.out.println();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    static ArrayList<CustomerAccount> obtainCustomerList() {

        ArrayList<CustomerAccount> customers = new ArrayList<>();

        try {
            Class.forName(JDBC_DRIVER);
            try (Connection connection = DriverManager.getConnection(DB_URL)) {

                connection.setAutoCommit(true);
                try (Statement statement = connection.createStatement()) {

                    String countQuery = "SELECT COUNT(*) FROM CUSTOMER;";
                    var res = statement.executeQuery(countQuery);
                    int count = -1;

                    while (res.next()) {
                        count = res.getInt(1);
                    }

                    if (count > 0) {

                        String listQuery = "SELECT * FROM CUSTOMER";
                        res = statement.executeQuery(listQuery);

                        while (res.next()) {

                            CustomerAccount customer = new CustomerAccount(res.getInt(1),
                                    res.getString(2),
                                    -1);
                            customers.add(customer);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    static void getCustomersRentedCar(CustomerAccount customerAccount) {

        try {
            Class.forName(JDBC_DRIVER);
            try (Connection connection = DriverManager.getConnection(DB_URL)) {

                connection.setAutoCommit(true);
                try (Statement statement = connection.createStatement()) {

                    String query = "SELECT RENTED_CAR_ID FROM CUSTOMER WHERE ID=" + customerAccount.id + ";";
                    ResultSet res = statement.executeQuery(query);

                    String carName = "NULL";
                    String companyName = "NULL";
                    int companyId = 0;
                    int rentedCarId = 0;

                    while (res.next()) {

                        if (res.getObject(1) == null) {
                            System.out.println("You didn't rent a car!\n");
                            return;
                        } else {
                            rentedCarId = res.getInt(1);
                        }
                    }

                    query = "SELECT * FROM CAR WHERE ID=" + rentedCarId;
                    ResultSet car = statement.executeQuery(query);

                    while (car.next()) {

                        carName = car.getString("NAME");
                        companyId = car.getInt("COMPANY_ID");
                    }

                    query = "SELECT NAME FROM COMPANY WHERE ID=" + companyId;
                    ResultSet company = statement.executeQuery(query);

                    while (company.next()) {
                        companyName = company.getString("NAME");
                    }

                    System.out.println("Your rented car:");
                    System.out.println(carName);
                    System.out.println("Company:");
                    System.out.println(companyName);
                    System.out.println();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    static void returnCar(CustomerAccount customerAccount) {

        try {
            Class.forName(JDBC_DRIVER);
            try (Connection connection = DriverManager.getConnection(DB_URL)) {

                connection.setAutoCommit(true);
                try (Statement statement = connection.createStatement()) {

                    String sql = "SELECT * FROM CUSTOMER WHERE ID=" + customerAccount.id + ";";
                    ResultSet customer = statement.executeQuery(sql);

                    while (customer.next()) {

                        if (customer.getObject(3) == null) {
                            System.out.println("You didn't rent a car!\n");
                            return;
                        }
                    }

                    sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID = " + customerAccount.id;
                    statement.executeUpdate(sql);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    static void rentACar(CustomerAccount customerAccount) {

        try {
            Class.forName(JDBC_DRIVER);
            try (Connection connection = DriverManager.getConnection(DB_URL)) {

                connection.setAutoCommit(true);
                try (Statement statement = connection.createStatement()) {

                    String sql = "SELECT * FROM CUSTOMER WHERE ID = " + customerAccount.id + ";";
                    ResultSet customer = statement.executeQuery(sql);

                    int carId = -1;
                    String carName = "NULL";

                    while (customer.next()) {

                        if (customer.getObject(3) != null) {
                            System.out.println("You've already rented a car!\n");
                            return;
                        } else {

                            Scanner scanner = new Scanner(System.in);

                            ArrayList<Company> companies = DB_Handler.obtainCompanyList();

                            if (companies.size() == 0) {
                                System.out.println("The company list is empty!");
                                System.out.println();
                                return;
                            } else {
                                Manager.printCompanies(companies);

                                int companySelection = Integer.parseInt(scanner.nextLine());
                                System.out.println();

                                if (companySelection == 0) {
                                    return;
                                }

                                int companyId = companies.get(companySelection - 1).id;
                                String companyName = companies.get(companySelection - 1).companyName;

                                ArrayList<Car> carList = DB_Handler.obtainCarList(companyId);

                                if (carList.size() == 0) {
                                    System.out.println("No available cars in the '" + companyName + "' company");
                                    System.out.println();
                                    return;
                                } else {

                                    Company.printCars(carList);
                                    System.out.println("0. Back");

                                    int carSelection = Integer.parseInt(scanner.nextLine());
                                    System.out.println();

                                    if (carSelection == 0) {
                                        return;
                                    }
                                    carId = carList.get(carSelection - 1).id;
                                    carName = carList.get(carSelection - 1).name;
                                }
                            }
                        }
                    }

                    sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = " +
                            carId + " WHERE ID = " + customerAccount.id;
                    statement.executeUpdate(sql);
                    System.out.println("You rented '" + carName + "'");
                    System.out.println();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
