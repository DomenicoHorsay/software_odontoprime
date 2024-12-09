package org.project.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/sistema_odontologico", "prime", "prime");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao conectar ao banco de dados");
        }
    }
}
