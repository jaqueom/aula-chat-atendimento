package br.com.tt.chat.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private Connection connection;

    public void conecta() throws SQLException {

        String urlConexao = "jdbc:postgresql://postgresql.lptn.com.br:5432/comunicador";
        connection = DriverManager.getConnection(urlConexao, "comunicador", "Comunicador123!");

    }

    public void desconecta() throws SQLException {
        connection.close();
    }

    public Connection getConexao() {
        return connection;
    }
}
