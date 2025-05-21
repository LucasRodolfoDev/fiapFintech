package br.com.matheuscarino.fiapfintech.dao.impl;

import br.com.matheuscarino.fiapfintech.dao.ConnectionManager;
import br.com.matheuscarino.fiapfintech.dao.UsuarioDao;
import br.com.matheuscarino.fiapfintech.exception.DBException;
import br.com.matheuscarino.fiapfintech.model.Usuario;
import br.com.matheuscarino.fiapfintech.util.CriptografiaUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OracleUsuarioDao implements UsuarioDao {
    private Connection conexao;
    
    @Override
    public boolean validarUsuario(Usuario usuario) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean resultado = false;
        
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM FINTECH_USUARIOS WHERE EMAIL = ? AND SENHA = ?";

            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getSenha());
            rs = stmt.executeQuery();

            resultado = rs.next();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultado;
    }

    @Override
    public void cadastrar(Usuario usuario) throws DBException {
        PreparedStatement stmt = null;
        
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "INSERT INTO FINTECH_USUARIOS (EMAIL, SENHA, TIPO_USUARIO) VALUES (?, ?, ?)";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getTipoUsuario());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Erro ao criar usuário", e);
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
    public void atualizar(Usuario usuario) throws DBException {
        PreparedStatement stmt = null;
        
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "UPDATE FINTECH_USUARIOS SET EMAIL = ?, SENHA = ?, TIPO_USUARIO = ? WHERE ID = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getTipoUsuario());
            stmt.setLong(4, usuario.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Erro ao atualizar usuário", e);
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
    public void remover(Long id) throws DBException {
        PreparedStatement stmt = null;
        
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "DELETE FROM FINTECH_USUARIOS WHERE ID = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Erro ao remover usuário", e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Usuario buscar(Long id) throws DBException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;
        
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM FINTECH_USUARIOS WHERE ID = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getLong("ID"));
                usuario.setEmail(rs.getString("EMAIL"));
                usuario.setSenha(rs.getString("SENHA"));
                usuario.setTipoUsuario(rs.getString("TIPO_USUARIO"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Erro ao buscar usuário", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usuario;
    }

    @Override
    public List<Usuario> listar() throws DBException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario> lista = new ArrayList<>();
        
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM FINTECH_USUARIOS ORDER BY ID";
            stmt = conexao.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getLong("ID"));
                usuario.setEmail(rs.getString("EMAIL"));
                usuario.setSenha(rs.getString("SENHA"));
                usuario.setTipoUsuario(rs.getString("TIPO_USUARIO"));
                lista.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Erro ao listar usuários", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }

    @Override
    public Usuario buscarPorEmail(String email) throws DBException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;

        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM FINTECH_USUARIOS WHERE EMAIL = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getLong("ID"));
                usuario.setEmail(rs.getString("EMAIL"));
                usuario.setSenha(rs.getString("SENHA"));
                usuario.setTipoUsuario(rs.getString("TIPO_USUARIO"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Erro ao buscar usuário por email", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usuario;
    }
}
