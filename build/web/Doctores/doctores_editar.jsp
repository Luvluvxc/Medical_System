<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Doctor - Sistema Médico</title>
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
        .card-header {
            background: linear-gradient(135deg, #ffc107 0%, #fd7e14 100%);
            color: white;
            padding: 2rem;
            border-bottom: none;
        }
        .form-control {
            border-radius: 12px;
            padding: 0.75rem 1rem;
            border: 2px solid #e8e8e8;
            transition: all 0.3s ease;
        }
        .form-control:focus {
            border-color: #ffc107;
            box-shadow: 0 0 0 0.2rem rgba(255, 193, 7, 0.25);
            transform: translateY(-2px);
        }
        .form-label {
            font-weight: 600;
            color: #495057;
            margin-bottom: 0.5rem;
        }
        .input-group-text {
            background: linear-gradient(135deg, #ffc107 0%, #fd7e14 100%);
            color: white;
            border: none;
            border-radius: 12px 0 0 12px;
        }
        .btn {
            border-radius: 12px;
            padding: 0.75rem 2rem;
            font-weight: 600;
            transition: all 0.3s ease;
        }
        .btn-warning {
            background: linear-gradient(135deg, #ffc107 0%, #fd7e14 100%);
            border: none;
            color: #000;
        }
        .btn-warning:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(255, 193, 7, 0.3);
        }
        .btn-secondary {
            background: linear-gradient(135deg, #868e96 0%, #495057 100%);
            border: none;
        }
        .btn-secondary:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(73, 80, 87, 0.3);
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
        .alert-custom {
            border-radius: 12px;
            border: none;
            padding: 1rem 1.25rem;
        }
        .doctor-avatar {
            width: 80px;
            height: 80px;
            border-radius: 50%;
            background: linear-gradient(135deg, #ffc107 0%, #fd7e14 100%);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.5rem;
            color: white;
            font-weight: bold;
            margin: 0 auto 1rem;
        }
        .nav-brand {
            font-weight: 600;
        }
        .form-section {
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
            border-radius: 15px;
            padding: 1.5rem;
            margin-bottom: 1.5rem;
        }
        .required-field::after {
            content: " *";
            color: #dc3545;
        }
        .info-card {
            background: linear-gradient(135deg, #fff3cd 0%, #ffeaa7 100%);
            border: none;
            border-radius: 12px;
            padding: 1rem;
            margin-bottom: 1.5rem;
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
                <a href="DoctoresController?accion=ver&id=${doctor.id}" class="btn btn-light me-2">
                    <i class="bi bi-eye"></i> Ver Doctor
                </a>
                <a href="DoctoresController?accion=listar" class="btn btn-outline-light">
                    <i class="bi bi-arrow-left"></i> Volver
                </a>
            </div>
        </div>
    </nav>

    <div class="container main-container">
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <!-- Header Section -->
                <div class="text-center mb-5">
                    <div class="doctor-avatar">
                        ${doctor.usuarioNombre.substring(0,1)}${doctor.usuarioApellido.substring(0,1)}
                    </div>
                    <h1 class="text-white mb-3"><i class="bi bi-pencil-square"></i> Editar Doctor</h1>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb justify-content-center">
                            <li class="breadcrumb-item"><a href="dashboard_recepcionista.jsp">Inicio</a></li>
                            <li class="breadcrumb-item"><a href="DoctoresController?accion=listar">Doctores</a></li>
                            <li class="breadcrumb-item active text-white">Editar Doctor</li>
                        </ol>
                    </nav>
                </div>

                <!-- Alert Messages -->
                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-custom alert-dismissible fade show" role="alert">
                        <i class="bi bi-exclamation-triangle-fill"></i> <strong>Error!</strong> ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <!-- Main Form Card -->
                <div class="card">
                    <div class="card-header text-center">
                        <h3 class="mb-2"><i class="bi bi-person-badge"></i> Modificar Datos del Doctor</h3>
                        <p class="mb-0 opacity-75">Actualice la información profesional del doctor</p>
                    </div>
                    
                    <div class="card-body p-4">
                        <form action="DoctoresController" method="post" id="doctorForm">
                            <input type="hidden" name="accion" value="actualizar">
                            <input type="hidden" name="id" value="${doctor.id}">
                            <input type="hidden" name="usuarioId" value="${doctor.usuarioId}">

                            <!-- Información del Doctor -->
                            <div class="info-card">
                                <div class="row align-items-center">
                                    <div class="col-md-8">
                                        <h5 class="mb-2 text-dark">
                                            <i class="bi bi-person-badge me-2"></i>
                                            ${doctor.usuarioNombre} ${doctor.usuarioApellido}
                                        </h5>
                                        <p class="mb-1 text-muted">
                                            <i class="bi bi-envelope me-2"></i>${doctor.usuarioCorreo}
                                        </p>
                                        <p class="mb-0 text-muted">
                                            <i class="bi bi-telephone me-2"></i>${doctor.usuarioTelefono}
                                        </p>
                                    </div>
                                    <div class="col-md-4 text-end">
                                        <span class="badge bg-warning text-dark fs-6 p-2">
                                            <i class="bi bi-person-badge"></i> Doctor
                                        </span>
                                    </div>
                                </div>
                            </div>

                            <!-- Información Profesional -->
                            <div class="form-section">
                                <h4 class="mb-4 text-warning">
                                    <i class="bi bi-briefcase me-2"></i>Información Profesional
                                </h4>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label required-field">Número de Licencia Médica</label>
                                        <div class="input-group">
                                            <span class="input-group-text"><i class="bi bi-file-medical"></i></span>
                                            <input type="text" class="form-control" name="numeroLicencia" 
                                                   value="${doctor.numeroLicencia}" required
                                                   pattern="[A-Za-z0-9-]+"
                                                   title="Ingrese un número de licencia válido">
                                        </div>
                                        <small class="text-muted mt-2 d-block">
                                            <i class="bi bi-info-circle"></i> 
                                            Número de cédula profesional oficial
                                        </small>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label required-field">Especialización</label>
                                        <div class="input-group">
                                            <span class="input-group-text"><i class="bi bi-star"></i></span>
                                            <select class="form-control" name="especializacion" required>
                                                <option value="">Seleccione una especialidad...</option>
                                                <option value="Medicina General" ${doctor.especializacion == 'Medicina General' ? 'selected' : ''}>Medicina General</option>
                                                <option value="Cardiología" ${doctor.especializacion == 'Cardiología' ? 'selected' : ''}>Cardiología</option>
                                                <option value="Pediatría" ${doctor.especializacion == 'Pediatría' ? 'selected' : ''}>Pediatría</option>
                                                <option value="Ginecología" ${doctor.especializacion == 'Ginecología' ? 'selected' : ''}>Ginecología</option>
                                                <option value="Dermatología" ${doctor.especializacion == 'Dermatología' ? 'selected' : ''}>Dermatología</option>
                                                <option value="Traumatología" ${doctor.especializacion == 'Traumatología' ? 'selected' : ''}>Traumatología</option>
                                                <option value="Neurología" ${doctor.especializacion == 'Neurología' ? 'selected' : ''}>Neurología</option>
                                                <option value="Oftalmología" ${doctor.especializacion == 'Oftalmología' ? 'selected' : ''}>Oftalmología</option>
                                                <option value="Psiquiatría" ${doctor.especializacion == 'Psiquiatría' ? 'selected' : ''}>Psiquiatría</option>
                                                <option value="Otra" ${doctor.especializacion == 'Otra' ? 'selected' : ''}>Otra especialidad</option>
                                            </select>
                                        </div>
                                        <small class="text-muted mt-2 d-block">
                                            <i class="bi bi-info-circle"></i> 
                                            Especialidad médica principal
                                        </small>
                                    </div>
                                </div>

                                <div class="row mt-3">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Horario de Consulta</label>
                                        <div class="input-group">
                                            <span class="input-group-text"><i class="bi bi-clock"></i></span>
                                            <input type="text" class="form-control" name="horarioConsulta" 
                                                   value="${doctor.horarioConsulta}"
                                                   placeholder="Ej: Lunes a Viernes 8:00 - 16:00">
                                        </div>
                                        <small class="text-muted mt-2 d-block">
                                            <i class="bi bi-info-circle"></i> 
                                            Horario de atención al paciente
                                        </small>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Años de Experiencia</label>
                                        <div class="input-group">
                                            <span class="input-group-text"><i class="bi bi-award"></i></span>
                                            <input type="number" class="form-control" name="anosExperiencia" 
                                                   value="${doctor.anosExperiencia}"
                                                   placeholder="Ej: 5" min="0" max="50">
                                        </div>
                                        <small class="text-muted mt-2 d-block">
                                            <i class="bi bi-info-circle"></i> 
                                            Años de experiencia profesional
                                        </small>
                                    </div>
                                </div>
                            </div>

                            <!-- Botones de Acción -->
                            <div class="d-flex justify-content-between align-items-center mt-5 pt-4 border-top">
                                <a href="DoctoresController?accion=listar" class="btn btn-secondary btn-lg">
                                    <i class="bi bi-arrow-left"></i> Volver al Listado
                                </a>
                                <div class="d-flex gap-3">
                                    <a href="DoctoresController?accion=ver&id=${doctor.id}" class="btn btn-info btn-lg">
                                        <i class="bi bi-eye"></i> Ver Doctor
                                    </a>
                                    <button type="submit" class="btn btn-warning btn-lg">
                                        <i class="bi bi-save"></i> Guardar Cambios
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Validación del formulario
        document.getElementById('doctorForm').addEventListener('submit', function(e) {
            const numeroLicencia = document.querySelector('input[name="numeroLicencia"]').value;
            const especializacion = document.querySelector('select[name="especializacion"]').value;
            
            if (!numeroLicencia) {
                e.preventDefault();
                alert('Por favor, ingrese el número de licencia médica');
                return false;
            }
            
            if (!especializacion) {
                e.preventDefault();
                alert('Por favor, seleccione una especialización');
                return false;
            }
            
            // Validar formato de licencia médica
            const licenciaPattern = /^[A-Za-z0-9-]+$/;
            if (!licenciaPattern.test(numeroLicencia)) {
                e.preventDefault();
                alert('El número de licencia solo puede contener letras, números y guiones');
                return false;
            }
            
            // Mostrar estado de carga
            const submitBtn = this.querySelector('button[type="submit"]');
            submitBtn.innerHTML = '<i class="bi bi-hourglass-split"></i> Guardando...';
            submitBtn.disabled = true;
        });
    </script>
</body>
</html>