<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lista de Citas - Sistema Médico</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
        <style>
            :root {
                --primary-color: #2563eb;
                --primary-dark: #1e40af;
                --light-bg: #f0f9ff;
            }

            body {
                background-color: var(--light-bg);
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }

            .navbar {
                background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-dark) 100%);
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            }

            .content-card {
                background: white;
                border-radius: 12px;
                box-shadow: 0 2px 15px rgba(0,0,0,0.1);
                padding: 2rem;
                margin-top: 2rem;
            }

            .section-title {
                color: var(--primary-color);
                font-weight: 600;
                margin-bottom: 1.5rem;
                padding-bottom: 0.75rem;
                border-bottom: 3px solid var(--primary-color);
            }

            .filter-card {
                background: #eff6ff;
                border-radius: 8px;
                padding: 1.5rem;
                margin-bottom: 1.5rem;
            }

            .table {
                border-radius: 8px;
                overflow: hidden;
            }

            .table thead {
                background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-dark) 100%);
                color: white;
            }

            .badge-programada {
                background-color: #3b82f6;
            }

            .badge-completada {
                background-color: #10b981;
            }

            .badge-cancelada {
                background-color: #ef4444;
            }

            .badge-confirmada {
                background-color: #f59e0b;
            }

            .btn-action {
                padding: 0.375rem 0.75rem;
                border-radius: 6px;
                font-size: 0.875rem;
                margin: 0.125rem;
            }

            .stats-card {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                border-radius: 10px;
                padding: 1rem;
                text-align: center;
            }

            .stats-number {
                font-size: 2rem;
                font-weight: bold;
                margin: 0;
            }

            .stats-label {
                font-size: 0.875rem;
                opacity: 0.9;
                margin: 0;
            }
        </style>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark">
            <div class="container-fluid">
                <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/dashboard_recepcionista.jsp">
                    <i class="bi bi-hospital-fill"></i> Sistema Médico
                </a>
            </div>
        </nav>

        <div class="container-fluid p-4">
            <div class="content-card">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2 class="section-title mb-0">
                        <i class="bi bi-calendar-check"></i> Gestión de Citas
                    </h2>
                    <a href="${pageContext.request.contextPath}/CitasController?accion=nuevo" 
                       class="btn btn-primary">
                        <i class="bi bi-plus-circle"></i> Nueva Cita
                    </a>
                </div>

                <!-- Estadísticas rápidas -->
                <div class="row mb-4">
                    <div class="col-md-3">
                        <div class="stats-card">
                            <p class="stats-number">${citas.size()}</p>
                            <p class="stats-label">Total Citas</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="stats-card" style="background: linear-gradient(135deg, #3b82f6 0%, #1e40af 100%);">
                            <p class="stats-number">
                                <c:set var="countProgramadas" value="0" />
                                <c:forEach var="cita" items="${citas}">
                                    <c:if test="${cita.estado == 'programada'}">
                                        <c:set var="countProgramadas" value="${countProgramadas + 1}" />
                                    </c:if>
                                </c:forEach>
                                ${countProgramadas}
                            </p>
                            <p class="stats-label">Programadas</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="stats-card" style="background: linear-gradient(135deg, #10b981 0%, #047857 100%);">
                            <p class="stats-number">
                                <c:set var="countCompletadas" value="0" />
                                <c:forEach var="cita" items="${citas}">
                                    <c:if test="${cita.estado == 'completada'}">
                                        <c:set var="countCompletadas" value="${countCompletadas + 1}" />
                                    </c:if>
                                </c:forEach>
                                ${countCompletadas}
                            </p>
                            <p class="stats-label">Completadas</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="stats-card" style="background: linear-gradient(135deg, #ef4444 0%, #dc2626 100%);">
                            <p class="stats-number">
                                <c:set var="countCanceladas" value="0" />
                                <c:forEach var="cita" items="${citas}">
                                    <c:if test="${cita.estado == 'cancelada'}">
                                        <c:set var="countCanceladas" value="${countCanceladas + 1}" />
                                    </c:if>
                                </c:forEach>
                                ${countCanceladas}
                            </p>
                            <p class="stats-label">Canceladas</p>
                        </div>
                    </div>
                </div>

                <!-- Filtros -->
                <div class="filter-card">
                    <form action="${pageContext.request.contextPath}/CitasController" method="get" class="row g-3">
                        <input type="hidden" name="accion" value="listar">
                        <div class="col-md-4">
                            <label class="form-label fw-bold">Filtrar por Paciente</label>
                            <select class="form-select" name="pacienteId">
                                <option value="">Todos los pacientes</option>
                                <c:forEach var="paciente" items="${pacientes}">
                                    <option value="${paciente.id}" 
                                            ${param.pacienteId == paciente.id ? 'selected' : ''}>
                                        ${paciente.usuarioNombre} ${paciente.usuarioApellido} 
                                        <c:if test="${not empty paciente.codigoPaciente}">
                                            - ${paciente.codigoPaciente}
                                        </c:if>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label fw-bold">Estado</label>
                            <select class="form-select" name="estado">
                                <option value="">Todos los estados</option>
                                <option value="programada" ${param.estado == 'programada' ? 'selected' : ''}>Programada</option>
                                <option value="confirmada" ${param.estado == 'confirmada' ? 'selected' : ''}>Confirmada</option>
                                <option value="completada" ${param.estado == 'completada' ? 'selected' : ''}>Completada</option>
                                <option value="cancelada" ${param.estado == 'cancelada' ? 'selected' : ''}>Cancelada</option>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label fw-bold">Fecha</label>
                            <input type="date" class="form-control" name="fecha" value="${param.fecha}">
                        </div>
                        <div class="col-md-2">
                            <label class="form-label">&nbsp;</label>
                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary">
                                    <i class="bi bi-funnel"></i> Filtrar
                                </button>
                                <a href="${pageContext.request.contextPath}/CitasController?accion=listar" 
                                   class="btn btn-outline-secondary">
                                    <i class="bi bi-x-circle"></i> Limpiar
                                </a>
                            </div>
                        </div>
                    </form>

                    <!-- Mostrar filtros activos -->
                    <c:if test="${not empty param.pacienteId || not empty param.estado || not empty param.fecha}">
                        <div class="mt-3">
                            <small class="text-muted">Filtros activos:</small>
                            <c:if test="${not empty param.pacienteId}">
                                <span class="badge bg-primary ms-2">
                                    Paciente: 
                                    <c:forEach var="paciente" items="${pacientes}">
                                        <c:if test="${paciente.id == param.pacienteId}">
                                            ${paciente.usuarioNombre} ${paciente.usuarioApellido}
                                        </c:if>
                                    </c:forEach>
                                </span>
                            </c:if>
                            <c:if test="${not empty param.estado}">
                                <span class="badge bg-info ms-2">Estado: ${param.estado}</span>
                            </c:if>
                            <c:if test="${not empty param.fecha}">
                                <span class="badge bg-success ms-2">Fecha: ${param.fecha}</span>
                            </c:if>
                        </div>
                    </c:if>
                </div>

                <!-- Mensajes -->
                <c:if test="${not empty mensaje}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <i class="bi bi-check-circle"></i> ${mensaje}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="bi bi-exclamation-triangle"></i> ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <!-- Tabla de citas -->
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Paciente</th>
                                <th>Doctor</th>
                                <th>Fecha</th>
                                <th>Hora</th>
                                <th>Estado</th>
                                <th>Motivo</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty citas}">
                                    <tr>
                                        <td colspan="8" class="text-center text-muted py-4">
                                            <i class="bi bi-calendar-x" style="font-size: 2rem;"></i>
                                            <p class="mt-2">No se encontraron citas</p>
                                            <c:if test="${not empty param.pacienteId || not empty param.estado || not empty param.fecha}">
                                                <small>Intenta con otros criterios de búsqueda</small>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="cita" items="${citas}">
                                        <tr>
                                            <td><strong>#${cita.id}</strong></td>
                                            <td>
                                                <strong>${cita.pacienteNombre} ${cita.pacienteApellido}</strong>
                                                <br>
                                                <small class="text-muted">ID: ${cita.pacienteId}</small>
                                            </td>
                                            <td>
                                                <strong>Dr. ${cita.doctorNombre} ${cita.doctorApellido}</strong><br>
                                                <small class="text-muted">${cita.doctorEspecializacion}</small>
                                            </td>
                                            <td>
                                                <strong>${cita.fechaCita}</strong>
                                            </td>
                                            <td>
                                                <span class="badge bg-dark">${cita.horaCita}</span>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${cita.estado == 'programada'}">
                                                        <span class="badge bg-primary">Programada</span>
                                                    </c:when>
                                                    <c:when test="${cita.estado == 'confirmada'}">
                                                        <span class="badge bg-warning text-dark">Confirmada</span>
                                                    </c:when>
                                                    <c:when test="${cita.estado == 'completada'}">
                                                        <span class="badge bg-success">Completada</span>
                                                    </c:when>
                                                    <c:when test="${cita.estado == 'cancelada'}">
                                                        <span class="badge bg-danger">Cancelada</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-secondary">${cita.estado}</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty cita.motivo && fn:length(cita.motivo) > 50}">
                                                        ${fn:substring(cita.motivo, 0, 50)}...
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${cita.motivo}
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <div class="btn-group">
                                                    <a href="${pageContext.request.contextPath}/CitasController?accion=editar&id=${cita.id}" 
                                                       class="btn btn-sm btn-outline-primary btn-action">
                                                        <i class="bi bi-pencil"></i> Editar
                                                    </a>

                                                    <c:if test="${cita.estado == 'programada' || cita.estado == 'confirmada'}">
                                                        <button class="btn btn-sm btn-outline-danger btn-action" 
                                                                onclick="cancelarCita(${cita.id})">
                                                            <i class="bi bi-x-circle"></i> Cancelar
                                                        </button>
                                                    </c:if>

                                                    
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>

                <div class="mt-3">
                    <a href="${pageContext.request.contextPath}/dashboard_recepcionista.jsp" class="btn btn-secondary">
                        <i class="bi bi-arrow-left"></i> Volver al Dashboard
                    </a>
                </div>
            </div>
        </div>

        <!-- Modal para cancelar cita -->
        <!-- Modal para cancelar cita -->
        <div class="modal fade" id="cancelModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header bg-danger text-white">
                        <h5 class="modal-title"><i class="bi bi-x-circle"></i> Cancelar Cita</h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                    </div>
                    <form action="${pageContext.request.contextPath}/CitasController" method="post" id="cancelForm">
                        <input type="hidden" name="accion" value="cancelar">
                        <input type="hidden" name="citaId" id="cancelCitaId">
                        <div class="modal-body">
                            <div class="mb-3">
                                <label class="form-label fw-bold">Motivo de Cancelación *</label>
                                <textarea class="form-control" id="motivoCancelacion" name="motivoCancelacion" rows="3" required 
                                          placeholder="Ingrese el motivo de la cancelación"></textarea>
                            </div>
                            <div class="alert alert-warning">
                                <i class="bi bi-exclamation-triangle"></i> Esta acción no se puede deshacer
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                            <button type="submit" class="btn btn-danger" id="confirmCancelBtn">
                                <i class="bi bi-check-circle"></i> Confirmar Cancelación
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
                                                                    function cancelarCita(citaId) {
                                                                        document.getElementById('cancelCitaId').value = citaId;
                                                                        document.getElementById('motivoCancelacion').value = '';
                                                                        new bootstrap.Modal(document.getElementById('cancelModal')).show();
                                                                    }

                                                                    // Manejar el envío del formulario de forma tradicional
                                                                    document.addEventListener('DOMContentLoaded', function () {
                                                                        const cancelForm = document.getElementById('cancelForm');
                                                                        const confirmCancelBtn = document.getElementById('confirmCancelBtn');

                                                                        if (cancelForm) {
                                                                            cancelForm.addEventListener('submit', function (e) {
                                                                                const motivo = document.getElementById('motivoCancelacion').value;
                                                                                const citaId = document.getElementById('cancelCitaId').value;

                                                                                console.log('Validando cancelación - ID:', citaId, 'Motivo:', motivo);

                                                                                if (!motivo.trim()) {
                                                                                    e.preventDefault();
                                                                                    alert('Por favor ingrese el motivo de la cancelación');
                                                                                    return;
                                                                                }

                                                                                if (!citaId) {
                                                                                    e.preventDefault();
                                                                                    alert('Error: No se encontró el ID de la cita');
                                                                                    return;
                                                                                }

                                                                                // Mostrar loading
                                                                                confirmCancelBtn.innerHTML = '<i class="bi bi-hourglass-split"></i> Cancelando...';
                                                                                confirmCancelBtn.disabled = true;

                                                                                // Permitir que el formulario se envíe de forma tradicional
                                                                                console.log('Enviando formulario de cancelación...');
                                                                            });
                                                                        }

                                                                        // Limpiar el formulario cuando se cierre el modal
                                                                        document.getElementById('cancelModal').addEventListener('hidden.bs.modal', function () {
                                                                            document.getElementById('motivoCancelacion').value = '';
                                                                            if (confirmCancelBtn) {
                                                                                confirmCancelBtn.innerHTML = '<i class="bi bi-check-circle"></i> Confirmar Cancelación';
                                                                                confirmCancelBtn.disabled = false;
                                                                            }
                                                                        });
                                                                    });

                                                                    // Auto-close alerts after 5 seconds
                                                                    setTimeout(function () {
                                                                        const alerts = document.querySelectorAll('.alert');
                                                                        alerts.forEach(alert => {
                                                                            const bsAlert = new bootstrap.Alert(alert);
                                                                            bsAlert.close();
                                                                        });
                                                                    }, 5000);
        </script>
    </body>
</html>