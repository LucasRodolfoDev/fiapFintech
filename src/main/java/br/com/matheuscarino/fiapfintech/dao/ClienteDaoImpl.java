package br.com.matheuscarino.fiapfintech.dao;

import br.com.matheuscarino.fiapfintech.model.Cliente;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDaoImpl implements ClienteDao {

    private static final String SQL_INSERT = 
        "INSERT INTO FINTECH_CLIENTES (NOME, CPF, EMAIL, TELEFONE, ENDERECO, DATA_NASCIMENTO, STATUS) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_UPDATE = 
        "UPDATE FINTECH_CLIENTES SET NOME = ?, CPF = ?, EMAIL = ?, TELEFONE = ?, " +
        "ENDERECO = ?, DATA_NASCIMENTO = ?, STATUS = ? WHERE ID = ?";
    
    private static final String SQL_DELETE = 
        "DELETE FROM FINTECH_CLIENTES WHERE ID = ?";
    
    private static final String SQL_SELECT_BY_ID = 
        "SELECT * FROM FINTECH_CLIENTES WHERE ID = ?";
    
    private static final String SQL_SELECT_ALL = 
        "SELECT * FROM FINTECH_CLIENTES";

    @Override
    public Cliente cadastrar(Cliente cliente) throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getEndereco());
            stmt.setDate(6, Date.valueOf(cliente.getDataNascimento()));
            stmt.setBoolean(7, cliente.getStatus());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Falha ao cadastrar cliente, nenhuma linha afetada.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cliente.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Falha ao cadastrar cliente, nenhum ID gerado.");
                }
            }
            
            return cliente;
        }
    }

    @Override
    public boolean atualizar(Cliente cliente) throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getEndereco());
            stmt.setDate(6, Date.valueOf(cliente.getDataNascimento()));
            stmt.setBoolean(7, cliente.getStatus());
            stmt.setLong(8, cliente.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean remover(Long id) throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public Cliente buscarPorId(Long id) throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
            return null;
        }
    }

    @Override
    public List<Cliente> listarTodos() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                clientes.add(mapearCliente(rs));
            }
        }
        
        return clientes;
    }

    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getLong("ID"));
        cliente.setNome(rs.getString("NOME"));
        cliente.setCpf(rs.getString("CPF"));
        cliente.setEmail(rs.getString("EMAIL"));
        cliente.setTelefone(rs.getString("TELEFONE"));
        cliente.setEndereco(rs.getString("ENDERECO"));
        cliente.setDataNascimento(rs.getDate("DATA_NASCIMENTO").toLocalDate());
        cliente.setStatus(rs.getBoolean("STATUS"));
        return cliente;
    }
} 