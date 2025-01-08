import java.sql.*;
public class DatabaseManager {
    private static final String url="jdbc:mysql://localhost:3306/game";
    private static final String user="root";
    private static final String password="root";
    private Connection connection;
    public void connect()  throws SQLException{
        connection=DriverManager.getConnection(url,user,password);
        System.out.println("Connected");
    }
    public Connection getConnection(){
        return connection;
    }
    public void close() throws SQLException{
        if(connection!=null && !connection.isClosed()){
            connection.close();
            System.out.println("Closed");
        }
    }
}
