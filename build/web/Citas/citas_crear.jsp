<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Crear Cita - Sistema Médico</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        body {
            background-color: #f8f9fa;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .form-container {
            background: white;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0,0,0,0.1);
            padding: 2rem;
            margin-top: 2rem;
        }
        .btn-custom {
            padding: 0.75rem 2rem;
            border-radius: 8px;
            font-weight: 500;
        }
        .conflict-alert {
            border-left: 4px solid #dc3545;
            animation: shake 0.5s ease-in-out;
        }
        @keyframes shake {
            0%, 100% { transform: translateX(0); }
            25% { transform: translateX(-5px); }
            75% { transform: translateX(5px); }
        }
        .time-input-highlight {
            border-color: #dc3545 !important;
            background-color: #fff5f5 !important;
            box-shadow: 0 0 0 0.2rem rgba(220, 53, 69, 0.25) !important;
        }
        .doctor-info {
            background-color: #e7f3ff;
            border-radius: 5px;
            padding: 10px;
            margin-top: 5px;
            font-size: 0.9rem;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/dashboard_recepcionista.jsp">
                <i class="bi bi-hospital-fill"></i> Sistema Médico
            </a>
        </div>
    </nav>

    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="form-container">
                    <h2 class="text-primary mb-4">
                        <i class="bi bi-calendar-plus"></i> Crear Nueva Cita
                    </h2>

                    <!-- Alertas generales -->
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <i class="bi bi-exclamation-triangle"></i> ${error}
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    </c:if>

                    <c:if test="${not empty mensaje}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <i class="bi bi-check-circle"></i> ${mensaje}
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    </c:if>

                    <!-- Alerta específica para conflicto de horarios -->
                    <c:if test="${not empty conflictoHorario}">
                        <div class="alert alert-warning conflict-alert alert-dismissible fade show" role="alert">
                            <div class="d-flex align-items-start">
                                <i class="bi bi-clock-history me-3 mt-1" style="font-size: 1.5rem;"></i>
                                <div class="flex-grow-1">
                                    <h5 class="alert-heading mb-2">
                                        <i class="bi bi-exclamation-triangle-fill"></i> Conflicto de Horario
                                    </h5>
                                    <p class="mb-2 fw-bold">${conflictoHorario}</p>
                                    <small class="text-muted d-block">
                                        <i class="bi bi-lightbulb"></i> 
                                        <strong>Sugerencia:</strong> Intente con otro horario o seleccione un doctor diferente.
                                    </small>
                                    <hr>
                                    <small class="text-muted">
                                        <i class="bi bi-info-circle"></i> 
                                        Los campos de fecha y hora se han resaltado para su atención.
                                    </small>
                                </div>
                            </div>
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/CitasController" method="post" id="citaForm">
                        <input type="hidden" name="accion" value="registrar">
                        
                        <!-- Si viene de un paciente específico -->
                        <c:if test="${not empty param.pacienteId}">
                            <input type="hidden" name="pacienteId" value="${param.pacienteId}">
                        </c:if>

                        <div class="mb-3">
                            <label for="pacienteId" class="form-label">
                                <i class="bi bi-person"></i> Paciente *
                            </label>
                            <select class="form-select" id="pacienteId" name="pacienteId" required>
                                <option value="">Seleccione un paciente</option>
                                <c:forEach var="paciente" items="${pacientes}">
                                    <option value="${paciente.id}" 
                                        <c:if test="${(param.pacienteId == paciente.id) || (pacienteId == paciente.id)}">selected</c:if>>
                                        ${paciente.usuarioNombre} ${paciente.usuarioApellido} 
                                        <c:if test="${not empty paciente.codigoPaciente}">
                                            - ${paciente.codigoPaciente}
                                        </c:if>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="doctorId" class="form-label">
                                <i class="bi bi-person-badge"></i> Doctor *
                            </label>
                            <select class="form-select" id="doctorId" name="doctorId" required>
                                <option value="">Seleccione un doctor</option>
                                <c:forEach var="doctor" items="${doctores}">
                                    <option value="${doctor.id}" 
                                        <c:if test="${not empty doctorId && doctorId == doctor.id}">selected</c:if>>
                                        Dr. ${doctor.usuarioNombre} ${doctor.usuarioApellido} 
                                        - ${doctor.especializacion}
                                    </option>
                                </c:forEach>
                            </select>
                            
                            <!-- Información del doctor seleccionado -->
                            <c:if test="${not empty doctorId}">
                                <c:forEach var="doctor" items="${doctores}">
                                    <c:if test="${doctor.id == doctorId}">
                                        <div class="doctor-info mt-2">
                                            <i class="bi bi-info-circle"></i>
                                            <strong>Doctor seleccionado:</strong> Dr. ${doctor.usuarioNombre} ${doctor.usuarioApellido} 
                                            - <em>${doctor.especializacion}</em>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="fechaCita" class="form-label">
                                    <i class="bi bi-calendar"></i> Fecha *
                                    <c:if test="${not empty conflictoHorario}">
                                        <span class="text-danger">(Horario ocupado)</span>
                                    </c:if>
                                </label>
                                <input type="date" class="form-control ${not empty conflictoHorario ? 'time-input-highlight' : ''}" 
                                       id="fechaCita" name="fechaCita" required 
                                       value="${not empty fechaCita ? fechaCita : ''}"
                                       min="<%= java.time.LocalDate.now() %>">
                            </div>
                            <div class="col-md-6">
                                <label for="horaCita" class="form-label">
                                    <i class="bi bi-clock"></i> Hora *
                                    <c:if test="${not empty conflictoHorario}">
                                        <span class="text-danger">(No disponible)</span>
                                    </c:if>
                                </label>
                                <input type="time" class="form-control ${not empty conflictoHorario ? 'time-input-highlight' : ''}" 
                                       id="horaCita" name="horaCita" 
                                       value="${not empty horaCita ? horaCita : ''}"
                                       min="08:00" max="18:00" step="1800" required>
                                <small class="text-muted">Horario: 8:00 AM - 6:00 PM (citas cada 30 minutos)</small>
                                
                                <c:if test="${not empty conflictoHorario}">
                                    <div class="mt-2 p-2 bg-light border rounded">
                                        <small class="text-danger fw-bold">
                                            <i class="bi bi-exclamation-circle-fill"></i>
                                            Este horario está ocupado. Por favor seleccione otro horario.
                                        </small>
                                    </div>
                                </c:if>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="estado" class="form-label">
                                <i class="bi bi-info-circle"></i> Estado
                            </label>
                            <select class="form-select" id="estado" name="estado">
                                <option value="programada" selected>Programada</option>
                                <option value="confirmada">Confirmada</option>
                                <option value="completada">Completada</option>
                                <option value="cancelada">Cancelada</option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="motivo" class="form-label">
                                <i class="bi bi-chat-left-text"></i> Motivo *
                            </label>
                            <textarea class="form-control" id="motivo" name="motivo" rows="3" 
                                      required placeholder="Describa el motivo de la consulta">${not empty motivo ? motivo : ''}</textarea>
                        </div>

                        <div class="alert alert-info">
                            <div class="d-flex align-items-center">
                                <i class="bi bi-info-circle-fill me-2"></i>
                                <div>
                                    <strong>Importante:</strong> El sistema verificará automáticamente la disponibilidad del doctor. 
                                    Si el horario seleccionado está ocupado, se mostrará una advertencia y podrá ajustar la cita.
                                </div>
                            </div>
                        </div>

                        <div class="d-flex justify-content-between mt-4">
                            <a href="${pageContext.request.contextPath}/CitasController?accion=listar" 
                               class="btn btn-secondary btn-custom">
                                <i class="bi bi-arrow-left"></i> Volver al Listado
                            </a>
                            <button type="submit" class="btn btn-primary btn-custom">
                                <i class="bi bi-check-circle"></i> Crear Cita
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Validación básica del formulario
        document.getElementById('citaForm').addEventListener('submit', function(e) {
            const fecha = document.getElementById('fechaCita').value;
            const hora = document.getElementById('horaCita').value;
            
            if (!fecha || !hora) {
                e.preventDefault();
                alert('Por favor complete todos los campos obligatorios');
                return;
            }
            
            // Validar que la fecha no sea en el pasado
            const selectedDate = new Date(fecha);
            const today = new Date();
            today.setHours(0, 0, 0, 0);
            
            if (selectedDate < today) {
                e.preventDefault();
                alert('No puede agendar citas en fechas pasadas');
                return;
            }
        });

        // Auto-close alerts after 8 seconds
        setTimeout(function() {
            const alerts = document.querySelectorAll('.alert');
            alerts.forEach(alert => {
                const bsAlert = new bootstrap.Alert(alert);
                bsAlert.close();
            });
        }, 8000);

        // Focus on hora input if there was a conflict
        <c:if test="${not empty conflictoHorario}">
            document.addEventListener('DOMContentLoaded', function() {
                document.getElementById('horaCita').focus();
                document.getElementById('horaCita').select();
                
                // Agregar efecto de parpadeo suave
                const horaInput = document.getElementById('horaCita');
                let blinkCount = 0;
                const blinkInterval = setInterval(() => {
                    horaInput.style.boxShadow = blinkCount % 2 === 0 ? 
                        '0 0 0 0.2rem rgba(220, 53, 69, 0.5)' : 
                        '0 0 0 0.2rem rgba(220, 53, 69, 0.25)';
                    blinkCount++;
                    if (blinkCount > 6) {
                        clearInterval(blinkInterval);
                        horaInput.style.boxShadow = '';
                    }
                }, 300);
            });
        </c:if>

        // Mostrar información del doctor cuando se selecciona
        document.getElementById('doctorId').addEventListener('change', function() {
            const selectedOption = this.options[this.selectedIndex];
            if (selectedOption.value) {
                console.log('Doctor seleccionado:', selectedOption.text);
            }
        });
    </script>
</body>
</html>