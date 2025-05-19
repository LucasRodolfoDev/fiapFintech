package br.com.matheuscarino.fiapfintech.teste;

import br.com.matheuscarino.fiapfintech.util.CriptografiaUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class CriptografiaTeste {

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        System.out.println(CriptografiaUtils.criptografar("123456"));
        try {
            System.out.println(CriptografiaUtils.criptografar("123456"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
