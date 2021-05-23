import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.BeforeClass;

import java.io.File;

public class CarSharingTest extends StageTest<Void> {

    private static final String databaseFileName = "src/carsharing/db/carsharing.mv.db";
    private static DatabaseUtil db = new DatabaseUtil();

    @BeforeClass
    public static void deleteDatabaseFile() {
        File file = new File(databaseFileName);

        if (!file.exists()) {
            return;
        }

        if (!file.delete()) {
            throw new WrongAnswer("Can't delete database file before starting your program.\n" +
                "Make sure you close all the connections with the database file!");
        }
    }

    @DynamicTest(order = -1)
    public CheckResult test2_ifDatabaseExist() {

        TestedProgram program = new TestedProgram();
        program.start("-databaseFileName", "carsharing");
        program.execute("0");

        if (!program.isFinished()) {
            return CheckResult.wrong("After choosing 'Exit' item your program should stop.");
        }

        File file = new File(databaseFileName);

        if (!file.exists()) {
            return CheckResult.wrong("Can't find a database file. It should be named 'carsharing.mv.db'" +
                " and located in /carsharing/db/ folder.\n" +
                "The file should be created right after starting the program!");
        }

        return correct();
    }

    @DynamicTest
    public CheckResult test1_testMenu() {
        TestedProgram program = new TestedProgram();
        String output = program.start("-databaseFileName", "carsharing");

        if (!output.contains("1. Log in as a manager")) {
            return CheckResult.wrong("Start menu should contain \"1. Log in as a manager\"");
        }

        if (!output.contains("0. Exit")) {
            return CheckResult.wrong("Start menu should contain \"0. Exit\"");
        }

        output = program.execute("1");

        if (!output.contains("1. Company list")) {
            return CheckResult.wrong("After choosing 'Log in as a manager' item you should print menu that contains '1. Company list' item");
        }

        if (!output.contains("2. Create a company")) {
            return CheckResult.wrong("After choosing 'Log in as a manager' item you should print menu that contains '2. Create a company' item");
        }

        if (!output.contains("0. Back")) {
            return CheckResult.wrong("After choosing 'Log in as a manager' item you should print menu that contains '0. Back' item");
        }

        output = program.execute("0");

        if (!output.contains("1. Log in as a manager")) {
            return CheckResult.wrong("After choosing '0. Back' item you should print previous menu and it should contain \"1. Log in as a manager\"");
        }

        if (!output.contains("0. Exit")) {
            return CheckResult.wrong("After choosing '0. Back' item you should print previous menu and it should contain \"0. Exit\"");
        }

        return CheckResult.correct();
    }

    @DynamicTest
    public CheckResult test3_checkDatabaseConnection() {
        db.getConnection();
        return correct();
    }

    @DynamicTest
    public CheckResult test4_checkIfTableExists() {
        if (!db.ifTableExist("company")) {
            return wrong("Can't find table named 'company'");
        }
        if (!db.ifTableExist("car")) {
            return wrong("Can't find table named 'car'");
        }
        return correct();
    }

    @DynamicTest
    public CheckResult test5_checkTableColumns() {
        String[][] companyColumns = {{"ID", "INT"}, {"NAME", "VARCHAR"}};
        db.ifColumnsExist("company", companyColumns);
        db.checkCompanyColumnProperties();

        String[][] carColumns = {{"ID", "INT"}, {"NAME", "VARCHAR"}, {"COMPANY_ID", "INT"}};
        db.ifColumnsExist("car", carColumns);
        db.checkCarColumnProperties();

        String[][] customerColumns = {{"ID", "INT"}, {"NAME", "VARCHAR"}, {"RENTED_CAR_ID", "INT"}};
        db.ifColumnsExist("customer", customerColumns);
        db.checkCustomerColumnProperties();
        return correct();
    }

    @DynamicTest
    public CheckResult test6_testAddCompany() {

        TestedProgram program = new TestedProgram();
        program.start("-databaseFileName", "carsharing");

        db.clearCustomerTable();
        db.clearCarTable();
        db.clearCompanyTable();

        program.execute("1");
        String output = program.execute("1");

        if (!output.contains("The company list is empty")) {
            return wrong("If no company has been created you should print 'The company list is empty' when '1. Company list' item is chosen");
        }

        output = program.execute("2");

        if (!output.contains("Enter the company name")) {
            return wrong("After choosing '2. Create a company' item you should ask to enter a company name.\n" +
                "Your output should contain 'Enter the company name:'");
        }

        program.execute("Super company");
        output = program.execute("1");

        if (!output.contains("1. Super company")) {
            return wrong("In the company list expected one company.\n" +
                "Your output should contain '1. Super company'");
        }

        db.checkCompany("Super company");

        program.execute("0\n2\nAnother company");
        program.execute("2\nOne more company");

        db.checkCompany("Another company");
        db.checkCompany("One more company");

        output = program.execute("1");

        if (!output.contains("1. Super company")) {
            return wrong("In the company list expected 'Super company' company.\n" +
                "Your output should contain '1. Super company'.\n" +
                "Companies should be sorted by 'ID' column");
        }

        if (!output.contains("2. Another company")) {
            return wrong("In the company list expected 'Another company' company.\n" +
                "Your output should contain '2. Another company'.\n" +
                "Companies should be sorted by 'ID' column");
        }

        if (!output.contains("3. One more company")) {
            return wrong("In the company list expected 'One more company' company.\n" +
                "Your output should contain '2. One more company'.\n" +
                "Companies should be sorted by 'ID' column");
        }

        if (!output.contains("0. Back")) {
            return wrong("There is no back option in the company list.\n" +
                "Your output should contain '0. Back'");
        }

        program.execute("2");

        return correct();
    }

    @DynamicTest
    public CheckResult test7_testAddCar() {

        TestedProgram program = new TestedProgram();
        program.start("-databaseFileName", "carsharing");
        String output;

        db.clearCarTable();
        db.clearCompanyTable();
        db.clearCustomerTable();

        program.execute("1");
        program.execute("2");
        program.execute("Car To Go");
        program.execute("2");
        program.execute("Drive Now");

        db.checkCompany("Car To Go");
        db.checkCompany("Drive Now");

        output = program.execute("1");

        if (!output.contains("1. Car To Go")) {
            return wrong("In the company list expected 'Car To Go' company.\n" +
                "Your output should contain '1. Car To Go'.\n" +
                "Companies should be sorted by 'ID' column");
        }

        if (!output.contains("2. Drive Now")) {
            return wrong("In the company list expected 'Drive Now' company.\n" +
                "Your output should contain '2. Drive Now'\n" +
                "Companies should be sorted by 'ID' column");
        }

        if (!output.contains("0. Back")) {
            return wrong("There is no back option in the company list.\n" +
                "Your output should contain '0. Back'");
        }

        output = program.execute("1");

        if (!output.contains("1. Car list")) {
            return wrong("After choosing company you should print menu that contains '1. Car list' item");
        }

        if (!output.contains("2. Create a car")) {
            return wrong("After choosing company you should print menu that contains '2. Create a car' item");
        }

        if (!output.contains("0. Back")) {
            return wrong("After choosing company you should print menu that contains '0. Back' item");
        }

        output = program.execute("1");

        if (!output.contains("The car list is empty!")) {
            return wrong("If no cars were added to the company you should print 'The car list is empty!'");
        }

        output = program.execute("2");

        if (!output.contains("Enter the car name:")) {
            return wrong("After choosing 'Create a car' item you should ask to enter a car name. " +
                "Your output should contain 'Enter the car name:'");
        }

        program.execute("Hyundai Venue");
        db.checkCar("Car To Go", "Hyundai Venue");

        program.execute("2");
        program.execute("Maruti Suzuki Dzire");
        db.checkCar("Car To Go", "Maruti Suzuki Dzire");

        output = program.execute("1");

        if (!output.contains("1. Hyundai Venue")) {
            return wrong("In the car list expected 'Hyundai Venue' car.\n" +
                "Your output should contain '1. Hyundai Venue'\n" +
                "Cars should be sorted by 'ID' column");
        }

        if (!output.contains("2. Maruti Suzuki Dzire")) {
            return wrong("In the car list expected 'Maruti Suzuki Dzire' car.\n" +
                "Your output should contain '2. Maruti Suzuki Dzire'\n" +
                "Cars should be sorted by 'ID' column");
        }

        program.execute("0");

        program.execute("1");
        program.execute("2");

        output = program.execute("1");

        if (!output.contains("The car list is empty!")) {
            return wrong("If no cars were added to the company you should print 'The car list is empty!'");
        }

        program.execute("2");
        program.execute("Lamborghini Urraco");

        output = program.execute("1");

        if (!output.contains("1. Lamborghini Urraco")) {
            return wrong("In the car list expected 'Lamborghini Urraco' car.\n" +
                "Your output should contain '1. Lamborghini Urraco'");
        }

        if (output.contains("Hyundai Venue")) {
            return wrong("Your output contains 'Hyundai Venue'. This car is from another company");
        }

        if (output.contains("Maruti Suzuki Dzire")) {
            return wrong("Your output contains 'Maruti Suzuki Dzire'. This car is from another company");
        }

        db.checkCar("Drive Now", "Lamborghini Urraco");

        program.execute("0");
        program.execute("0");
        program.execute("0");

        return correct();
    }

    @DynamicTest
    public CheckResult test8_testAddCustomer() {

        TestedProgram program = new TestedProgram();
        String output = program.start("-databaseFileName", "carsharing");

        db.clearCustomerTable();

        if (!output.contains("2. Log in as a customer")) {
            return wrong("Start menu should contain \"2. Log in as a customer\"");
        }

        if (!output.contains("3. Create a customer")) {
            return wrong("Start menu should contain \"3. Create a customer\"");
        }

        output = program.execute("2");

        if (!output.contains("The customer list is empty!")) {
            return wrong("If no customers were created you should print 'The customer list is empty!'");
        }

        output = program.execute("3");

        if (!output.contains("Enter the customer name:")) {
            return wrong("After choosing '3. Create a customer' option you should ask to enter a customer name.\n" +
                "Your output should contain 'Enter the customer name:'");
        }

        program.execute("First customer");
        db.checkCustomer("First customer", null);

        program.execute("3");
        output = program.execute("Second customer");
        db.checkCustomer("Second customer", null);

        if (!output.contains("2. Log in as a customer")) {
            return wrong("After creating a customer you should print main menu again.\n" +
                "Your output should contain '2. Log in as a customer'");
        }

        output = program.execute("2");


        if (!output.contains("1. First customer")) {
            return wrong("In the customer list expected 'First customer' customer.\n" +
                "Your output should contain '1. First customer'\n" +
                "Customers should be sorted by 'ID' column");
        }

        if (!output.contains("2. Second customer")) {
            return wrong("In the customer list expected 'Second customer' customer.\n" +
                "Your output should contain '2. Second customer'\n" +
                "Customers should be sorted by 'ID' column");
        }

        output = program.execute("1");

        if (!output.contains("1. Rent a car")) {
            return wrong("After choosing customer you should print menu that contains '1. Rent a car' item");
        }

        if (!output.contains("2. Return a rented car")) {
            return wrong("After choosing customer you should print menu that contains '2. Return a rented car' item");
        }

        if (!output.contains("3. My rented car")) {
            return wrong("After choosing customer you should print menu that contains '3. My rented car' item");
        }

        if (!output.contains("0. Back")) {
            return wrong("After choosing customer you should print menu that contains '3. My rented car' item");
        }

        output = program.execute("3");

        if (!output.contains("You didn't rent a car!")) {
            return wrong("After choosing '3. My rented car' option you should print 'You didn't rent a car!' if a customer didn't rent a car.");
        }

        output = program.execute("2");

        if (!output.contains("You didn't rent a car!")) {
            return wrong("After choosing '2. Return a rented car' option you should print 'You didn't rent a car!' if a customer didn't rent a car.");
        }

        return correct();
    }

    @DynamicTest
    public CheckResult test9_testRentCar() {

        TestedProgram program = new TestedProgram();
        String output;
        program.start("-databaseFileName", "carsharing");

        db.checkCustomer("First customer", null);
        db.checkCustomer("Second customer", null);

        db.checkCompany("Car To Go");
        db.checkCompany("Drive Now");

        db.checkCar("Drive Now", "Lamborghini Urraco");
        db.checkCar("Car To Go", "Hyundai Venue");
        db.checkCar("Car To Go", "Maruti Suzuki Dzire");

        program.execute("2");
        program.execute("1");

        output = program.execute("1");

        if (!output.contains("1. Car To Go")) {
            return wrong("In the company list expected 'Car To Go' company.\n" +
                "Your output should contain '1. Car To Go'.\n" +
                "Companies should be sorted by 'ID' column");
        }

        if (!output.contains("2. Drive Now")) {
            return wrong("In the company list expected 'Drive Now' company.\n" +
                "Your output should contain '2. Drive Now'\n" +
                "Companies should be sorted by 'ID' column");
        }

        if (!output.contains("0. Back")) {
            return wrong("There is no back option in the company list.\n" +
                "Your output should contain '0. Back'");
        }

        output = program.execute("1");

        if (!output.contains("1. Hyundai Venue")) {
            return wrong("In the car list expected 'Hyundai Venue' car.\n" +
                "Your output should contain '1. Hyundai Venue'\n" +
                "Cars should be sorted by 'ID' column");
        }

        if (!output.contains("2. Maruti Suzuki Dzire")) {
            return wrong("In the car list expected 'Maruti Suzuki Dzire' car.\n" +
                "Your output should contain '2. Maruti Suzuki Dzire'\n" +
                "Cars should be sorted by 'ID' column");
        }

        output = program.execute("1");

        if (!output.contains("You rented 'Hyundai Venue'")) {
            return wrong("After renting 'Hyundai Venue' you shoul print 'You rented 'Hyundai Venue'");
        }

        db.checkCustomer("First customer", "Hyundai Venue");

        if (!output.contains("3. My rented car")) {
            return wrong("After renting a car you should print menu that contains '3. My rented car' option.");
        }

        output = program.execute("3");

        if (!output.contains("Hyundai Venue")) {
            return wrong("After choosing '3. My rented car' option expected car name is 'Hyundai Venue'");
        }

        if (!output.contains("Car To Go")) {
            return wrong("After choosing '3. My rented car' option expected company name is 'Car To Go'");
        }

        output = program.execute("1");

        if (!output.contains("You've already rented a car!")) {
            return wrong("If a customer has already rented a car and is trying to rent another one you should print 'You've already rented a car!'");
        }

        output = program.execute("2");

        if (!output.contains("You've returned a rented car!")) {
            return wrong("If a customer has already rented a car and is trying to rent another one you should print 'You've already rented a car!'");
        }

        db.checkCustomer("First customer", null);

        program.execute("0");
        program.execute("0");

        return correct();
    }

    @DynamicTest
    public CheckResult test10_testRentedCarInList() {

        TestedProgram program = new TestedProgram();
        String output;
        program.start("-databaseFileName", "carsharing");

        program.execute("2");
        program.execute("2");
        program.execute("1");
        program.execute("1");
        program.execute("1");
        program.execute("0");

        program.execute("2");
        program.execute("1");
        program.execute("1");

        output = program.execute("1");
        if (output.contains("Hyundai Venue")) {
            return wrong("You shouldn't print out a car if it is already rented!");
        }

        program.execute("0");
        program.execute("0");
        program.execute("0");

        return correct();
    }

    private CheckResult wrong(String message) {
        db.closeConnection();
        return CheckResult.wrong(message);
    }

    private CheckResult correct() {
        db.closeConnection();
        return CheckResult.correct();
    }
}
