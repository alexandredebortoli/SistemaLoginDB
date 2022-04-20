package dao;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class JavaMailDAO {
    
    public static void enviarEmail(String recipient, int perfil, int id, String senha) throws Exception {
        System.out.println("Preparando para enviar email...");
        Properties properties = new Properties();
        
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        String myAccount = "xande10.floripa@gmail.com";
        String myPass = "@lex0102";
        
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccount, myPass);
            }
        });
        //String senha = acharSenha(perfil, recipient);
        if(senha != null) {
            //int id = acharId(perfil, recipient);
            Message message = prepareMessage(session, myAccount, recipient, senha, id);
            Transport.send(message);
            System.out.println("Mensagem enviada com sucesso");
        } else {
            System.out.println("Opção inválido");
        }
    }
    
    private static Message prepareMessage(Session session, String myAccount, String recipient, String senha, int id) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccount));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Recuperação de conta");
            message.setText("Seu ID: " + id + "\nSua senha: " + senha);
            
            
            
            return message;
        } catch (Exception ex) {
            System.out.println("Error ao enviar mensagem");
            Logger.getLogger(JavaMailDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /*private static String acharSenha(int perfil, String recipiente) {
        if(perfil == 1) {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            for(int i = 0; i < usuarioDAO.listar(0).size(); i++) {
                if(recipiente.equals(usuarioDAO.listar(0).get(i).getEmail())) {
                    return usuarioDAO.listar(0).get(i).getSenha();
                }
            }
        } else {
            AdministradorDAO administradorDAO = new AdministradorDAO();
            for(int i = 0; i < administradorDAO.listar(0).size(); i++) {
                if(recipiente.equals(administradorDAO.listar(0).get(i).getEmail())) {
                    return administradorDAO.listar(0).get(i).getSenha();
                }
            }
        }
        return null;
    }*/

}
