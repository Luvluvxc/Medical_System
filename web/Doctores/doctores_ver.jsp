<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalles del Doctor - Sistema Médico</title>
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
        .doctor-header {
            background: linear-gradient(135deg, #0d6efd 0%, #0dcaf0 100%);
            color: white;
            padding: 3rem 2rem;
            text-align: center;
        }
        .doctor-avatar {
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
            color: #0d6efd;
            font-weight: 600;
            margin-bottom: 1.5rem;
            padding-bottom: 0.75rem;
            border-bottom: 3px solid #0d6efd;
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
            background: linear-gradient(90deg, transparent, #0d6efd, transparent);
            margin: 2rem 0;
        }
        .specialty-badge {
            background: linear-gradient(135deg, #0d6efd 0%, #0dcaf0 100%);
            color: white;
            border-radius: 20px;
            padding: 0.75rem 1.5rem;
            font-size: 1rem;
            display: inline-block;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark" style="background: rgba(0,0,0,0.2);">
        <div class="container-fluid">
            <a class="navbar-brand nav-brand" href="dashboard_recepcionista.jsp">
                <i class="bi bi-hospital"></i> Sistema Médico
            </a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="DoctoresController?accion=listar">
                    <i class="bi bi-arrow-left"></i> Volver a Doctores
                </a>
            </div>
        </div>
    </nav>

    <div class="container main-container">
        <!-- Breadcrumb -->
        <nav aria-label="breadcrumb" class="mb-4">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="dashboard_recepcionista.jsp">Inicio</a></li>
                <li class="breadcrumb-item"><a href="DoctoresController?accion=listar">Doctores</a></li>
                <li class="breadcrumb-item active text-white">Detalles del Doctor</li>
            </ol>
        </nav>

        <div class="card">
            <!-- Header del Doctor -->
            <div class="doctor-header">
                <div class="doctor-avatar">
                    ${doctor.usuarioNombre.substring(0,1)}${doctor.usuarioApellido.substring(0,1)}
                </div>
                <h1 class="mb-3">Dr. ${doctor.usuarioNombre} ${doctor.usuarioApellido}</h1>
                <div class="row justify-content-center">
                    <div class="col-auto">
                        <span class="specialty-badge">
                            <i class="bi bi-star-fill me-2"></i>
                            ${doctor.especializacion}
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
                                <i class="bi bi-person me-2"></i>Nombre Completo
                            </div>
                            <div class="info-value">
                                <i class="bi bi-person-check me-2 text-primary"></i>
                                ${doctor.usuarioNombre} ${doctor.usuarioApellido}
                            </div>
                        </div>

                        <div class="mb-3">
                            <div class="info-label">
                                <i class="bi bi-envelope me-2"></i>Correo Electrónico
                            </div>
                            <div class="info-value">
                                <i class="bi bi-at me-2 text-info"></i>${doctor.usuarioCorreo}
                            </div>
                        </div>

                        <div class="mb-3">
                            <div class="info-label">
                                <i class="bi bi-telephone me-2"></i>Teléfono
                            </div>
                            <div class="info-value">
                                <i class="bi bi-phone me-2 text-success"></i>${doctor.usuarioTelefono}
                            </div>
                        </div>
                    </div>

                    <!-- Información Profesional -->
                    <div class="info-card">
                        <h4 class="section-title">
                            <i class="bi bi-briefcase me-2"></i>Información Profesional
                        </h4>

                        <div class="mb-3">
                            <div class="info-label">
                                <i class="bi bi-file-medical me-2"></i>Licencia Médica
                            </div>
                            <div class="info-value">
                                <i class="bi bi-card-checklist me-2 text-warning"></i>
                                ${doctor.numeroLicencia}
                            </div>
                        </div>

                        <div class="mb-3">
                            <div class="info-label">
                                <i class="bi bi-star me-2"></i>Especialización
                            </div>
                            <div class="info-value">
                                <span class="badge bg-primary badge-custom">
                                    <i class="bi bi-star-fill me-1"></i> ${doctor.especializacion}
                                </span>
                            </div>
                        </div>

                        <c:if test="${not empty doctor.anosExperiencia}">
                        <div class="mb-3">
                            <div class="info-label">
                                <i class="bi bi-award me-2"></i>Experiencia
                            </div>
                            <div class="info-value">
                                <i class="bi bi-graph-up me-2 text-success"></i>
                                ${doctor.anosExperiencia} años
                            </div>
                        </div>
                        </c:if>
                    </div>
                </div>

                <div class="divider"></div>

                <!-- Información Adicional -->
                <c:if test="${not empty doctor.horarioConsulta}">
                <div class="info-card">
                    <h4 class="section-title">
                        <i class="bi bi-clock me-2"></i>Horario de Consulta
                    </h4>
                    <div class="info-value">
                        <i class="bi bi-calendar-week me-2 text-primary"></i>
                        ${doctor.horarioConsulta}
                    </div>
                </div>
                </c:if>

                <!-- Información del Usuario -->
                <div class="info-card">
                    <h4 class="section-title">
                        <i class="bi bi-person-gear me-2"></i>Información de Cuenta
                    </h4>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="mb-3">
                                <div class="info-label">
                                    <i class="bi bi-person-circle me-2"></i>Rol
                                </div>
                                <div class="info-value">
                                    <span class="badge bg-info badge-custom">
                                        <i class="bi bi-person-badge me-1"></i> ${doctor.usuarioRol}
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="mb-3">
                                <div class="info-label">
                                    <i class="bi bi-calendar me-2"></i>Fecha de Registro
                                </div>
                                <div class="info-value">
                                    <i class="bi bi-clock-history me-2 text-muted"></i>
                                    ${doctor.fechaRegistro}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="divider"></div>

                <!-- Botones de Acción -->
                <div class="d-flex justify-content-between align-items-center mt-4">
                    <a href="DoctoresController?accion=listar" 
                       class="btn btn-secondary action-btn">
                        <i class="bi bi-arrow-left me-2"></i>Volver a la Lista
                    </a>
                    <div class="d-flex gap-3">
                        <a href="DoctoresController?accion=editar&id=${doctor.id}" 
                           class="btn btn-warning action-btn">
                            <i class="bi bi-pencil-square me-2"></i>Editar Doctor
                        </a>
                        <a href="CitasController?accion=nuevo&doctorId=${doctor.id}" 
                           class="btn btn-primary action-btn">
                            <i class="bi bi-calendar-plus me-2"></i>Agendar Cita
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>