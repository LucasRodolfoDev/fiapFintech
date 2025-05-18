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
import java.util.List;


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
        }
    }

    private void cadastrar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

    private void editar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        String nome = req.getParameter("nome");
        String cpf = req.getParameter("cpf");
        String email = req.getParameter("email");
        String telefone = req.getParameter("telefone");
        String endereco = req.getParameter("endereco");
        LocalDate dataNascimento = LocalDate.parse(req.getParameter("dataNascimento"));
        boolean status = Boolean.parseBoolean(req.getParameter("status"));

        Cliente cliente = new Cliente(nome, cpf, email, telefone, endereco, dataNascimento, status);
        cliente.setId(id);
        try {
            dao.atualizar(cliente);
            req.setAttribute("mensagem", "Cliente atualizado com sucesso");
        } catch (DBException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao atualizar cliente");
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
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            Cliente cliente = dao.buscar(id);
            req.setAttribute("cliente", cliente);
            req.getRequestDispatcher("editar-cliente.jsp").forward(req, resp);

        } catch (DBException e) {
            e.printStackTrace();
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
            req.setAttribute("erro", "Erro ao buscar cliente: " + e.getMessage());
            req.getRequestDispatcher("listar-clientes.jsp").forward(req, resp);

        }
    }

    private void listar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            System.out.println("Iniciando listagem de clientes...");
            List<Cliente> clientes = dao.listar();
            System.out.println("Número de clientes encontrados: " + (clientes != null ? clientes.size() : 0));
            req.setAttribute("clientes", clientes);
            req.getRequestDispatcher("listar-clientes.jsp").forward(req, resp);
        } catch (DBException e) {
            e.printStackTrace();
            System.out.println("Erro ao listar clientes: " + e.getMessage());
            req.setAttribute("erro", "Erro ao listar clientes: " + e.getMessage());
            req.getRequestDispatcher("listar-clientes.jsp").forward(req, resp);
        }
    }
}
