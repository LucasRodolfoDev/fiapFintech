<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro - FINTECH</title>
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }
        .cadastro-container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background-color: white;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .main-content {
            flex: 1;
        }
    </style>
</head>
<body>
    <div class="main-content">
        <div class="container">
            <div class="cadastro-container">
                <h2 class="text-center mb-4">Cadastro FINTECH</h2>

                <c:if test="${not empty erro}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        ${erro}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>

                <c:if test="${not empty sucesso}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        ${sucesso}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}/cadastro" method="post">
                    <h4 class="mb-3">Dados de Acesso</h4>
                    <div class="mb-3">
                        <label for="email" class="form-label">E-mail</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    <div class="mb-3">
                        <label for="senha" class="form-label">Senha</label>
                        <input type="password" class="form-control" id="senha" name="senha" required>
                    </div>
                    <div class="mb-3">
                        <label for="confirmarSenha" class="form-label">Confirmar Senha</label>
                        <input type="password" class="form-control" id="confirmarSenha" name="confirmarSenha" required>
                    </div>

                    <h4 class="mb-3 mt-4">Dados Pessoais</h4>
                    <div class="mb-3">
                        <label for="nome" class="form-label">Nome Completo</label>
                        <input type="text" class="form-control" id="nome" name="nome" required>
                    </div>
                    <div class="mb-3">
                        <label for="cpf" class="form-label">CPF (apenas números)</label>
                        <input type="text" class="form-control" id="cpf" name="cpf" pattern="[0-9]{11}" maxlength="11" required>
                    </div>
                    <div class="mb-3">
                        <label for="telefone" class="form-label">Telefone</label>
                        <input type="text" class="form-control" id="telefone" name="telefone" required>
                    </div>
                    <div class="mb-3">
                        <label for="endereco" class="form-label">Endereço</label>
                        <input type="text" class="form-control" id="endereco" name="endereco" required>
                    </div>
                    <div class="mb-3">
                        <label for="dataNascimento" class="form-label">Data de Nascimento</label>
                        <input type="date" class="form-control" id="dataNascimento" name="dataNascimento" required>
                    </div>

                    <h4 class="mb-3 mt-4">Dados da Conta</h4>
                    <div class="mb-3">
                        <label for="tipoConta" class="form-label">Tipo de Conta</label>
                        <select class="form-select" id="tipoConta" name="tipoConta" required>
                            <option value="1">Conta Corrente</option>
                            <option value="2">Conta Poupança</option>
                        </select>
                    </div>

                    <div class="d-grid gap-2 mt-4">
                        <button type="submit" class="btn btn-primary">Cadastrar</button>
                        <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-outline-secondary">Já tenho uma conta</a>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <%@ include file="footer.jsp" %>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
</body>
</html>
