<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>HOME</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">

</head>
<body>
    <jsp:include page="header.jsp" />

    <div class="container">
        <c:if test="${empty sessionScope.usuario}">
            <h1>Você precisa estar logado para acessar esta página</h1>
            <h5>Faça login para continuar</h5>
        </c:if>
        <c:if test="${not empty sessionScope.usuario}">
            <h1>Bem-vindo ao sistema Fintech</h1>
            <h5>Olá, ${sessionScope.usuario.email}.</h5>
        </c:if>

    </div>

    <jsp:include page="footer.jsp" />
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
</body>
</html>
