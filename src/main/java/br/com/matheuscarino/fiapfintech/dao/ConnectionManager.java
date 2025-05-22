package br.com.matheuscarino.fiapfintech.dao;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final Dotenv dotenv = Dotenv.configure()
            .directory("/Users/carino/IdeaProjects/fiapFintech")
            .load();

    private static final String URL = dotenv.get("DB_URL");
    private static final String USER = dotenv.get("DB_USER"); 
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");

    private static ConnectionManager connectionManager;

    public static ConnectionManager getInstance() {
        if (connectionManager == null) {
            connectionManager = new ConnectionManager();
        }
        return connectionManager;
    }

    private ConnectionManager() {}

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            System.out.println("Tentando conectar ao banco de dados...");
            System.out.println("URL: " + URL);
            System.out.println("USER: " + USER);
            // Password not printed for security reasons

            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão estabelecida com sucesso");
        } catch (ClassNotFoundException e) {
            System.out.println("Erro: Driver Oracle JDBC não encontrado");
            throw new SQLException("Driver Oracle JDBC não encontrado", e);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            System.out.println("Verifique se as credenciais e URL do banco estão corretas no arquivo .env");
            throw new SQLException("Erro ao conectar ao banco de dados: " + e.getMessage(), e);
        } 
        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}
