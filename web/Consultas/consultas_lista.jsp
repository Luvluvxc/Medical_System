<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Consultas Cerradas - Sistema Médico</title>
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

        .content-card {
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

        .filter-card {
            background: #eff6ff;
            border-radius: 8px;
            padding: 1.5rem;
            margin-bottom: 1.5rem;
        }

        .consulta-card {
            background: white;
            border: 2px solid #e2e8f0;
            border-radius: 10px;
            padding: 1.5rem;
            margin-bottom: 1rem;
            transition: all 0.3s ease;
            border-left: 5px solid #10b981;
        }

        .consulta-card:hover {
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            transform: translateY(-2px);
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

    <div class="container-fluid p-4">
        <div class="content-card">
            <h2 class="section-title">
                <i class="bi bi-file-medical"></i> Consultas Cerradas
            </h2>

            <div class="filter-card">
                <form action="${pageContext.request.contextPath}/ConsultasController" method="get" class="row g-3">
                    <input type="hidden" name="accion" value="listarCerradas">
                    <div class="col-md-8">
                        <label class="form-label fw-bold">
                            <i class="bi bi-search"></i> Buscar por Código de Paciente
                        </label>
                        <input type="text" class="form-control" name="codigoPaciente" 
                               value="${param.codigoPaciente}" 
                               placeholder="Ingrese el código del paciente">
                    </div>
                    <div class="col-md-2">
                        <label class="form-label">&nbsp;</label>
                        <button type="submit" class="btn btn-primary w-100">
                            <i class="bi bi-search"></i> Buscar
                        </button>
                    </div>
                    <div class="col-md-2">
                        <label class="form-label">&nbsp;</label>
                        <a href="${pageContext.request.contextPath}/ConsultasController?accion=listarCerradas" 
                           class="btn btn-secondary w-100">
                            <i class="bi bi-x-circle"></i> Limpiar
                        </a>
                    </div>
                </form>
            </div>

            <c:if test="${not empty param.codigoPaciente}">
                <div class="alert alert-info">
                    <i class="bi bi-info-circle"></i> Mostrando resultados para el código: <strong>${param.codigoPaciente}</strong>
                </div>
            </c:if>

            <c:forEach var="consulta" items="${consultas}">
                <div class="consulta-card">
                    <div class="row">
                        <div class="col-md-8">
                            <h5><i class="bi bi-person"></i> ${consulta.pacienteNombre} ${consulta.pacienteApellido}</h5>
                            <p class="text-muted mb-2">Código: ${consulta.codigoPaciente}</p>
                            <p class="mb-1"><i class="bi bi-person-badge"></i> <strong>Doctor:</strong> Dr. ${consulta.doctorNombre} ${consulta.doctorApellido} - ${consulta.especializacion}</p>
                            <p class="mb-1"><i class="bi bi-calendar3"></i> <strong>Fecha:</strong> ${consulta.fechaCita} - ${consulta.horaCita}</p>
                            <p class="mb-1"><i class="bi bi-clipboard-pulse"></i> <strong>Diagnóstico:</strong> ${consulta.diagnostico}</p>
                            <p class="mb-1"><i class="bi bi-prescription2"></i> <strong>Tratamiento:</strong> ${consulta.planTratamiento}</p>
                            <c:if test="${not empty consulta.observaciones}">
                                <p class="mb-1"><i class="bi bi-chat-left-text"></i> <strong>Observaciones:</strong> ${consulta.observaciones}</p>
                            </c:if>
                        </div>
                        <div class="col-md-4 text-end">
                            <a href="${pageContext.request.contextPath}/ConsultasController?accion=ver&citaId=${consulta.citaId}" 
                               class="btn btn-info mb-2 w-100">
                                <i class="bi bi-eye"></i> Ver Detalle
                            </a>
                            <button onclick="imprimirReporte(${consulta.id})" class="btn btn-primary w-100">
                                <i class="bi bi-printer"></i> Imprimir Reporte
                            </button>
                        </div>
                    </div>
                </div>
            </c:forEach>

            <c:if test="${empty consultas}">
                <div class="alert alert-warning text-center">
                    <i class="bi bi-exclamation-triangle"></i> No se encontraron consultas cerradas
                </div>
            </c:if>

            <div class="mt-3">
                <a href="${pageContext.request.contextPath}/dashboard_recepcionista.jsp" class="btn btn-secondary">
                    <i class="bi bi-arrow-left"></i> Volver al Dashboard
                </a>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function imprimirReporte(consultaId) {
            window.open('${pageContext.request.contextPath}/ConsultasController?accion=generarReporte&consultaId=' + consultaId, '_blank');
        }
    </script>
</body>
</html>
