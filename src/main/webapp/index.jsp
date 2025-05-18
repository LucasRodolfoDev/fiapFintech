<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>JSP - Hello World</title>
    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
    <%@ include file="header.jsp" %>

    <h1>Bem-vindo ao sistema de contas</h1>
    
    <%
    // Criando um objeto para simular os dados da conta
    java.util.Map<String, Object> conta = new java.util.HashMap<>();
    conta.put("numero", "12345-6");
    conta.put("agencia", "0001");
    conta.put("tipo", "Conta Corrente");
    conta.put("titular", "João da Silva");
    conta.put("saldo", 2547.89);
    conta.put("ultimaMovimentacao", new java.util.Date());

    // Adicionando o objeto ao escopo da página
    request.setAttribute("conta", conta);
    %>

    <div class="container mt-4">
        <div class="card" style="width: 24rem;">
            <div class="card-body">
                <h5 class="card-title">
                    <i class="bi bi-bank"></i> Conta Corrente
                </h5>
                <h6 class="card-subtitle mb-2 text-body-secondary">
                    Agência: ${conta.agencia} | Conta: ${conta.numero}
                </h6>
                <div class="mt-4">
                    <p class="card-text">Titular: ${conta.titular}</p>
                    <h3 class="text-primary">
                        Saldo: R$ <fmt:formatNumber value="${conta.saldo}" type="number" pattern="#,##0.00"/>
                    </h3>
                    <p class="text-muted small">
                        Última movimentação: 
                        <fmt:formatDate value="${conta.ultimaMovimentacao}" pattern="dd/MM/yyyy HH:mm"/>
                    </p>
                </div>
                <div class="mt-3">
                    <a href="#" class="btn btn-primary btn-sm">
                        <i class="bi bi-cash"></i> Nova Transferência
                    </a>
                    <a href="#" class="btn btn-outline-secondary btn-sm">
                        <i class="bi bi-clock-history"></i> Extrato
                    </a>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="footer.jsp" %>

    <!-- Bootstrap Bundle with Popper -->
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
</body>
</html>