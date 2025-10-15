<%@page import="Model.UsuariosResourse"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Lista de Usuarios</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2>Gestión de Usuarios</h2>
        
        <% if (request.getAttribute("mensaje") != null) { %>
            <div class="alert alert-success"><%= request.getAttribute("mensaje") %></div>
        <% } %>
        
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
        <% } %>
        
        <a href="UsuariosController?accion=nuevo" class="btn btn-primary mb-3">Nuevo Usuario</a>
        
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>Correo</th>
                    <th>Teléfono</th>
                    <th>Rol</th>
                    <th>Activo</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<UsuariosResourse> usuarios = (List<UsuariosResourse>) request.getAttribute("usuarios");
                    if (usuarios != null) {
                        for (UsuariosResourse u : usuarios) {
                %>
                <tr>
                    <td><%= u.getId() %></td>
                    <td><%= u.getNombre() %></td>
                    <td><%= u.getApellido() %></td>
                    <td><%= u.getCorreo() %></td>
                    <td><%= u.getTelefono() %></td>
                    <td><%= u.getRol() %></td>
                    <td><%= u.isActivo() ? "Sí" : "No" %></td>
                    <td>
                        <a href="UsuariosController?accion=editar&id=<%= u.getId() %>" class="btn btn-sm btn-warning">Editar</a>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>