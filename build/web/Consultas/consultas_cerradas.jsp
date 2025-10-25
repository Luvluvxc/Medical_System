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
        .filter-card {
            background: white;
            border-radius: 12px;
            padding: 1.5rem;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            margin-bottom: 2rem;
        }
        .consulta-card {
            border: none;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            margin-bottom: 1rem;
            border-left: 4px solid #10b981;
        }
    </style>
</head>
<body>
    <div class="container-fluid p-4">
        <div class="row">
            <div class="col-12">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2><i class="bi bi-file-medical"></i> Consultas Cerradas</h2>
                    <a href="dashboard_recepcionista.jsp" class="btn btn-outline-primary">
                        <i class="bi bi-arrow-left"></i> Volver al Dashboard
                    </a>
                </div>

                <!-- Filtros -->
                <div class="filter-card">
                    <h5 class="mb-3"><i class="bi bi-funnel"></i> Filtros</h5>
                    <form method="get" class="row g-3">
                        <input type="hidden" name="accion" value="listarCerradas">
                        <div class="col-md-3">
                            <label class="form-label">Doctor</label>
                            <select name="doctorId" class="form-select">
                                <option value="">Todos los doctores</option>
                                <!-- Aquí irían los doctores -->
                            </select>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">Fecha Desde</label>
                            <input type="date" name="fechaDesde" class="form-control">
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">Fecha Hasta</label>
                            <input type="date" name="fechaHasta" class="form-control">
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">&nbsp;</label>
                            <button type="submit" class="btn btn-primary w-100">
                                <i class="bi bi-filter"></i> Filtrar
                            </button>
                        </div>
                    </form>
                </div>

                <!-- Lista de Consultas -->
                <c:if test="${empty consultas}">
                    <div class="alert alert-info">
                        <i class="bi bi-info-circle"></i> No hay consultas cerradas registradas.
                    </div>
                </c:if>

                <c:forEach var="consulta" items="${consultas}">
                    <div class="card consulta-card">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-8">
                                    <h5 class="card-title">Consulta #${consulta.id}</h5>
                                    <p class="card-text"><strong>Diagnóstico:</strong> ${consulta.diagnostico}</p>
                                    <c:if test="${not empty consulta.planTratamiento}">
                                        <p class="card-text"><strong>Tratamiento:</strong> ${consulta.planTratamiento}</p>
                                    </c:if>
                                    <c:if test="${not empty consulta.observaciones}">
                                        <p class="card-text"><strong>Observaciones:</strong> ${consulta.observaciones}</p>
                                    </c:if>
                                </div>
                                <div class="col-md-4 text-end">
                                    <small class="text-muted">Cita ID: ${consulta.citaId}</small>
                                    <div class="mt-2">
                                        <button class="btn btn-sm btn-outline-primary" 
                                                onclick="verDetallesConsulta(${consulta.id})">
                                            <i class="bi bi-eye"></i> Ver Detalles
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function verDetallesConsulta(consultaId) {
            window.location.href = '${pageContext.request.contextPath}/ConsultasController?accion=ver&id=' + consultaId;
        }
    </script>
</body>
</html>