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
</head>
<body class="bg-light">
     Navbar 
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="dashboard_recepcionista.jsp">
                <i class="bi bi-hospital"></i> Sistema Médico
            </a>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                 Header 
                <div class="mb-4">
                    <h2><i class="bi bi-person-plus-fill"></i> Registrar Nuevo Usuario</h2>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="dashboard_recepcionista.jsp">Inicio</a></li>
                            <li class="breadcrumb-item"><a href="UsuariosController?accion=listar">Usuarios</a></li>
                            <li class="breadcrumb-item active">Nuevo</li>
                        </ol>
                    </nav>
                </div>

                 Alertas 
                <c:if test="${not empty mensaje}">
                    <div class="alert alert-success alert-dismissible fade show">
                        <i class="bi bi-check-circle"></i> ${mensaje}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show">
                        <i class="bi bi-exclamation-triangle"></i> ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                 Formulario 
                <div class="card shadow-sm">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0">Datos del Usuario</h5>
                    </div>
                    <div class="card-body">
                        <form action="UsuariosController" method="post">
                            <input type="hidden" name="accion" value="registrar">
                            
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label class="form-label">Nombre <span class="text-danger">*</span></label>
                                    <input type="text" class="form-control" name="nombre" required>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Apellido <span class="text-danger">*</span></label>
                                    <input type="text" class="form-control" name="apellido" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label class="form-label">Correo Electrónico <span class="text-danger">*</span></label>
                                    <input type="email" class="form-control" name="correo" required>
                                    <small class="text-muted">Este será su usuario de acceso</small>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Teléfono <span class="text-danger">*</span></label>
                                    <input type="tel" class="form-control" name="telefono" required>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Rol <span class="text-danger">*</span></label>
                                <select class="form-select" name="rol" required>
                                    <option value="">Seleccione un rol...</option>
                                    <option value="paciente">Paciente</option>
                                    <option value="medico">Médico</option>
                                    <option value="recepcionista">Recepcionista</option>
                                    <option value="admin">Administrador</option>
                                </select>
                            </div>

                            <div class="alert alert-info">
                                <i class="bi bi-info-circle"></i> 
                                <strong>Nota:</strong> La contraseña temporal será el mismo correo electrónico. 
                                El usuario deberá cambiarla en su primer acceso.
                            </div>

                             Botones 
                            <div class="d-flex justify-content-end gap-2 mt-4">
                                <a href="UsuariosController?accion=listar" class="btn btn-secondary">
                                    <i class="bi bi-x-circle"></i> Cancelar
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="bi bi-save"></i> Registrar Usuario
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

                 Siguiente paso 
                <div class="card mt-3 border-success">
                    <div class="card-body">
                        <h6 class="text-success"><i class="bi bi-arrow-right-circle"></i> Siguiente Paso</h6>
                        <p class="mb-2">Después de crear el usuario, podrás:</p>
                        <a href="PacientesController?accion=nuevo" class="btn btn-sm btn-success">
                            <i class="bi bi-person-plus"></i> Crear Perfil de Paciente
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>