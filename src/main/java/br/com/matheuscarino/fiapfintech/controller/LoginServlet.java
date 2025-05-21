package br.com.matheuscarino.fiapfintech.controller;

import br.com.matheuscarino.fiapfintech.dao.UsuarioDao;
import br.com.matheuscarino.fiapfintech.factory.DaoFactory;
import br.com.matheuscarino.fiapfintech.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    private UsuarioDao usuarioDao;
    
    @Override
    public void init() throws ServletException {
        usuarioDao = DaoFactory.getUsuarioDao();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        
        try {
            Usuario usuario = new Usuario(email, senha, null);
            
            if (usuarioDao.validarUsuario(usuario)) {
                usuario = usuarioDao.buscarPorEmail(email);
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario.getEmail());
                session.setAttribute("tipoUsuario", usuario.getTipoUsuario());
                session.setAttribute("usuarioId", usuario.getId());
                
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }
            
            request.setAttribute("erro", "E-mail ou senha inválidos");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("erro", "Erro ao realizar login: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
