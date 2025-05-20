package br.com.matheuscarino.fiapfintech.model;

import br.com.matheuscarino.fiapfintech.util.CriptografiaUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class Usuario {

    private Long id;
    private String email;
    private String senha;
    private String tipoUsuario;

    public Usuario(String email, String senha, String tipoUsuario) {
        super();
        this.email = email;
        this.tipoUsuario = tipoUsuario;
        setSenha(senha);
    }

    public Usuario() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        try {
            this.senha = CriptografiaUtils.criptografar(senha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
