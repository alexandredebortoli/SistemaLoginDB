package controller;

import conexao.ConexaoDB;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import view.SistemaView;
import java.sql.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SistemaController {
    public static void iniciar() throws SQLException {
        Connection connection = ConexaoDB.createConnectionMySQL();
        if(connection != null) {
            SistemaView.msgConexaoSucesso();
            connection.close();
        }
        menuLogin();
    }

    public static void menuLogin() {
        int opcao = 0;
        do {
            opcao = SistemaView.menuLogin();
            switch (opcao) {
                case 0 -> SistemaView.msgFecharPrograma();
                case 1 -> {
                    UsuarioController.loginUsuario();
                }
                case 2 -> {
                    AdministradorController.loginAdministrador();
                }
                case 3 -> {
                    UsuarioController.cadastrarUsuario();
                }
                case 4 -> {
                try {
                    recuperarSenha();
                } catch (Exception ex) {
                    Logger.getLogger(SistemaView.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                default -> throw new AssertionError();
            }
        } while (opcao != 0);
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
    
    public static void recuperarSenha() throws Exception {
        int pessoa = SistemaView.recuperarSenha();
        if(pessoa == 1) {
            UsuarioController.recuperarSenhaUsuario();
            SistemaView.msgEmailEnviado();
        } else if(pessoa == 2) {
            AdministradorController.recuperarSenhaAdministrador();
            SistemaView.msgEmailEnviado();
        } else {
            SistemaView.msgOpcaoInvalida();
        }
    }
    public static String gerarNovaSenha() {
        Random random = new Random();
        int randSenha = random.nextInt(9000)+1000; //Gerar senha entre 1000-9999
        return Integer.toString(randSenha);
    }
}
