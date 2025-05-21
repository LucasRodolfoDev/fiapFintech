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

    <c:choose>
        <c:when test="${not empty sessionScope.usuario}">
            <div class="container mt-4">
                <h1>Bem-vindo ao sistema de contas</h1>
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
            </div>
        </c:when>
        <c:otherwise>
            <!-- Hero Section for non-logged-in users -->
            <div class="bg-primary text-white py-5">
                <div class="container">
                    <div class="row align-items-center">
                        <div class="col-lg-6">
                            <h1 class="display-4 fw-bold">Gerencie suas finanças com facilidade</h1>
                            <p class="lead my-4">A Fintech oferece soluções modernas para o controle financeiro pessoal e empresarial. Acesse suas contas, faça transferências e acompanhe seus gastos em um só lugar.</p>
                            <div class="d-grid gap-2 d-md-flex justify-content-md-start">
                                <a href="${pageContext.request.contextPath}/cadastro" class="btn btn-light btn-lg px-4 me-md-2">Criar uma conta</a>
                                <button type="button" class="btn btn-outline-light btn-lg px-4" data-bs-toggle="modal" data-bs-target="#loginModal">Entrar</button>
                            </div>
                        </div>
                        <div class="col-lg-6 d-none d-lg-block">
                            <img src="${pageContext.request.contextPath}/resources/img/finance-illustration.svg" alt="Ilustração Financeira" class="img-fluid" onerror="this.src='https://via.placeholder.com/600x400?text=Fintech';this.onerror='';">
                        </div>
                    </div>
                </div>
            </div>

            <!-- Features Section -->
            <div class="container py-5">
                <div class="row text-center mb-5">
                    <div class="col-12">
                        <h2 class="display-5 fw-bold">Nossos Serviços</h2>
                        <p class="lead">Descubra como podemos ajudar você a gerenciar suas finanças</p>
                    </div>
                </div>

                <div class="row g-4 py-3">
                    <div class="col-md-4">
                        <div class="card h-100 shadow-sm">
                            <div class="card-body text-center">
                                <i class="bi bi-wallet2 text-primary" style="font-size: 3rem;"></i>
                                <h3 class="card-title mt-3">Contas Digitais</h3>
                                <p class="card-text">Abra sua conta digital sem taxas de manutenção e com rendimento superior à poupança.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card h-100 shadow-sm">
                            <div class="card-body text-center">
                                <i class="bi bi-arrow-left-right text-primary" style="font-size: 3rem;"></i>
                                <h3 class="card-title mt-3">Transferências</h3>
                                <p class="card-text">Realize transferências entre contas de forma rápida, segura e sem custos adicionais.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card h-100 shadow-sm">
                            <div class="card-body text-center">
                                <i class="bi bi-graph-up-arrow text-primary" style="font-size: 3rem;"></i>
                                <h3 class="card-title mt-3">Controle Financeiro</h3>
                                <p class="card-text">Acompanhe seus gastos e receitas com relatórios detalhados e gráficos intuitivos.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Testimonials Section -->
            <div class="bg-light py-5">
                <div class="container">
                    <div class="row text-center mb-4">
                        <div class="col-12">
                            <h2 class="display-5 fw-bold">O que nossos clientes dizem</h2>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4 mb-4">
                            <div class="card h-100">
                                <div class="card-body">
                                    <div class="d-flex justify-content-center mb-3">
                                        <i class="bi bi-star-fill text-warning mx-1"></i>
                                        <i class="bi bi-star-fill text-warning mx-1"></i>
                                        <i class="bi bi-star-fill text-warning mx-1"></i>
                                        <i class="bi bi-star-fill text-warning mx-1"></i>
                                        <i class="bi bi-star-fill text-warning mx-1"></i>
                                    </div>
                                    <p class="card-text text-center">"A melhor plataforma financeira que já utilizei. Interface intuitiva e excelente atendimento ao cliente."</p>
                                    <p class="text-center mb-0"><strong>Ana Silva</strong></p>
                                    <p class="text-center text-muted small">Empresária</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 mb-4">
                            <div class="card h-100">
                                <div class="card-body">
                                    <div class="d-flex justify-content-center mb-3">
                                        <i class="bi bi-star-fill text-warning mx-1"></i>
                                        <i class="bi bi-star-fill text-warning mx-1"></i>
                                        <i class="bi bi-star-fill text-warning mx-1"></i>
                                        <i class="bi bi-star-fill text-warning mx-1"></i>
                                        <i class="bi bi-star-half text-warning mx-1"></i>
                                    </div>
                                    <p class="card-text text-center">"Consigo gerenciar minhas finanças de forma prática e segura. Recomendo a todos!"</p>
                                    <p class="text-center mb-0"><strong>Carlos Oliveira</strong></p>
                                    <p class="text-center text-muted small">Engenheiro</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 mb-4">
                            <div class="card h-100">
                                <div class="card-body">
                                    <div class="d-flex justify-content-center mb-3">
                                        <i class="bi bi-star-fill text-warning mx-1"></i>
                                        <i class="bi bi-star-fill text-warning mx-1"></i>
                                        <i class="bi bi-star-fill text-warning mx-1"></i>
                                        <i class="bi bi-star-fill text-warning mx-1"></i>
                                        <i class="bi bi-star-fill text-warning mx-1"></i>
                                    </div>
                                    <p class="card-text text-center">"Transferências instantâneas e sem taxas! A Fintech revolucionou a forma como lido com meu dinheiro."</p>
                                    <p class="text-center mb-0"><strong>Mariana Costa</strong></p>
                                    <p class="text-center text-muted small">Advogada</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Call to Action Section -->
            <div class="container py-5">
                <div class="row">
                    <div class="col-12 text-center">
                        <h2 class="display-5 fw-bold mb-4">Pronto para começar?</h2>
                        <p class="lead mb-4">Junte-se a milhares de clientes satisfeitos e comece a gerenciar suas finanças de forma inteligente.</p>
                        <a href="${pageContext.request.contextPath}/cadastro" class="btn btn-primary btn-lg px-5 py-3 mb-3">Abra sua conta agora</a>
                        <p class="text-muted">É rápido, fácil e sem burocracia.</p>
                    </div>
                </div>
            </div>

            <!-- Login Modal -->
            <div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="loginModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="loginModalLabel">Entrar na sua conta</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="${pageContext.request.contextPath}/login" method="post">
                                <div class="mb-3">
                                    <label for="email" class="form-label">Email</label>
                                    <input type="email" class="form-control" id="email" name="email" required>
                                </div>
                                <div class="mb-3">
                                    <label for="senha" class="form-label">Senha</label>
                                    <input type="password" class="form-control" id="senha" name="senha" required>
                                </div>
                                <div class="d-grid">
                                    <button type="submit" class="btn btn-primary">Entrar</button>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer justify-content-center">
                            <p class="mb-0">Não tem uma conta? <a href="${pageContext.request.contextPath}/cadastro">Cadastre-se</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>

    <%@ include file="footer.jsp" %>

    <!-- Bootstrap Bundle with Popper -->
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
</body>
</html>
