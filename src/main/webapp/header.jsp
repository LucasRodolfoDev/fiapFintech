<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
<nav class="navbar navbar-dark navbar-expand-lg bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">FINTECH</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <c:choose>
                    <c:when test="${sessionScope.tipoUsuario == 'gerente'}">
<%--                        <li class="nav-item">--%>
<%--                            <a class="nav-link" aria-current="page" href="${pageContext.request.contextPath}/cadastrar-conta">Cadastro de Conta</a>--%>
<%--                        </li>--%>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/contas?acao=listar">Listar Contas</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/transferencia?acao=abrir-form">Transferência</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/extrato?acao=listar">Extrato</a>
                        </li>
<%--                        <li class="nav-item">--%>
<%--                            <a class="nav-link" href="${pageContext.request.contextPath}/cadastrar-cliente.jsp">Cadastro de Cliente</a>--%>
<%--                        </li>--%>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/clientes?acao=listar">Listar Clientes</a>
                        </li>
<%--                        <li class="nav-item">--%>
<%--                            <a class="nav-link" href="${pageContext.request.contextPath}/cadastrar-usuario.jsp">Cadastro de Usuário</a>--%>
<%--                        </li>--%>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/usuarios?acao=listar">Listar Usuários</a>
                        </li>
                    </c:when>
                    <c:when test="${sessionScope.tipoUsuario == 'cliente'}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/contas?acao=listar">Minhas Contas</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="transferencia?acao=abrir-form">Transferência</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="extrato?acao=listar">Extrato</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="clientes?acao=abrir-form-edicao&id=${sessionScope.usuarioId}">Meu Cadastro</a>
                        </li>
                    </c:when>
                </c:choose>
            </ul>
            <div class="ms-auto">
                <c:if test="${empty sessionScope.usuario}">
                    <span class="navbar-text text-danger" style="margin-right: 10px;">
                        ${erro}
                    </span>

                    <form action="${pageContext.request.contextPath}/login" method="post" class="d-flex">
                        <input type="email" name="email" placeholder="Email" class="form-control me-2">
                        <input type="password" name="senha" placeholder="Senha" class="form-control me-2">
                        <button type="submit" class="btn btn-primary me-2">Entrar</button>
                        <a href="${pageContext.request.contextPath}/cadastro" class="btn btn-outline-light">Cadastrar</a>
                    </form>
                </c:if>
                <c:if test="${not empty sessionScope.usuario}">
                    <div class="d-flex">
                        <span class="navbar-text me-3">
                            Olá, ${sessionScope.usuario}
                        </span>
                        <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light btn-sm">Sair</a>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</nav>
