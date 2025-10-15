<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.UsuariosResourse, java.util.Map" %>
<%
    UsuariosResourse usuario = (UsuariosResourse) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    
    Map<String, Integer> stats = (Map<String, Integer>) request.getAttribute("estadisticas");
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Dashboard Doctor</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h2>👨‍⚕️ Dashboard Doctor</h2>
        <p>Bienvenido/a: <strong><%= usuario.getNombre() %> <%= usuario.getApellido() %></strong></p>
        <p>Rol: <span class="badge bg-success"><%= usuario.getRol() %></span></p>
        
        <% if (stats != null) { %>
        <div class="row">
            <div class="col-md-3">
                <div class="card text-white bg-primary mb-3">
                    <div class="card-body">
                        <h5 class="card-title">Citas Hoy</h5>
                        <h2><%= stats.get("citasHoy") %></h2>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card text-white bg-info mb-3">
                    <div class="card-body">
                        <h5 class="card-title">Consultas Totales</h5>
                        <h2><%= stats.get("totalConsultas") %></h2>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card text-white bg-warning mb-3">
                    <div class="card-body">
                        <h5 class="card-title">Citas Pendientes</h5>
                        <h2><%= stats.get("citasPendientes") %></h2>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card text-white bg-success mb-3">
                    <div class="card-body">
                        <h5 class="card-title">Recetas Hoy</h5>
                        <h2><%= stats.get("recetasHoy") %></h2>
                    </div>
                </div>
            </div>
        </div>
        <% } %>
        
        <div class="mt-4">
            <a href="#" class="btn btn-primary">📋 Mis Consultas</a>
            <a href="#" class="btn btn-success">📊 Ver Reportes</a>
        </div>
        
        <div class="mt-4">
            <a href="index.jsp" class="btn btn-danger">🚪 Cerrar Sesión</a>
        </div>
    </div>
</body>
</html>