package br.com.matheuscarino.fiapfintech.dao;

import br.com.matheuscarino.fiapfintech.exception.DBException;
import br.com.matheuscarino.fiapfintech.model.Cliente;
import java.util.List;

public interface ClienteDao {
    

    void cadastrar(Cliente cliente) throws DBException;
    void atualizar(Cliente cliente) throws DBException;
    void remover(Cliente cliente) throws DBException;
    Cliente buscar(int id) throws DBException;
    List<Cliente> listar() throws DBException;

}
