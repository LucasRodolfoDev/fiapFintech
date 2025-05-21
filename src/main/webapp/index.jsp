<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Fintech - Home</title>
    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="container mt-4">
        <h1>Bem-vindo ao sistema de contas</h1>

        <c:if test="${not empty sessionScope.usuario}">
            <div class="row mt-4">
                <div class="col-12">
                    <h2>Suas Contas</h2>
                </div>

                <c:if test="${not empty erro}">
                    <div class="alert alert-danger" role="alert">
                        ${erro}
                    </div>
                </c:if>

                <c:if test="${empty contas}">
                    <div class="col-12">
                        <div class="alert alert-info" role="alert">
                            Você não possui contas cadastradas.
                        </div>
                    </div>
                </c:if>

                <c:forEach items="${contas}" var="conta">
                    <div class="col-md-6 col-lg-4 mb-4">
                        <div class="card h-100">
                            <div class="card-header bg-primary text-white">
                                <h5 class="card-title mb-0">
                                    Conta ${conta.id} - ${conta.tipoConta == 1 ? 'Corrente' : 'Poupança'}
                                </h5>
                            </div>
                            <div class="card-body">
                                <p class="card-text">
                                    <strong>Cliente:</strong> ${clientesMap[conta.clienteId].nome}
                                </p>
                                <p class="card-text">
                                    <strong>Saldo:</strong> R$ <fmt:formatNumber value="${conta.saldo}" minFractionDigits="2" maxFractionDigits="2" />
                                </p>
                                <p class="card-text">
                                    <strong>Status:</strong> <span class="badge ${conta.status ? 'bg-success' : 'bg-danger'}">${conta.status ? 'Ativa' : 'Inativa'}</span>
                                </p>
                                <p class="card-text">
                                    <strong>Data de Criação:</strong> ${conta.dataCriacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}
                                </p>
                            </div>
                            <div class="card-footer">
                                <div class="d-flex justify-content-between">
                                    <a href="contas?acao=abrir-form-edicao&id=${conta.id}" class="btn btn-sm btn-primary">
                                        <i class="bi bi-pencil-square"></i> Editar
                                    </a>
                                    <a href="extrato?acao=listar&contaId=${conta.id}" class="btn btn-sm btn-info">
                                        <i class="bi bi-list-ul"></i> Extrato
                                    </a>
                                    <a href="transferencia" class="btn btn-sm btn-success">
                                        <i class="bi bi-arrow-left-right"></i> Transferir
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>

    <%@ include file="footer.jsp" %>

    <!-- Bootstrap Bundle with Popper -->
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
</body>
</html>
