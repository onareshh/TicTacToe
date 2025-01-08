import java.sql.*;
import java.util.Scanner;

public class GameManager {
    private final Connection connection;

    public GameManager(Connection connection) {
        this.connection = connection;
    }

    public void startGame(Scanner scanner, TicTacToeGame game) throws SQLException {
        System.out.print("Enter Player 1 username: ");
        String player1Username = scanner.next();
        System.out.print("Enter Player 2 username: ");
        String player2Username = scanner.next();
        scanner.nextLine(); // Consume newline

        if (player1Username.equals(player2Username)) {
            System.out.println("Player 1 and Player 2 are the same.");
            return;
        }

        if (!playerExists(player1Username) || !playerExists(player2Username)) {
            System.out.println("One or both players do not exist.");
            return;
        }

        String winnerUsername = game.play(scanner, player1Username, player2Username);

        String query = "INSERT INTO Games (player1_username, player2_username, winner_username) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, player1Username);
            statement.setString(2, player2Username);
            statement.setString(3, winnerUsername.equals("null") ? null : winnerUsername);
            statement.executeUpdate();
        }
        System.out.println("Game finished.");
    }

    private boolean playerExists(String username) throws SQLException {
        String query = "SELECT * FROM Players WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        }
    }

    public void viewGames() throws SQLException {
        String query = "SELECT * FROM Games";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            System.out.println("\nGames:");
            while (resultSet.next()) {
                System.out.printf("Game ID: %d, Player 1: %s, Player 2: %s, Winner: %s\n",
                        resultSet.getInt("game_id"),
                        resultSet.getString("player1_username"),
                        resultSet.getString("player2_username"),
                        resultSet.getString("winner_username") == null ? "Draw" : resultSet.getString("winner_username"));
            }
        }
    }
}
