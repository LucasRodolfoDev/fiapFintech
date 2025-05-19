package br.com.matheuscarino.fiapfintech.dao.impl;

import br.com.matheuscarino.fiapfintech.dao.ContaDao;
import br.com.matheuscarino.fiapfintech.dao.ConnectionManager;
import br.com.matheuscarino.fiapfintech.exception.DBException;
import br.com.matheuscarino.fiapfintech.model.Conta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

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
            stmt.setBoolean(4, conta.isStatus());
            stmt.setDate(5, Date.valueOf(conta.getDataCriacao()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Erro ao criar conta", e);
        } finally {
            try {
                stmt.close();
                System.out.println("Statement fechado");
                conexao.close();
                System.out.println("Conexão fechada");
                System.out.println("Conta cadastrada com sucesso");
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
            stmt.setBoolean(4, conta.isStatus());
            stmt.setDate(5, Date.valueOf(conta.getDataCriacao()));
            stmt.setLong(6, conta.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Erro ao atualizar conta", e);
        } finally {
            try {
                stmt.close();
                System.out.println("Statement fechado");
                conexao.close();
                System.out.println("Conexão fechada");
                System.out.println("Conta atualizada com sucesso");
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
            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Erro ao remover conta", e);
        } finally {
            try {
                stmt.close();
                System.out.println("Statement fechado");
                conexao.close();
                System.out.println("Conexão fechada");
                System.out.println("Conta removida com sucesso");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Conta buscar(int id) throws DBException {
        PreparedStatement stmt = null;
        Conta conta = null;
        ResultSet rs = null;

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
                conta.setDataCriacao(rs.getDate("DATA_CRIACAO").toLocalDate());
            }

        } catch (SQLException e) {
            throw new DBException("Erro ao buscar conta por ID", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conexao != null) conexao.close();
                System.out.println("Conexão e recursos fechados");
                System.out.println("Conta encontrada com sucesso");
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
            System.out.println("Obtendo conexão com o banco de dados...");
            conexao = ConnectionManager.getInstance().getConnection();
            System.out.println("Conexão obtida com sucesso");
            
            String sql = "SELECT * FROM FINTECH_CONTAS ORDER BY ID";
            System.out.println("Executando query: " + sql);
            stmt = conexao.prepareStatement(sql);
            rs = stmt.executeQuery();
            System.out.println("Query executada com sucesso");

            while (rs.next()) {
                Conta conta = new Conta();
                conta.setId(rs.getLong("ID"));
                conta.setClienteId(rs.getLong("CLIENTE_ID"));
                conta.setTipoConta(rs.getInt("TIPO_CONTA"));
                conta.setSaldo(rs.getDouble("SALDO"));
                conta.setStatus(rs.getBoolean("STATUS"));
                conta.setDataCriacao(rs.getDate("DATA_CRIACAO").toLocalDate());
                lista.add(conta);
                System.out.println("Conta adicionada à lista: ID " + conta.getId());
            }
            System.out.println("Total de contas encontradas: " + lista.size());

        } catch (SQLException e) {
            System.out.println("Erro SQL ao listar contas: " + e.getMessage());
            throw new DBException("Erro ao listar todas as contas", e);
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