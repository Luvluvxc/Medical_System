<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nuevo Paciente - Sistema Médico</title>
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
            background: linear-gradient(135deg, #198754 0%, #20c997 100%);
            color: white;
            padding: 2rem;
            border-bottom: none;
        }
        .form-control, .form-select, .form-textarea {
            border-radius: 12px;
            padding: 0.75rem 1rem;
            border: 2px solid #e8e8e8;
            transition: all 0.3s ease;
        }
        .form-control:focus, .form-select:focus, .form-textarea:focus {
            border-color: #198754;
            box-shadow: 0 0 0 0.2rem rgba(25, 135, 84, 0.25);
            transform: translateY(-2px);
        }
        .form-label {
            font-weight: 600;
            color: #495057;
            margin-bottom: 0.5rem;
        }
        .input-group-text {
            background: linear-gradient(135deg, #198754 0%, #20c997 100%);
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
        .btn-primary {
            background: linear-gradient(135deg, #198754 0%, #20c997 100%);
            border: none;
        }
        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(25, 135, 84, 0.3);
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
        .section-divider {
            color: #198754;
            font-weight: 600;
            margin: 2rem 0 1.5rem 0;
            padding-bottom: 0.75rem;
            border-bottom: 3px solid #198754;
        }
        .alert-custom {
            border-radius: 12px;
            border: none;
            padding: 1rem 1.25rem;
        }
        .user-avatar-preview {
            width: 80px;
            height: 80px;
            border-radius: 50%;
            background: linear-gradient(135deg, #198754 0%, #20c997 100%);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.5rem;
            color: white;
            font-weight: bold;
            margin: 0 auto 1rem;
        }
        .info-note {
            background: linear-gradient(135deg, #cfe2ff 0%, #d1e7ff 100%);
            border: none;
            border-radius: 15px;
            padding: 1.5rem;
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
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark" style="background: rgba(0,0,0,0.2);">
        <div class="container-fluid">
            <a class="navbar-brand nav-brand" href="dashboard_recepcionista.jsp">
                <i class="bi bi-hospital"></i> Sistema Médico
            </a>
            <div class="navbar-nav ms-auto">
                <a href="dashboard_recepcionista.jsp" class="btn btn-light">
                    <i class="bi bi-house-door"></i> Inicio
                </a>
            </div>
        </div>
    </nav>

    <div class="container main-container">
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <!-- Header Section -->
                <div class="text-center mb-5">
                    <div class="user-avatar-preview">
                        <i class="bi bi-person-plus"></i>
                    </div>
                    <h1 class="text-white mb-3"><i class="bi bi-person-plus-fill"></i> Registrar Nuevo Paciente</h1>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb justify-content-center">
                            <li class="breadcrumb-item"><a href="dashboard_recepcionista.jsp">Inicio</a></li>
                            <li class="breadcrumb-item"><a href="PacientesController?accion=listar">Pacientes</a></li>
                            <li class="breadcrumb-item active text-white">Nuevo Paciente</li>
                        </ol>
                    </nav>
                </div>

                <!-- Alert Messages -->
                <c:if test="${not empty mensaje}">
                    <div class="alert alert-success alert-custom alert-dismissible fade show" role="alert">
                        <i class="bi bi-check-circle-fill"></i> <strong>Éxito!</strong> ${mensaje}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-custom alert-dismissible fade show" role="alert">
                        <i class="bi bi-exclamation-triangle-fill"></i> <strong>Error!</strong> ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <!-- Main Form Card -->
                <div class="card">
                    <div class="card-header text-center">
                        <h3 class="mb-2"><i class="bi bi-clipboard2-pulse"></i> Información del Paciente</h3>
                        <p class="mb-0 opacity-75">Complete todos los campos para registrar un nuevo paciente en el sistema</p>
                    </div>
                    
                    <div class="card-body p-4">
                        <form action="PacientesController" method="post" id="patientForm">
                            <input type="hidden" name="accion" value="registrar">

                            <!-- Selección de Usuario -->
                            <div class="form-section">
                                <h4 class="section-divider">
                                    <i class="bi bi-person-circle me-2"></i>Usuario Asociado
                                </h4>
                                <div class="row">
                                    <div class="col-md-12 mb-3">
                                        <label class="form-label required-field">Seleccionar Usuario</label>
                                        <div class="input-group">
                                            <span class="input-group-text"><i class="bi bi-person-check"></i></span>
                                            <select class="form-select" name="usuarioId" required id="usuarioSelect">
                                                <option value="">Seleccione un usuario...</option>
                                                <c:forEach var="usuario" items="${usuarios}">
                                                    <option value="${usuario.id}" 
                                                            ${usuarioSeleccionado != null && usuarioSeleccionado.id == usuario.id ? 'selected' : ''}
                                                            data-nombre="${usuario.nombre}"
                                                            data-apellido="${usuario.apellido}"
                                                            data-correo="${usuario.correo}">
                                                        ${usuario.nombre} ${usuario.apellido} - ${usuario.correo}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <small class="text-muted mt-2 d-block">
                                            <i class="bi bi-info-circle"></i> 
                                            Seleccione el usuario que será registrado como paciente
                                        </small>
                                        <div id="userPreview" class="mt-3 p-3 bg-light rounded d-none">
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <strong>Nombre:</strong> <span id="previewNombre"></span>
                                                </div>
                                                <div class="col-md-6">
                                                    <strong>Correo:</strong> <span id="previewCorreo"></span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Información Personal -->
                            <div class="form-section">
                                <h4 class="section-divider">
                                    <i class="bi bi-person-vcard me-2"></i>Información Personal
                                </h4>
                                <div class="row">
                                    <div class="col-md-4 mb-3">
                                        <label class="form-label required-field">Fecha de Nacimiento</label>
                                        <div class="input-group">
                                            <span class="input-group-text"><i class="bi bi-calendar3"></i></span>
                                            <input type="date" class="form-control" name="fechaNacimiento" required>
                                        </div>
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <label class="form-label required-field">Género</label>
                                        <div class="input-group">
                                            <span class="input-group-text"><i class="bi bi-gender-ambiguous"></i></span>
                                            <select class="form-select" name="genero" required>
                                                <option value="">Seleccione...</option>
                                                <option value="Masculino">Masculino</option>
                                                <option value="Femenino">Femenino</option>
                                                <option value="Otro">Otro</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <label class="form-label">Dirección</label>
                                        <div class="input-group">
                                            <span class="input-group-text"><i class="bi bi-geo-alt"></i></span>
                                            <input type="text" class="form-control" name="direccion" 
                                                   placeholder="Calle, número, colonia">
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Contacto de Emergencia -->
                            <div class="form-section">
                                <h4 class="section-divider">
                                    <i class="bi bi-telephone-fill me-2"></i>Contacto de Emergencia
                                </h4>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Nombre del Contacto</label>
                                        <div class="input-group">
                                            <span class="input-group-text"><i class="bi bi-person-badge"></i></span>
                                            <input type="text" class="form-control" name="contactoEmergenciaNombre" 
                                                   placeholder="Nombre completo del contacto">
                                        </div>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Teléfono del Contacto</label>
                                        <div class="input-group">
                                            <span class="input-group-text"><i class="bi bi-telephone-outbound"></i></span>
                                            <input type="tel" class="form-control" name="contactoEmergenciaTelefono" 
                                                   placeholder="Ej: 555-1234">
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Información Médica -->
                            <div class="form-section">
                                <h4 class="section-divider">
                                    <i class="bi bi-heart-pulse me-2"></i>Información Médica
                                </h4>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Historial Médico</label>
                                        <div class="input-group">
                                            <span class="input-group-text"><i class="bi bi-clipboard-pulse"></i></span>
                                            <textarea class="form-control form-textarea" name="historialMedico" rows="4" 
                                                      placeholder="Enfermedades previas, cirugías, tratamientos actuales, etc."></textarea>
                                        </div>
                                        <small class="text-muted mt-2 d-block">
                                            <i class="bi bi-info-circle"></i> 
                                            Describa el historial médico relevante del paciente
                                        </small>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Alergias</label>
                                        <div class="input-group">
                                            <span class="input-group-text"><i class="bi bi-bandaid"></i></span>
                                            <textarea class="form-control form-textarea" name="alergias" rows="4" 
                                                      placeholder="Ej: Penicilina, Polen, Mariscos, etc."></textarea>
                                        </div>
                                        <small class="text-muted mt-2 d-block">
                                            <i class="bi bi-info-circle"></i> 
                                            Liste todas las alergias conocidas
                                        </small>
                                    </div>
                                </div>
                            </div>

                            <!-- Botones de Acción -->
                            <div class="d-flex justify-content-between align-items-center mt-5 pt-4 border-top">
                                <a href="UsuariosController?accion=listar" class="btn btn-secondary btn-lg">
                                    <i class="bi bi-arrow-left"></i> Volver al Listado
                                </a>
                                <button type="submit" class="btn btn-primary btn-lg">
                                    <i class="bi bi-person-plus"></i> Registrar Paciente
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

                <!-- Nota Informativa -->
                <div class="info-note mt-4">
                    <div class="d-flex align-items-center">
                        <i class="bi bi-info-circle-fill text-primary fs-4 me-3"></i>
                        <div>
                            <h5 class="mb-2 text-primary">Información Importante</h5>
                            <p class="mb-0">
                                Primero debe crear el usuario en el sistema antes de registrarlo como paciente. 
                                Si el usuario no existe, <a href="../Usuarios/usuarios_nuevo.jsp" class="fw-bold text-decoration-none">créelo aquí</a>.
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Preview de usuario seleccionado
        document.getElementById('usuarioSelect').addEventListener('change', function() {
            const userPreview = document.getElementById('userPreview');
            const selectedOption = this.options[this.selectedIndex];
            
            if (this.value) {
                userPreview.classList.remove('d-none');
                document.getElementById('previewNombre').textContent = 
                    selectedOption.dataset.nombre + ' ' + selectedOption.dataset.apellido;
                document.getElementById('previewCorreo').textContent = selectedOption.dataset.correo;
            } else {
                userPreview.classList.add('d-none');
            }
        });

        // Validación del formulario
        document.getElementById('patientForm').addEventListener('submit', function(e) {
            const fechaNacimiento = document.querySelector('input[name="fechaNacimiento"]').value;
            const genero = document.querySelector('select[name="genero"]').value;
            const usuarioId = document.getElementById('usuarioSelect').value;
            
            if (!usuarioId) {
                e.preventDefault();
                alert('Por favor, seleccione un usuario');
                return false;
            }
            
            if (!fechaNacimiento) {
                e.preventDefault();
                alert('Por favor, ingrese la fecha de nacimiento');
                return false;
            }
            
            if (!genero) {
                e.preventDefault();
                alert('Por favor, seleccione el género');
                return false;
            }
            
            // Validar fecha de nacimiento (no puede ser futura)
            const hoy = new Date().toISOString().split('T')[0];
            if (fechaNacimiento > hoy) {
                e.preventDefault();
                alert('La fecha de nacimiento no puede ser futura');
                return false;
            }
            
            // Mostrar estado de carga
            const submitBtn = this.querySelector('button[type="submit"]');
            submitBtn.innerHTML = '<i class="bi bi-hourglass-split"></i> Registrando...';
            submitBtn.disabled = true;
        });

        // Inicializar preview si hay usuario seleccionado
        document.addEventListener('DOMContentLoaded', function() {
            const usuarioSelect = document.getElementById('usuarioSelect');
            if (usuarioSelect.value) {
                usuarioSelect.dispatchEvent(new Event('change'));
            }
        });
    </script>
</body>
</html>