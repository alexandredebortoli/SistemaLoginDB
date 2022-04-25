package dao;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;

public class JavaMailDAO {
    
    public static void enviarEmail(String recipient, String senha) throws Exception {
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
        if(senha != null) {
            Message message = prepareMessage(session, myAccount, recipient, senha);
            Transport.send(message);
            System.out.println("Mensagem enviada com sucesso");
        } else {
            System.out.println("Opção inválido");
        }
    }
    
    private static Message prepareMessage(Session session, String myAccount, String recipient, String senha) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccount));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Recuperação de conta");
            message.setText("Sua nova senha: " + senha);
   
            return message;
        } catch (MessagingException ex) {
            System.out.println("Error ao enviar mensagem");
            Logger.getLogger(JavaMailDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
