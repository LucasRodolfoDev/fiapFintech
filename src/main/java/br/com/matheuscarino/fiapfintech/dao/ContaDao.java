package br.com.matheuscarino.fiapfintech.dao;

import br.com.matheuscarino.fiapfintech.exception.DBException;
import br.com.matheuscarino.fiapfintech.model.Conta;
import java.util.List;

public interface ContaDao {
    
    void cadastrar(Conta conta) throws DBException;
    void atualizar(Conta conta) throws DBException;
    void remover(int id) throws DBException;
    Conta buscar(int id) throws DBException;
    List<Conta> listar() throws DBException;
    List<Conta> listarPorCliente(int clienteId) throws DBException;
}
