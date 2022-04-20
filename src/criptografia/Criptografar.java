package criptografia;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Criptografar {
    public static String encriptografar(String senha) {
        String retorno = "";
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
            BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
            retorno = hash.toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Criptografar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return retorno;
    }
}
