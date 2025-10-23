<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Citas - Sistema Médico</title>
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
    <nav class="navbar navbar-expand-lg navbar-dark bg-info">
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
                <li class="breadcrumb-item active">Citas</li>
            </ol>
        </nav>

        <div class="card">
            <div class="card-header bg-info text-white">
                <div class="d-flex justify-content-between align-items-center">
                    <h4 class="mb-0"><i class="bi bi-calendar-check-fill"></i> Gestión de Citas Médicas</h4>
                    <a href="${pageContext.request.contextPath}/CitasController?accion=nuevo" class="btn btn-light">
                        <i class="bi bi-calendar-plus"></i> Nueva Cita
                    </a>
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
                                <th>Fecha</th>
                                <th>Hora</th>
                                <th>Paciente</th>
                                <th>Doctor</th>
                                <th>Motivo</th>
                                <th>Estado</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="cita" items="${citas}">
                                <tr>
                                    <td>
                                        <span class="badge bg-info">#${cita.id}</span>
                                    </td>
                                    <td>
                                        <i class="bi bi-calendar3"></i> ${cita.fechaCita}
                                    </td>
                                    <td>
                                        <i class="bi bi-clock"></i> ${cita.horaCita}
                                    </td>
                                    <td>
                                        <strong>${cita.pacienteNombre}</strong>
                                    </td>
                                    <td>
                                        <span class="badge bg-primary">
                                            Dr. ${cita.doctorNombre}
                                        </span>
                                    </td>
                                    <td>
                                        <small>${cita.motivo}</small>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${cita.estado == 'Pendiente'}">
                                                <span class="badge bg-warning text-dark">
                                                    <i class="bi bi-clock-history"></i> Pendiente
                                                </span>
                                            </c:when>
                                            <c:when test="${cita.estado == 'Confirmada'}">
                                                <span class="badge bg-success">
                                                    <i class="bi bi-check-circle"></i> Confirmada
                                                </span>
                                            </c:when>
                                            <c:when test="${cita.estado == 'Completada'}">
                                                <span class="badge bg-info">
                                                    <i class="bi bi-check-all"></i> Completada
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-danger">
                                                    <i class="bi bi-x-circle"></i> Cancelada
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-center table-actions">
                                        <a href="${pageContext.request.contextPath}/CitasController?accion=editar&id=${cita.id}" 
                                           class="btn btn-sm btn-warning btn-action" 
                                           title="Editar cita">
                                            <i class="bi bi-pencil-square"></i>
                                        </a>
                                        
                                        <button type="button" 
                                                class="btn btn-sm btn-danger btn-action" 
                                                onclick="confirmarEliminar(${cita.id}, '${cita.pacienteNombre}', '${cita.fechaCita}')"
                                                title="Eliminar cita">
                                            <i class="bi bi-trash-fill"></i>
                                        </button>
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
                    <p>¿Está seguro que desea eliminar la cita de <strong id="nombrePaciente"></strong> para el día <strong id="fechaCita"></strong>?</p>
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
        function confirmarEliminar(id, paciente, fecha) {
            document.getElementById('nombrePaciente').textContent = paciente;
            document.getElementById('fechaCita').textContent = fecha;
            document.getElementById('btnConfirmarEliminar').href = 
                '${pageContext.request.contextPath}/CitasController?accion=eliminar&id=' + id;
            new bootstrap.Modal(document.getElementById('modalEliminar')).show();
        }
    </script>
</body>
</html>
