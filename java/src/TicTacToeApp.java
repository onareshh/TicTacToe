import java.sql.SQLException;
import java.util.Scanner;

public class TicTacToeApp {
    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();
        try {
            dbManager.connect();
            PlayerManager playerManager = new PlayerManager(dbManager.getConnection());
            GameManager gameManager = new GameManager(dbManager.getConnection());
            TicTacToeGame game = new TicTacToeGame();

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\n--- Tic-Tac-Toe ---");
                System.out.println("1. Register Player");
                System.out.println("2. Start Game");
                System.out.println("3. View Players");
                System.out.println("4. View Games");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        playerManager.registerPlayer(scanner);
                        break;
                    case 2:
                        gameManager.startGame(scanner, game);
                        break;
                    case 3:
                        playerManager.viewPlayers();
                        break;
                    case 4:
                        gameManager.viewGames();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        dbManager.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
