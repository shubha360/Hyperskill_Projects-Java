import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BudgetManagerTest extends StageTest<String> {


    @Override
    public List<TestCase<String>> generate() {
        return List.of(

                new TestCase<String>()
                        .setInput("0\n")
                        .setCheckFunc(BudgetManagerTest::test1),

                new TestCase<String>()
                        .setInput("0\n")
                        .setCheckFunc(BudgetManagerTest::test2),

                new TestCase<String>()
                        .setInput("4\n0")
                        .setCheckFunc(BudgetManagerTest::test3),

                new TestCase<String>()
                        .setInput("1\n400\n4\n1\n200\n4\n0")
                        .setCheckFunc(BudgetManagerTest::test4),

                new TestCase<String>()
                        .setInput("1\n1000\n2\n" +
                                "1\nAlmond 250g\n35.43\n" +
                                "1\nMilk\n3.50\n" +
                                "1\nRed Fuji Apple\n5.99\n" +
                                "1\nEggs\n3.99\n" +
                                "1\nFIJI Natural Artesian Water\n25.98\n" +
                                "1\nHershey's milk chocolate bars\n8.54\n" +
                                "1\nGreat Value Broccoli Florets\n1.00\n" +
                                "1\nKeystone Ground Bee\n6.28\n" +
                                "2\nGildan LT\n8.61\n" +
                                "2\nMen's Dual Defense Crew Socks 12 Pairs\n13.00\n" +
                                "2\nWrangler Men's Stretch Cargo Pant\n19.97\n" +
                                "3\nLEGO DUPLO Town Farm Animals\n10.10\n" +
                                "3\nCinema\n8.73\n" +
                                "3\nSkate rental\n30\n" +
                                "4\nSensodyne Pronamel Toothpaste\n19.74\n" +
                                "4\nChick-fil-A $10 Gift Card\n10\n" +
                                "4\nDebt\n3.50\n" +
                                "5\n5\n0")
                        .setCheckFunc(BudgetManagerTest::test5),

                new TestCase<String>()
                        .setInput("6\n4\n3\n5\n1\n6\n0")
                        .setCheckFunc(BudgetManagerTest::test6),

                new TestCase<String>()
                        .setInput("7\n1\n2\n3\n4\n4\n6\n7\n1\n2\n3\n1\n4\n0")
                        .setCheckFunc(BudgetManagerTest::test7)
        );
    }


    //Checking program stop
    private static CheckResult test1(String reply, String attach) {
        if (!reply.contains("Bye!")) {
            return new CheckResult(false,
                    "Your program should stop after choosing \"Exit\"");
        }
        return new CheckResult(true);
    }


    //Checking menu
    private static CheckResult test2(String reply, String attach) {
        String[] menuPatterns = {"1)", "2)", "3)", "4)", "0)"};
        for (String menuPattern : menuPatterns) {
            if (!reply.contains(menuPattern)) {
                return new CheckResult(false,
                        "Your menu doesn't have item " + menuPattern);
            }
        }
        return new CheckResult(true);
    }


    //Checking balance
    private static CheckResult test3(String reply, String attach) {

        String[] blocks = reply.split("\n(\n+)?\n");

        if (blocks.length != 4) {
            return new CheckResult(false,
                    "Your program shows wrong blocks of output. Expected: 4\n" +
                            "You have: " + blocks.length + "\n" +
                            "Make sure that you print an empty line after each chosen action");
        }

        String balance = blocks[1];

        if (!balance.toLowerCase().contains("balance")) {
            return new CheckResult(false,
                    "Your program should show balance after choosing 4th item");
        }

        Pattern doublePattern = Pattern.compile("\\d+[,.]\\d+");
        Matcher matcher = doublePattern.matcher(balance);

        if (!matcher.find()) {
            return new CheckResult(false,
                    "Your balance should contain a number!");
        }

        double balanceDouble = Double.parseDouble(matcher.group());

        if (Math.abs(balanceDouble - 0) > 0.0001) {
            System.out.println(balance);
            return new CheckResult(false,
                    "Balance should be $0.00 at the beginning");
        }

        return new CheckResult(true);
    }


    //Checking adding income
    private static CheckResult test4(String reply, String attach) {

        String[] blocks = reply.split("\n(\n+)?\n");

        if (blocks.length != 10) {
            return new CheckResult(false,
                    "Your program shows wrong blocks of output. Expected: 10\n" +
                            "You have: " + blocks.length + "\n" +
                            "Make sure that you print an empty line after each chosen action");
        }

        String balanceAfterFirstAddingIncome = blocks[3];

        if (!balanceAfterFirstAddingIncome.contains("$400")) {
            return new CheckResult(false,
                    "Balance is wrong after adding income!.\n" +
                            "Expected:\n" +
                            "Balance: $400.00\n" +
                            "Your output:\n" +
                            balanceAfterFirstAddingIncome);
        }

        return new CheckResult(true);
    }

    //Checking the saving purchases
    private static CheckResult test5(String reply, String attach) {
        File file = new File("purchases.txt");
        if (!file.exists()) {
            return new CheckResult(false,
                    "Your program should create purchase.txt file and save purchases there!");
        }
        return new CheckResult(true);
    }

    //Checking the downloading purchases
    private static CheckResult test6(String reply, String attach) {

        String[] blocks = reply.split("\n(\n+)?\n");

        if (blocks.length != 12) {
            return new CheckResult(false,
                    "Your program shows wrong blocks of output. Expected: 12\n" +
                            "You have: " + blocks.length + "\n" +
                            "Make sure that you print an empty line after each chosen action");
        }

        String balanceAfterDownloadingPurchases = blocks[3];

        if (!balanceAfterDownloadingPurchases.replace(",", ".").contains("785.64")) {
            return new CheckResult(false,
                    "Your program reads balance from file wrong!");
        }

        //All purchases list

        String allPurchases = blocks[6];

        String[] expectedPurchases = {
                "Almond 250g $35.43",
                "Milk $3.50",
                "Red Fuji Apple $5.99",
                "Eggs $3.99",
                "FIJI Natural Artesian Water $25.98",
                "Hershey's milk chocolate bars $8.54",
                "Great Value Broccoli Florets $1.00",
                "Keystone Ground Bee $6.28",
                "Gildan LT $8.61",
                "Men's Dual Defense Crew Socks 12 Pairs $13.00",
                "Wrangler Men's Stretch Cargo Pant $19.97",
                "LEGO DUPLO Town Farm Animals $10.10",
                "Cinema $8.73",
                "Skate rental $30.00",
                "Sensodyne Pronamel Toothpaste $19.74",
                "Chick-fil-A $10 Gift Card $10.00",
                "Debt $3.50"
        };

        for (String expectedPurchase : expectedPurchases) {
            if (!allPurchases.contains(expectedPurchase)) {
                return new CheckResult(false,
                        "Your all purchases list doesn't have purchase:\n" +
                                expectedPurchase + "\n" +
                                "But should have!");
            }
        }

        String[] temp = allPurchases.split("\n");
        String totalSum = temp[temp.length - 1];

        Pattern doublePattern = Pattern.compile("\\d+[,.]\\d+");
        Matcher matcher = doublePattern.matcher(totalSum);

        if (!matcher.find()) {
            return new CheckResult(false,
                    "Total sum of all purchases is wrong. Expected:\n" +
                            "Total sum: $214.36\n" +
                            "Your output:\n" +
                            totalSum);
        }

        double allTotalSum = Double.parseDouble(matcher.group());

        if (Math.abs(allTotalSum - 214.36) > 0.0001) {
            return new CheckResult(false,
                    "Your all total sum is wrong!");
        }

        //Food list

        String foodList = blocks[8];

        expectedPurchases = new String[]{
                "Almond 250g $35.43",
                "Milk $3.50",
                "Red Fuji Apple $5.99",
                "Eggs $3.99",
                "FIJI Natural Artesian Water $25.98",
                "Hershey's milk chocolate bars $8.54",
                "Great Value Broccoli Florets $1.00",
                "Keystone Ground Bee $6.28"
        };

        for (String expectedPurchase : expectedPurchases) {
            if (!foodList.contains(expectedPurchase)) {
                return new CheckResult(false,
                        "Your food list doesn't have purchase:\n" +
                                expectedPurchase + "\n" +
                                "But should have!");
            }
        }

        temp = foodList.split("\n");
        totalSum = temp[temp.length - 1];

        matcher = doublePattern.matcher(totalSum);

        if (!matcher.find()) {
            return new CheckResult(false,
                    "Total sum of food list is wrong. Expected:\n" +
                            "Total sum: $90.71\n" +
                            "Your output:\n" + totalSum);
        }

        double foodTotalSum = Double.parseDouble(matcher.group());

        if (Math.abs(foodTotalSum - 90.71) > 0.0001) {
            return new CheckResult(false,
                    "Your food total sum is wrong!");
        }

        return new CheckResult(true);
    }


    //Sorting check
    private static CheckResult test7(String reply, String attach) {

        String[] blocks = reply.split("\n(\n+)?\n");

        if (blocks.length != 22) {
            return new CheckResult(false,
                    "Your program shows wrong blocks of output. Expected: 22\n" +
                            "You have: " + blocks.length + "\n" +
                            "Make sure that you print an empty line after each chosen action");
        }

        String sortEmptyAllPurchases = blocks[2];

        if (!sortEmptyAllPurchases.contains("list is empty")) {
            return new CheckResult(false,
                    "\"Sort all purchases\" item shouldn't work if there are no purchases in the list.\n" +
                            "You should print 'List is empty!'");
        }

        String sortByEmptyType = blocks[4];

        if (sortByEmptyType.contains("list is empty")) {
            return new CheckResult(false,
                    "\"Sort by type\" item should work even if there are no purchases in the list\n" +
                            "You should print each category with total sum $0\n" +
                            "Your output shouldn't contain 'List is empty!'");
        }

        String sortEmptyCertainType = blocks[7];

        if (!sortEmptyCertainType.contains("list is empty")) {
            return new CheckResult(false,
                    "\"Sort certain type\" item shouldn't work if there are no purchases in the certain list\n" +
                            "You should print 'List is empty'");
        }

        String allSorted = blocks[13];
        String rightSorted = "Almond 250g $35.43\n" +
                "Skate rental $30.00\n" +
                "FIJI Natural Artesian Water $25.98\n" +
                "Wrangler Men's Stretch Cargo Pant $19.97\n" +
                "Sensodyne Pronamel Toothpaste $19.74\n" +
                "Men's Dual Defense Crew Socks 12 Pairs $13.00\n" +
                "LEGO DUPLO Town Farm Animals $10.10\n" +
                "Chick-fil-A $10 Gift Card $10.00\n" +
                "Cinema $8.73\n" +
                "Gildan LT $8.61\n" +
                "Hershey's milk chocolate bars $8.54\n" +
                "Keystone Ground Bee $6.28\n" +
                "Red Fuji Apple $5.99\n" +
                "Eggs $3.99\n" +
                "Milk $3.50\n" +
                "Debt $3.50\n" +
                "Great Value Broccoli Florets $1.00";

        if (!allSorted.contains(rightSorted)) {
            return new CheckResult(false,
                    "Your program sort all purchases wrong!\n" +
                            "Expected:\n" +
                            rightSorted + "\n" +
                            "Your output:\n" +
                            allSorted);
        }


        String sortedByType = blocks[15];
        String rightSortedByType = "Food - $90.71\n" +
                "Entertainment - $48.83\n" +
                "Clothes - $41.58\n" +
                "Other - $33.24";

        if (!sortedByType.contains(rightSortedByType)) {
            return new CheckResult(false,
                    "Your program sort by type wrong!\n" +
                            "Expected:\n" +
                            rightSortedByType + "\n" +
                            "Your output:\n" +
                            sortedByType);
        }

        String sortedCertainType = blocks[18];
        String rightSortedCertainType = "Almond 250g $35.43\n" +
                "FIJI Natural Artesian Water $25.98\n" +
                "Hershey's milk chocolate bars $8.54\n" +
                "Keystone Ground Bee $6.28\n" +
                "Red Fuji Apple $5.99\n" +
                "Eggs $3.99\n" +
                "Milk $3.50\n" +
                "Great Value Broccoli Florets $1.00";

        if (!sortedCertainType.contains(rightSortedCertainType)) {
            return new CheckResult(false,
                    "Your program sort certain type wrong!\n" +
                            "Expected:\n" +
                            rightSortedCertainType + "\n" +
                            "Your output:\n" +
                            sortedCertainType);
        }

        return new CheckResult(true);
    }
}
