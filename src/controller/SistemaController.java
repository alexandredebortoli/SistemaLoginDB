package controller;

import conexao.ConexaoDB;
import frame.MenuFrame;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.*;
import java.util.Random;
import frame.RecuperarSenhaFrame;

public class SistemaController {
    public static void iniciar() throws SQLException {
        Connection connection = ConexaoDB.createConnectionMySQL();
        if(connection != null) {
            System.out.println("Conexão realizada com sucesso!");
            connection.close();
        }
        MenuFrame menuLoginFrame = new MenuFrame();
        menuLoginFrame.setVisible(true);
    }
    public static String encriptografar(String senha) {
        String senhaHash = "";
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
            BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
            senhaHash = hash.toString(16);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return senhaHash;
    }
    public static String gerarNovaSenha() {
        Random random = new Random();
        int randSenha = random.nextInt(9000)+1000; //Gerar senha entre 1000-9999
        return Integer.toString(randSenha);
    }
    public static void recuperarSenhaFrame() {
        RecuperarSenhaFrame recuperarSenhaFrame = new RecuperarSenhaFrame();
        recuperarSenhaFrame.show(true);
    }
}
