package br.com.matheuscarino.fiapfintech.dao.impl;

import br.com.matheuscarino.fiapfintech.dao.ClienteDao;
import br.com.matheuscarino.fiapfintech.dao.ConnectionManager;
import br.com.matheuscarino.fiapfintech.exception.DBException;
import br.com.matheuscarino.fiapfintech.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

public class OracleClienteDao implements ClienteDao {
    @Override
    public void cadastrar(Cliente cliente) throws DBException {
        PreparedStatement stmt = null;
        Connection conexao = null;
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "INSERT INTO FINTECH_CLIENTES (NOME, CPF, EMAIL, TELEFONE, ENDERECO, DATA_NASCIMENTO, STATUS) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getEndereco());
            stmt.setDate(6, Date.valueOf(cliente.getDataNascimento()));
            stmt.setBoolean(7, cliente.getStatus());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DBException("Erro ao conectar ao banco de dados", e);
        } finally {
            try {
                stmt.close();
                System.out.println("Statement fechado");
                conexao.close();
                System.out.println("Conexão fechada");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void atualizar(Cliente cliente) throws DBException {

    }

    @Override
    public void remover(Cliente cliente) throws DBException {

    }

    @Override
    public Cliente buscar(int id) throws DBException {
        return null;
    }

    @Override
    public List<Cliente> listar() throws DBException {
        return List.of();
    }
}