package br.com.matheuscarino.fiapfintech.controller;

import br.com.matheuscarino.fiapfintech.dao.ClienteDao;
import br.com.matheuscarino.fiapfintech.dao.ContaDao;
import br.com.matheuscarino.fiapfintech.dao.UsuarioDao;
import br.com.matheuscarino.fiapfintech.exception.DBException;
import br.com.matheuscarino.fiapfintech.factory.DaoFactory;
import br.com.matheuscarino.fiapfintech.model.Cliente;
import br.com.matheuscarino.fiapfintech.model.Conta;
import br.com.matheuscarino.fiapfintech.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@WebServlet("/cadastro")
public class CadastroServlet extends HttpServlet {

    private UsuarioDao usuarioDao;
    private ClienteDao clienteDao;
    private ContaDao contaDao;

    @Override
    public void init() throws ServletException {
        usuarioDao = DaoFactory.getUsuarioDao();
        clienteDao = DaoFactory.getClienteDao();
        contaDao = DaoFactory.getContaDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redireciona para a página de cadastro
        request.getRequestDispatcher("cadastro.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Recupera os dados do formulário
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            String confirmarSenha = request.getParameter("confirmarSenha");
            String nome = request.getParameter("nome");
            String cpf = request.getParameter("cpf");
            String telefone = request.getParameter("telefone");
            String endereco = request.getParameter("endereco");
            String dataNascimentoStr = request.getParameter("dataNascimento");
            int tipoConta = Integer.parseInt(request.getParameter("tipoConta"));

            // Validações básicas
            if (!senha.equals(confirmarSenha)) {
                request.setAttribute("erro", "As senhas não conferem");
                request.getRequestDispatcher("cadastro.jsp").forward(request, response);
                return;
            }

            // Verifica se o email já está cadastrado
            Usuario usuarioExistente = usuarioDao.buscarPorEmail(email);
            if (usuarioExistente != null) {
                request.setAttribute("erro", "Este email já está cadastrado");
                request.getRequestDispatcher("cadastro.jsp").forward(request, response);
                return;
            }

            // Cria o usuário
            Usuario usuario = new Usuario(email, senha, "cliente");
            usuarioDao.cadastrar(usuario);

            // Recupera o usuário recém-criado para obter o ID
            usuario = usuarioDao.buscarPorEmail(email);

            // Cria o cliente
            LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr);
            Cliente cliente = new Cliente(nome, cpf, email, telefone, endereco, dataNascimento, true);
            clienteDao.cadastrar(cliente);

            // Busca o ID do cliente recém-criado usando o método buscarPorEmail
            cliente = clienteDao.buscarPorEmail(email);

            // Cria a conta
            Conta conta = new Conta();
            // Usa o ID do cliente obtido pelo método buscarPorEmail
            conta.setClienteId(cliente.getId());
            conta.setTipoConta(tipoConta);
            conta.setSaldo(0.0);
            conta.setStatus(true);
            conta.setDataCriacao(LocalDateTime.now());
            contaDao.cadastrar(conta);

            // Redireciona para a página de login com mensagem de sucesso
            request.setAttribute("sucesso", "Cadastro realizado com sucesso! Faça login para acessar sua conta.");
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao realizar cadastro: " + e.getMessage());
            request.getRequestDispatcher("cadastro.jsp").forward(request, response);
        }
    }
}
