<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Conta</title>
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
<%@ include file="header.jsp" %>

<div class="container mt-4">
    <h2>Editar Conta</h2>
    <c:if test="${not empty mensagem}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${mensagem}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <c:if test="${not empty erro}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${erro}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <form action="contas" method="post">
        <input type="hidden" name="acao" value="editar">
        <input type="hidden" name="id" value="${conta.id}">
        <input type="hidden" name="dataCriacao" value="${conta.dataCriacao}">
        
        <div class="mb-3">
            <label for="cliente" class="form-label">Cliente</label>
            <select class="form-select" id="cliente" name="clienteId" required>
                <c:forEach var="cliente" items="${clientes}">
                    <option value="${cliente.id}" ${cliente.id == conta.clienteId ? 'selected' : ''}>
                        ${cliente.nome} - CPF: ${cliente.cpf}
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="mb-3">
            <label for="tipo" class="form-label">Tipo de Conta</label>
            <select class="form-select" id="tipo" name="tipo" required>
                <option value="1" ${conta.tipoConta == 1 ? 'selected' : ''}>Corrente</option>
                <option value="2" ${conta.tipoConta == 2 ? 'selected' : ''}>Poupança</option>
            </select>
        </div>
        <div class="mb-3">
            <label for="saldo" class="form-label">Saldo</label>
            <input type="number" step="0.01" class="form-control" id="saldo" name="saldo" value="${conta.saldo}" required>
        </div>
        <div class="mb-3">
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="status" name="status" value="1" ${conta.status ? 'checked' : ''}>
                <label class="form-check-label" for="status">
                    Conta Ativa
                </label>
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Atualizar</button>
        <a href="contas?acao=listar" class="btn btn-secondary">Cancelar</a>
    </form>
</div>

<%@ include file="footer.jsp" %>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
</body>
</html> 