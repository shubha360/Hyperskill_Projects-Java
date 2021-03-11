package battleship;

public class BattleshipGame {

    private static Player player1;
    private static Player player2;

    public static void playGame() {

        player1 = new Player("Player 1");
        player2 = new Player("Player 2");

        while (true) {

            boolean a = player1.takeAShot(player2);
            boolean b = player2.takeAShot(player1);

            if (a || b) {
                break;
            }
        }
    }
}
