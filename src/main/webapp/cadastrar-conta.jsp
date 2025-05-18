
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Cadastrar Conta</title>
  <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/resources/css/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
<%@ include file="header.jsp" %>

<div class="container mt-4">
  <h2>Cadastrar Conta</h2>
  <form action="cadastrarConta" method="post">
    <div class="mb-3">
      <label for="cliente" class="form-label">Cliente</label>
      <select class="form-select" id="cliente" name="clienteId" required>
        <c:forEach var="cliente" items="${clientes}">
          <option value="${cliente.id}">${cliente.nome} - CPF: ${cliente.cpf}</option>
        </c:forEach>
      </select>
    </div>
    <div class="mb-3">
      <label for="agencia" class="form-label">Agência</label>
      <input type="text" class="form-control" id="agencia" name="agencia" required>
    </div>
    <div class="mb-3">
      <label for="numero" class="form-label">Número da Conta</label>
      <input type="text" class="form-control" id="numero" name="numero" required>
    </div>
    <div class="mb-3">
      <label for="tipo" class="form-label">Tipo de Conta</label>
      <select class="form-select" id="tipo" name="tipo" required>
        <option value="Corrente">Corrente</option>
        <option value="Poupança">Poupança</option>
      </select>
    </div>
    <button type="submit" class="btn btn-primary">Cadastrar</button>
  </form>
</div>

<%@ include file="footer.jsp" %>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
</body>
</html>