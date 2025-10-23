<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Usuarios - Sistema Médico</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .card {
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            border: none;
        }
        .table-actions {
            white-space: nowrap;
        }
        .btn-action {
            margin: 2px;
        }
        .badge-activo {
            background-color: #198754;
        }
        .badge-inactivo {
            background-color: #dc3545;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/dashboard_recepcionista.jsp">
                <i class="bi bi-hospital"></i> Sistema Médico
            </a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="${pageContext.request.contextPath}/dashboard_recepcionista.jsp">
                    <i class="bi bi-house-door"></i> Dashboard
                </a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/dashboard_recepcionista.jsp">Inicio</a></li>
                <li class="breadcrumb-item active">Usuarios</li>
            </ol>
        </nav>

        <div class="card">
            <div class="card-header bg-primary text-white">
                <div class="d-flex justify-content-between align-items-center">
                    <h4 class="mb-0"><i class="bi bi-person-circle"></i> Gestión de Usuarios</h4>
                    <div>
                        <a href="${pageContext.request.contextPath}/UsuariosController?accion=nuevo" class="btn btn-light">
                            <i class="bi bi-person-plus-fill"></i> Nuevo Usuario
                        </a>
                        <a href="${pageContext.request.contextPath}/PacientesController?accion=listar" class="btn btn-outline-light">
                            <i class="bi bi-people-fill"></i> Ver Pacientes
                        </a>
                    </div>
                </div>
            </div>
            <div class="card-body">
                <c:if test="${not empty mensaje}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <i class="bi bi-check-circle-fill"></i> ${mensaje}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>
                
                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="bi bi-exclamation-triangle-fill"></i> ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Nombre Completo</th>
                                <th>Correo</th>
                                <th>Teléfono</th>
                                <th>Rol</th>
                                <th>Estado</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="usuario" items="${usuarios}">
                                <tr>
                                    <td><strong>#${usuario.id}</strong></td>
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <div class="bg-primary text-white rounded-circle d-flex align-items-center justify-content-center me-2" 
                                                 style="width: 40px; height: 40px;">
                                                <i class="bi bi-person-fill"></i>
                                            </div>
                                            <div>
                                                <strong>${usuario.nombre} ${usuario.apellido}</strong>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <i class="bi bi-envelope"></i> ${usuario.correo}
                                    </td>
                                    <td>
                                        <i class="bi bi-telephone"></i> ${usuario.telefono}
                                    </td>
                                    <td>
                                        <span class="badge bg-info text-dark">
                                            <i class="bi bi-shield-check"></i> ${usuario.rol}
                                        </span>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${usuario.activo}">
                                                <span class="badge badge-activo">
                                                    <i class="bi bi-check-circle"></i> Activo
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge badge-inactivo">
                                                    <i class="bi bi-x-circle"></i> Inactivo
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-center table-actions">
                                        <a href="${pageContext.request.contextPath}/UsuariosController?accion=editar&id=${usuario.id}" 
                                           class="btn btn-sm btn-warning btn-action" 
                                           title="Editar usuario">
                                            <i class="bi bi-pencil-square"></i>
                                        </a>
                                        
                                        <c:choose>
                                            <c:when test="${usuario.activo}">
                                                <button onclick="confirmarDesactivar(${usuario.id}, '${usuario.nombre} ${usuario.apellido}')" 
                                                        class="btn btn-sm btn-danger btn-action" 
                                                        title="Desactivar usuario">
                                                    <i class="bi bi-x-circle"></i>
                                                </button>
                                            </c:when>
                                            <c:otherwise>
                                                <button onclick="confirmarActivar(${usuario.id}, '${usuario.nombre} ${usuario.apellido}')" 
                                                        class="btn btn-sm btn-success btn-action" 
                                                        title="Activar usuario">
                                                    <i class="bi bi-check-circle"></i>
                                                </button>
                                            </c:otherwise>
                                        </c:choose>
                                        
                                       
                                        
                                        
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function confirmarDesactivar(id, nombre) {
            if (confirm('¿Está seguro que desea desactivar al usuario ' + nombre + '?\n\nEsta acción impedirá que el usuario acceda al sistema.')) {
                window.location.href = '${pageContext.request.contextPath}/UsuariosController?accion=desactivar&id=' + id;
            }
        }
        
        function confirmarActivar(id, nombre) {
            if (confirm('¿Está seguro que desea activar al usuario ' + nombre + '?')) {
                window.location.href = '${pageContext.request.contextPath}/UsuariosController?accion=activar&id=' + id;
            }
        }
    </script>
</body>
</html>
