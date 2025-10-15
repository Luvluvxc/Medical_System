<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pacientes - Sistema Médico</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <!-- DataTables CSS -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/dataTables.bootstrap5.min.css">
</head>
<body class="bg-light">
    <!-- Navbar (igual que el dashboard) -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="dashboard_recepcionista.jsp">
                <i class="bi bi-hospital"></i> Sistema Médico
            </a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="dashboard_recepcionista.jsp">
                            <i class="bi bi-house-door"></i> Inicio
                        </a>
                    </li>
                    <li class="nav-item">
                        <span class="nav-link"><i class="bi bi-person-circle"></i> ${usuario.nombre}</span>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="Validar?accion=salir">
                            <i class="bi bi-box-arrow-right"></i> Salir
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container-fluid mt-4">
        <div class="row">
            <div class="col-12">
                <!-- Header -->
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <div>
                        <h2><i class="bi bi-people-fill"></i> Gestión de Pacientes</h2>
                        <nav aria-label="breadcrumb">
                            <ol class="breadcrumb">
                                <li class="breadcrumb-item"><a href="dashboard_recepcionista.jsp">Inicio</a></li>
                                <li class="breadcrumb-item active">Pacientes</li>
                            </ol>
                        </nav>
                    </div>
                    <div>
                        <a href="PacientesController?accion=nuevo" class="btn btn-primary btn-lg">
                            <i class="bi bi-person-plus"></i> Nuevo Paciente
                        </a>
                    </div>
                </div>

                <!-- Mensajes -->
                <c:if test="${not empty mensaje}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <i class="bi bi-check-circle-fill"></i> ${mensaje}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="bi bi-exclamation-triangle-fill"></i> ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <!-- Tabla de Pacientes -->
                <div class="card shadow-sm">
                    <div class="card-header bg-white">
                        <h5 class="mb-0">Lista de Pacientes</h5>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table id="tablaPacientes" class="table table-striped table-hover">
                                <thead class="table-primary">
                                    <tr>
                                        <th>Código</th>
                                        <th>Nombre Completo</th>
                                        <th>Fecha Nacimiento</th>
                                        <th>Género</th>
                                        <th>Teléfono</th>
                                        <th>Correo</th>
                                        <th>Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="paciente" items="${pacientes}">
                                        <tr>
                                            <td><strong>${paciente.codigoPaciente}</strong></td>
                                            <td>${paciente.nombreCompleto}</td>
                                            <td>${paciente.fechaNacimiento}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${paciente.genero == 'M'}">
                                                        <span class="badge bg-info">Masculino</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-pink">Femenino</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${paciente.telefono}</td>
                                            <td>${paciente.correo}</td>
                                            <td>
                                                <div class="btn-group" role="group">
                                                    <a href="PacientesController?accion=verHistorial&id=${paciente.id}" 
                                                       class="btn btn-sm btn-info" title="Ver Historial">
                                                        <i class="bi bi-file-medical"></i>
                                                    </a>
                                                    <a href="PacientesController?accion=editar&id=${paciente.id}" 
                                                       class="btn btn-sm btn-warning" title="Editar">
                                                        <i class="bi bi-pencil"></i>
                                                    </a>
                                                    <a href="CitasController?accion=nueva&pacienteId=${paciente.id}" 
                                                       class="btn btn-sm btn-success" title="Nueva Cita">
                                                        <i class="bi bi-calendar-plus"></i>
                                                    </a>
                                                    <button onclick="confirmarEliminar(${paciente.id})" 
                                                            class="btn btn-sm btn-danger" title="Eliminar">
                                                        <i class="bi bi-trash"></i>
                                                    </button>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- jQuery y DataTables -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.4/js/dataTables.bootstrap5.min.js"></script>
    
    <script>
        $(document).ready(function() {
            $('#tablaPacientes').DataTable({
                language: {
                    url: '//cdn.datatables.net/plug-ins/1.13.4/i18n/es-ES.json'
                },
                pageLength: 10,
                order: [[0, 'desc']]
            });
        });

        function confirmarEliminar(id) {
            if (confirm('¿Está seguro de eliminar este paciente?')) {
                window.location.href = 'PacientesController?accion=eliminar&id=' + id;
            }
        }
    </script>
</body>
</html>