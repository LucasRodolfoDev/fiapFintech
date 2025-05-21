package br.com.matheuscarino.fiapfintech.controller;

import br.com.matheuscarino.fiapfintech.dao.ClienteDao;
import br.com.matheuscarino.fiapfintech.dao.ContaDao;
import br.com.matheuscarino.fiapfintech.dao.TransferenciaDao;
import br.com.matheuscarino.fiapfintech.exception.DBException;
import br.com.matheuscarino.fiapfintech.factory.DaoFactory;
import br.com.matheuscarino.fiapfintech.model.Cliente;
import br.com.matheuscarino.fiapfintech.model.Conta;
import br.com.matheuscarino.fiapfintech.model.Transferencia;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/extrato")
public class ExtratoServlet extends HttpServlet {
    
    private TransferenciaDao transferenciaDao;
    private ContaDao contaDao;
    private ClienteDao clienteDao;
    
    @Override
    public void init() throws ServletException {
        transferenciaDao = DaoFactory.getTransferenciaDao();
        contaDao = DaoFactory.getContaDao();
        clienteDao = DaoFactory.getClienteDao();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String acao = request.getParameter("acao");
        
        if (acao == null) {
            acao = "listar";
        }
        
        try {
            switch (acao) {
                case "listar":
                    listarTransferencias(request, response);
                    break;
                default:
                    response.sendRedirect("index.jsp");
            }
        } catch (Exception e) {
            request.setAttribute("erro", "Erro ao processar a requisição: " + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        }
    }
    
    private void listarTransferencias(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DBException {
        HttpSession session = request.getSession();
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        Long usuarioId = (Long) session.getAttribute("usuarioId");
        
        // Parâmetro opcional para filtrar por conta específica
        String contaIdParam = request.getParameter("contaId");
        Integer contaId = null;
        if (contaIdParam != null && !contaIdParam.isEmpty()) {
            contaId = Integer.parseInt(contaIdParam);
        }
        
        // Parâmetro opcional para filtrar por cliente específico (apenas para gerentes)
        String clienteIdParam = request.getParameter("clienteId");
        Integer clienteId = null;
        if (clienteIdParam != null && !clienteIdParam.isEmpty()) {
            clienteId = Integer.parseInt(clienteIdParam);
        }
        
        List<Transferencia> transferencias = new ArrayList<>();
        List<Cliente> clientes = new ArrayList<>();
        Map<Long, Conta> contasMap = new HashMap<>();
        
        // Verificar permissões e obter transferências
        if ("gerente".equals(tipoUsuario)) {
            // Gerente pode ver transferências de qualquer cliente
            if (clienteId != null) {
                // Filtrar por cliente específico
                transferencias = transferenciaDao.listarPorCliente(clienteId);
            } else if (contaId != null) {
                // Filtrar por conta específica
                transferencias = transferenciaDao.listarPorConta(contaId);
            } else {
                // Listar todas as transferências
                transferencias = transferenciaDao.listar();
            }
            
            // Carregar lista de clientes para o gerente poder selecionar
            clientes = clienteDao.listar();
            
        } else {
            // Cliente só pode ver suas próprias transferências
            if (contaId != null) {
                // Verificar se a conta pertence ao cliente
                Conta conta = contaDao.buscar(contaId);
                if (conta != null && conta.getClienteId().equals(usuarioId)) {
                    transferencias = transferenciaDao.listarPorConta(contaId);
                } else {
                    request.setAttribute("erro", "Você não tem permissão para acessar esta conta");
                    request.getRequestDispatcher("erro.jsp").forward(request, response);
                    return;
                }
            } else {
                // Listar todas as transferências do cliente
                transferencias = transferenciaDao.listarPorCliente(usuarioId.intValue());
            }
        }
        
        // Carregar informações das contas para exibição
        for (Transferencia transferencia : transferencias) {
            Long origemId = transferencia.getContaOrigemId();
            Long destinoId = transferencia.getContaDestinoId();
            
            if (!contasMap.containsKey(origemId)) {
                Conta conta = contaDao.buscar(origemId.intValue());
                contasMap.put(origemId, conta);
            }
            
            if (!contasMap.containsKey(destinoId)) {
                Conta conta = contaDao.buscar(destinoId.intValue());
                contasMap.put(destinoId, conta);
            }
        }
        
        // Carregar contas do usuário para o filtro
        List<Conta> contasUsuario;
        if ("gerente".equals(tipoUsuario)) {
            if (clienteId != null) {
                contasUsuario = contaDao.listarPorCliente(clienteId);
            } else {
                contasUsuario = new ArrayList<>();
            }
        } else {
            contasUsuario = contaDao.listarPorCliente(usuarioId.intValue());
        }
        
        // Preparar mapa de clientes para exibição dos nomes
        Map<Long, Cliente> clientesMap = new HashMap<>();
        for (Conta conta : contasMap.values()) {
            if (!clientesMap.containsKey(conta.getClienteId())) {
                Cliente cliente = clienteDao.buscar(conta.getClienteId().intValue());
                clientesMap.put(conta.getClienteId(), cliente);
            }
        }
        
        request.setAttribute("transferencias", transferencias);
        request.setAttribute("contasMap", contasMap);
        request.setAttribute("clientesMap", clientesMap);
        request.setAttribute("contasUsuario", contasUsuario);
        request.setAttribute("clientes", clientes);
        request.setAttribute("clienteIdSelecionado", clienteId);
        request.setAttribute("contaIdSelecionada", contaId);
        
        request.getRequestDispatcher("extrato.jsp").forward(request, response);
    }
}