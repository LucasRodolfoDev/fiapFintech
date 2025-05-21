package br.com.matheuscarino.fiapfintech.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String path = requestURI.substring(contextPath.length());

        // URLs públicas que não precisam de autenticação
        if (path.equals("/") || 
            path.equals("/login.jsp") || 
            path.equals("/login") || 
            path.equals("/cadastro") ||
            path.equals("/cadastro.jsp") ||
            path.contains("/resources/") ||
            path.contains(".css") ||
            path.contains(".js") ||
            path.contains(".ico")) {
            chain.doFilter(request, response);
            return;
        }

        // Verifica se o usuário está logado
        if (session == null || session.getAttribute("usuario") == null) {
            httpResponse.sendRedirect(contextPath + "/login.jsp");
            return;
        }

        // Obtém o tipo de usuário da sessão
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        Long usuarioId = (Long) session.getAttribute("usuarioId");

        // Permissões específicas para cada tipo de usuário
        if (tipoUsuario.equals("cliente")) {
            // Cliente só pode acessar suas próprias informações
            if (path.startsWith("/clientes")) {
                String idParam = request.getParameter("id");
                if (idParam != null) {
                    Long id = Long.parseLong(idParam);
                    // Verifica se o ID pertence ao usuário logado
                    if (!id.equals(usuarioId)) {
                        httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado");
                        return;
                    }
                }
            }
            // Para contas, a verificação é feita no ContaServlet
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
} 
