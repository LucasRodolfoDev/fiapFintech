package br.com.matheuscarino.fiapfintech.controller;

import br.com.matheuscarino.fiapfintech.dao.UsuarioDao;
import br.com.matheuscarino.fiapfintech.exception.DBException;
import br.com.matheuscarino.fiapfintech.factory.DaoFactory;
import br.com.matheuscarino.fiapfintech.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

    private UsuarioDao dao;

    @Override
    public void init() throws ServletException {
        dao = DaoFactory.getUsuarioDao();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");
        if (acao == null) {
            acao = "cadastrar";
        }

        switch (acao) {
            case "cadastrar":
                cadastrar(req, resp);
                break;
            case "editar":
                editar(req, resp);
                break;
            case "remover":
                remover(req, resp);
                break;
        }
    }

    private void cadastrar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");
        String tipoUsuario = req.getParameter("tipoUsuario");

        Usuario usuario = new Usuario(email, senha, tipoUsuario);
        try {
            dao.cadastrar(usuario);
            req.setAttribute("mensagem", "Usuário cadastrado com sucesso");
        } catch (DBException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao cadastrar usuário");
        }

        req.getRequestDispatcher("cadastrar-usuario.jsp").forward(req, resp);
    }

    private void editar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");
        String tipoUsuario = req.getParameter("tipoUsuario");

        Usuario usuario = new Usuario(email, senha, tipoUsuario);
        usuario.setId(id);
        try {
            dao.atualizar(usuario);
            req.setAttribute("mensagem", "Usuário atualizado com sucesso");
        } catch (DBException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao atualizar usuário");
        }
        listar(req, resp);
    }

    private void remover(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        try {
            dao.remover(id);
            req.setAttribute("mensagem", "Usuário removido com sucesso");
        } catch (DBException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao remover usuário");
        }
        listar(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");
        if (acao == null) {
            acao = "listar";
        }

        switch (acao) {
            case "listar":
                listar(req, resp);
                break;
            case "abrir-form-edicao":
                abrirForm(req, resp);
                break;
        }
    }

    private void abrirForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        try {
            Usuario usuario = dao.buscar(id);
            req.setAttribute("usuario", usuario);
            req.getRequestDispatcher("editar-usuario.jsp").forward(req, resp);
        } catch (DBException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao buscar usuário");
            req.getRequestDispatcher("listar-usuarios.jsp").forward(req, resp);
        }
    }

    private void listar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Usuario> usuarios = dao.listar();
            req.setAttribute("usuarios", usuarios);
            req.getRequestDispatcher("listar-usuarios.jsp").forward(req, resp);
        } catch (DBException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao listar usuários");
            req.getRequestDispatcher("listar-usuarios.jsp").forward(req, resp);
        }
    }
} 