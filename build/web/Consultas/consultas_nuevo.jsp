<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nueva Consulta - Sistema Médico</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
</head>
<body class="bg-light">
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="../dashboard">
                <i class="bi bi-hospital"></i> Sistema Médico
            </a>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-10">
                <div class="mb-4">
                    <h2><i class="bi bi-clipboard2-pulse-fill"></i> Registrar Nueva Consulta Médica</h2>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="../dashboard">Inicio</a></li>
                            <li class="breadcrumb-item"><a href="ConsultasController?accion=listar">Consultas</a></li>
                            <li class="breadcrumb-item active">Nueva Consulta</li>
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

                <form action="ConsultasController" method="post">
                    <input type="hidden" name="accion" value="registrar">

                    <div class="card shadow-sm mb-3">
                        <div class="card-header bg-primary text-white">
                            <h5 class="mb-0"><i class="bi bi-calendar-check"></i> Información de la Cita</h5>
                        </div>
                        <div class="card-body">
                            <div class="mb-3">
                                <label class="form-label">Cita Asociada <span class="text-danger">*</span></label>
                                <select class="form-select" name="idCita" required id="citaSelect">
                                    <option value="">Seleccione una cita...</option>
                                    <c:forEach var="cita" items="${citas}">
                                        <option value="${cita.id}" 
                                                data-paciente="${cita.pacienteNombre} ${cita.pacienteApellido}"
                                                data-doctor="${cita.doctorNombre} ${cita.doctorApellido}"
                                                data-fecha="${cita.fecha}">
                                            ${cita.fecha} - ${cita.hora} | Paciente: ${cita.pacienteNombre} ${cita.pacienteApellido} | Dr. ${cita.doctorNombre} ${cita.doctorApellido}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div id="citaInfo" class="alert alert-info" style="display: none;">
                                <strong>Información de la cita:</strong>
                                <ul class="mb-0 mt-2">
                                    <li>Paciente: <span id="infoPaciente"></span></li>
                                    <li>Doctor: <span id="infoDoctor"></span></li>
                                    <li>Fecha: <span id="infoFecha"></span></li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <div class="card shadow-sm mb-3">
                        <div class="card-header bg-primary text-white">
                            <h5 class="mb-0"><i class="bi bi-clipboard-pulse"></i> Diagnóstico y Tratamiento</h5>
                        </div>
                        <div class="card-body">
                            <div class="mb-3">
                                <label class="form-label">Diagnóstico <span class="text-danger">*</span></label>
                                <textarea class="form-control" name="diagnostico" rows="4" required placeholder="Describa el diagnóstico del paciente..."></textarea>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Tratamiento <span class="text-danger">*</span></label>
                                <textarea class="form-control" name="tratamiento" rows="4" required placeholder="Indique el tratamiento a seguir..."></textarea>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Observaciones</label>
                                <textarea class="form-control" name="observaciones" rows="3" placeholder="Observaciones adicionales (opcional)..."></textarea>
                            </div>
                        </div>
                    </div>

                    <div class="card shadow-sm mb-3">
                        <div class="card-header bg-primary text-white">
                            <h5 class="mb-0"><i class="bi bi-prescription2"></i> Receta Médica</h5>
                        </div>
                        <div class="card-body">
                            <div class="mb-3">
                                <label class="form-label">Receta</label>
                                <textarea class="form-control" name="receta" rows="5" placeholder="Medicamentos recetados, dosis, frecuencia..."></textarea>
                                <small class="text-muted">Incluya nombre del medicamento, dosis, frecuencia y duración del tratamiento</small>
                            </div>

                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" id="agregarMedicamentos">
                                <label class="form-check-label" for="agregarMedicamentos">
                                    Seleccionar medicamentos del inventario
                                </label>
                            </div>

                            <div id="medicamentosSection" style="display: none;" class="mt-3">
                                <label class="form-label">Medicamentos</label>
                                <select class="form-select" multiple size="5">
                                    <c:forEach var="medicamento" items="${medicamentos}">
                                        <option value="${medicamento.id}">${medicamento.nombre} - ${medicamento.presentacion} (Stock: ${medicamento.stock})</option>
                                    </c:forEach>
                                </select>
                                <small class="text-muted">Mantenga presionado Ctrl (Cmd en Mac) para seleccionar múltiples medicamentos</small>
                            </div>
                        </div>
                    </div>

                    <div class="d-flex justify-content-end gap-2 mb-4">
                        <a href="ConsultasController?accion=listar" class="btn btn-secondary">
                            <i class="bi bi-x-circle"></i> Cancelar
                        </a>
                        <button type="submit" class="btn btn-primary">
                            <i class="bi bi-save"></i> Registrar Consulta
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Mostrar información de la cita seleccionada
        document.getElementById('citaSelect').addEventListener('change', function() {
            const selectedOption = this.options[this.selectedIndex];
            const citaInfo = document.getElementById('citaInfo');
            
            if (this.value) {
                document.getElementById('infoPaciente').textContent = selectedOption.dataset.paciente;
                document.getElementById('infoDoctor').textContent = selectedOption.dataset.doctor;
                document.getElementById('infoFecha').textContent = selectedOption.dataset.fecha;
                citaInfo.style.display = 'block';
            } else {
                citaInfo.style.display = 'none';
            }
        });

        // Mostrar/ocultar sección de medicamentos
        document.getElementById('agregarMedicamentos').addEventListener('change', function() {
            document.getElementById('medicamentosSection').style.display = this.checked ? 'block' : 'none';
        });
    </script>
</body>
</html>
