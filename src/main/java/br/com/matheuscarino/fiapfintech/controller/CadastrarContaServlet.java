package br.com.matheuscarino.fiapfintech.controller;

import br.com.matheuscarino.fiapfintech.dao.ClienteDao;
import br.com.matheuscarino.fiapfintech.exception.DBException;
import br.com.matheuscarino.fiapfintech.factory.DaoFactory;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/cadastrar-conta")
public class CadastrarContaServlet extends HttpServlet {

    private ClienteDao clienteDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        clienteDao = DaoFactory.getClienteDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("clientes", clienteDao.listar());
            req.getRequestDispatcher("cadastrar-conta.jsp").forward(req, resp);
        } catch (DBException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao carregar lista de clientes: " + e.getMessage());
            req.getRequestDispatcher("cadastrar-conta.jsp").forward(req, resp);
        }
    }
} 