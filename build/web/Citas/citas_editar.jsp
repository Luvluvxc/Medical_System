<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Cita - Sistema Médico</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
</head>
<body class="bg-light">
    <nav class="navbar navbar-expand-lg navbar-dark bg-info">
        <div class="container-fluid">
            <a class="navbar-brand" href="../dashboard/dashboard_recepcionista.jsp">
                <i class="bi bi-hospital"></i> Sistema Médico
            </a>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="mb-4">
                    <h2><i class="bi bi-pencil-square"></i> Editar Cita Médica</h2>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="dashboard_recepcionista.jsp">Inicio</a></li>
                            <li class="breadcrumb-item"><a href="CitasController?accion=listar">Citas</a></li>
                            <li class="breadcrumb-item active">Editar</li>
                        </ol>
                    </nav>
                </div>

                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show">
                        <i class="bi bi-exclamation-triangle"></i> ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <div class="card shadow-sm">
                    <div class="card-header bg-warning text-dark">
                        <h5 class="mb-0"><i class="bi bi-calendar-event"></i> Modificar Cita</h5>
                    </div>
                    <div class="card-body">
                        <form action="CitasController" method="post">
                            <input type="hidden" name="accion" value="actualizar">
                            <input type="hidden" name="id" value="${cita.id}">

                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label class="form-label">Paciente <span class="text-danger">*</span></label>
                                    <select class="form-select" name="pacienteId" required>
                                        <option value="">Seleccione un paciente...</option>
                                        <c:forEach var="paciente" items="${pacientes}">
                                            <option value="${paciente.id}" ${cita.pacienteId == paciente.id ? 'selected' : ''}>
                                                ${paciente.usuarioNombre} ${paciente.usuarioApellido}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Doctor <span class="text-danger">*</span></label>
                                    <select class="form-select" name="doctorId" required>
                                        <option value="">Seleccione un doctor...</option>
                                        <c:forEach var="doctor" items="${doctores}">
                                            <option value="${doctor.id}" ${cita.doctorId == doctor.id ? 'selected' : ''}>
                                                Dr. ${doctor.usuarioNombre} ${doctor.usuarioApellido} - ${doctor.especializacion}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label class="form-label">Fecha de la Cita <span class="text-danger">*</span></label>
                                    <input type="date" class="form-control" name="fechaCita" 
                                           value="${cita.fechaCita}" required>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Hora de la Cita <span class="text-danger">*</span></label>
                                    <input type="time" class="form-control" name="horaCita" 
                                           value="${cita.horaCita}" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label class="form-label">Estado <span class="text-danger">*</span></label>
                                    <select class="form-select" name="estado" required>
                                        <option value="Pendiente" ${cita.estado == 'Pendiente' ? 'selected' : ''}>Pendiente</option>
                                        <option value="Confirmada" ${cita.estado == 'Confirmada' ? 'selected' : ''}>Confirmada</option>
                                        <option value="Completada" ${cita.estado == 'Completada' ? 'selected' : ''}>Completada</option>
                                        <option value="Cancelada" ${cita.estado == 'Cancelada' ? 'selected' : ''}>Cancelada</option>
                                    </select>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Motivo de la Consulta</label>
                                    <input type="text" class="form-control" name="motivo" 
                                           value="${cita.motivo}" placeholder="Ej: Consulta general, revisión">
                                </div>
                            </div>

                            <div class="d-flex justify-content-end gap-2 mt-4">
                                <a href="CitasController?accion=listar" class="btn btn-secondary">
                                    <i class="bi bi-x-circle"></i> Cancelar
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
