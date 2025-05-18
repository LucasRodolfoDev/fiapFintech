package br.com.matheuscarino.fiapfintech.controller;

import br.com.matheuscarino.fiapfintech.dao.ClienteDao;
import br.com.matheuscarino.fiapfintech.model.Cliente;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import br.com.matheuscarino.fiapfintech.exception.DBException;
import br.com.matheuscarino.fiapfintech.factory.DaoFactory;

import java.io.IOException;

@WebServlet("/clientes")
public class ClienteServlet extends HttpServlet {

    private ClienteDao dao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        dao = DaoFactory.getClienteDao();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nome = req.getParameter("nome");
        String cpf = req.getParameter("cpf");
        String email = req.getParameter("email");
        String telefone = req.getParameter("telefone");
        String endereco = req.getParameter("endereco");
        LocalDate dataNascimento = LocalDate.parse(req.getParameter("dataNascimento"));
        boolean status = Boolean.parseBoolean(req.getParameter("status"));
        
        Cliente cliente = new Cliente(nome, cpf, email, telefone, endereco, dataNascimento, status);
        try {
            dao.cadastrar(cliente);
            req.setAttribute("mensagem", "Cliente cadastrado com sucesso");
        } catch (DBException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao cadastrar cliente");
        }

        req.getRequestDispatcher("cadastrar-cliente.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    }
}
