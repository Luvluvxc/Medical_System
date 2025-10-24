<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reporte de Consulta - Sistema Médico</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        @media print {
            .no-print {
                display: none;
            }
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            padding: 2rem;
        }

        .report-header {
            text-align: center;
            border-bottom: 3px solid #2563eb;
            padding-bottom: 1rem;
            margin-bottom: 2rem;
        }

        .report-section {
            margin-bottom: 1.5rem;
            padding: 1rem;
            border: 1px solid #e2e8f0;
            border-radius: 8px;
        }

        .report-section h4 {
            color: #2563eb;
            margin-bottom: 1rem;
            font-size: 1.1rem;
            font-weight: 600;
        }

        .report-field {
            margin-bottom: 0.75rem;
        }

        .report-label {
            font-weight: 600;
            color: #64748b;
        }

        .report-value {
            color: #1e293b;
        }

        .report-footer {
            margin-top: 3rem;
            padding-top: 1rem;
            border-top: 2px solid #e2e8f0;
            text-align: center;
            color: #64748b;
            font-size: 0.875rem;
        }
    </style>
</head>
<body>
    <div class="no-print mb-3">
        <button onclick="window.print()" class="btn btn-primary">
            <i class="bi bi-printer"></i> Imprimir
        </button>
        <button onclick="window.close()" class="btn btn-secondary">
            <i class="bi bi-x-circle"></i> Cerrar
        </button>
    </div>

    <div class="report-header">
        <h1>SISTEMA MÉDICO</h1>
        <h2>Reporte de Consulta Médica</h2>
        <p>Fecha de Emisión: <%= new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()) %></p>
    </div>

    <div class="report-section">
        <h4>INFORMACIÓN DEL PACIENTE</h4>
        <div class="row">
            <div class="col-6">
                <div class="report-field">
                    <span class="report-label">Nombre:</span>
                    <span class="report-value">${cita.pacienteNombre} ${cita.pacienteApellido}</span>
                </div>
                <div class="report-field">
                    <span class="report-label">Código:</span>
                    <span class="report-value">${cita.codigoPaciente}</span>
                </div>
            </div>
            <div class="col-6">
                <div class="report-field">
                    <span class="report-label">Fecha de Consulta:</span>
                    <span class="report-value">${cita.fechaCita}</span>
                </div>
                <div class="report-field">
                    <span class="report-label">Hora:</span>
                    <span class="report-value">${cita.horaCita}</span>
                </div>
            </div>
        </div>
    </div>

    <div class="report-section">
        <h4>INFORMACIÓN DEL MÉDICO</h4>
        <div class="row">
            <div class="col-6">
                <div class="report-field">
                    <span class="report-label">Doctor:</span>
                    <span class="report-value">Dr. ${cita.doctorNombre} ${cita.doctorApellido}</span>
                </div>
            </div>
            <div class="col-6">
                <div class="report-field">
                    <span class="report-label">Especialización:</span>
                    <span class="report-value">${cita.doctorEspecializacion}</span>
                </div>
            </div>
        </div>
    </div>

    <div class="report-section">
        <h4>MOTIVO DE CONSULTA</h4>
        <p class="report-value">${cita.motivo}</p>
    </div>

    <div class="report-section">
        <h4>DIAGNÓSTICO</h4>
        <p class="report-value">${consulta.diagnostico}</p>
    </div>

    <div class="report-section">
        <h4>PLAN DE TRATAMIENTO</h4>
        <p class="report-value">${consulta.planTratamiento}</p>
    </div>

    <c:if test="${not empty consulta.observaciones}">
        <div class="report-section">
            <h4>OBSERVACIONES</h4>
            <p class="report-value">${consulta.observaciones}</p>
        </div>
    </c:if>

    <div class="report-footer">
        <p>Este documento es un reporte oficial del Sistema Médico</p>
        <p>Generado el <%= new java.text.SimpleDateFormat("dd/MM/yyyy 'a las' HH:mm:ss").format(new java.util.Date()) %></p>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
