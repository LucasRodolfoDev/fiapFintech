<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ERRO 404</title>
    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-icons.css" rel="stylesheet">
    <style>
        .error-template {
            padding: 40px 15px;
            text-align: center;
        }
        .error-actions {
            margin-top: 15px;
            margin-bottom: 15px;
        }
        .error-actions .btn {
            margin-right: 10px;
        }
        .error-icon {
            font-size: 8rem;
            color: #dc3545;
        }
    </style>
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="error-template">
                    <div class="card shadow-lg border-0 rounded-lg mt-5">
                        <div class="card-header bg-danger text-white">
                            <h3 class="text-center font-weight-bold my-2">Página não encontrada</h3>
                        </div>
                        <div class="card-body">
                            <div class="text-center mt-4 mb-4">
                                <i class="bi bi-exclamation-triangle-fill error-icon"></i>
                            </div>
                            <h2 class="display-3">Erro 404</h2>
                            <p class="display-5 mb-4">Ops! Parece que você se perdeu.</p>
                            <p class="lead">
                                A página que você está procurando pode ter sido removida, 
                                teve seu nome alterado ou está temporariamente indisponível.
                            </p>
                            <div class="error-actions">
                                <a href="${pageContext.request.contextPath}/" class="btn btn-primary btn-lg">
                                    <i class="bi bi-house-door"></i> Voltar para Home
                                </a>
                                <a href="javascript:history.back()" class="btn btn-outline-secondary btn-lg">
                                    <i class="bi bi-arrow-left"></i> Voltar
                                </a>
                            </div>
                        </div>
                        <div class="card-footer text-center py-3">
                            <div class="small">
                                <a href="${pageContext.request.contextPath}/contas?acao=listar">Ver minhas contas</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="footer.jsp" %>

    <!-- Bootstrap Bundle with Popper -->
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
</body>
</html>
