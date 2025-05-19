<nav class="navbar navbar-dark navbar-expand-lg bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="index.jsp">FINTECH - HOME</a>
<%--        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent">--%>
<%--            <span class="navbar-toggler-icon"></span>--%>
<%--        </button>--%>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="cadastrar-conta">Cadastro de Conta</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="contas?acao=listar">Listar Contas</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="cadastrar-cliente.jsp">Cadastro de Cliente</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="clientes?acao=listar">Listar Clientes</a>
                </li>
            </ul>
            <form action="login" method="post" class="d-flex ms-auto">
                <input type="email" name="email" placeholder="Email" class="form-control me-2">
                <input type="password" name="senha" placeholder="Senha" class="form-control me-2">
                <button type="submit" class="btn btn-primary">Entrar</button>
            </form>
        </div>
    </div>
</nav>
