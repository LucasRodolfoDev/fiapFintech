package br.com.matheuscarino.fiapfintech.dao;

import br.com.matheuscarino.fiapfintech.exception.DBException;
import br.com.matheuscarino.fiapfintech.model.Transferencia;
import java.util.List;

public interface TransferenciaDao {
    
    void cadastrar(Transferencia transferencia) throws DBException;
    Transferencia buscar(int id) throws DBException;
    List<Transferencia> listar() throws DBException;
    List<Transferencia> listarPorConta(int contaId) throws DBException;
    List<Transferencia> listarPorCliente(int clienteId) throws DBException;
}