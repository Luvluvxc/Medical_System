<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Doctor - Sistema Médico</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        :root {
            --primary-color: #059669;
            --primary-dark: #047857;
            --primary-light: #10b981;
            --secondary-color: #0ea5e9;
            --accent-color: #06b6d4;
            --light-bg: #f0fdf4;
        }

        body {
            background-color: var(--light-bg);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .navbar {
            background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-dark) 100%);
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .sidebar {
            background: white;
            min-height: calc(100vh - 56px);
            box-shadow: 2px 0 10px rgba(0,0,0,0.05);
            padding: 1.5rem 0;
        }

        .sidebar .nav-link {
            color: #64748b;
            padding: 0.875rem 1.5rem;
            margin: 0.25rem 0;
            border-radius: 0;
            border-left: 3px solid transparent;
            transition: all 0.3s ease;
            font-weight: 500;
        }

        .sidebar .nav-link:hover {
            background-color: #f0fdf4;
            color: var(--primary-color);
            border-left-color: var(--primary-color);
        }

        .sidebar .nav-link.active {
            background-color: #dcfce7;
            color: var(--primary-color);
            border-left-color: var(--primary-color);
        }

        .welcome-card {
            background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-dark) 100%);
            color: white;
            border-radius: 12px;
            padding: 2rem;
            margin-bottom: 2rem;
            box-shadow: 0 4px 15px rgba(5, 150, 105, 0.3);
        }

        .cita-card {
            border: none;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            margin-bottom: 1rem;
            transition: all 0.3s ease;
        }

        .cita-card:hover {
            box-shadow: 0 4px 15px rgba(0,0,0,0.15);
        }

        .cita-card.programada {
            border-left: 4px solid #3b82f6;
        }

        .cita-card.completada {
            border-left: 4px solid #10b981;
        }

        .cita-card.cancelada {
            border-left: 4px solid #ef4444;
        }

        .badge-estado {
            font-size: 0.75rem;
            padding: 0.35em 0.65em;
        }

        .btn-consulta {
            background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-dark) 100%);
            color: white;
            border: none;
            padding: 0.5rem 1rem;
            border-radius: 8px;
            transition: all 0.3s ease;
        }

        .btn-consulta:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(5, 150, 105, 0.3);
        }

        .section-title {
            font-size: 1.5rem;
            font-weight: 600;
            color: #1e293b;
            margin-bottom: 1.5rem;
            padding-bottom: 0.75rem;
            border-bottom: 3px solid var(--primary-color);
            display: inline-block;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container-fluid">
            <a class="navbar-brand fw-bold" href="#">
                <i class="bi bi-hospital-fill"></i> Sistema Médico
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                            <i class="bi bi-person-circle"></i> Dr. ${usuario.nombre}
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li><a class="dropdown-item" href="Validar?accion=salir"><i class="bi bi-box-arrow-right"></i> Cerrar Sesión</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container-fluid">
        <div class="row">
            <div class="col-md-2 sidebar">
                <nav class="nav flex-column">
                    <a class="nav-link active" href="${pageContext.request.contextPath}/CitasController?accion=listarDoctor">
                        <i class="bi bi-speedometer2"></i> Dashboard
                    </a>
                    <a class="nav-link" href="${pageContext.request.contextPath}/CitasController?accion=listarDoctor">
                        <i class="bi bi-calendar-check"></i> Mis Citas
                    </a>
                    <a class="nav-link" href="${pageContext.request.contextPath}/ConsultasController?accion=listarMisConsultas">
                        <i class="bi bi-file-medical"></i> Mis Consultas
                    </a>
                </nav>
            </div>

            <div class="col-md-10 p-4">
                <div class="welcome-card">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h2>Bienvenido, Dr. ${usuario.nombre}</h2>
                            <p>Panel Médico - Gestión de Consultas y Pacientes</p>
                        </div>
                        <div>
                            <span class="badge bg-white text-primary fs-6 px-3 py-2">
                                <i class="bi bi-calendar3"></i> 
                                <%= new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()) %>
                            </span>
                        </div>
                    </div>
                </div>

                <!-- Mostrar mensajes de éxito o error -->
                <c:if test="${not empty mensaje}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <i class="bi bi-check-circle"></i> ${mensaje}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>
                
                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="bi bi-exclamation-triangle"></i> ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>
                
                
                <!-- Barra de Filtros y Búsqueda -->
<div class="row mb-4">
    <div class="col-md-12">
        <div class="card">
            <div class="card-body">
                <div class="row align-items-center">
                    <div class="col-md-6">
                        <div class="input-group">
                            <input type="text" id="searchInput" class="form-control" 
                                   placeholder="Buscar por nombre de paciente..." 
                                   onkeyup="buscarCitas()">
                            <button class="btn btn-outline-secondary" type="button">
                                <i class="bi bi-search"></i>
                            </button>
                        </div>
                    </div>
                    <div class="col-md-6 text-end">
                        <div class="btn-group" role="group">
                            <button type="button" class="btn btn-outline-primary active" 
                                    onclick="filtrarCitas('todas')">
                                <i class="bi bi-list-ul"></i> Todas
                            </button>
                            <button type="button" class="btn btn-outline-primary" 
                                    onclick="filtrarCitas('hoy')">
                                <i class="bi bi-calendar-day"></i> Hoy
                            </button>
                            <button type="button" class="btn btn-outline-primary" 
                                    onclick="filtrarCitas('programadas')">
                                <i class="bi bi-clock"></i> Programadas
                            </button>
                            <button type="button" class="btn btn-outline-primary" 
                                    onclick="filtrarCitas('completadas')">
                                <i class="bi bi-check-circle"></i> Completadas
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Estadísticas Rápidas -->
<div class="row mb-4">
    <div class="col-md-3">
        <div class="card bg-primary text-white">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <h4>${citasHoy.size()}</h4>
                        <p class="mb-0">Citas Hoy</p>
                    </div>
                    <div class="align-self-center">
                        <i class="bi bi-calendar-day fs-1"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card bg-success text-white">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <h4 id="contadorProgramadas">0</h4>
                        <p class="mb-0">Programadas</p>
                    </div>
                    <div class="align-self-center">
                        <i class="bi bi-clock fs-1"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card bg-info text-white">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <h4 id="contadorCompletadas">0</h4>
                        <p class="mb-0">Completadas</p>
                    </div>
                    <div class="align-self-center">
                        <i class="bi bi-check-circle fs-1"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card bg-warning text-white">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <h4>${proximasCitas.size()}</h4>
                        <p class="mb-0">Próximas</p>
                    </div>
                    <div class="align-self-center">
                        <i class="bi bi-calendar-week fs-1"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

                <!-- Citas del Día -->
                <div class="row">
                    <div class="col-12">
                        <h3 class="section-title">Citas de Hoy</h3>
                        
                        <c:if test="${empty citasHoy}">
                            <div class="alert alert-info">
                                <i class="bi bi-info-circle"></i> No tienes citas programadas para hoy.
                            </div>
                        </c:if>

                        <c:forEach var="cita" items="${citasHoy}">
                            <div class="card cita-card ${cita.estado}">
                                <div class="card-body">
                                    <div class="row align-items-center">
                                        <div class="col-md-3">
                                            <h5 class="card-title">${cita.pacienteNombre} ${cita.pacienteApellido}</h5>
                                            <p class="card-text text-muted mb-1">
                                                <i class="bi bi-clock"></i> ${cita.horaCita}
                                            </p>
                                        </div>
                                        <div class="col-md-3">
                                            <p class="mb-1"><strong>Motivo:</strong></p>
                                            <p class="text-muted">${cita.motivo}</p>
                                        </div>
                                        <div class="col-md-2">
                                            <span class="badge badge-estado 
                                                ${cita.estado == 'programada' ? 'bg-primary' : 
                                                  cita.estado == 'completada' ? 'bg-success' : 
                                                  'bg-danger'}">
                                                ${cita.estado}
                                            </span>
                                        </div>
                                        <div class="col-md-4 text-end">
                                            <c:if test="${cita.estado == 'programada'}">
                                                <button class="btn btn-consulta me-2" 
                                                        onclick="abrirModalConsulta(${cita.id}, '${cita.pacienteNombre} ${cita.pacienteApellido}')">
                                                    <i class="bi bi-file-medical"></i> Realizar Consulta
                                                </button>
                                            </c:if>
                                            <button class="btn btn-outline-primary" 
                                                    onclick="verDetallesPaciente(${cita.pacienteId})">
                                                <i class="bi bi-person"></i> Ver Paciente
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <!-- Próximas Citas -->
                <div class="row mt-4">
                    <div class="col-12">
                        <h3 class="section-title">Próximas Citas</h3>
                        
                        <c:if test="${empty proximasCitas}">
                            <div class="alert alert-info">
                                <i class="bi bi-info-circle"></i> No tienes citas programadas para los próximos días.
                            </div>
                        </c:if>

                        <c:forEach var="cita" items="${proximasCitas}">
                            <div class="card cita-card ${cita.estado}">
                                <div class="card-body">
                                    <div class="row align-items-center">
                                        <div class="col-md-3">
                                            <h5 class="card-title">${cita.pacienteNombre} ${cita.pacienteApellido}</h5>
                                            <p class="card-text text-muted mb-1">
                                                <i class="bi bi-calendar"></i> ${cita.fechaCita}
                                            </p>
                                            <p class="card-text text-muted">
                                                <i class="bi bi-clock"></i> ${cita.horaCita}
                                            </p>
                                        </div>
                                        <div class="col-md-3">
                                            <p class="mb-1"><strong>Motivo:</strong></p>
                                            <p class="text-muted">${cita.motivo}</p>
                                        </div>
                                        <div class="col-md-2">
                                            <span class="badge badge-estado 
                                                ${cita.estado == 'programada' ? 'bg-primary' : 
                                                  cita.estado == 'completada' ? 'bg-success' : 
                                                  'bg-danger'}">
                                                ${cita.estado}
                                            </span>
                                        </div>
                                        <div class="col-md-4 text-end">
                                            <button class="btn btn-outline-primary" 
                                                    onclick="verDetallesPaciente(${cita.pacienteId})">
                                                <i class="bi bi-person"></i> Ver Paciente
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal para Realizar Consulta -->
    <div class="modal fade" id="modalConsulta" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Realizar Consulta Médica - <span id="nombrePacienteModal"></span></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <form id="formConsulta" action="${pageContext.request.contextPath}/ConsultasController" method="post">
                        <input type="hidden" name="accion" value="registrarDesdeDoctor">
                        <input type="hidden" name="citaId" id="citaId">
                        
                        <div class="mb-3">
                            <label for="diagnostico" class="form-label">Diagnóstico *</label>
                            <textarea class="form-control" id="diagnostico" name="diagnostico" 
                                      rows="4" placeholder="Ingrese el diagnóstico del paciente..." required></textarea>
                        </div>
                        
                        <div class="mb-3">
                            <label for="planTratamiento" class="form-label">Plan de Tratamiento</label>
                            <textarea class="form-control" id="planTratamiento" name="planTratamiento" 
                                      rows="3" placeholder="Describa el plan de tratamiento..."></textarea>
                        </div>
                        
                        <div class="mb-3">
                            <label for="observaciones" class="form-label">Observaciones</label>
                            <textarea class="form-control" id="observaciones" name="observaciones" 
                                      rows="2" placeholder="Observaciones adicionales..."></textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button type="button" class="btn btn-success" onclick="registrarConsulta()">
                        <i class="bi bi-check-lg"></i> Finalizar Consulta
                    </button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function abrirModalConsulta(citaId, nombrePaciente) {
            document.getElementById('citaId').value = citaId;
            document.getElementById('nombrePacienteModal').textContent = nombrePaciente;
            const modal = new bootstrap.Modal(document.getElementById('modalConsulta'));
            modal.show();
        }

        function registrarConsulta() {
            const form = document.getElementById('formConsulta');
            const diagnostico = document.getElementById('diagnostico').value.trim();
            
            if (!diagnostico) {
                alert('Por favor ingrese el diagnóstico del paciente.');
                document.getElementById('diagnostico').focus();
                return;
            }
            
            if (form.checkValidity()) {
                form.submit();
            } else {
                form.reportValidity();
            }
        }

        function verDetallesPaciente(pacienteId) {
            window.location.href = '${pageContext.request.contextPath}/PacientesController?accion=ver&id=' + pacienteId;
        }
        
        // Recargar la página cada 2 minutos para ver citas nuevas
        setTimeout(function() {
            window.location.reload();
        }, 120000);
        
        
        // Agregar después de las funciones existentes en el script
function filtrarCitas(tipo) {
    const citasHoy = document.querySelectorAll('.cita-card');
    const citasProximas = document.querySelectorAll('.row.mt-4 .cita-card');
    
    if (tipo === 'todas') {
        // Mostrar todas las citas
        citasHoy.forEach(cita => cita.style.display = 'block');
        citasProximas.forEach(cita => cita.style.display = 'block');
    } else if (tipo === 'hoy') {
        // Mostrar solo citas de hoy
        citasHoy.forEach(cita => cita.style.display = 'block');
        citasProximas.forEach(cita => cita.style.display = 'none');
    } else if (tipo === 'programadas') {
        // Mostrar solo citas programadas
        citasHoy.forEach(cita => {
            const estado = cita.querySelector('.badge-estado').textContent.trim();
            cita.style.display = estado === 'programada' ? 'block' : 'none';
        });
        citasProximas.forEach(cita => {
            const estado = cita.querySelector('.badge-estado').textContent.trim();
            cita.style.display = estado === 'programada' ? 'block' : 'none';
        });
    } else if (tipo === 'completadas') {
        // Mostrar solo citas completadas
        citasHoy.forEach(cita => {
            const estado = cita.querySelector('.badge-estado').textContent.trim();
            cita.style.display = estado === 'completada' ? 'block' : 'none';
        });
        citasProximas.forEach(cita => {
            const estado = cita.querySelector('.badge-estado').textContent.trim();
            cita.style.display = estado === 'completada' ? 'block' : 'none';
        });
    }
}

// Función para buscar citas por nombre de paciente
function buscarCitas() {
    const searchTerm = document.getElementById('searchInput').value.toLowerCase().trim();
    const todasLasCitas = document.querySelectorAll('.cita-card');
    
    todasLasCitas.forEach(cita => {
        const nombrePaciente = cita.querySelector('.card-title').textContent.toLowerCase();
        if (nombrePaciente.includes(searchTerm) || searchTerm === '') {
            cita.style.display = 'block';
        } else {
            cita.style.display = 'none';
        }
    });
}

// Función para actualizar contadores
function actualizarContadores() {
    const todasLasCitas = document.querySelectorAll('.cita-card');
    let programadas = 0;
    let completadas = 0;
    
    todasLasCitas.forEach(cita => {
        const estado = cita.querySelector('.badge-estado').textContent.trim();
        if (estado === 'programada') programadas++;
        if (estado === 'completada') completadas++;
    });
    
    document.getElementById('contadorProgramadas').textContent = programadas;
    document.getElementById('contadorCompletadas').textContent = completadas;
}

// Actualizar contadores cuando se carga la página
document.addEventListener('DOMContentLoaded', function() {
    actualizarContadores();
    
    // Auto-refresh cada 2 minutos
    setInterval(function() {
        window.location.reload();
    }, 120000);
});
    </script>
</body>
</html>