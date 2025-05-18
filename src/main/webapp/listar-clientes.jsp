<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="br.com.matheuscarino.fiapfintech.dao.ConnectionManager" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-icons.css" rel="stylesheet">
    <title>Lista de Clientes</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th {
            background-color: #f5f5f5;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        tr:hover {
            background-color: #f0f0f0;
        }
    </style>
</head>
<body>
    <%@ include file="header.jsp" %>
    <h1>Lista de Clientes</h1>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>CPF</th>
                <th>Email</th>
                <th>Telefone</th>
                <th>Endereço</th>
                <th>Data de Nascimento</th>
                <th>Status</th>
                <th>Data de Criação</th>
                <th>Última Atualização</th>
            </tr>
        </thead>
        <tbody>
            <%
                try {
                    Connection conn = ConnectionManager.getConnection();
                    String sql = "SELECT * FROM FINTECH_CLIENTES ORDER BY ID";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat timestampFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    
                    while (rs.next()) {
            %>
                        <tr>
                            <td><%= rs.getLong("ID") %></td>
                            <td><%= rs.getString("NOME") %></td>
                            <td><%= rs.getString("CPF") %></td>
                            <td><%= rs.getString("EMAIL") %></td>
                            <td><%= rs.getString("TELEFONE") %></td>
                            <td><%= rs.getString("ENDERECO") %></td>
                            <td><%= dateFormat.format(rs.getDate("DATA_NASCIMENTO")) %></td>
                            <td><%= rs.getBoolean("STATUS") ? "Ativo" : "Inativo" %></td>
                            <td><%= timestampFormat.format(rs.getTimestamp("DATA_CRIACAO")) %></td>
                            <td><%= timestampFormat.format(rs.getTimestamp("DATA_ATUALIZACAO")) %></td>
                        </tr>
            <%
                    }
                    
                    rs.close();
                    stmt.close();
                    ConnectionManager.closeConnection();
                    
                } catch (Exception e) {
                    out.println("<tr><td colspan='10' style='color: red;'>Erro ao carregar dados: " + e.getMessage() + "</td></tr>");
                }
            %>
        </tbody>
    </table>
    <%@ include file="footer.jsp" %>
</body>
</html>
