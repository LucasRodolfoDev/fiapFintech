package br.com.matheuscarino.fiapfintech.controller;

import br.com.matheuscarino.fiapfintech.dao.ClienteDao;
import br.com.matheuscarino.fiapfintech.model.Cliente;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

        // Verifica permissões
        if (!verificarPermissao(req, resp, acao)) {
            return;
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

    private boolean verificarPermissao(HttpServletRequest req, HttpServletResponse resp, String acao) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return false;
        }

        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        Long usuarioId = (Long) session.getAttribute("usuarioId");

        // Gerentes têm acesso total
        if (tipoUsuario.equals("gerente")) {
            return true;
        }

        // Clientes só podem editar seus próprios dados
        if (tipoUsuario.equals("cliente")) {
            if (acao.equals("editar") || acao.equals("remover")) {
                String idParam = req.getParameter("id");
                if (idParam != null) {
                    Long id = Long.parseLong(idParam);
                    if (!id.equals(usuarioId)) {
                        resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado");
                        return false;
                    }
                }
            }
            return true;
        }

        resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado");
        return false;
    }

    private void cadastrar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nome = req.getParameter("nome");
        String cpf = req.getParameter("cpf");
        String email = req.getParameter("email");
        String telefone = req.getParameter("telefone");
        String endereco = req.getParameter("endereco");
        LocalDate dataNascimento = LocalDate.parse(req.getParameter("dataNascimento"));
        boolean status = "1".equals(req.getParameter("status"));

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
        boolean status = "1".equals(req.getParameter("status"));

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

    private void remover(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            dao.remover(id);
            req.setAttribute("mensagem", "Cliente removido com sucesso");
        } catch (DBException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao remover cliente");
        }
        listar(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");
        if (acao == null) {
            acao = "listar";
        }

        // Verifica permissões
        if (!verificarPermissao(req, resp, acao)) {
            return;
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

    private void listar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession(false);
            String tipoUsuario = (String) session.getAttribute("tipoUsuario");
            Long usuarioId = (Long) session.getAttribute("usuarioId");

            List<Cliente> clientes;
            if (tipoUsuario.equals("cliente")) {
                // Cliente só vê seus próprios dados
                Cliente cliente = dao.buscar(usuarioId.intValue());
                clientes = List.of(cliente);
            } else {
                // Gerente vê todos os clientes
                clientes = dao.listar();
            }

            req.setAttribute("clientes", clientes);
            req.getRequestDispatcher("listar-clientes.jsp").forward(req, resp);
        } catch (DBException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao listar clientes");
            req.getRequestDispatcher("listar-clientes.jsp").forward(req, resp);
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
}
