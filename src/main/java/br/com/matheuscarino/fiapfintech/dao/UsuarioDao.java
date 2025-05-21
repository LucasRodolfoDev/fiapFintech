package br.com.matheuscarino.fiapfintech.dao;

import br.com.matheuscarino.fiapfintech.exception.DBException;
import br.com.matheuscarino.fiapfintech.model.Usuario;
import java.util.List;

public interface UsuarioDao {

    boolean validarUsuario(Usuario usuario);
    void cadastrar(Usuario usuario) throws DBException;
    void atualizar(Usuario usuario) throws DBException;
    void remover(Long id) throws DBException;
    Usuario buscar(Long id) throws DBException;
    List<Usuario> listar() throws DBException;
    Usuario buscarPorEmail(String email) throws DBException;

}
