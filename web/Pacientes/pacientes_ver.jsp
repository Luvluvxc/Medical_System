<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Detalles del Paciente - Sistema Médico</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
        <style>
            body {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                min-height: 100vh;
            }
            .main-container {
                margin-top: 2rem;
                margin-bottom: 2rem;
            }
            .card {
                border: none;
                border-radius: 20px;
                box-shadow: 0 20px 60px rgba(0,0,0,0.3);
                overflow: hidden;
            }
            .patient-header {
                background: linear-gradient(135deg, #198754 0%, #20c997 100%);
                color: white;
                padding: 3rem 2rem;
                text-align: center;
            }
            .patient-avatar {
                width: 120px;
                height: 120px;
                background: rgba(255,255,255,0.2);
                color: white;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 3rem;
                margin: 0 auto 1.5rem;
                border: 4px solid rgba(255,255,255,0.3);
                backdrop-filter: blur(10px);
            }
            .info-card {
                background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
                border-radius: 15px;
                padding: 1.5rem;
                margin-bottom: 1.5rem;
                border: none;
                transition: transform 0.3s ease;
            }
            .info-card:hover {
                transform: translateY(-5px);
            }
            .info-label {
                font-weight: 600;
                color: #495057;
                margin-bottom: 0.5rem;
                font-size: 0.9rem;
            }
            .info-value {
                font-size: 1.1rem;
                color: #212529;
                margin-bottom: 0;
                font-weight: 500;
            }
            .section-title {
                color: #198754;
                font-weight: 600;
                margin-bottom: 1.5rem;
                padding-bottom: 0.75rem;
                border-bottom: 3px solid #198754;
            }
            .badge-custom {
                font-size: 0.9rem;
                padding: 0.5rem 1rem;
                border-radius: 25px;
            }
            .breadcrumb {
                background: rgba(255,255,255,0.1);
                backdrop-filter: blur(10px);
                border-radius: 10px;
                padding: 0.75rem 1rem;
            }
            .breadcrumb-item a {
                color: #e9ecef;
                text-decoration: none;
            }
            .breadcrumb-item.active {
                color: white;
            }
            .nav-brand {
                font-weight: 600;
            }
            .alert-custom {
                border-radius: 12px;
                border: none;
                padding: 1rem 1.25rem;
            }
            .action-btn {
                border-radius: 12px;
                padding: 0.75rem 2rem;
                font-weight: 600;
                transition: all 0.3s ease;
            }
            .action-btn:hover {
                transform: translateY(-2px);
                box-shadow: 0 8px 25px rgba(0,0,0,0.2);
            }
            .info-grid {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
                gap: 1.5rem;
                margin-bottom: 2rem;
            }
            .divider {
                height: 2px;
                background: linear-gradient(90deg, transparent, #198754, transparent);
                margin: 2rem 0;
            }
        </style>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark" style="background: rgba(0,0,0,0.2);">
            <div class="container-fluid">
                <a class="navbar-brand nav-brand" href="${pageContext.request.contextPath}/dashboard_recepcionista.jsp">
                    <i class="bi bi-hospital"></i> Sistema Médico
                </a>
                <div class="navbar-nav ms-auto">
                    <a class="nav-link" href="${pageContext.request.contextPath}/PacientesController?accion=listar">
                        <i class="bi bi-arrow-left"></i> Volver a Pacientes
                    </a>
                </div>
            </div>
        </nav>

        <div class="container main-container">
            <!-- Breadcrumb -->
            <nav aria-label="breadcrumb" class="mb-4">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/dashboard_recepcionista.jsp">Inicio</a></li>
                    <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/PacientesController?accion=listar">Pacientes</a></li>
                    <li class="breadcrumb-item active text-white">Detalles del Paciente</li>
                </ol>
            </nav>

            <div class="card">
                <!-- Header del Paciente -->
                <div class="patient-header">
                    <div class="patient-avatar">
                        <i class="bi bi-person-fill"></i>
                    </div>
                    <h1 class="mb-3">${paciente.usuarioNombre} ${paciente.usuarioApellido}</h1>
                    <div class="row justify-content-center">
                        <div class="col-auto">
                            <span class="badge bg-light text-dark fs-6 p-3">
                                <i class="bi bi-credit-card-2-front me-2"></i>
                                Código: <strong>${paciente.codigoPaciente}</strong>
                            </span>
                        </div>
                    </div>
                </div>

                <div class="card-body p-4">
                    <!-- Información Principal -->
                    <div class="info-grid">
                        <!-- Información Personal -->
                        <div class="info-card">
                            <h4 class="section-title">
                                <i class="bi bi-person-badge me-2"></i>Información Personal
                            </h4>
                            
                            <div class="mb-3">
                                <div class="info-label">
                                    <i class="bi bi-calendar3 me-2"></i>Fecha de Nacimiento
                                </div>
                                <div class="info-value">
                                    <i class="bi bi-calendar-check me-2 text-primary"></i>${paciente.fechaNacimiento}
                                </div>
                            </div>

                            <div class="mb-3">
                                <div class="info-label">
                                    <i class="bi bi-gender-ambiguous me-2"></i>Género
                                </div>
                                <div class="info-value">
                                    <c:choose>
                                        <c:when test="${paciente.genero == 'Masculino'}">
                                            <span class="badge bg-primary badge-custom">
                                                <i class="bi bi-gender-male me-1"></i> Masculino
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-danger badge-custom">
                                                <i class="bi bi-gender-female me-1"></i> Femenino
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>

                            <div class="mb-3">
                                <div class="info-label">
                                    <i class="bi bi-geo-alt-fill me-2"></i>Dirección
                                </div>
                                <div class="info-value">
                                    <i class="bi bi-house me-2 text-success"></i>${paciente.direccion}
                                </div>
                            </div>
                        </div>

                        <!-- Información de Contacto -->
                        <div class="info-card">
                            <h4 class="section-title">
                                <i class="bi bi-telephone-fill me-2"></i>Información de Contacto
                            </h4>

                            <div class="mb-3">
                                <div class="info-label">
                                    <i class="bi bi-envelope-fill me-2"></i>Correo Electrónico
                                </div>
                                <div class="info-value">
                                    <i class="bi bi-at me-2 text-info"></i>${paciente.usuarioCorreo}
                                </div>
                            </div>

                            <div class="mb-3">
                                <div class="info-label">
                                    <i class="bi bi-phone-fill me-2"></i>Teléfono
                                </div>
                                <div class="info-value">
                                    <i class="bi bi-telephone me-2 text-success"></i>${paciente.usuarioTelefono}
                                </div>
                            </div>

                            <div class="mb-3">
                                <div class="info-label">
                                    <i class="bi bi-person-circle me-2"></i>Rol
                                </div>
                                <div class="info-value">
                                    <span class="badge bg-info badge-custom">
                                        <i class="bi bi-person-heart me-1"></i> ${paciente.usuarioRol}
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="divider"></div>

                    <!-- Contacto de Emergencia -->
                    <div class="info-card">
                        <h4 class="section-title">
                            <i class="bi bi-exclamation-triangle-fill me-2"></i>Contacto de Emergencia
                        </h4>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <div class="info-label">
                                        <i class="bi bi-person-fill me-2"></i>Nombre del Contacto
                                    </div>
                                    <div class="info-value">
                                        <i class="bi bi-person-badge me-2 text-warning"></i>${paciente.contactoEmergenciaNombre}
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <div class="info-label">
                                        <i class="bi bi-telephone-fill me-2"></i>Teléfono del Contacto
                                    </div>
                                    <div class="info-value">
                                        <i class="bi bi-telephone-outbound me-2 text-danger"></i>${paciente.contactoEmergenciaTelefono}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="divider"></div>

                    <!-- Información Médica -->
                    <div class="info-grid">
                        <div class="info-card">
                            <h4 class="section-title">
                                <i class="bi bi-file-medical-fill me-2"></i>Historial Médico
                            </h4>
                            <div class="info-value">
                                <c:choose>
                                    <c:when test="${empty paciente.historialMedico}">
                                        <div class="alert alert-info alert-custom mb-0">
                                            <i class="bi bi-info-circle me-2"></i>
                                            <span class="text-muted">Sin historial médico registrado</span>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="alert alert-info alert-custom mb-0">
                                            <i class="bi bi-clipboard-pulse me-2"></i>
                                            ${paciente.historialMedico}
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <div class="info-card">
                            <h4 class="section-title">
                                <i class="bi bi-bandaid-fill me-2"></i>Alergias
                            </h4>
                            <div class="info-value">
                                <c:choose>
                                    <c:when test="${empty paciente.alergias}">
                                        <div class="alert alert-warning alert-custom mb-0">
                                            <i class="bi bi-check-circle me-2"></i>
                                            <span class="text-muted">Sin alergias registradas</span>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="alert alert-warning alert-custom mb-0">
                                            <i class="bi bi-exclamation-triangle me-2"></i>
                                            ${paciente.alergias}
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>

                    <div class="divider"></div>

                    <!-- Botones de Acción -->
                    <div class="d-flex justify-content-between align-items-center mt-4">
                        <a href="UsuariosController?accion=listar'" 
                           class="btn btn-secondary action-btn">
                            <i class="bi bi-arrow-left me-2"></i>Volver a la Lista
                        </a>
                        <div class="d-flex gap-3">
                            <a href="${pageContext.request.contextPath}/PacientesController?accion=editar&id=${paciente.id}" 
                               class="btn btn-warning action-btn">
                                <i class="bi bi-pencil-square me-2"></i>Editar Paciente
                            </a>
                            <a href="${pageContext.request.contextPath}/CitasController?accion=nuevo&pacienteId=${paciente.id}" 
                               class="btn btn-primary action-btn">
                                <i class="bi bi-calendar-plus me-2"></i>Crear Cita
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>