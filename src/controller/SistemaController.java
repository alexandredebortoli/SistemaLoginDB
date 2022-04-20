package controller;

import conexao.ConexaoDB;
import view.SistemaView;
import java.sql.*;

public class SistemaController {
    public static void iniciar() throws SQLException {
        Connection connection = ConexaoDB.createConnectionMySQL();
        if(connection != null) {
            System.out.println("Conexão realizada com sucesso");
            connection.close();
        }

        SistemaView.menu();
    }
}
