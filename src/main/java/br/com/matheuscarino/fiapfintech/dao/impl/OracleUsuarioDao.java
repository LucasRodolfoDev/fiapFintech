package br.com.matheuscarino.fiapfintech.dao.impl;

import br.com.matheuscarino.fiapfintech.dao.ConnectionManager;
import br.com.matheuscarino.fiapfintech.dao.UsuarioDao;
import br.com.matheuscarino.fiapfintech.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OracleUsuarioDao implements UsuarioDao {
    private Connection conexao;
    
    @Override
    public boolean validarUsuario(Usuario usuario) {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM FINTECH_USUARIOS WHERE EMAIL = ? AND SENHA = ?";

            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getSenha());
            rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            }
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
        return false;
    }
}
