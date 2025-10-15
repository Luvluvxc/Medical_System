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
    <title>Dashboard Paciente</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h2>👤 Dashboard Paciente</h2>
        <p>Bienvenido: <strong><%= usuario.getNombre() %> <%= usuario.getApellido() %></strong></p>
        <p>Rol: <span class="badge bg-info"><%= usuario.getRol() %></span></p>
        
        <div class="mt-4">
            <a href="#" class="btn btn-primary">📅 Mis Citas</a>
            <a href="#" class="btn btn-success">📋 Mi Historial</a>
            <a href="#" class="btn btn-info">💊 Mis Medicamentos</a>
        </div>
        
        <div class="mt-4">
            <a href="index.jsp" class="btn btn-danger">🚪 Cerrar Sesión</a>
        </div>
    </div>
</body>
</html>