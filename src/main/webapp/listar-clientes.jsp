<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.matheuscarino.fiapfintech.model.Cliente" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

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
                            <th>Editar</th>
                            <th>Remover</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${clientes}" var="cliente">
                            <tr>
                                <td>${cliente.id}</td>
                                <td>${cliente.nome}</td>
                                <td>${cliente.cpf}</td>
                                <td>${cliente.email}</td>
                                <td>${cliente.telefone}</td>
                                <td>${cliente.endereco}</td>
                                <td>${cliente.dataNascimento}</td>
                                <td>${cliente.status ? 'Ativo' : 'Inativo'}</td>
                                <td class="text-center">
                                    <c:url value="clientes" var="link">
                                        <c:param name="acao" value="abrir-form-edicao"/>
                                        <c:param name="id" value="${cliente.id}"/>
                                    </c:url>
                                    <a href="${link}" class="btn btn-primary">
                                        <i class="bi bi-pencil-square">Editar</i>
                                    </a>
                                </td>
                                <td class="text-center">
                                    <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#excluirModal" onclick="setIdExcluir(${cliente.id})">
                                        <i class="bi bi-trash">Remover</i>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
        <%
            }
        %>
    </div>

    <!-- Modal de Confirmação -->
    <div class="modal fade" id="excluirModal" tabindex="-1" aria-labelledby="modalConfirmacaoLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalConfirmacaoLabel">Confirmar Exclusão</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fechar"></button>
                </div>
                <div class="modal-body">
                    Tem certeza que deseja excluir este cliente?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <form action="clientes" method="post">
                        <input type="hidden" name="acao" value="remover">
                        <input type="hidden" name="id" id="idExcluir" value="">
                        <button type="submit" class="btn btn-danger">Confirmar Exclusão</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="footer.jsp" %>
    
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
    <script>
        function setIdExcluir(id) {
            document.getElementById('idExcluir').value = id;
        }
    </script>
</body>
</html>
