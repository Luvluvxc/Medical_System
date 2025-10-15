<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.UsuariosResourse" %>
<%
    UsuariosResourse usuario = (UsuariosResourse) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect("../index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Dashboard Admin</title>
</head>
<body>
    <h1>👑 Dashboard Administrador</h1>
    <p>Bienvenido: <%= usuario.getNombre() %></p>
    <a href="../index.jsp">Cerrar Sesión</a>
</body>
</html>