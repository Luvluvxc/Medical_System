<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalle de Consulta - Sistema Médico</title>
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

        .detail-card {
            background: white;
            border-radius: 12px;
            box-shadow: 0 2px 15px rgba(0,0,0,0.1);
            padding: 2rem;
            margin-top: 2rem;
        }

        .section-title {
            color: var(--primary-color);
            font-weight: 600;
            margin-bottom: 1.5rem;
            padding-bottom: 0.75rem;
            border-bottom: 3px solid var(--primary-color);
        }

        .info-section {
            background: #f8fafc;
            border-radius: 8px;
            padding: 1.5rem;
            margin-bottom: 1.5rem;
        }

        .info-label {
            font-weight: 600;
            color: #64748b;
            margin-bottom: 0.5rem;
        }

        .info-value {
            color: #1e293b;
            margin-bottom: 1rem;
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

    <div class="container p-4">
        <div class="detail-card">
            <h2 class="section-title">
                <i class="bi bi-file-medical"></i> Detalle de Consulta
            </h2>

            <div class="info-section">
                <h4><i class="bi bi-person"></i> Información del Paciente</h4>
                <div class="row">
                    <div class="col-md-6">
                        <div class="info-label">Nombre Completo</div>
                        <div class="info-value">${cita.pacienteNombre} ${cita.pacienteApellido}</div>
                    </div>
                    <div class="col-md-6">
                        <div class="info-label">Código de Paciente</div>
                        <div class="info-value">${cita.codigoPaciente}</div>
                    </div>
                </div>
            </div>

            <div class="info-section">
                <h4><i class="bi bi-person-badge"></i> Información del Doctor</h4>
                <div class="row">
                    <div class="col-md-6">
                        <div class="info-label">Doctor</div>
                        <div class="info-value">Dr. ${cita.doctorNombre} ${cita.doctorApellido}</div>
                    </div>
                    <div class="col-md-6">
                        <div class="info-label">Especialización</div>
                        <div class="info-value">${cita.doctorEspecializacion}</div>
                    </div>
                </div>
            </div>

            <div class="info-section">
                <h4><i class="bi bi-calendar-check"></i> Información de la Cita</h4>
                <div class="row">
                    <div class="col-md-4">
                        <div class="info-label">Fecha</div>
                        <div class="info-value">${cita.fechaCita}</div>
                    </div>
                    <div class="col-md-4">
                        <div class="info-label">Hora</div>
                        <div class="info-value">${cita.horaCita}</div>
                    </div>
                    <div class="col-md-4">
                        <div class="info-label">Motivo</div>
                        <div class="info-value">${cita.motivo}</div>
                    </div>
                </div>
            </div>

            <div class="info-section">
                <h4><i class="bi bi-clipboard-pulse"></i> Información de la Consulta</h4>
                <div class="info-label">Diagnóstico</div>
                <div class="info-value">${consulta.diagnostico}</div>

                <div class="info-label">Plan de Tratamiento</div>
                <div class="info-value">${consulta.planTratamiento}</div>

                <c:if test="${not empty consulta.observaciones}">
                    <div class="info-label">Observaciones</div>
                    <div class="info-value">${consulta.observaciones}</div>
                </c:if>
            </div>

            <div class="d-flex justify-content-between mt-4">
                <button onclick="window.history.back()" class="btn btn-secondary">
                    <i class="bi bi-arrow-left"></i> Volver
                </button>
                <button onclick="imprimirReporte()" class="btn btn-primary">
                    <i class="bi bi-printer"></i> Imprimir Reporte
                </button>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function imprimirReporte() {
            window.print();
        }
    </script>
</body>
</html>
