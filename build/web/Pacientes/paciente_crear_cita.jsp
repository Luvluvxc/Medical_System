<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agendar Cita</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow">
                    <div class="card-header bg-primary text-white">
                        <h4 class="mb-0">
                            <i class="bi bi-calendar-plus"></i> Agendar Nueva Cita
                        </h4>
                    </div>
                    <div class="card-body">
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
                        
                        <form action="${pageContext.request.contextPath}/CitasController" method="post" id="formCita">
                            <input type="hidden" name="accion" value="registrar">
                            <input type="hidden" name="pacienteId" value="${pacienteId}">
                            <input type="hidden" name="estado" value="Pendiente">
                            
                            <div class="mb-3">
                                <label for="doctorId" class="form-label">
                                    <i class="bi bi-person-badge"></i> Seleccionar Doctor <span class="text-danger">*</span>
                                </label>
                                <select name="doctorId" id="doctorId" class="form-select" required>
                                    <option value="">-- Seleccione un doctor --</option>
                                    <c:forEach var="doctor" items="${doctores}">
                                        <option value="${doctor.id}">
                                            Dr. ${doctor.usuarioNombre} ${doctor.usuarioApellido}
                                            <c:if test="${not empty doctor.especializacion}">
                                                - ${doctor.especializacion}
                                            </c:if>
                                        </option>
                                    </c:forEach>
                                </select>
                                <c:if test="${empty doctores}">
                                    <div class="text-danger mt-2">
                                        <small>No hay doctores disponibles en este momento.</small>
                                    </div>
                                </c:if>
                            </div>
                            
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="fechaCita" class="form-label">
                                        <i class="bi bi-calendar"></i> Fecha de la Cita <span class="text-danger">*</span>
                                    </label>
                                    <input type="date" name="fechaCita" id="fechaCita" 
                                           class="form-control" required 
                                           min="<%= java.time.LocalDate.now() %>">
                                </div>
                                
                                <div class="col-md-6 mb-3">
                                    <label for="horaCita" class="form-label">
                                        <i class="bi bi-clock"></i> Hora de la Cita <span class="text-danger">*</span>
                                    </label>
                                    <input type="time" name="horaCita" id="horaCita" 
                                           class="form-control" required
                                           min="08:00" max="18:00">
                                    <small class="text-muted">Horario de atención: 8:00 AM - 6:00 PM</small>
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="motivo" class="form-label">
                                    <i class="bi bi-file-text"></i> Motivo de la Consulta <span class="text-danger">*</span>
                                </label>
                                <textarea name="motivo" id="motivo" class="form-control" 
                                          rows="4" required 
                                          placeholder="Describa brevemente el motivo de su consulta..."
                                          maxlength="500"></textarea>
                                <small class="text-muted">Máximo 500 caracteres</small>
                            </div>
                            
                            <div class="d-grid gap-2 d-md-flex justify-content-md-end mt-4">
                                <a href="${pageContext.request.contextPath}/CitasController?accion=listar" 
                                   class="btn btn-secondary">
                                    <i class="bi bi-x-circle"></i> Cancelar
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="bi bi-check-circle"></i> Agendar Cita
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
        // Validación adicional del formulario
        document.getElementById('formCita').addEventListener('submit', function(e) {
            const doctorId = document.getElementById('doctorId').value;
            const fechaCita = document.getElementById('fechaCita').value;
            const horaCita = document.getElementById('horaCita').value;
            const motivo = document.getElementById('motivo').value;
            
            console.log('[v0] Enviando formulario con:');
            console.log('[v0]   doctorId:', doctorId);
            console.log('[v0]   fechaCita:', fechaCita);
            console.log('[v0]   horaCita:', horaCita);
            console.log('[v0]   motivo:', motivo);
            
            if (!doctorId || !fechaCita || !horaCita || !motivo.trim()) {
                e.preventDefault();
                alert('Por favor complete todos los campos obligatorios');
                return false;
            }
        });
    </script>
</body>
</html>