<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Editar Usuario - Sistema Médico</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px 0;
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
        .form-control {
            border-radius: 12px;
            padding: 0.75rem 1rem;
            border: 2px solid #e8e8e8;
            transition: all 0.3s ease;
        }
        .form-control:focus {
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
        .btn-warning {
            background: linear-gradient(135deg, #ffd200 0%, #f7971e 100%);
            border: none;
            color: #000;
        }
        .btn-warning:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(247, 151, 30, 0.3);
        }
        .btn-secondary {
            background: linear-gradient(135deg, #868e96 0%, #495057 100%);
            border: none;
        }
        .btn-secondary:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(73, 80, 87, 0.3);
        }
        .readonly-field {
            background-color: #f8f9fa;
            border-color: #dee2e6;
        }
        .user-avatar {
            width: 80px;
            height: 80px;
            border-radius: 50%;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 2rem;
            color: white;
            font-weight: bold;
            margin: 0 auto 1rem;
        }
        .info-card {
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
            border-radius: 15px;
            padding: 1.5rem;
            margin-bottom: 1.5rem;
        }
        .section-title {
            color: #495057;
            font-weight: 600;
            margin-bottom: 1rem;
            padding-bottom: 0.5rem;
            border-bottom: 2px solid #667eea;
        }
        .password-toggle {
            cursor: pointer;
            position: absolute;
            right: 15px;
            top: 50%;
            transform: translateY(-50%);
            color: #6c757d;
        }
        .input-group-password {
            position: relative;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <div class="card">
                    <div class="card-header text-center">
                        <div class="user-avatar">
                            ${usuario.nombre.substring(0,1)}${usuario.apellido.substring(0,1)}
                        </div>
                        <h3 class="mb-2"><i class="fas fa-user-edit"></i> Editar Usuario</h3>
                        <p class="mb-0 opacity-75">Actualiza la información del usuario del sistema</p>
                    </div>
                    
                    <div class="card-body p-4">
                        <!-- Información del Usuario -->
                        <div class="info-card">
                            <h5 class="section-title"><i class="fas fa-info-circle"></i> Información del Usuario</h5>
                            <div class="row">
                                <div class="col-md-6">
                                    <p class="mb-1"><strong>ID:</strong> ${usuario.id}</p>
                                    <p class="mb-1"><strong>Rol:</strong> 
                                        <span class="badge bg-primary">${usuario.rol}</span>
                                    </p>
                                </div>
                                <div class="col-md-6">
                                    <p class="mb-1"><strong>Correo:</strong> ${usuario.correo}</p>
                                    <p class="mb-0"><strong>Estado:</strong> 
                                        <c:choose>
                                            <c:when test="${usuario.activo}">
                                                <span class="badge bg-success">Activo</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-danger">Inactivo</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                </div>
                            </div>
                        </div>

                        <form action="UsuariosController?accion=actualizar" method="POST">
                            <input type="hidden" name="id" value="${usuario.id}">
                            <input type="hidden" name="rol" value="${usuario.rol}">
                            
                            <!-- Información Personal -->
                            <h5 class="section-title mt-4"><i class="fas fa-user"></i> Información Personal</h5>
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="nombre" class="form-label">Nombre *</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="fas fa-user"></i></span>
                                        <input type="text" class="form-control" id="nombre" name="nombre" 
                                               value="${usuario.nombre}" required>
                                    </div>
                                </div>
                                
                                <div class="col-md-6 mb-3">
                                    <label for="apellido" class="form-label">Apellido *</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="fas fa-user"></i></span>
                                        <input type="text" class="form-control" id="apellido" name="apellido" 
                                               value="${usuario.apellido}" required>
                                    </div>
                                </div>
                            </div>
                            
                            <!-- Información de Contacto -->
                            <h5 class="section-title mt-4"><i class="fas fa-address-book"></i> Información de Contacto</h5>
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="correo" class="form-label">Correo Electrónico *</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="fas fa-envelope"></i></span>
                                        <input type="email" class="form-control readonly-field" id="correo" 
                                               value="${usuario.correo}" readonly>
                                    </div>
                                    <small class="text-muted mt-1"><i class="fas fa-info-circle"></i> El correo no se puede modificar</small>
                                </div>
                                
                                <div class="col-md-6 mb-3">
                                    <label for="telefono" class="form-label">Teléfono</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="fas fa-phone"></i></span>
                                        <input type="tel" class="form-control" id="telefono" name="telefono" 
                                               value="${usuario.telefono}" placeholder="Ingrese número telefónico">
                                    </div>
                                </div>
                            </div>
                            
                            <!-- Configuración de Seguridad -->
                            <h5 class="section-title mt-4"><i class="fas fa-shield-alt"></i> Configuración de Seguridad</h5>
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Rol del Usuario</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="fas fa-user-tag"></i></span>
                                        <input type="text" class="form-control readonly-field" value="${usuario.rol}" readonly>
                                    </div>
                                    <small class="text-muted mt-1"><i class="fas fa-info-circle"></i> El rol no se puede modificar</small>
                                </div>
                                
                                <div class="col-md-6 mb-3">
                                    <label for="contrasena" class="form-label">Nueva Contraseña</label>
                                    <div class="input-group-password">
                                        <div class="input-group">
                                            <span class="input-group-text"><i class="fas fa-lock"></i></span>
                                            <input type="password" class="form-control" id="contrasena" name="contrasena" 
                                                   placeholder="Ingrese nueva contraseña">
                                        </div>
                                        <span class="password-toggle" onclick="togglePassword()">
                                            <i class="fas fa-eye" id="passwordIcon"></i>
                                        </span>
                                    </div>
                                    <small class="text-muted mt-1"><i class="fas fa-info-circle"></i> Solo complete si desea cambiar la contraseña</small>
                                </div>
                            </div>
                            
                            <!-- Botones de Acción -->
                            <div class="d-flex justify-content-between align-items-center mt-5 pt-3 border-top">
                                <a href="UsuariosController?accion=listar" class="btn btn-secondary btn-lg">
                                    <i class="fas fa-arrow-left"></i> Volver al Listado
                                </a>
                                <button type="submit" class="btn btn-warning btn-lg">
                                    <i class="fas fa-save"></i> Guardar Cambios
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
        function togglePassword() {
            const passwordInput = document.getElementById('contrasena');
            const passwordIcon = document.getElementById('passwordIcon');
            
            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                passwordIcon.classList.remove('fa-eye');
                passwordIcon.classList.add('fa-eye-slash');
            } else {
                passwordInput.type = 'password';
                passwordIcon.classList.remove('fa-eye-slash');
                passwordIcon.classList.add('fa-eye');
            }
        }
        
        // Validación del formulario
        document.querySelector('form').addEventListener('submit', function(e) {
            const nombre = document.getElementById('nombre').value.trim();
            const apellido = document.getElementById('apellido').value.trim();
            
            if (!nombre || !apellido) {
                e.preventDefault();
                alert('Por favor, complete todos los campos obligatorios (*)');
                return false;
            }
        });
    </script>
</body>
</html>