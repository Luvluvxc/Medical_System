<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nuevo Doctor - Sistema Médico</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
</head>
<body class="bg-light">
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="dashboard_recepcionista.jsp">
                <i class="bi bi-hospital"></i> Sistema Médico
            </a>
            <div class="ms-auto">
                <a href="dashboard_recepcionista.jsp" class="btn btn-light btn-sm">
                    <i class="bi bi-house-door"></i> Inicio
                </a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-10">
                <div class="mb-4">
                    <h2><i class="bi bi-person-badge-fill"></i> Registrar Nuevo Doctor</h2>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="dashboard_recepcionista.jsp">Inicio</a></li>
                            <li class="breadcrumb-item"><a href="DoctoresController?accion=listar">Doctores</a></li>
                            <li class="breadcrumb-item active">Nuevo Doctor</li>
                        </ol>
                    </nav>
                </div>

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

                <div class="card shadow-sm">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0"><i class="bi bi-clipboard2-pulse"></i> Información del Doctor</h5>
                    </div>
                    <div class="card-body">
                        <form action="DoctoresController" method="post">
                            <input type="hidden" name="accion" value="registrar">

                            <!-- Selección de Usuario -->
                            <h6 class="border-bottom pb-2 mb-3">
                                <i class="bi bi-person-circle"></i> Usuario Asociado
                            </h6>
                            <div class="row mb-4">
                                <div class="col-md-12">
                                    <label class="form-label">Seleccionar Usuario <span class="text-danger">*</span></label>
                                    <select class="form-select" name="usuarioId" required>
                                        <option value="">Seleccione un usuario...</option>
                                        <c:forEach var="usuario" items="${usuarios}">
                                            <option value="${usuario.id}" 
                                                    ${usuarioSeleccionado != null && usuarioSeleccionado.id == usuario.id ? 'selected' : ''}>
                                                ${usuario.nombre} ${usuario.apellido} - ${usuario.correo}
                                            </option>
                                        </c:forEach>
                                    </select>
                                    <small class="text-muted">
                                        <i class="bi bi-info-circle"></i> 
                                        Seleccione el usuario que será registrado como doctor
                                    </small>
                                </div>
                            </div>

                            <!-- Datos Profesionales del Doctor -->
                            <h6 class="border-bottom pb-2 mb-3">
                                <i class="bi bi-briefcase"></i> Información Profesional
                            </h6>
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label class="form-label">Número de Licencia Médica <span class="text-danger">*</span></label>
                                    <input type="text" class="form-control" name="numeroLicencia" 
                                           placeholder="Ej: LIC-12345" required>
                                    <small class="text-muted">Ingrese el número de cédula profesional</small>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Especialización <span class="text-danger">*</span></label>
                                    <select class="form-select" name="especializacion" required>
                                        <option value="">Seleccione una especialidad...</option>
                                        <option value="Medicina General">Medicina General</option>
                                        <option value="Cardiología">Cardiología</option>
                                        <option value="Pediatría">Pediatría</option>
                                        <option value="Ginecología">Ginecología</option>
                                        <option value="Dermatología">Dermatología</option>
                                        <option value="Traumatología">Traumatología</option>
                                        <option value="Neurología">Neurología</option>
                                        <option value="Oftalmología">Oftalmología</option>
                                        <option value="Psiquiatría">Psiquiatría</option>
                                        <option value="Otra">Otra</option>
                                    </select>
                                </div>
                            </div>

                            <!-- Botones -->
                            <div class="d-flex justify-content-end gap-2 mt-4">
                                <a href="DoctoresController?accion=listar" class="btn btn-secondary">
                                    <i class="bi bi-x-circle"></i> Cancelar
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="bi bi-save"></i> Registrar Doctor
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

                <!-- Nota informativa -->
                <div class="alert alert-info mt-3">
                    <i class="bi bi-info-circle-fill"></i>
                    <strong>Nota:</strong> Primero debe crear el usuario en el sistema antes de registrarlo como doctor. 
                    Si el usuario no existe, <a href="../Usuarios/usuarios_nuevo.jsp" class="alert-link">créelo aquí</a>.
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
