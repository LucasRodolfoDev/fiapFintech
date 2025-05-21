package br.com.matheuscarino.fiapfintech.dao.impl;

import br.com.matheuscarino.fiapfintech.dao.ContaDao;
import br.com.matheuscarino.fiapfintech.dao.ConnectionManager;
import br.com.matheuscarino.fiapfintech.exception.DBException;
import br.com.matheuscarino.fiapfintech.model.Conta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OracleContaDao implements ContaDao {

    private Connection conexao;

    @Override
    public void cadastrar(Conta conta) throws DBException {
        PreparedStatement stmt = null;

        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "INSERT INTO FINTECH_CONTAS (CLIENTE_ID, TIPO_CONTA, SALDO, STATUS, DATA_CRIACAO) VALUES (?, ?, ?, ?, ?)";
            stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, conta.getClienteId());
            stmt.setInt(2, conta.getTipoConta());
            stmt.setDouble(3, conta.getSaldo());
            stmt.setInt(4, conta.isStatus() ? 1 : 0);
            stmt.setTimestamp(5, Timestamp.valueOf(conta.getDataCriacao()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Erro ao criar conta", e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conexao != null) ConnectionManager.closeConnection(conexao);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void atualizar(Conta conta) throws DBException {
        PreparedStatement stmt = null;

        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "UPDATE FINTECH_CONTAS SET CLIENTE_ID = ?, TIPO_CONTA = ?, SALDO = ?, STATUS = ?, DATA_CRIACAO = ? WHERE ID = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, conta.getClienteId());
            stmt.setInt(2, conta.getTipoConta());
            stmt.setDouble(3, conta.getSaldo());
            stmt.setInt(4, conta.isStatus() ? 1 : 0);
            stmt.setTimestamp(5, Timestamp.valueOf(conta.getDataCriacao()));
            stmt.setLong(6, conta.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Erro ao atualizar conta", e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conexao != null) ConnectionManager.closeConnection(conexao);
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
            String sql = "DELETE FROM FINTECH_CONTAS WHERE ID = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Erro ao remover conta", e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conexao != null) ConnectionManager.closeConnection(conexao);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Conta buscar(int id) throws DBException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Conta conta = null;

        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM FINTECH_CONTAS WHERE ID = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                conta = new Conta();
                conta.setId(rs.getLong("ID"));
                conta.setClienteId(rs.getLong("CLIENTE_ID"));
                conta.setTipoConta(rs.getInt("TIPO_CONTA"));
                conta.setSaldo(rs.getDouble("SALDO"));
                conta.setStatus(rs.getBoolean("STATUS"));
                conta.setDataCriacao(rs.getTimestamp("DATA_CRIACAO").toLocalDateTime());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Erro ao buscar conta", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conexao != null) ConnectionManager.closeConnection(conexao);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conta;
    }

    @Override
    public List<Conta> listar() throws DBException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Conta> lista = new ArrayList<>();

        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM FINTECH_CONTAS ORDER BY ID";
            stmt = conexao.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Conta conta = new Conta();
                conta.setId(rs.getLong("ID"));
                conta.setClienteId(rs.getLong("CLIENTE_ID"));
                conta.setTipoConta(rs.getInt("TIPO_CONTA"));
                conta.setSaldo(rs.getDouble("SALDO"));
                conta.setStatus(rs.getBoolean("STATUS"));
                conta.setDataCriacao(rs.getTimestamp("DATA_CRIACAO").toLocalDateTime());
                lista.add(conta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Erro ao listar contas", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conexao != null) ConnectionManager.closeConnection(conexao);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }

    @Override
    public List<Conta> listarPorCliente(int clienteId) throws DBException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Conta> lista = new ArrayList<>();

        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM FINTECH_CONTAS WHERE CLIENTE_ID = ? ORDER BY ID";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, clienteId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Conta conta = new Conta();
                conta.setId(rs.getLong("ID"));
                conta.setClienteId(rs.getLong("CLIENTE_ID"));
                conta.setTipoConta(rs.getInt("TIPO_CONTA"));
                conta.setSaldo(rs.getDouble("SALDO"));
                conta.setStatus(rs.getBoolean("STATUS"));
                conta.setDataCriacao(rs.getTimestamp("DATA_CRIACAO").toLocalDateTime());
                lista.add(conta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Erro ao listar contas do cliente", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conexao != null) ConnectionManager.closeConnection(conexao);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }
}
