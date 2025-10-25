<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nuevo Usuario - Sistema Médico</title>
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
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 2rem;
            border-bottom: none;
        }
        .form-control, .form-select {
            border-radius: 12px;
            padding: 0.75rem 1rem;
            border: 2px solid #e8e8e8;
            transition: all 0.3s ease;
        }
        .form-control:focus, .form-select:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
            transform: translateY(-2px);
        }
        .form-label {
            font-weight: 600;
            color: #495057;
            margin-bottom: 0.5rem;
        }
        .input-group-text {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
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
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
        }
        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(102, 126, 234, 0.3);
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
        .password-toggle {
            cursor: pointer;
            position: absolute;
            right: 15px;
            top: 50%;
            transform: translateY(-50%);
            color: #6c757d;
            z-index: 5;
        }
        .input-group-password {
            position: relative;
        }
        .role-icon {
            width: 24px;
            height: 24px;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            margin-right: 8px;
        }
        .section-title {
            color: #495057;
            font-weight: 600;
            margin-bottom: 1.5rem;
            padding-bottom: 0.75rem;
            border-bottom: 2px solid #667eea;
        }
        .user-avatar-preview {
            width: 80px;
            height: 80px;
            border-radius: 50%;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.5rem;
            color: white;
            font-weight: bold;
            margin: 0 auto 1rem;
        }
        .alert {
            border-radius: 12px;
            border: none;
        }
        .nav-brand {
            font-weight: 600;
            font-size: 1.25rem;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark" style="background: rgba(0,0,0,0.2);">
        <div class="container-fluid">
            <a class="navbar-brand nav-brand" href="../dashboard">
                <i class="bi bi-hospital"></i> Sistema Médico
            </a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="dashboard_recepcionista.jsp">
                    <i class="bi bi-house-door"></i> Dashboard
                </a>
            </div>
        </div>
    </nav>

    <div class="container main-container">
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <!-- Header Section -->
                <div class="text-center mb-5">
                    <div class="user-avatar-preview" id="avatarPreview">
                        <i class="bi bi-person-plus"></i>
                    </div>
                    <h1 class="text-white mb-3"><i class="bi bi-person-plus-fill"></i> Registrar Nuevo Usuario</h1>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb justify-content-center">
                            <li class="breadcrumb-item"><a href="dashboard_recepcionista.jsp">Inicio</a></li>
                            <li class="breadcrumb-item"><a href="UsuariosController?accion=listar">Usuarios</a></li>
                            <li class="breadcrumb-item active text-white">Nuevo Usuario</li>
                        </ol>
                    </nav>
                </div>

                <!-- Alert Messages -->
                <c:if test="${not empty mensaje}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <i class="bi bi-check-circle-fill"></i> <strong>Éxito!</strong> ${mensaje}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="bi bi-exclamation-triangle-fill"></i> <strong>Error!</strong> ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <!-- Main Form Card -->
                <div class="card">
                    <div class="card-header text-center">
                        <h3 class="mb-2"><i class="bi bi-person-badge"></i> Información del Usuario</h3>
                        <p class="mb-0 opacity-75">Complete todos los campos para registrar un nuevo usuario en el sistema</p>
                    </div>
                    
                    <div class="card-body p-4">
                        <form action="UsuariosController" method="post" id="userForm">
                            <input type="hidden" name="accion" value="registrar">

                            <!-- Información Personal -->
                            <h5 class="section-title"><i class="bi bi-person-vcard"></i> Información Personal</h5>
                            <div class="row mb-4">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Nombre <span class="text-danger">*</span></label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="bi bi-person"></i></span>
                                        <input type="text" class="form-control" name="nombre" 
                                               placeholder="Ingrese el nombre" required
                                               oninput="updateAvatarPreview()">
                                    </div>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Apellido <span class="text-danger">*</span></label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="bi bi-person"></i></span>
                                        <input type="text" class="form-control" name="apellido" 
                                               placeholder="Ingrese el apellido" required
                                               oninput="updateAvatarPreview()">
                                    </div>
                                </div>
                            </div>

                            <!-- Información de Contacto -->
                            <h5 class="section-title"><i class="bi bi-telephone"></i> Información de Contacto</h5>
                            <div class="row mb-4">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Correo Electrónico <span class="text-danger">*</span></label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="bi bi-envelope"></i></span>
                                        <input type="email" class="form-control" name="correo" 
                                               placeholder="usuario@ejemplo.com" required>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Teléfono <span class="text-danger">*</span></label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="bi bi-phone"></i></span>
                                        <input type="tel" class="form-control" name="telefono" 
                                               placeholder="Ingrese número telefónico" required>
                                    </div>
                                </div>
                            </div>

                            <!-- Configuración de Cuenta -->
                            <h5 class="section-title"><i class="bi bi-shield-lock"></i> Configuración de Cuenta</h5>
                            <div class="row mb-4">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Rol <span class="text-danger">*</span></label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="bi bi-person-gear"></i></span>
                                        <select class="form-select" name="rol" required id="roleSelect">
                                            <option value="">Seleccione un rol...</option>
                                            <option value="doctor">
                                                <i class="bi bi-person-badge"></i> Doctor
                                            </option>
                                            <option value="paciente">
                                                <i class="bi bi-person"></i> Paciente
                                            </option>
                                        </select>
                                    </div>
                                    <small class="text-muted mt-2 d-block">
                                        <i class="bi bi-info-circle"></i> 
                                        <span id="roleDescription">Seleccione el tipo de usuario</span>
                                    </small>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Contraseña <span class="text-danger">*</span></label>
                                    <div class="input-group-password">
                                        <div class="input-group">
                                            <span class="input-group-text"><i class="bi bi-key"></i></span>
                                            <input type="password" class="form-control" name="contrasena" 
                                                   id="passwordInput" placeholder="Ingrese contraseña segura" required>
                                        </div>
                                        <span class="password-toggle" onclick="togglePassword()">
                                            <i class="bi bi-eye" id="passwordIcon"></i>
                                        </span>
                                    </div>
                                    <div class="password-strength mt-2">
                                        <small class="text-muted">
                                            <i class="bi bi-shield-check"></i> 
                                            La contraseña debe tener al menos 8 caracteres
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
                                    <i class="bi bi-person-plus"></i> Registrar Usuario
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Toggle password visibility
        function togglePassword() {
            const passwordInput = document.getElementById('passwordInput');
            const passwordIcon = document.getElementById('passwordIcon');
            
            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                passwordIcon.classList.remove('bi-eye');
                passwordIcon.classList.add('bi-eye-slash');
            } else {
                passwordInput.type = 'password';
                passwordIcon.classList.remove('bi-eye-slash');
                passwordIcon.classList.add('bi-eye');
            }
        }

        // Update avatar preview with initials
        function updateAvatarPreview() {
            const nombre = document.querySelector('input[name="nombre"]').value;
            const apellido = document.querySelector('input[name="apellido"]').value;
            const avatarPreview = document.getElementById('avatarPreview');
            
            if (nombre && apellido) {
                const initials = nombre.charAt(0) + apellido.charAt(0);
                avatarPreview.innerHTML = initials.toUpperCase();
                avatarPreview.style.fontSize = '1.5rem';
                avatarPreview.style.fontWeight = 'bold';
            } else {
                avatarPreview.innerHTML = '<i class="bi bi-person-plus"></i>';
                avatarPreview.style.fontSize = '1.5rem';
            }
        }

        // Role description
        document.getElementById('roleSelect').addEventListener('change', function() {
            const description = document.getElementById('roleDescription');
            const role = this.value;
            
            if (role === 'doctor') {
                description.innerHTML = 'Personal médico autorizado para atender pacientes y gestionar consultas';
            } else if (role === 'paciente') {
                description.innerHTML = 'Usuario que recibirá atención médica y gestionará sus citas';
            } else {
                description.innerHTML = 'Seleccione el tipo de usuario';
            }
        });

        // Form validation
        document.getElementById('userForm').addEventListener('submit', function(e) {
            const password = document.getElementById('passwordInput').value;
            
            if (password.length < 8) {
                e.preventDefault();
                alert('La contraseña debe tener al menos 8 caracteres');
                return false;
            }
            
            // Show loading state
            const submitBtn = this.querySelector('button[type="submit"]');
            submitBtn.innerHTML = '<i class="bi bi-hourglass-split"></i> Registrando...';
            submitBtn.disabled = true;
        });

        // Initialize avatar preview
        updateAvatarPreview();
    </script>
</body>
</html>