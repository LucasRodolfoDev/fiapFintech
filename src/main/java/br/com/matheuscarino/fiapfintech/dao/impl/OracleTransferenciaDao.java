package br.com.matheuscarino.fiapfintech.dao.impl;

import br.com.matheuscarino.fiapfintech.dao.TransferenciaDao;
import br.com.matheuscarino.fiapfintech.dao.ConnectionManager;
import br.com.matheuscarino.fiapfintech.exception.DBException;
import br.com.matheuscarino.fiapfintech.model.Transferencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OracleTransferenciaDao implements TransferenciaDao {
    
    private Connection conexao;
    
    @Override
    public void cadastrar(Transferencia transferencia) throws DBException {
        PreparedStatement stmt = null;
        
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "INSERT INTO FINTECH_TRANSFERENCIAS (CONTA_ORIGEM_ID, CONTA_DESTINO_ID, VALOR, DATA_TRANSFERENCIA) VALUES (?, ?, ?, ?)";
            stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, transferencia.getContaOrigemId());
            stmt.setLong(2, transferencia.getContaDestinoId());
            stmt.setDouble(3, transferencia.getValor());
            stmt.setTimestamp(4, Timestamp.valueOf(transferencia.getDataTransferencia()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Erro ao registrar transferência", e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Transferencia buscar(int id) throws DBException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Transferencia transferencia = null;

        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM FINTECH_TRANSFERENCIAS WHERE ID = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                transferencia = new Transferencia();
                transferencia.setId(rs.getLong("ID"));
                transferencia.setContaOrigemId(rs.getLong("CONTA_ORIGEM_ID"));
                transferencia.setContaDestinoId(rs.getLong("CONTA_DESTINO_ID"));
                transferencia.setValor(rs.getDouble("VALOR"));
                transferencia.setDataTransferencia(rs.getTimestamp("DATA_TRANSFERENCIA").toLocalDateTime());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Erro ao buscar transferência", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return transferencia;
    }

    @Override
    public List<Transferencia> listar() throws DBException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Transferencia> lista = new ArrayList<>();

        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM FINTECH_TRANSFERENCIAS ORDER BY DATA_TRANSFERENCIA DESC";
            stmt = conexao.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Transferencia transferencia = new Transferencia();
                transferencia.setId(rs.getLong("ID"));
                transferencia.setContaOrigemId(rs.getLong("CONTA_ORIGEM_ID"));
                transferencia.setContaDestinoId(rs.getLong("CONTA_DESTINO_ID"));
                transferencia.setValor(rs.getDouble("VALOR"));
                transferencia.setDataTransferencia(rs.getTimestamp("DATA_TRANSFERENCIA").toLocalDateTime());
                lista.add(transferencia);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Erro ao listar transferências", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }

    @Override
    public List<Transferencia> listarPorConta(int contaId) throws DBException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Transferencia> lista = new ArrayList<>();

        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM FINTECH_TRANSFERENCIAS WHERE CONTA_ORIGEM_ID = ? OR CONTA_DESTINO_ID = ? ORDER BY DATA_TRANSFERENCIA DESC";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, contaId);
            stmt.setInt(2, contaId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Transferencia transferencia = new Transferencia();
                transferencia.setId(rs.getLong("ID"));
                transferencia.setContaOrigemId(rs.getLong("CONTA_ORIGEM_ID"));
                transferencia.setContaDestinoId(rs.getLong("CONTA_DESTINO_ID"));
                transferencia.setValor(rs.getDouble("VALOR"));
                transferencia.setDataTransferencia(rs.getTimestamp("DATA_TRANSFERENCIA").toLocalDateTime());
                lista.add(transferencia);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Erro ao listar transferências da conta", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }

    @Override
    public List<Transferencia> listarPorCliente(int clienteId) throws DBException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Transferencia> lista = new ArrayList<>();

        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT t.* FROM FINTECH_TRANSFERENCIAS t " +
                         "JOIN FINTECH_CONTAS co ON t.CONTA_ORIGEM_ID = co.ID " +
                         "JOIN FINTECH_CONTAS cd ON t.CONTA_DESTINO_ID = cd.ID " +
                         "WHERE co.CLIENTE_ID = ? OR cd.CLIENTE_ID = ? " +
                         "ORDER BY t.DATA_TRANSFERENCIA DESC";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, clienteId);
            stmt.setInt(2, clienteId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Transferencia transferencia = new Transferencia();
                transferencia.setId(rs.getLong("ID"));
                transferencia.setContaOrigemId(rs.getLong("CONTA_ORIGEM_ID"));
                transferencia.setContaDestinoId(rs.getLong("CONTA_DESTINO_ID"));
                transferencia.setValor(rs.getDouble("VALOR"));
                transferencia.setDataTransferencia(rs.getTimestamp("DATA_TRANSFERENCIA").toLocalDateTime());
                lista.add(transferencia);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Erro ao listar transferências do cliente", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }
}