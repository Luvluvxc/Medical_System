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
    <style>
        :root {
            --primary-color: #2563eb;
            --primary-dark: #1e40af;
            --light-bg: #f0f9ff;
        }

        body {
            background-color: var(--light-bg);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .navbar {
            background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-dark) 100%);
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .form-card {
            background: white;
            border-radius: 12px;
            box-shadow: 0 2px 15px rgba(0,0,0,0.1);
            padding: 2rem;
            margin-top: 2rem;
        }

        .form-label {
            font-weight: 600;
            color: #334155;
            margin-bottom: 0.5rem;
        }

        .form-control, .form-select {
            border-radius: 8px;
            border: 2px solid #e2e8f0;
            padding: 0.75rem;
        }

        .form-control:focus, .form-select:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 0.2rem rgba(37, 99, 235, 0.25);
        }

        .btn-custom {
            padding: 0.75rem 2rem;
            border-radius: 8px;
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .section-title {
            color: var(--primary-color);
            font-weight: 600;
            margin-bottom: 1.5rem;
            padding-bottom: 0.75rem;
            border-bottom: 3px solid var(--primary-color);
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container-fluid">
            <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/dashboard_recepcionista.jsp">
                <i class="bi bi-hospital-fill"></i> Sistema Médico
            </a>
        </div>
    </nav>

    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="form-card">
                    <h2 class="section-title">
                        <i class="bi bi-pencil-square"></i> Editar Cita
                    </h2>

                    <form action="${pageContext.request.contextPath}/CitasController" method="post">
                        <input type="hidden" name="accion" value="actualizar">
                        <input type="hidden" name="id" value="${cita.id}">

                        <div class="mb-3">
                            <label for="doctorId" class="form-label">
                                <i class="bi bi-person-badge"></i> Doctor *
                            </label>
                            <select class="form-select" id="doctorId" name="doctorId" required>
                                <c:forEach var="doctor" items="${doctores}">
                                    <option value="${doctor.id}" ${cita.doctorId == doctor.id ? 'selected' : ''}>
                                        Dr. ${doctor.usuarioNombre} ${doctor.usuarioApellido} - ${doctor.especializacion}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="fechaCita" class="form-label">
                                    <i class="bi bi-calendar"></i> Fecha de la Cita *
                                </label>
                                <input type="date" class="form-control" id="fechaCita" name="fechaCita" 
                                       value="${cita.fechaCita}" required>
                            </div>
                            <div class="col-md-6">
                                <label for="horaCita" class="form-label">
                                    <i class="bi bi-clock"></i> Hora de la Cita *
                                </label>
                                <input type="time" class="form-control" id="horaCita" name="horaCita" 
                                       value="${cita.horaCita}" required>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="motivo" class="form-label">
                                <i class="bi bi-chat-left-text"></i> Motivo de la Consulta *
                            </label>
                            <textarea class="form-control" id="motivo" name="motivo" rows="3" required>${cita.motivo}</textarea>
                        </div>

                        <div class="d-flex justify-content-between mt-4">
                            <a href="${pageContext.request.contextPath}/CitasController?accion=citasPaciente&pacienteId=${cita.pacienteId}" 
                               class="btn btn-secondary btn-custom">
                                <i class="bi bi-arrow-left"></i> Cancelar
                            </a>
                            <button type="submit" class="btn btn-primary btn-custom">
                                <i class="bi bi-check-circle"></i> Guardar Cambios
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
