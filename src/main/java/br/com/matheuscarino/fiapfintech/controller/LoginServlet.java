package br.com.matheuscarino.fiapfintech.controller;

import br.com.matheuscarino.fiapfintech.dao.UsuarioDao;
import br.com.matheuscarino.fiapfintech.exception.EmailException;
import br.com.matheuscarino.fiapfintech.factory.DaoFactory;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
import br.com.matheuscarino.fiapfintech.bo.EmailBo;
import br.com.matheuscarino.fiapfintech.model.Usuario;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UsuarioDao dao;
    private EmailBo bo;

    public LoginServlet() {
        dao = DaoFactory.getUsuarioDao();
        bo = new EmailBo();
    }

    @Override
    protected void doPost(
        HttpServletRequest request, 
        HttpServletResponse response) throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        System.out.println("Tentativa de login - Email: " + email);

        Usuario usuario = new Usuario(email, senha);

        if (dao.validarUsuario(usuario)) {
            System.out.println("Login bem-sucedido para: " + email);
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
            request.setAttribute("usuario", usuario);
            request.getRequestDispatcher("/home.jsp").forward(request, response);
            try {
                bo.enviarEmail(email, "Login realizado com sucesso", "Você realizou um login no sistema Fintech");
            } catch (EmailException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Falha no login para: " + email);
            request.setAttribute("erro", "Email ou senha inválidos");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } 
    }

    protected void doGet(
        HttpServletRequest request, 
        HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/home.jsp");
    }
}
