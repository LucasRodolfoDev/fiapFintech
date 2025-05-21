<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transferência entre Contas</title>
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="container mt-4">
        <h2>Transferência entre Contas</h2>
        
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

        <form action="transferencia" method="post" class="mt-4">
            <input type="hidden" name="acao" value="transferir">
            
            <div class="mb-3">
                <label for="contaOrigem" class="form-label">Conta de Origem</label>
                <select class="form-select" id="contaOrigem" name="contaOrigem" required>
                    <option value="">Selecione a conta de origem</option>
                    <c:forEach var="conta" items="${contasOrigem}">
                        <option value="${conta.id}">
                            Conta ${conta.id} - 
                            ${conta.tipoConta == 1 ? 'Corrente' : 'Poupança'} - 
                            Saldo: R$ ${conta.saldo}
                        </option>
                    </c:forEach>
                </select>
            </div>
            
            <div class="mb-3">
                <label for="contaDestino" class="form-label">Conta de Destino</label>
                <select class="form-select" id="contaDestino" name="contaDestino" required>
                    <option value="">Selecione a conta de destino</option>
                    <c:forEach var="conta" items="${contasDestino}">
                        <option value="${conta.id}">
                            Conta ${conta.id} - 
                            ${conta.tipoConta == 1 ? 'Corrente' : 'Poupança'} - 
                            Saldo: R$ ${conta.saldo}
                        </option>
                    </c:forEach>
                </select>
            </div>
            
            <div class="mb-3">
                <label for="valor" class="form-label">Valor da Transferência</label>
                <div class="input-group">
                    <span class="input-group-text">R$</span>
                    <input type="number" step="0.01" min="0.01" class="form-control" id="valor" name="valor" required>
                </div>
            </div>
            
            <button type="submit" class="btn btn-primary">Realizar Transferência</button>
            <a href="contas?acao=listar" class="btn btn-secondary">Cancelar</a>
        </form>
    </div>

    <%@ include file="footer.jsp" %>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
    <script>
        // Validação para não permitir selecionar a mesma conta
        document.getElementById('contaOrigem').addEventListener('change', function() {
            var contaDestino = document.getElementById('contaDestino');
            var contaOrigem = this.value;
            
            for (var i = 0; i < contaDestino.options.length; i++) {
                if (contaDestino.options[i].value === contaOrigem) {
                    contaDestino.options[i].disabled = true;
                } else {
                    contaDestino.options[i].disabled = false;
                }
            }
        });
    </script>
</body>
</html> 