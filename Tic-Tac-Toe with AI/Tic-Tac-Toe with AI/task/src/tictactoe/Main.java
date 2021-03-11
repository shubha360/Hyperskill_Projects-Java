package tictactoe;

public class Main {
    public static void main(String[] args) {

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        TicTacToe game = null;

        String paramOne, paramTwo;

        while (true) {
            System.out.print("Input command: ");
            String input = scanner.nextLine();

            if (input.equals("exit")) {
                return;
            } else {

                String[] arr = input.split(" ");

                if (arr.length != 3 || !"start".equals(arr[0])) {
                    System.out.println("Bad parameters!");
                    continue;
                }

                if ("user".equals(arr[1]) || "easy".equals(arr[1]) || "medium".equals(arr[1]) || "hard".equals(arr[1])) {
                    paramOne = arr[1];
                } else {
                    System.out.println("Bad parameters!");
                    continue;
                }

                if ("user".equals(arr[2]) || "easy".equals(arr[2]) || "medium".equals(arr[2]) || "hard".equals(arr[2])) {
                    paramTwo = arr[2];
                } else {
                    System.out.println("Bad parameters!");
                    continue;
                }
            }
            game = new TicTacToe(paramOne, paramTwo);
            game.letsPlay();
        }
    }
}