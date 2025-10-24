<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

        .btn-action {
            padding: 0.375rem 0.75rem;
            border-radius: 6px;
            font-size: 0.875rem;
            margin: 0.125rem;
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
            <h2 class="section-title">
                <i class="bi bi-calendar-check"></i> Todas las Citas
            </h2>

            <!-- Added filter by patient -->
            <div class="filter-card">
                <form action="${pageContext.request.contextPath}/CitasController" method="get" class="row g-3">
                    <input type="hidden" name="accion" value="listar">
                    <div class="col-md-4">
                        <label class="form-label fw-bold">Filtrar por Paciente</label>
                        <select class="form-select" name="pacienteId" onchange="this.form.submit()">
                            <option value="">Todos los pacientes</option>
                            <c:forEach var="paciente" items="${pacientes}">
                                <option value="${paciente.id}" ${param.pacienteId == paciente.id ? 'selected' : ''}>
                                    ${paciente.usuarioNombre} ${paciente.usuarioApellido} - ${paciente.codigoPaciente}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label fw-bold">Estado</label>
                        <select class="form-select" name="estado" onchange="this.form.submit()">
                            <option value="">Todos</option>
                            <option value="programada" ${param.estado == 'programada' ? 'selected' : ''}>Programada</option>
                            <option value="completada" ${param.estado == 'completada' ? 'selected' : ''}>Completada</option>
                            <option value="cancelada" ${param.estado == 'cancelada' ? 'selected' : ''}>Cancelada</option>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label fw-bold">Fecha</label>
                        <input type="date" class="form-control" name="fecha" value="${param.fecha}" onchange="this.form.submit()">
                    </div>
                    <div class="col-md-2">
                        <label class="form-label">&nbsp;</label>
                        <a href="${pageContext.request.contextPath}/CitasController?accion=listar" class="btn btn-secondary w-100">
                            <i class="bi bi-x-circle"></i> Limpiar
                        </a>
                    </div>
                </form>
            </div>

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
                        <c:forEach var="cita" items="${citas}">
                            <tr>
                                <td>${cita.id}</td>
                                <td>
                                    <strong>${cita.pacienteNombre} ${cita.pacienteApellido}</strong>
                                </td>
                                <td>
                                    Dr. ${cita.doctorNombre} ${cita.doctorApellido}<br>
                                    <small class="text-muted">${cita.doctorEspecializacion}</small>
                                </td>
                                <td>${cita.fechaCita}</td>
                                <td>${cita.horaCita}</td>
                                <td>
                                    <span class="badge badge-${cita.estado}">
                                        ${cita.estado}
                                    </span>
                                </td>
                                <td>${cita.motivo}</td>
                                <td>
                                    <c:if test="${cita.estado == 'programada'}">
                                        <button class="btn btn-sm btn-danger btn-action" onclick="cancelarCita(${cita.id})">
                                            <i class="bi bi-x-circle"></i> Cancelar
                                        </button>
                                    </c:if>
                                    <c:if test="${cita.estado == 'completada'}">
                                        <a href="${pageContext.request.contextPath}/ConsultasController?accion=ver&citaId=${cita.id}" class="btn btn-sm btn-info btn-action">
                                            <i class="bi bi-eye"></i> Ver Consulta
                                        </a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
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

    <!-- Added cancel modal with reason -->
    <div class="modal fade" id="cancelModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-danger text-white">
                    <h5 class="modal-title"><i class="bi bi-x-circle"></i> Cancelar Cita</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <form action="${pageContext.request.contextPath}/CitasController" method="post">
                    <input type="hidden" name="accion" value="cancelar">
                    <input type="hidden" name="citaId" id="cancelCitaId">
                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label fw-bold">Motivo de Cancelación *</label>
                            <textarea class="form-control" name="motivoCancelacion" rows="3" required placeholder="Ingrese el motivo de la cancelación"></textarea>
                        </div>
                        <div class="alert alert-warning">
                            <i class="bi bi-exclamation-triangle"></i> Esta acción no se puede deshacer
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        <button type="submit" class="btn btn-danger">Confirmar Cancelación</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function cancelarCita(citaId) {
            document.getElementById('cancelCitaId').value = citaId;
            new bootstrap.Modal(document.getElementById('cancelModal')).show();
        }
    </script>
</body>
</html>
