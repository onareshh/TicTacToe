import java.sql.*;
import java.util.Scanner;

public class PlayerManager {
    private final Connection connection;

    public PlayerManager(Connection connection) {
        this.connection = connection;
    }

    public void registerPlayer(Scanner scanner) throws SQLException {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        String checkQuery = "SELECT * FROM Players WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(checkQuery)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Username already exists.");
                return;
            }
        }

        String insertQuery = "INSERT INTO Players (username, name) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, username);
            statement.setString(2, name);
            statement.executeUpdate();
            System.out.println("Player registered successfully.");
        }
    }

    public void viewPlayers() throws SQLException {
        String query = "SELECT * FROM Players";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            System.out.println("\nPlayers:");
            if (!resultSet.next()) {
                System.out.println("No players found.");
                return;
            }
            do {
                System.out.printf("%s %s \n", resultSet.getString("username"), resultSet.getString("name"));
            } while (resultSet.next());
        }
    }
}
