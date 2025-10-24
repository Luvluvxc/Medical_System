<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Usuarios - Sistema Médico</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
        }
        .main-card {
            background: white;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            overflow: hidden;
        }
        .card-header-custom {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 2rem;
        }
        .filter-btn {
            border-radius: 25px;
            padding: 0.5rem 1.5rem;
            margin: 0.25rem;
            transition: all 0.3s ease;
        }
        .filter-btn.active {
            transform: scale(1.05);
            box-shadow: 0 5px 15px rgba(0,0,0,0.2);
        }
        .search-box {
            border-radius: 25px;
            border: 2px solid #e0e0e0;
            padding: 0.75rem 1.5rem;
            transition: all 0.3s ease;
        }
        .search-box:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
        }
        .user-avatar {
            width: 45px;
            height: 45px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
            color: white;
        }
        .badge-sin-registro {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            animation: pulse 2s infinite;
        }
        @keyframes pulse {
            0%, 100% { opacity: 1; }
            50% { opacity: 0.7; }
        }
        .table-hover tbody tr {
            transition: all 0.3s ease;
        }
        .table-hover tbody tr:hover {
            transform: scale(1.01);
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }
        .btn-action {
            border-radius: 10px;
            padding: 0.5rem 1rem;
            margin: 0.2rem;
            transition: all 0.3s ease;
        }
        .btn-action:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.2);
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark" style="background: rgba(0,0,0,0.2);">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/dashboard_recepcionista.jsp">
                <i class="bi bi-hospital"></i> Sistema Médico
            </a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="${pageContext.request.contextPath}/dashboard_recepcionista.jsp">
                    <i class="bi bi-house-door"></i> Dashboard
                </a>
            </div>
        </div>
    </nav>

    <div class="container mt-4 mb-5">
        <div class="main-card">
            <div class="card-header-custom">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h3 class="mb-1"><i class="bi bi-people-fill"></i> Gestión de Usuarios</h3>
                        <p class="mb-0 opacity-75">Administra usuarios, pacientes y doctores del sistema</p>
                    </div>
                    <div>
                        <a href="${pageContext.request.contextPath}/UsuariosController?accion=nuevo" 
                           class="btn btn-light btn-lg">
                            <i class="bi bi-person-plus-fill"></i> Nuevo Usuario
                        </a>
                    </div>
                </div>
            </div>
            
            <div class="card-body p-4">
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

                <!-- Search and Filter Section -->
                <div class="row mb-4">
                    <div class="col-md-6">
                        <input type="text" id="searchInput" class="form-control search-box" 
                               placeholder="Buscar por nombre, correo o teléfono...">
                    </div>
                    <div class="col-md-6 text-end">
                        <button class="btn btn-primary filter-btn active" onclick="filtrarPorRol('todos')">
                            <i class="bi bi-people"></i> Todos
                        </button>
                        <button class="btn btn-success filter-btn" onclick="filtrarPorRol('paciente')">
                            <i class="bi bi-person"></i> Pacientes
                        </button>
                        <button class="btn btn-info filter-btn" onclick="filtrarPorRol('doctor')">
                            <i class="bi bi-person-badge"></i> Doctores
                        </button>
                        <button class="btn btn-warning filter-btn" onclick="filtrarPorRol('recepcionista')">
                            <i class="bi bi-person-workspace"></i> Recepcionistas
                        </button>
                    </div>
                </div>

                <div class="table-responsive">
                    <table class="table table-hover align-middle" id="usuariosTable">
                        <thead style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white;">
                            <tr>
                                <th>Usuario</th>
                                <th>Contacto</th>
                                <th>Rol</th>
                                <th>Estado</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="usuario" items="${usuarios}">
                                <!-- Filter out admin users -->
                                <c:if test="${usuario.rol != 'admin'}">
                                    <tr data-rol="${usuario.rol}" data-usuario-id="${usuario.id}">
                                        <td>
                                            <div class="d-flex align-items-center">
                                                <div class="user-avatar me-3" 
                                                     style="background: linear-gradient(135deg, 
                                                     <c:choose>
                                                         <c:when test='${usuario.rol == "paciente"}'>#11998e, #38ef7d</c:when>
                                                         <c:when test='${usuario.rol == "doctor"}'>#4facfe, #00f2fe</c:when>
                                                         <c:when test='${usuario.rol == "recepcionista"}'>#fa709a, #fee140</c:when>
                                                         <c:otherwise>#667eea, #764ba2</c:otherwise>
                                                     </c:choose>);">
                                                    ${usuario.nombre.substring(0,1)}${usuario.apellido.substring(0,1)}
                                                </div>
                                                <div>
                                                    <strong>${usuario.nombre} ${usuario.apellido}</strong>
                                                    <br>
                                                    <small class="text-muted">ID: ${usuario.id}</small>
                                                </div>
                                            </div>
                                        </td>
                                        <td>
                                            <div>
                                                <i class="bi bi-envelope"></i> ${usuario.correo}
                                            </div>
                                            <div>
                                                <i class="bi bi-telephone"></i> ${usuario.telefono}
                                            </div>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${usuario.rol == 'paciente'}">
                                                    <span class="badge bg-success">
                                                        <i class="bi bi-person"></i> Paciente
                                                    </span>
                                                    <!-- Badge to show if patient record doesn't exist -->
                                                    <span class="badge badge-sin-registro d-none" data-tipo="paciente">
                                                        <i class="bi bi-exclamation-circle"></i> Sin Registro
                                                    </span>
                                                </c:when>
                                                <c:when test="${usuario.rol == 'doctor'}">
                                                    <span class="badge bg-info">
                                                        <i class="bi bi-person-badge"></i> Doctor
                                                    </span>
                                                    <!-- Badge to show if doctor record doesn't exist -->
                                                    <span class="badge badge-sin-registro d-none" data-tipo="doctor">
                                                        <i class="bi bi-exclamation-circle"></i> Sin Registro
                                                    </span>
                                                </c:when>
                                                <c:when test="${usuario.rol == 'recepcionista'}">
                                                    <span class="badge bg-warning">
                                                        <i class="bi bi-person-workspace"></i> Recepcionista
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-secondary">${usuario.rol}</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${usuario.activo}">
                                                    <span class="badge bg-success">
                                                        <i class="bi bi-check-circle"></i> Activo
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-danger">
                                                        <i class="bi bi-x-circle"></i> Inactivo
                                                    </span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="text-center">
                                            <a href="${pageContext.request.contextPath}/UsuariosController?accion=editar&id=${usuario.id}" 
                                               class="btn btn-sm btn-warning btn-action" 
                                               title="Editar usuario">
                                                <i class="bi bi-pencil-square"></i>
                                            </a>

                                            <c:choose>
                                                <c:when test="${usuario.activo}">
                                                    <button type="button" 
                                                            class="btn btn-sm btn-danger btn-action" 
                                                            onclick="confirmarDesactivar(${usuario.id}, '${usuario.nombre} ${usuario.apellido}')"
                                                            title="Desactivar usuario">
                                                        <i class="bi bi-x-circle"></i>
                                                    </button>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="${pageContext.request.contextPath}/UsuariosController?accion=activar&id=${usuario.id}" 
                                                       class="btn btn-sm btn-success btn-action" 
                                                       title="Activar usuario">
                                                        <i class="bi bi-check-circle"></i>
                                                    </a>
                                                </c:otherwise>
                                            </c:choose>

                                            <!-- Conditional buttons based on role and registration status -->
                                            <c:if test="${usuario.rol == 'paciente'}">
                                                <a href="${pageContext.request.contextPath}/PacientesController?accion=nuevo&usuarioId=${usuario.id}"
                                                   class="btn btn-sm btn-success btn-action btn-crear-paciente d-none" 
                                                   data-usuario-id="${usuario.id}"
                                                   title="Crear perfil de paciente">
                                                    <i class="bi bi-person-plus"></i> Crear Paciente
                                                </a>
                                                <a href="${pageContext.request.contextPath}/PacientesController?accion=ver&id=" 
                                                   class="btn btn-sm btn-info btn-action btn-ver-paciente d-none" 
                                                   data-usuario-id="${usuario.id}"
                                                   title="Ver perfil de paciente">
                                                    <i class="bi bi-eye"></i> Ver Paciente
                                                </a>
                                            </c:if>

                                            <c:if test="${usuario.rol == 'doctor'}">
                                                <a href="${pageContext.request.contextPath}/DoctoresController?accion=nuevo&usuarioId=${usuario.id}" 
                                                   class="btn btn-sm btn-primary btn-action btn-crear-doctor d-none" 
                                                   data-usuario-id="${usuario.id}"
                                                   title="Crear perfil de doctor">
                                                    <i class="bi bi-person-badge"></i> Crear Doctor
                                                </a>
                                                <a href="${pageContext.request.contextPath}/DoctoresController?accion=ver&id=" 
                                                   class="btn btn-sm btn-info btn-action btn-ver-doctor d-none" 
                                                   data-usuario-id="${usuario.id}"
                                                   title="Ver perfil de doctor">
                                                    <i class="bi bi-eye"></i> Ver Doctor
                                                </a>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal de confirmación -->
    <div class="modal fade" id="modalDesactivar" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-danger text-white">
                    <h5 class="modal-title">
                        <i class="bi bi-exclamation-triangle-fill"></i> Confirmar Desactivación
                    </h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <p>¿Está seguro que desea desactivar al usuario <strong id="nombreUsuario"></strong>?</p>
                    <p class="text-warning">
                        <i class="bi bi-info-circle"></i> El usuario no podrá acceder al sistema hasta que sea reactivado.
                    </p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <a href="#" id="btnConfirmarDesactivar" class="btn btn-danger">
                        <i class="bi bi-x-circle"></i> Desactivar
                    </a>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        let pacientes = [];
        let doctores = [];

        async function cargarPacientesYDoctores() {
            try {
                // Load patients
                const resPacientes = await fetch('${pageContext.request.contextPath}/webresources/pacientes/lista');
                pacientes = await resPacientes.json();
                
                // Load doctors
                const resDoctores = await fetch('${pageContext.request.contextPath}/webresources/doctores/lista');
                doctores = await resDoctores.json();
                
                // Update UI based on registration status
                actualizarEstadoRegistros();
            } catch (error) {
                console.error('[v0] Error al cargar pacientes y doctores:', error);
            }
        }

        function actualizarEstadoRegistros() {
            // Check each row for patient/doctor registration
            document.querySelectorAll('tr[data-usuario-id]').forEach(row => {
                const usuarioId = parseInt(row.dataset.usuarioId);
                const rol = row.dataset.rol;
                
                if (rol === 'paciente') {
                    const paciente = pacientes.find(p => p.usuarioId === usuarioId);
                    const badgeSinRegistro = row.querySelector('.badge-sin-registro[data-tipo="paciente"]');
                    const btnCrear = row.querySelector('.btn-crear-paciente');
                    const btnVer = row.querySelector('.btn-ver-paciente');
                    
                    if (paciente) {
                        // Patient record exists
                        badgeSinRegistro.classList.add('d-none');
                        btnCrear.classList.add('d-none');
                        btnVer.classList.remove('d-none');
                        btnVer.href = '${pageContext.request.contextPath}/PacientesController?accion=ver&id=' + paciente.id;
                    } else {
                        // Patient record doesn't exist
                        badgeSinRegistro.classList.remove('d-none');
                        btnCrear.classList.remove('d-none');
                        btnVer.classList.add('d-none');
                    }
                } else if (rol === 'doctor') {
                    const doctor = doctores.find(d => d.usuarioId === usuarioId);
                    const badgeSinRegistro = row.querySelector('.badge-sin-registro[data-tipo="doctor"]');
                    const btnCrear = row.querySelector('.btn-crear-doctor');
                    const btnVer = row.querySelector('.btn-ver-doctor');
                    
                    if (doctor) {
                        // Doctor record exists
                        badgeSinRegistro.classList.add('d-none');
                        btnCrear.classList.add('d-none');
                        btnVer.classList.remove('d-none');
                        btnVer.href = '${pageContext.request.contextPath}/DoctoresController?accion=ver&id=' + doctor.id;
                    } else {
                        // Doctor record doesn't exist
                        badgeSinRegistro.classList.remove('d-none');
                        btnCrear.classList.remove('d-none');
                        btnVer.classList.add('d-none');
                    }
                }
            });
        }

        // Search functionality
        document.getElementById('searchInput').addEventListener('keyup', function() {
            const searchTerm = this.value.toLowerCase();
            const rows = document.querySelectorAll('#usuariosTable tbody tr');
            
            rows.forEach(row => {
                const text = row.textContent.toLowerCase();
                row.style.display = text.includes(searchTerm) ? '' : 'none';
            });
        });

        // Filter by role
        function filtrarPorRol(rol) {
            const rows = document.querySelectorAll('#usuariosTable tbody tr');
            const buttons = document.querySelectorAll('.filter-btn');
            
            buttons.forEach(btn => btn.classList.remove('active'));
            event.target.classList.add('active');
            
            rows.forEach(row => {
                if (rol === 'todos') {
                    row.style.display = '';
                } else {
                    row.style.display = row.dataset.rol === rol ? '' : 'none';
                }
            });
        }

        function confirmarDesactivar(id, nombre) {
            document.getElementById('nombreUsuario').textContent = nombre;
            document.getElementById('btnConfirmarDesactivar').href = 
                '${pageContext.request.contextPath}/UsuariosController?accion=desactivar&id=' + id;
            new bootstrap.Modal(document.getElementById('modalDesactivar')).show();
        }

        // Load data on page load
        window.addEventListener('DOMContentLoaded', cargarPacientesYDoctores);
    </script>
</body>
</html>
