package br.com.matheuscarino.fiapfintech.dao.impl;

import br.com.matheuscarino.fiapfintech.dao.ClienteDao;
import br.com.matheuscarino.fiapfintech.dao.ConnectionManager;
import br.com.matheuscarino.fiapfintech.exception.DBException;
import br.com.matheuscarino.fiapfintech.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;




public class OracleClienteDao implements ClienteDao {
    
    private Connection conexao;
    @Override
    public void cadastrar(Cliente cliente) throws DBException {
        PreparedStatement stmt = null;
        
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
            e.printStackTrace();
            throw new DBException("Erro ao criar cliente", e);
        } finally {
            try {
                stmt.close();
                System.out.println("Statement fechado");
                conexao.close();
                System.out.println("Conexão fechada");
                System.out.println("Cliente cadastrado com sucesso");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void atualizar(Cliente cliente) throws DBException {
        PreparedStatement stmt = null;

        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "UPDATE FINTECH_CLIENTES SET NOME = ?, CPF = ?, EMAIL = ?, TELEFONE = ?, ENDERECO = ?, DATA_NASCIMENTO = ?, STATUS = ? WHERE ID = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getEndereco());
            stmt.setDate(6, Date.valueOf(cliente.getDataNascimento()));
            stmt.setBoolean(7, cliente.getStatus());
            stmt.setLong(8, cliente.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Erro ao atualizar cliente", e);
        } finally {
            try {
                stmt.close();
                System.out.println("Statement fechado");
                conexao.close();
                System.out.println("Conexão fechada");
                System.out.println("Cliente atualizado com sucesso");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void remover(int id) throws DBException {
        PreparedStatement stmt = null;

        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "DELETE FROM FINTECH_CLIENTES WHERE ID = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Erro ao remover cliente", e);
        } finally {
            try {
                stmt.close();
                System.out.println("Statement fechado");
                conexao.close();
                System.out.println("Conexão fechada");
                System.out.println("Cliente removido com sucesso");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public Cliente buscar(int id) throws DBException {
        PreparedStatement stmt = null;
        Cliente cliente = null;
        ResultSet rs = null;

        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM FINTECH_CLIENTES WHERE ID = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getLong("ID"));
                cliente.setNome(rs.getString("NOME"));
                cliente.setCpf(rs.getString("CPF"));
                cliente.setEmail(rs.getString("EMAIL"));
                cliente.setTelefone(rs.getString("TELEFONE"));
                cliente.setEndereco(rs.getString("ENDERECO"));
                cliente.setDataNascimento(rs.getDate("DATA_NASCIMENTO").toLocalDate());
                cliente.setStatus(rs.getBoolean("STATUS"));
            }

        } catch (SQLException e) {
            throw new DBException("Erro ao buscar cliente por ID", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conexao != null) conexao.close();
                System.out.println("Conexão e recursos fechados");
                System.out.println("Cliente encontrado com sucesso");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cliente;
    }

    @Override
    public List<Cliente> listar() throws DBException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Cliente> lista = new ArrayList<>();

        try {
            System.out.println("Obtendo conexão com o banco de dados...");
            conexao = ConnectionManager.getInstance().getConnection();
            System.out.println("Conexão obtida com sucesso");
            
            String sql = "SELECT * FROM FINTECH_CLIENTES ORDER BY ID";
            System.out.println("Executando query: " + sql);
            stmt = conexao.prepareStatement(sql);
            rs = stmt.executeQuery();
            System.out.println("Query executada com sucesso");

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getLong("ID"));
                cliente.setNome(rs.getString("NOME"));
                cliente.setCpf(rs.getString("CPF"));
                cliente.setEmail(rs.getString("EMAIL"));
                cliente.setTelefone(rs.getString("TELEFONE"));
                cliente.setEndereco(rs.getString("ENDERECO"));
                cliente.setDataNascimento(rs.getDate("DATA_NASCIMENTO").toLocalDate());
                cliente.setStatus(rs.getBoolean("STATUS"));
                lista.add(cliente);
                System.out.println("Cliente adicionado à lista: " + cliente.getNome());
            }
            System.out.println("Total de clientes encontrados: " + lista.size());

        } catch (SQLException e) {
            System.out.println("Erro SQL ao listar clientes: " + e.getMessage());
            throw new DBException("Erro ao listar todos os clientes", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conexao != null) conexao.close();
                System.out.println("Conexão e recursos fechados");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return lista;
    }
}