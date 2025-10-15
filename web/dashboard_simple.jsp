<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.UsuariosResourse" %>
<%
    UsuariosResourse usuario = (UsuariosResourse) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Dashboard Simple</title>
</head>
<body>
    <h1>✅ ¡FUNCIONA! Dashboard Simple</h1>
    <p>Usuario: <%= usuario.getNombre() %> <%= usuario.getApellido() %></p>
    <p>Rol: <%= usuario.getRol() %></p>
    <p>Correo: <%= usuario.getCorreo() %></p>
    <p><strong>🎉 ¡Si ves esto, el sistema está funcionando!</strong></p>
    <a href="index.jsp">Cerrar Sesión</a>
</body>
</html>