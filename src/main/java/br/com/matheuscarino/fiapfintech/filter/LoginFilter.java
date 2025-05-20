package br.com.matheuscarino.fiapfintech.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        String url = httpRequest.getRequestURL().toString();

        if (session.getAttribute("usuario") == null && !url.endsWith("login") && 
            !url.contains("resources") && !url.contains("home.jsp")) {
            request.setAttribute("erro", "Entre com o usuário e senha!");
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }
}


