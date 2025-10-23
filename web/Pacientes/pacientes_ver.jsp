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
            background-color: #f8f9fa;
        }
        .card {
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            border: none;
            margin-bottom: 20px;
        }
        .patient-header {
            background: linear-gradient(135deg, #198754 0%, #20c997 100%);
            color: white;
            padding: 30px;
            border-radius: 10px 10px 0 0;
        }
        .patient-avatar {
            width: 100px;
            height: 100px;
            background-color: white;
            color: #198754;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 48px;
            margin-bottom: 15px;
        }
        .info-label {
            font-weight: 600;
            color: #6c757d;
            margin-bottom: 5px;
        }
        .info-value {
            font-size: 1.1rem;
            color: #212529;
            margin-bottom: 15px;
        }
        .section-title {
            color: #198754;
            font-weight: 600;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #198754;
        }
        .badge-custom {
            font-size: 0.9rem;
            padding: 8px 15px;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-success">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}dashboard_recepcionista.jsp">
                <i class="bi bi-hospital"></i> Sistema Médico
            </a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="${pageContext.request.contextPath}/PacientesController?accion=listar">
                    <i class="bi bi-arrow-left"></i> Volver a Pacientes
                </a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}dashboard_recepcionista.jsp">Inicio</a></li>
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/PacientesController?accion=listar">Pacientes</a></li>
                <li class="breadcrumb-item active">Detalles del Paciente</li>
            </ol>
        </nav>

        <div class="card">
            <div class="patient-header text-center">
                <div class="patient-avatar mx-auto">
                    <i class="bi bi-person-fill"></i>
                </div>
                <h2 class="mb-2">${paciente.usuarioNombre} ${paciente.usuarioApellido}</h2>
                <p class="mb-0 fs-5">
                    <i class="bi bi-credit-card-2-front"></i> 
                    Código: <strong>${paciente.codigoPaciente}</strong>
                </p>
            </div>

            <div class="card-body p-4">
                <div class="row">
                    <!-- Información Personal -->
                    <div class="col-md-6">
                        <h4 class="section-title">
                            <i class="bi bi-person-badge"></i> Información Personal
                        </h4>
                        
                        <div class="info-label">
                            <i class="bi bi-calendar3"></i> Fecha de Nacimiento
                        </div>
                        <div class="info-value">${paciente.fechaNacimiento}</div>
                        
                        <div class="info-label">
                            <i class="bi bi-gender-ambiguous"></i> Género
                        </div>
                        <div class="info-value">
                            <c:choose>
                                <c:when test="${paciente.genero == 'Masculino'}">
                                    <span class="badge bg-primary badge-custom">
                                        <i class="bi bi-gender-male"></i> Masculino
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-danger badge-custom">
                                        <i class="bi bi-gender-female"></i> Femenino
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        
                        <div class="info-label">
                            <i class="bi bi-geo-alt-fill"></i> Dirección
                        </div>
                        <div class="info-value">${paciente.direccion}</div>
                    </div>

                    <!-- Información de Contacto -->
                    <div class="col-md-6">
                        <h4 class="section-title">
                            <i class="bi bi-telephone-fill"></i> Información de Contacto
                        </h4>
                        
                        <div class="info-label">
                            <i class="bi bi-envelope-fill"></i> Correo Electrónico
                        </div>
                        <div class="info-value">${paciente.usuarioCorreo}</div>
                        
                        <div class="info-label">
                            <i class="bi bi-phone-fill"></i> Teléfono
                        </div>
                        <div class="info-value">${paciente.usuarioTelefono}</div>
                        
                        <div class="info-label">
                            <i class="bi bi-person-circle"></i> Rol
                        </div>
                        <div class="info-value">
                            <span class="badge bg-info badge-custom">
                                <i class="bi bi-person-heart"></i> ${paciente.usuarioRol}
                            </span>
                        </div>
                    </div>
                </div>

                <hr class="my-4">

                <!-- Contacto de Emergencia -->
                <div class="row">
                    <div class="col-12">
                        <h4 class="section-title">
                            <i class="bi bi-exclamation-triangle-fill"></i> Contacto de Emergencia
                        </h4>
                    </div>
                    <div class="col-md-6">
                        <div class="info-label">
                            <i class="bi bi-person-fill"></i> Nombre del Contacto
                        </div>
                        <div class="info-value">${paciente.contactoEmergenciaNombre}</div>
                    </div>
                    <div class="col-md-6">
                        <div class="info-label">
                            <i class="bi bi-telephone-fill"></i> Teléfono del Contacto
                        </div>
                        <div class="info-value">${paciente.contactoEmergenciaTelefono}</div>
                    </div>
                </div>

                <hr class="my-4">

                <!-- Información Médica -->
                <div class="row">
                    <div class="col-12">
                        <h4 class="section-title">
                            <i class="bi bi-file-medical-fill"></i> Información Médica
                        </h4>
                    </div>
                    <div class="col-md-6">
                        <div class="info-label">
                            <i class="bi bi-clipboard-pulse"></i> Historial Médico
                        </div>
                        <div class="info-value">
                            <c:choose>
                                <c:when test="${empty paciente.historialMedico}">
                                    <span class="text-muted">Sin historial registrado</span>
                                </c:when>
                                <c:otherwise>
                                    <div class="alert alert-info mb-0">
                                        ${paciente.historialMedico}
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="info-label">
                            <i class="bi bi-bandaid-fill"></i> Alergias
                        </div>
                        <div class="info-value">
                            <c:choose>
                                <c:when test="${empty paciente.alergias}">
                                    <span class="text-muted">Sin alergias registradas</span>
                                </c:when>
                                <c:otherwise>
                                    <div class="alert alert-warning mb-0">
                                        <i class="bi bi-exclamation-circle-fill"></i> ${paciente.alergias}
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>

                <hr class="my-4">

                <!-- Botones de Acción -->
                <div class="d-flex justify-content-between">
                    <a href="${pageContext.request.contextPath}/PacientesController?accion=listar" 
                       class="btn btn-secondary">
                        <i class="bi bi-arrow-left"></i> Volver a la Lista
                    </a>
                    <div>
                        <a href="${pageContext.request.contextPath}/PacientesController?accion=editar&id=${paciente.id}" 
                           class="btn btn-warning">
                            <i class="bi bi-pencil-square"></i> Editar Paciente
                        </a>
                        <a href="${pageContext.request.contextPath}/PacientesController?accion=historial&id=${paciente.id}" 
                           class="btn btn-primary">
                            <i class="bi bi-file-medical-fill"></i> Ver Historial Completo
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
