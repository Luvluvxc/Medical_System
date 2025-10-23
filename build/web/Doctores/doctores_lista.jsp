<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Doctores - Sistema Médico</title>
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
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}dashboard_recepcionista.jsp">
                <i class="bi bi-hospital"></i> Sistema Médico
            </a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="${pageContext.request.contextPath}dashboard_recepcionista.jsp">
                    <i class="bi bi-house-door"></i> Dashboard
                </a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}dashboard_recepcionista.jsp">Inicio</a></li>
                <li class="breadcrumb-item active">Doctores</li>
            </ol>
        </nav>

        <div class="card">
            <div class="card-header bg-primary text-white">
                <div class="d-flex justify-content-between align-items-center">
                    <h4 class="mb-0"><i class="bi bi-person-badge-fill"></i> Gestión de Doctores</h4>
                    <div>
                        <a href="${pageContext.request.contextPath}/DoctoresController?accion=listardoctores&rol=doctor'" class="btn btn-light">
                            <i class="bi bi-person-plus-fill"></i> Nuevo Doctor
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
                                <th>Doctor</th>
                                <th>Especialización</th>
                                <th>Número de Licencia</th>
                                <th>Contacto</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="doctor" items="${doctores}">
                                <tr>
                                    <td>
                                        <span class="badge bg-primary">#${doctor.id}</span>
                                    </td>
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <div class="bg-primary text-white rounded-circle d-flex align-items-center justify-content-center me-2" 
                                                 style="width: 40px; height: 40px;">
                                                <i class="bi bi-person-fill"></i>
                                            </div>
                                            <div>
                                                <strong>Dr. ${doctor.usuarioNombre} ${doctor.usuarioApellido}</strong>
                                                <br>
                                                <small class="text-muted">${doctor.usuarioCorreo}</small>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <span class="badge bg-info">
                                            <i class="bi bi-award-fill"></i> ${doctor.especializacion}
                                        </span>
                                    </td>
                                    <td>
                                        <i class="bi bi-card-text"></i> ${doctor.numeroLicencia}
                                    </td>
                                    <td>
                                        <i class="bi bi-telephone-fill"></i> ${doctor.usuarioTelefono}
                                    </td>
                                    <td class="text-center table-actions">
                                        <a href="${pageContext.request.contextPath}/DoctoresController?accion=editar&id=${doctor.id}" 
                                           class="btn btn-sm btn-warning btn-action" 
                                           title="Editar doctor">
                                            <i class="bi bi-pencil-square"></i>
                                        </a>
                                           
                  
                                        
                                        <button type="button" 
                                                class="btn btn-sm btn-danger btn-action" 
                                                onclick="confirmarEliminar(${doctor.id}, '${doctor.usuarioNombre} ${doctor.usuarioApellido}')"
                                                title="Eliminar doctor">
                                            <i class="bi bi-trash-fill"></i>
                                        </button>
                                                
                                        <a href ="${pageContext.request.contextPath}/DoctoresController?accion=nuevo&usuarioId=${usuario.id}"
                                           class="btn btn-sm btn-secondary btn-action"
                                           title="Registrar como doctor">
                                            <i class="bi bi-person-badge"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal de confirmación -->
    <div class="modal fade" id="modalEliminar" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-danger text-white">
                    <h5 class="modal-title">
                        <i class="bi bi-exclamation-triangle-fill"></i> Confirmar Eliminación
                    </h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <p>¿Está seguro que desea eliminar al doctor <strong id="nombreDoctor"></strong>?</p>
                    <p class="text-danger">
                        <i class="bi bi-info-circle"></i> Esta acción no se puede deshacer.
                    </p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <a href="#" id="btnConfirmarEliminar" class="btn btn-danger">
                        <i class="bi bi-trash-fill"></i> Eliminar
                    </a>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function confirmarEliminar(id, nombre) {
            document.getElementById('nombreDoctor').textContent = nombre;
            document.getElementById('btnConfirmarEliminar').href = 
                '${pageContext.request.contextPath}/DoctoresController?accion=eliminar&id=' + id;
            new bootstrap.Modal(document.getElementById('modalEliminar')).show();
        }
    </script>
</body>
</html>
