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
</head>
<body class="bg-light">
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="dashboard_recepcionista.jsp">
                <i class="bi bi-hospital"></i> Sistema Médico
            </a>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-10">
                <!-- Header -->
                <div class="mb-4">
                    <h2><i class="bi bi-person-plus-fill"></i> Registrar Nuevo Paciente</h2>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="dashboard_recepcionista.jsp">Inicio</a></li>
                            <li class="breadcrumb-item"><a href="PacientesController?accion=listar">Pacientes</a></li>
                            <li class="breadcrumb-item active">Nuevo</li>
                        </ol>
                    </nav>
                </div>

                <!-- Formulario -->
                <div class="card shadow-sm">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0">Datos del Paciente</h5>
                    </div>
                    <div class="card-body">
                        <form action="PacientesController" method="post">
                            <input type="hidden" name="accion" value="registrar">
                            
                            <!-- Datos Personales -->
                            <h6 class="border-bottom pb-2 mb-3">
                                <i class="bi bi-person"></i> Información Personal
                            </h6>
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
                                <div class="col-md-4">
                                    <label class="form-label">Fecha de Nacimiento <span class="text-danger">*</span></label>
                                    <input type="date" class="form-control" name="fechaNacimiento" required>
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Género <span class="text-danger">*</span></label>
                                    <select class="form-select" name="genero" required>
                                        <option value="">Seleccione...</option>
                                        <option value="M">Masculino</option>
                                        <option value="F">Femenino</option>
                                    </select>
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Teléfono <span class="text-danger">*</span></label>
                                    <input type="tel" class="form-control" name="telefono" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label class="form-label">Correo Electrónico <span class="text-danger">*</span></label>
                                    <input type="email" class="form-control" name="correo" required>
                                    <small class="text-muted">Este será su usuario de acceso</small>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Dirección</label>
                                    <input type="text" class="form-control" name="direccion">
                                </div>
                            </div>

                            <!-- Contacto de Emergencia -->
                            <h6 class="border-bottom pb-2 mb-3 mt-4">
                                <i class="bi bi-telephone-fill"></i> Contacto de Emergencia
                            </h6>
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label class="form-label">Nombre del Contacto</label>
                                    <input type="text" class="form-control" name="contactoEmergenciaNombre">
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Teléfono del Contacto</label>
                                    <input type="tel" class="form-control" name="contactoEmergenciaTelefono">
                                </div>
                            </div>

                            <!-- Información Médica -->
                            <h6 class="border-bottom pb-2 mb-3 mt-4">
                                <i class="bi bi-heart-pulse"></i> Información Médica
                            </h6>
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label class="form-label">Alergias</label>
                                    <textarea class="form-control" name="alergias" rows="3" 
                                              placeholder="Ej: Penicilina, Polen, etc."></textarea>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Historial Médico</label>
                                    <textarea class="form-control" name="historialMedico" rows="3" 
                                              placeholder="Enfermedades previas, cirugías, etc."></textarea>
                                </div>
                            </div>

                            <!-- Botones -->
                            <div class="d-flex justify-content-end gap-2 mt-4">
                                <a href="PacientesController?accion=listar" class="btn btn-secondary">
                                    <i class="bi bi-x-circle"></i> Cancelar
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="bi bi-save"></i> Registrar Paciente
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>