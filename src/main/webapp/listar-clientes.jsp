<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.matheuscarino.fiapfintech.model.Cliente" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-icons.css" rel="stylesheet">
    <title>Lista de Clientes</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th {
            background-color: #f5f5f5;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        tr:hover {
            background-color: #f0f0f0;
        }
        .error-message {
            background-color: #fff3f3;
            border: 1px solid #ffcdd2;
            border-radius: 4px;
            padding: 15px;
            margin: 20px 0;
            color: #d32f2f;
        }
        .error-details {
            margin-top: 10px;
            font-size: 0.9em;
            color: #666;
        }
    </style>
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="container">
        <h1>Lista de Clientes</h1>
        <%
            List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
            String erro = (String) request.getAttribute("erro");
            
            if (erro != null) {
        %>
                <div class="error-message">
                    <h4>Erro ao carregar dados</h4>
                    <p><%= erro %></p>
                </div>
        <%
            } else if (clientes == null || clientes.isEmpty()) {
        %>
                <div class="alert alert-info" role="alert">
                    Nenhum cliente cadastrado no sistema.
                </div>
        <%
            } else {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        %>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nome</th>
                            <th>CPF</th>
                            <th>Email</th>
                            <th>Telefone</th>
                            <th>Endereço</th>
                            <th>Data de Nascimento</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Cliente cliente : clientes) { %>
                            <tr>
                                <td><%= cliente.getId() %></td>
                                <td><%= cliente.getNome() %></td>
                                <td><%= cliente.getCpf() %></td>
                                <td><%= cliente.getEmail() %></td>
                                <td><%= cliente.getTelefone() %></td>
                                <td><%= cliente.getEndereco() %></td>
                                <td><%= cliente.getDataNascimento().format(dateFormatter) %></td>
                                <td><%= cliente.getStatus() ? "Ativo" : "Inativo" %></td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
        <%
            }
        %>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>
