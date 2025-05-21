<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Extrato de Transferências</title>
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
<%@ include file="header.jsp" %>

<div class="container mt-4">
    <h2>Extrato de Transferências</h2>
    
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

    <!-- Filtros -->
    <div class="card mb-4">
        <div class="card-header">
            <h5>Filtros</h5>
        </div>
        <div class="card-body">
            <form action="extrato" method="get" class="row g-3">
                <input type="hidden" name="acao" value="listar">
                
                <c:if test="${sessionScope.tipoUsuario == 'gerente'}">
                    <div class="col-md-4">
                        <label for="clienteId" class="form-label">Cliente</label>
                        <select name="clienteId" id="clienteId" class="form-select" onchange="this.form.submit()">
                            <option value="">Todos os clientes</option>
                            <c:forEach items="${clientes}" var="cliente">
                                <option value="${cliente.id}" ${clienteIdSelecionado == cliente.id ? 'selected' : ''}>
                                    ${cliente.nome} (${cliente.cpf})
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </c:if>
                
                <div class="col-md-4">
                    <label for="contaId" class="form-label">Conta</label>
                    <select name="contaId" id="contaId" class="form-select" onchange="this.form.submit()">
                        <option value="">Todas as contas</option>
                        <c:forEach items="${contasUsuario}" var="conta">
                            <option value="${conta.id}" ${contaIdSelecionada == conta.id ? 'selected' : ''}>
                                Conta ${conta.id} - Tipo: ${conta.tipoConta == 1 ? 'Corrente' : 'Poupança'} - Saldo: R$ ${conta.saldo}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="col-md-4 d-flex align-items-end">
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-filter"></i> Filtrar
                    </button>
                </div>
            </form>
        </div>
    </div>

    <c:if test="${empty transferencias}">
        <div class="alert alert-info">
            Nenhuma transferência encontrada.
        </div>
    </c:if>

    <c:if test="${not empty transferencias}">
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Data</th>
                    <th>Origem</th>
                    <th>Destino</th>
                    <th>Valor</th>
                    <th>Tipo</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${transferencias}" var="transferencia">
                    <c:set var="contaOrigem" value="${contasMap[transferencia.contaOrigemId]}" />
                    <c:set var="contaDestino" value="${contasMap[transferencia.contaDestinoId]}" />
                    <c:set var="clienteOrigem" value="${clientesMap[contaOrigem.clienteId]}" />
                    <c:set var="clienteDestino" value="${clientesMap[contaDestino.clienteId]}" />
                    
                    <c:set var="isEntrada" value="${contaDestino.clienteId == sessionScope.usuarioId}" />
                    <c:set var="isSaida" value="${contaOrigem.clienteId == sessionScope.usuarioId}" />
                    
                    <tr class="${isEntrada ? 'table-success' : (isSaida ? 'table-danger' : '')}">
                        <td>${transferencia.id}</td>
                        <td>
                            <fmt:parseDate value="${transferencia.dataTransferencia}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" />
                            <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy HH:mm" />
                        </td>
                        <td>
                            Conta ${contaOrigem.id} (${clienteOrigem.nome})
                        </td>
                        <td>
                            Conta ${contaDestino.id} (${clienteDestino.nome})
                        </td>
                        <td>R$ ${transferencia.valor}</td>
                        <td>
                            <c:choose>
                                <c:when test="${contaOrigem.clienteId == contaDestino.clienteId}">
                                    <span class="badge bg-info">Entre contas</span>
                                </c:when>
                                <c:when test="${isEntrada}">
                                    <span class="badge bg-success">Entrada</span>
                                </c:when>
                                <c:when test="${isSaida}">
                                    <span class="badge bg-danger">Saída</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-secondary">Outros</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>

<%@ include file="footer.jsp" %>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
</body>
</html>