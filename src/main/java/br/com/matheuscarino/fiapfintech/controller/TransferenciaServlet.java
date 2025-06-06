package br.com.matheuscarino.fiapfintech.controller;

import br.com.matheuscarino.fiapfintech.dao.ContaDao;
import br.com.matheuscarino.fiapfintech.dao.UsuarioDao;
import br.com.matheuscarino.fiapfintech.dao.ClienteDao;
import br.com.matheuscarino.fiapfintech.dao.TransferenciaDao;
import br.com.matheuscarino.fiapfintech.exception.DBException;
import br.com.matheuscarino.fiapfintech.factory.DaoFactory;
import br.com.matheuscarino.fiapfintech.model.Conta;
import br.com.matheuscarino.fiapfintech.model.Usuario;
import br.com.matheuscarino.fiapfintech.model.Cliente;
import br.com.matheuscarino.fiapfintech.model.Transferencia;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@WebServlet("/transferencia")
public class TransferenciaServlet extends HttpServlet {

    private ContaDao contaDao;
    private UsuarioDao usuarioDao;
    private ClienteDao clienteDao;
    private TransferenciaDao transferenciaDao;

    @Override
    public void init() throws ServletException {
        contaDao = DaoFactory.getContaDao();
        usuarioDao = DaoFactory.getUsuarioDao();
        clienteDao = DaoFactory.getClienteDao();
        transferenciaDao = DaoFactory.getTransferenciaDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String acao = request.getParameter("acao");

        if (acao == null) {
            acao = "abrir-form";
        }

        try {
            switch (acao) {
                case "abrir-form":
                    abrirFormTransferencia(request, response);
                    break;
                default:
                    response.sendRedirect("index.jsp");
            }
        } catch (Exception e) {
            request.setAttribute("erro", "Erro ao processar a requisição: " + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String acao = request.getParameter("acao");

        try {
            switch (acao) {
                case "transferir":
                    realizarTransferencia(request, response);
                    break;
                default:
                    response.sendRedirect("index.jsp");
            }
        } catch (Exception e) {
            request.setAttribute("erro", "Erro ao processar a requisição: " + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        }
    }

    private void abrirFormTransferencia(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DBException {
        HttpSession session = request.getSession();
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        Long usuarioId = (Long) session.getAttribute("usuarioId");

        List<Conta> contasOrigem;
        if ("gerente".equals(tipoUsuario)) {
            contasOrigem = contaDao.listar();
        } else {
            contasOrigem = contaDao.listarPorCliente(usuarioId.intValue());
        }

        List<Conta> contasDestino = contaDao.listar();

        // Buscar os dados dos clientes para todas as contas
        Map<Long, Cliente> clientesMap = new HashMap<>();
        for (Conta conta : contasOrigem) {
            if (!clientesMap.containsKey(conta.getClienteId())) {
                Cliente cliente = clienteDao.buscar(conta.getClienteId().intValue());
                clientesMap.put(conta.getClienteId(), cliente);
            }
        }
        for (Conta conta : contasDestino) {
            if (!clientesMap.containsKey(conta.getClienteId())) {
                Cliente cliente = clienteDao.buscar(conta.getClienteId().intValue());
                clientesMap.put(conta.getClienteId(), cliente);
            }
        }

        request.setAttribute("contasOrigem", contasOrigem);
        request.setAttribute("contasDestino", contasDestino);
        request.setAttribute("clientesMap", clientesMap);
        request.getRequestDispatcher("transferencia.jsp").forward(request, response);
    }

    private void realizarTransferencia(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DBException {
        Long contaOrigemId = Long.parseLong(request.getParameter("contaOrigem"));
        Long contaDestinoId = Long.parseLong(request.getParameter("contaDestino"));
        double valor = Double.parseDouble(request.getParameter("valor"));

        HttpSession session = request.getSession();
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        Long usuarioId = (Long) session.getAttribute("usuarioId");

        // Buscar as contas
        Conta contaOrigem = contaDao.buscar(contaOrigemId.intValue());
        Conta contaDestino = contaDao.buscar(contaDestinoId.intValue());

        // Validar se o usuário tem permissão para transferir da conta origem
        if (!"gerente".equals(tipoUsuario) && !contaOrigem.getClienteId().equals(usuarioId)) {
            request.setAttribute("erro", "Você não tem permissão para transferir desta conta");
            request.getRequestDispatcher("transferencia.jsp").forward(request, response);
            return;
        }

        // Validar se há saldo suficiente
        if (contaOrigem.getSaldo() < valor) {
            request.setAttribute("erro", "Saldo insuficiente para realizar a transferência");
            request.getRequestDispatcher("transferencia.jsp").forward(request, response);
            return;
        }

        // Realizar a transferência
        contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
        contaDestino.setSaldo(contaDestino.getSaldo() + valor);

        contaDao.atualizar(contaOrigem);
        contaDao.atualizar(contaDestino);

        // Registrar a transferência na tabela FINTECH_TRANSFERENCIAS
        Transferencia transferencia = new Transferencia(
            contaOrigemId, 
            contaDestinoId, 
            valor, 
            LocalDateTime.now()
        );
        transferenciaDao.cadastrar(transferencia);

        request.setAttribute("mensagem", "Transferência realizada com sucesso!");
        response.sendRedirect("contas?acao=listar");
    }
} 
