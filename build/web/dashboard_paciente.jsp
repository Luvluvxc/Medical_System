<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Paciente - Sistema Médico</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        :root {
            --primary-color: #2563eb;
            --primary-dark: #1e40af;
            --secondary-color: #0ea5e9;
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

        .sidebar {
            background: white;
            min-height: calc(100vh - 56px);
            box-shadow: 2px 0 10px rgba(0,0,0,0.05);
            padding: 1.5rem 0;
        }

        .sidebar .nav-link {
            color: #64748b;
            padding: 0.875rem 1.5rem;
            margin: 0.25rem 0;
            border-radius: 0;
            border-left: 3px solid transparent;
            transition: all 0.3s ease;
            font-weight: 500;
        }

        .sidebar .nav-link:hover {
            background-color: #eff6ff;
            color: var(--primary-color);
            border-left-color: var(--primary-color);
        }

        .sidebar .nav-link.active {
            background-color: #dbeafe;
            color: var(--primary-color);
            border-left-color: var(--primary-color);
        }

        .welcome-card {
            background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-dark) 100%);
            color: white;
            border-radius: 12px;
            padding: 2rem;
            margin-bottom: 2rem;
            box-shadow: 0 4px 15px rgba(37, 99, 235, 0.3);
        }

        .info-card {
            background: white;
            border-radius: 12px;
            padding: 2rem;
            box-shadow: 0 2px 15px rgba(0,0,0,0.1);
            margin-bottom: 2rem;
        }

        .cita-card {
            background: white;
            border-radius: 10px;
            padding: 1.5rem;
            margin-bottom: 1rem;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            border-left: 4px solid;
            transition: all 0.3s ease;
        }

        .cita-card.programada {
            border-left-color: #3b82f6;
        }

        .cita-card.completada {
            border-left-color: #10b981;
        }

        .cita-card:hover {
            box-shadow: 0 4px 15px rgba(0,0,0,0.15);
            transform: translateX(5px);
        }

        .section-title {
            color: var(--primary-color);
            font-weight: 600;
            margin-bottom: 1.5rem;
            padding-bottom: 0.75rem;
            border-bottom: 3px solid var(--primary-color);
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
            <a class="navbar-brand fw-bold" href="#">
                <i class="bi bi-hospital-fill"></i> Sistema Médico
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                            <i class="bi bi-person-circle"></i> ${usuario.nombre}
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li><a class="dropdown-item" href="Validar?accion=salir"><i class="bi bi-box-arrow-right"></i> Cerrar Sesión</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container-fluid">
        <div class="row">
            <div class="col-md-2 sidebar">
                <nav class="nav flex-column">
                    <a class="nav-link active" href="dashboard_paciente.jsp">
                        <i class="bi bi-speedometer2"></i> Dashboard
                    </a>
                    <a class="nav-link" href="${pageContext.request.contextPath}/PacientesController?accion=misCitas">
                        <i class="bi bi-calendar-check"></i> Mis Citas
                    </a>
                    <a class="nav-link" href="${pageContext.request.contextPath}/PacientesController?accion=misConsultas">
                        <i class="bi bi-file-medical"></i> Mis Consultas
                    </a>
                </nav>
            </div>

            <div class="col-md-10 p-4">
                <div class="welcome-card">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h2>Bienvenido, ${usuario.nombre} ${usuario.apellido}</h2>
                            <p>Panel de Paciente - Sistema de Gestión Médica</p>
                        </div>
                        <div>
                            <span class="badge bg-white text-primary fs-6 px-3 py-2">
                                <i class="bi bi-credit-card"></i> ${paciente.codigoPaciente}
                            </span>
                        </div>
                    </div>
                </div>

                <div class="info-card">
                    <h4 class="section-title">
                        <i class="bi bi-person-badge"></i> Mi Información
                    </h4>
                    <div class="row">
                        <div class="col-md-6">
                            <p><strong>Nombre:</strong> ${paciente.usuarioNombre} ${paciente.usuarioApellido}</p>
                            <p><strong>Código:</strong> ${paciente.codigoPaciente}</p>
                            <p><strong>Correo:</strong> ${paciente.usuarioCorreo}</p>
                        </div>
                        <div class="col-md-6">
                            <p><strong>Teléfono:</strong> ${paciente.usuarioTelefono}</p>
                            <p><strong>Fecha de Nacimiento:</strong> ${paciente.fechaNacimiento}</p>
                            <p><strong>Género:</strong> ${paciente.genero}</p>
                        </div>
                    </div>
                </div>

                <h3 class="section-title">
                    <i class="bi bi-calendar-check"></i> Próximas Citas
                </h3>

                <c:forEach var="cita" items="${proximasCitas}">
                    <div class="cita-card programada">
                        <div class="row align-items-center">
                            <div class="col-md-8">
                                <h5><i class="bi bi-person-badge"></i> Dr. ${cita.doctorNombre} ${cita.doctorApellido}</h5>
                                <p class="text-muted mb-2">${cita.doctorEspecializacion}</p>
                                <p class="mb-1"><i class="bi bi-calendar3"></i> <strong>Fecha:</strong> ${cita.fechaCita}</p>
                                <p class="mb-1"><i class="bi bi-clock"></i> <strong>Hora:</strong> ${cita.horaCita}</p>
                                <p class="mb-1"><i class="bi bi-chat-left-text"></i> <strong>Motivo:</strong> ${cita.motivo}</p>
                                <span class="badge badge-${cita.estado}">${cita.estado}</span>
                            </div>
                            <div class="col-md-4 text-end">
                                <c:if test="${cita.estado == 'programada'}">
                                    <button class="btn btn-danger" onclick="cancelarCita(${cita.id})">
                                        <i class="bi bi-x-circle"></i> Cancelar Cita
                                    </button>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </c:forEach>

                <c:if test="${empty proximasCitas}">
                    <div class="alert alert-info">
                        <i class="bi bi-info-circle"></i> No tienes citas programadas
                    </div>
                </c:if>

                <h3 class="section-title mt-4">
                    <i class="bi bi-file-medical"></i> Consultas Recientes
                </h3>

                <c:forEach var="consulta" items="${consultasRecientes}">
                    <div class="cita-card completada">
                        <div class="row">
                            <div class="col-md-8">
                                <h5><i class="bi bi-person-badge"></i> Dr. ${consulta.doctorNombre} ${consulta.doctorApellido}</h5>
                                <p class="mb-1"><i class="bi bi-calendar3"></i> <strong>Fecha:</strong> ${consulta.fechaCita}</p>
                                <p class="mb-1"><i class="bi bi-clipboard-pulse"></i> <strong>Diagnóstico:</strong> ${consulta.diagnostico}</p>
                                <p class="mb-1"><i class="bi bi-prescription2"></i> <strong>Tratamiento:</strong> ${consulta.planTratamiento}</p>
                            </div>
                            <div class="col-md-4 text-end">
                                <a href="${pageContext.request.contextPath}/ConsultasController?accion=ver&citaId=${consulta.citaId}" class="btn btn-info">
                                    <i class="bi bi-eye"></i> Ver Detalle
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>

                <c:if test="${empty consultasRecientes}">
                    <div class="alert alert-info">
                        <i class="bi bi-info-circle"></i> No tienes consultas registradas
                    </div>
                </c:if>
            </div>
        </div>
    </div>

    <!-- Modal Cancelar Cita -->
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
                    <input type="hidden" name="returnUrl" value="dashboard_paciente">
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
