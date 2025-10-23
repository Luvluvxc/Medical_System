<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Gestión de Pacientes - Sistema Médico</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
        <style>
            body {
                background-color: #f8f9fa;
            }
            .card {
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                border: none;
            }
            .table-actions {
                white-space: nowrap;
            }
            .btn-action {
                margin: 2px;
            }
            .patient-code {
                font-family: 'Courier New', monospace;
                font-weight: bold;
                color: #0d6efd;
            }
        </style>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-success">
            <div class="container-fluid">
                <a class="navbar-brand" href="${pageContext.request.contextPath}dashboard_recepcionista.jsp">
                    <i class="bi bi-hospital"></i> Sistema Médico
                </a>
                <div class="navbar-nav ms-auto">
                    <a class="nav-link" href="${pageContext.request.contextPath}dashboard_recepcionista.jsp">
                        <i class="bi bi-house-door"></i> Dashboard
                    </a>
                </div>
            </div>
        </nav>

        <div class="container mt-4">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}dashboard_recepcionista.jsp">Inicio</a></li>
                    <li class="breadcrumb-item active">Pacientes</li>
                </ol>
            </nav>

            <div class="card">
                <div class="card-header bg-success text-white">
                    <div class="d-flex justify-content-between align-items-center">
                        <h4 class="mb-0"><i class="bi bi-people-fill"></i> Gestión de Pacientes</h4>
                        <div>
                            <a href="${pageContext.request.contextPath}/PacientesController?accion=nuevo" class="btn btn-light">
                                <i class="bi bi-person-plus-fill"></i> Nuevo Paciente
                            </a>
                            <a href="${pageContext.request.contextPath}/UsuariosController?accion=listar" class="btn btn-outline-light">
                                <i class="bi bi-person-circle"></i> Ver Usuarios
                            </a>
                        </div>
                    </div>
                </div>
                <div class="card-body">
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

                    <div class="table-responsive">
                        <table class="table table-hover align-middle">
                            <thead class="table-dark">
                                <tr>
                                    <th>Código</th>
                                    <th>Paciente</th>
                                    <th>Fecha Nac.</th>
                                    <th>Género</th>
                                    <th>Contacto</th>
                                    <th>Dirección</th>
                                    <th class="text-center">Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="paciente" items="${pacientes}">
                                    <tr>
                                        <td>
                                            <span class="patient-code">${paciente.codigoPaciente}</span>
                                        </td>
                                        <td>
                                            <div class="d-flex align-items-center">
                                                <div class="bg-success text-white rounded-circle d-flex align-items-center justify-content-center me-2" 
                                                     style="width: 40px; height: 40px;">
                                                    <i class="bi bi-person-fill"></i>
                                                </div>
                                                <div>
                                                    <strong>${paciente.usuarioNombre} ${paciente.usuarioApellido}</strong>
                                                </div>
                                            </div>
                                        </td>
                                        <td>
                                            <i class="bi bi-calendar3"></i> ${paciente.fechaNacimiento}
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${paciente.genero == 'Masculino'}">
                                                    <span class="badge bg-primary">
                                                        <i class="bi bi-gender-male"></i> Masculino
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-danger">
                                                        <i class="bi bi-gender-female"></i> Femenino
                                                    </span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <i class="bi bi-telephone-fill"></i> ${paciente.usuarioTelefono}
                                        </td>
                                        <td>
                                            <i class="bi bi-geo-alt-fill"></i> ${paciente.direccion}
                                        </td>
                                        <td class="text-center table-actions">
                                            <a href="${pageContext.request.contextPath}/PacientesController?accion=editar&id=${paciente.id}" 
                                               class="btn btn-sm btn-warning btn-action" 
                                               title="Editar paciente">
                                                <i class="bi bi-pencil-square"></i>
                                            </a>

                                            <a href="${pageContext.request.contextPath}/PacientesController?accion=ver&id=${paciente.id}" 
                                               class="btn btn-sm btn-info btn-action" 
                                               title="Ver detalles">
                                                <i class="bi bi-eye-fill"></i>
                                            </a>

                                            <!-- Added button to schedule appointment for patient -->
                                            <a href="${pageContext.request.contextPath}/CitasController?accion=nuevoPaciente&pacienteId=${paciente.id}" 
                                               class="btn btn-primary btn-lg">
                                                <i class="bi bi-calendar-plus"></i> 
                                            </a>                          
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
