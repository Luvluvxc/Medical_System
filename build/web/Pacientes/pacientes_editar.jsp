<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Paciente - Sistema Médico</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
</head>
<body class="bg-light">
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
                <div class="mb-4">
                    <h2><i class="bi bi-pencil-square"></i> Editar Paciente</h2>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="dashboard_recepcionista.jsp">Inicio</a></li>
                            <li class="breadcrumb-item"><a href="PacientesController?accion=listar">Pacientes</a></li>
                            <li class="breadcrumb-item active">Editar</li>
                        </ol>
                    </nav>
                </div>

                <div class="card shadow-sm">
                    <div class="card-header bg-warning text-dark">
                        <h5 class="mb-0">Modificar Datos del Paciente</h5>
                    </div>
                    <div class="card-body">
                        <form action="PacientesController?accion=actualizar" method="POST">
                            <input type="hidden" name="id" value="${paciente.id}">
                            <input type="hidden" name="usuarioId" value="${paciente.usuarioId}">
                            
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label class="form-label">Código Paciente</label>
                                    <input type="text" class="form-control" value="${paciente.codigoPaciente}" readonly>
                                    <small class="text-muted">El código no se puede modificar</small>
                                </div>
                                
                                <div class="col-md-6">
                                    <label for="fechaNacimiento" class="form-label">Fecha de Nacimiento <span class="text-danger">*</span></label>
                                    <input type="date" class="form-control" id="fechaNacimiento" name="fechaNacimiento" 
                                           value="${paciente.fechaNacimiento}" required>
                                </div>
                            </div>
                            
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="genero" class="form-label">Género <span class="text-danger">*</span></label>
                                    <select class="form-select" id="genero" name="genero" required>
                                        <option value="">Seleccione...</option>
                                        <option value="Masculino" ${paciente.genero == 'Masculino' || paciente.genero == 'Masculino' ? 'selected' : ''}>Masculino</option>
                                        <option value="Femenino" ${paciente.genero == 'Femenino' || paciente.genero == 'Femenino' ? 'selected' : ''}>Femenino</option>
                                    </select>
                                </div>
                                
                                <div class="col-md-6">
                                    <label for="direccion" class="form-label">Dirección</label>
                                    <input type="text" class="form-control" id="direccion" name="direccion" 
                                           value="${paciente.direccion}">
                                </div>
                            </div>
                            
                            <h6 class="border-bottom pb-2 mb-3 mt-4">
                                <i class="bi bi-telephone-fill"></i> Contacto de Emergencia
                            </h6>
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="contactoEmergenciaNombre" class="form-label">Nombre del Contacto</label>
                                    <input type="text" class="form-control" id="contactoEmergenciaNombre" 
                                           name="contactoEmergenciaNombre" value="${paciente.contactoEmergenciaNombre}">
                                </div>
                                
                                <div class="col-md-6">
                                    <label for="contactoEmergenciaTelefono" class="form-label">Teléfono de Emergencia</label>
                                    <input type="tel" class="form-control" id="contactoEmergenciaTelefono" 
                                           name="contactoEmergenciaTelefono" value="${paciente.contactoEmergenciaTelefono}">
                                </div>
                            </div>
                            
                            <h6 class="border-bottom pb-2 mb-3 mt-4">
                                <i class="bi bi-heart-pulse"></i> Información Médica
                            </h6>
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="alergias" class="form-label">Alergias</label>
                                    <textarea class="form-control" id="alergias" name="alergias" 
                                              rows="3" placeholder="Ej: Penicilina, Polen, etc.">${paciente.alergias}</textarea>
                                </div>
                                <div class="col-md-6">
                                    <label for="historialMedico" class="form-label">Historial Médico</label>
                                    <textarea class="form-control" id="historialMedico" name="historialMedico" 
                                              rows="3" placeholder="Enfermedades previas, cirugías, etc.">${paciente.historialMedico}</textarea>
                                </div>
                            </div>
                            
                            <div class="d-flex justify-content-between mt-4">
                                <a href="PacientesController?accion=listar" class="btn btn-secondary">
                                    <i class="bi bi-arrow-left"></i> Cancelar
                                </a>
                                <button type="submit" class="btn btn-warning">
                                    <i class="bi bi-save"></i> Guardar Cambios
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
