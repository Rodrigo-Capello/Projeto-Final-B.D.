package uel.ProjetoFinalBD.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static Connection abrir() throws SQLException {
        return DriverManager.getConnection(
            System.getenv("DB_URL"),
            System.getenv("DB_USUARIO"),
            System.getenv("DB_SENHA")
        );
    }
}