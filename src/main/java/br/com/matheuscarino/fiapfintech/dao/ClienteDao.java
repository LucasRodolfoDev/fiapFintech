package br.com.matheuscarino.fiapfintech.dao;

import br.com.matheuscarino.fiapfintech.model.Cliente;
import java.sql.SQLException;
import java.util.List;

public interface ClienteDao {
    
    /**
     * Cadastra um novo cliente no banco de dados
     * @param cliente objeto Cliente a ser cadastrado
     * @return Cliente cadastrado com ID gerado
     * @throws SQLException em caso de erro na operação
     */
    Cliente cadastrar(Cliente cliente) throws SQLException;
    
    /**
     * Atualiza os dados de um cliente existente
     * @param cliente objeto Cliente com os dados atualizados
     * @return true se a atualização foi bem sucedida
     * @throws SQLException em caso de erro na operação
     */
    boolean atualizar(Cliente cliente) throws SQLException;
    
    /**
     * Remove um cliente do banco de dados
     * @param id identificador do cliente a ser removido
     * @return true se a remoção foi bem sucedida
     * @throws SQLException em caso de erro na operação
     */
    boolean remover(Long id) throws SQLException;
    
    /**
     * Busca um cliente pelo seu ID
     * @param id identificador do cliente
     * @return objeto Cliente encontrado ou null se não existir
     * @throws SQLException em caso de erro na operação
     */
    Cliente buscarPorId(Long id) throws SQLException;
    
    /**
     * Lista todos os clientes cadastrados
     * @return Lista de clientes
     * @throws SQLException em caso de erro na operação
     */
    List<Cliente> listarTodos() throws SQLException;
}
