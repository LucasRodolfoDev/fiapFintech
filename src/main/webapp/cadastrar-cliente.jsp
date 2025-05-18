<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Cadastrar Cliente</title>
  <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/resources/css/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
<%@ include file="header.jsp" %>

<div class="container mt-4">
  <h2>Cadastrar Cliente</h2>
  <c:if test="${not empty mensagem}">
    <div class="alert alert-${tipoMensagem} alert-dismissible fade show" role="alert">
      ${mensagem}
      <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
  </c:if>
  <form action="clientes" method="post">
    <div class="mb-3">
      <label for="nome" class="form-label">Nome</label>
      <input type="text" class="form-control" id="nome" name="nome" required>
    </div>
    <div class="mb-3">
      <label for="cpf" class="form-label">CPF</label>
      <input type="text" class="form-control" id="cpf" name="cpf" required>
    </div>
    <div class="mb-3">
      <label for="email" class="form-label">E-mail</label>
      <input type="email" class="form-control" id="email" name="email" required>
    </div>
    <div class="mb-3">
      <label for="telefone" class="form-label">Telefone</label>
      <input type="text" class="form-control" id="telefone" name="telefone">
    </div>
    <div class="mb-3">
      <label for="endereco" class="form-label">Endereço</label>
      <input type="text" class="form-control" id="endereco" name="endereco" required>
    </div>
    <div class="mb-3">
      <label for="dataNascimento" class="form-label">Data de Nascimento</label>
      <input type="date" class="form-control" id="dataNascimento" name="dataNascimento" required>
    </div>
    <div class="mb-3">
      <div class="form-check">
        <input class="form-check-input" type="checkbox" id="status" name="status" checked>
        <label class="form-check-label" for="status">
          Cliente Ativo
        </label>
      </div>
    </div>
    <button type="submit" class="btn btn-primary">Cadastrar</button>
  </form>
</div>

<%@ include file="footer.jsp" %>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
</body>
</html>