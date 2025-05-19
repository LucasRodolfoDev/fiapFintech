package br.com.matheuscarino.fiapfintech.controller;

import br.com.matheuscarino.fiapfintech.dao.ContaDao;
import br.com.matheuscarino.fiapfintech.model.Conta;
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
import br.com.matheuscarino.fiapfintech.dao.ClienteDao;

@WebServlet("/contas")
public class ContaServlet extends HttpServlet {

    private ContaDao dao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("Inicializando ContaServlet...");
        dao = DaoFactory.getContaDao();
        System.out.println("ContaServlet inicializado com sucesso");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Recebendo requisição POST em ContaServlet");
        String acao = req.getParameter("acao");
        if (acao == null) {
            acao = "cadastrar";
        }
        System.out.println("Ação POST: " + acao);

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
        System.out.println("Iniciando cadastro de conta...");
        Long clienteId = Long.parseLong(req.getParameter("clienteId"));
        int tipoConta = Integer.parseInt(req.getParameter("tipo"));
        double saldo = Double.parseDouble(req.getParameter("saldo"));
        boolean status = "1".equals(req.getParameter("status"));
        LocalDate dataCriacao = LocalDate.now();

        Conta conta = new Conta(clienteId, tipoConta, saldo, status, dataCriacao);
        try {
            dao.cadastrar(conta);
            req.setAttribute("mensagem", "Conta cadastrada com sucesso");
            System.out.println("Conta cadastrada com sucesso");
        } catch (DBException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao cadastrar conta");
            System.out.println("Erro ao cadastrar conta: " + e.getMessage());
        }

        req.getRequestDispatcher("cadastrar-conta.jsp").forward(req, resp);
    }

    private void editar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Iniciando edição de conta...");
        Long id = Long.parseLong(req.getParameter("id"));
        Long clienteId = Long.parseLong(req.getParameter("clienteId"));
        int tipoConta = Integer.parseInt(req.getParameter("tipo"));
        double saldo = Double.parseDouble(req.getParameter("saldo"));
        boolean status = "1".equals(req.getParameter("status"));
        LocalDate dataCriacao = LocalDate.parse(req.getParameter("dataCriacao"));

        Conta conta = new Conta(clienteId, tipoConta, saldo, status, dataCriacao);
        conta.setId(id);
        try {
            dao.atualizar(conta);
            req.setAttribute("mensagem", "Conta atualizada com sucesso");
            System.out.println("Conta atualizada com sucesso");
        } catch (DBException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao atualizar conta");
            System.out.println("Erro ao atualizar conta: " + e.getMessage());
        }
        listar(req, resp);
    }

    private void remover(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Iniciando remoção de conta...");
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            dao.remover(id);
            req.setAttribute("mensagem", "Conta removida com sucesso");
            System.out.println("Conta removida com sucesso");
        } catch (DBException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao remover conta");
            System.out.println("Erro ao remover conta: " + e.getMessage());
        }
        listar(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Recebendo requisição GET em ContaServlet");
        String acao = req.getParameter("acao");
        if (acao == null) {
            acao = "listar";
        }
        System.out.println("Ação GET: " + acao);

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
        System.out.println("Abrindo formulário de edição...");
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            Conta conta = dao.buscar(id);
            req.setAttribute("conta", conta);
            
            // Carregar lista de clientes
            ClienteDao clienteDao = DaoFactory.getClienteDao();
            req.setAttribute("clientes", clienteDao.listar());
            
            System.out.println("Conta encontrada para edição: " + conta.getId());
            req.getRequestDispatcher("editar-conta.jsp").forward(req, resp);

        } catch (DBException e) {
            e.printStackTrace();
            System.out.println("Erro ao buscar conta: " + e.getMessage());
            req.setAttribute("erro", "Erro ao buscar conta: " + e.getMessage());
            req.getRequestDispatcher("listar-contas.jsp").forward(req, resp);
        }
    }

    private void listar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Iniciando listagem de contas...");
        try {
            List<Conta> contas = dao.listar();
            System.out.println("Total de contas encontradas: " + (contas != null ? contas.size() : 0));
            req.setAttribute("contas", contas);
            System.out.println("Redirecionando para listar-contas.jsp");
            req.getRequestDispatcher("listar-contas.jsp").forward(req, resp);
        } catch (DBException e) {
            e.printStackTrace();
            System.out.println("Erro ao listar contas: " + e.getMessage());
            req.setAttribute("erro", "Erro ao listar contas: " + e.getMessage());
            req.getRequestDispatcher("listar-contas.jsp").forward(req, resp);
        }
    }
} 