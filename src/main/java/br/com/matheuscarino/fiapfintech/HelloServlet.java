package br.com.matheuscarino.fiapfintech;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;
    private String nome;

    public void init() {
        message = "Bem-vindo: ";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        nome = request.getParameter("nome");
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + nome + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}