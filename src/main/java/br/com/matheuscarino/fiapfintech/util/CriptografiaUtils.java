package br.com.matheuscarino.fiapfintech.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CriptografiaUtils {
    public static String criptografar(String senha)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(senha.getBytes("UTF-8"));

        BigInteger hash = new BigInteger(1, md.digest());

        return hash.toString(16);
    }
}
