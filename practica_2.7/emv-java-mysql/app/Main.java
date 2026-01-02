import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hola mundo desde Java dentro de Docker!");

        String host = System.getenv("DB_HOST");
        String port = System.getenv("DB_PORT");
        String db   = System.getenv("DB_NAME");
        String user = System.getenv("DB_USER");
        String pass = System.getenv("DB_PASSWORD");

        String url = "jdbc:mysql://" + host + ":" + port + "/" + db + "?serverTimezone=UTC";

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            System.out.println("✔ Conexión a MySQL OK");
        } catch (SQLException e) {
            System.out.println("❌ Error conectando a MySQL:");
            e.printStackTrace();
        }
    }
}
