<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Historial de Citas - Sistema Médico</title>
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

        .patient-header {
            background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-dark) 100%);
            color: white;
            padding: 2rem;
            border-radius: 12px;
            margin-bottom: 2rem;
        }

        .content-card {
            background: white;
            border-radius: 12px;
            box-shadow: 0 2px 15px rgba(0,0,0,0.1);
            padding: 2rem;
            margin-bottom: 1.5rem;
        }

        .cita-card {
            border: 2px solid #e2e8f0;
            border-radius: 10px;
            padding: 1.5rem;
            margin-bottom: 1rem;
            transition: all 0.3s ease;
        }

        .cita-card:hover {
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            transform: translateY(-2px);
        }

        .cita-card.programada {
            border-left: 5px solid #3b82f6;
        }

        .cita-card.completada {
            border-left: 5px solid #10b981;
        }

        .cita-card.cancelada {
            border-left: 5px solid #ef4444;
            opacity: 0.7;
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

    <div class="container p-4">
        <div class="patient-header">
            <h2><i class="bi bi-person-circle"></i> ${paciente.usuarioNombre} ${paciente.usuarioApellido}</h2>
            <p class="mb-0">Código: ${paciente.codigoPaciente}</p>
        </div>

        <div class="content-card">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h3><i class="bi bi-calendar-check"></i> Historial de Citas</h3>
                <a href="${pageContext.request.contextPath}/CitasController?accion=nueva&pacienteId=${paciente.id}" class="btn btn-primary">
                    <i class="bi bi-plus-circle"></i> Agregar Cita
                </a>
            </div>

            <c:forEach var="cita" items="${citas}">
                <div class="cita-card ${cita.estado}">
                    <div class="row">
                        <div class="col-md-8">
                            <h5>
                                <i class="bi bi-person-badge"></i> 
                                Dr. ${cita.doctorNombre} ${cita.doctorApellido}
                            </h5>
                            <p class="text-muted mb-2">${cita.doctorEspecializacion}</p>
                            <p class="mb-1">
                                <i class="bi bi-calendar3"></i> <strong>Fecha:</strong> ${cita.fechaCita}
                            </p>
                            <p class="mb-1">
                                <i class="bi bi-clock"></i> <strong>Hora:</strong> ${cita.horaCita}
                            </p>
                            <p class="mb-1">
                                <i class="bi bi-chat-left-text"></i> <strong>Motivo:</strong> ${cita.motivo}
                            </p>
                        </div>
                        <div class="col-md-4 text-end">
                            <span class="badge badge-${cita.estado} mb-3">${cita.estado}</span>
                            <div>
                                <c:if test="${cita.estado == 'programada'}">
                                    <button class="btn btn-sm btn-warning mb-2 w-100" onclick="editarCita(${cita.id})">
                                        <i class="bi bi-pencil"></i> Editar
                                    </button>
                                    <button class="btn btn-sm btn-danger w-100" onclick="cancelarCita(${cita.id})">
                                        <i class="bi bi-x-circle"></i> Cancelar
                                    </button>
                                </c:if>
                                <c:if test="${cita.estado == 'completada'}">
                                    <a href="${pageContext.request.contextPath}/ConsultasController?accion=ver&citaId=${cita.id}" class="btn btn-sm btn-info w-100">
                                        <i class="bi bi-eye"></i> Ver Consulta
                                    </a>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>

            <c:if test="${empty citas}">
                <div class="alert alert-info text-center">
                    <i class="bi bi-info-circle"></i> No hay citas registradas para este paciente
                </div>
            </c:if>

            <div class="mt-4">
                <a href="${pageContext.request.contextPath}/UsuariosController?accion=listar" class="btn btn-secondary">
                    <i class="bi bi-arrow-left"></i> Regresar
                </a>
            </div>
        </div>
    </div>

    <!-- Cancel Modal -->
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
                    <input type="hidden" name="returnUrl" value="paciente_citas">
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

        function editarCita(citaId) {
            window.location.href = '${pageContext.request.contextPath}/CitasController?accion=editar&id=' + citaId;
        }
    </script>
</body>
</html>
