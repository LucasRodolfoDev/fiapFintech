package br.com.matheuscarino.fiapfintech.controller;

import br.com.matheuscarino.fiapfintech.dao.ContaDao;
import br.com.matheuscarino.fiapfintech.dao.ClienteDao;
import br.com.matheuscarino.fiapfintech.model.Conta;
import br.com.matheuscarino.fiapfintech.model.Cliente;
import br.com.matheuscarino.fiapfintech.exception.DBException;
import br.com.matheuscarino.fiapfintech.factory.DaoFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@WebServlet("")
public class IndexServlet extends HttpServlet {
    
    private ContaDao contaDao;
    private ClienteDao clienteDao;
    
    @Override
    public void init() throws ServletException {
        contaDao = DaoFactory.getContaDao();
        clienteDao = DaoFactory.getClienteDao();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        // Se o usuário estiver logado, busca as contas dele
        if (session != null && session.getAttribute("usuario") != null) {
            String tipoUsuario = (String) session.getAttribute("tipoUsuario");
            Long usuarioId = (Long) session.getAttribute("usuarioId");
            
            try {
                List<Conta> contas;
                if (tipoUsuario.equals("cliente")) {
                    // Cliente só vê suas próprias contas
                    contas = contaDao.listarPorCliente(usuarioId.intValue());
                } else {
                    // Gerente vê todas as contas
                    contas = contaDao.listar();
                }
                
                // Buscar os dados dos clientes para todas as contas
                Map<Long, Cliente> clientesMap = new HashMap<>();
                for (Conta conta : contas) {
                    if (!clientesMap.containsKey(conta.getClienteId())) {
                        Cliente cliente = clienteDao.buscar(conta.getClienteId().intValue());
                        clientesMap.put(conta.getClienteId(), cliente);
                    }
                }
                
                request.setAttribute("contas", contas);
                request.setAttribute("clientesMap", clientesMap);
            } catch (DBException e) {
                e.printStackTrace();
                request.setAttribute("erro", "Erro ao buscar contas: " + e.getMessage());
            }
        }
        
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}