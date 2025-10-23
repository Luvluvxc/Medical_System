<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Doctores - Sistema Médico</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        .doctor-card {
            transition: transform 0.2s, box-shadow 0.2s;
        }
        .doctor-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 16px rgba(0,0,0,0.1);
        }
        .doctor-avatar {
            width: 80px;
            height: 80px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 2rem;
            color: white;
            font-weight: bold;
        }
        .badge-especialidad {
            font-size: 0.85rem;
            padding: 0.4rem 0.8rem;
        }
    </style>
</head>
<body class="bg-light">
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="../dashboard">
                <i class="bi bi-hospital"></i> Sistema Médico
            </a>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h2><i class="bi bi-person-badge-fill"></i> Gestión de Doctores</h2>
                <nav aria-label="breadcrumb">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="../dashboard">Inicio</a></li>
                        <li class="breadcrumb-item active">Doctores</li>
                    </ol>
                </nav>
            </div>
            <a href="DoctoresController?accion=nuevo" class="btn btn-primary">
                <i class="bi bi-plus-circle"></i> Nuevo Doctor
            </a>
        </div>

        <c:if test="${not empty mensaje}">
            <div class="alert alert-success alert-dismissible fade show">
                <i class="bi bi-check-circle"></i> ${mensaje}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <div class="row mb-3">
            <div class="col-md-6">
                <div class="input-group">
                    <span class="input-group-text"><i class="bi bi-search"></i></span>
                    <input type="text" class="form-control" id="searchInput" placeholder="Buscar doctor por nombre o especialidad...">
                </div>
            </div>
            <div class="col-md-3">
                <select class="form-select" id="filterEspecialidad">
                    <option value="">Todas las especialidades</option>
                    <option value="Cardiología">Cardiología</option>
                    <option value="Pediatría">Pediatría</option>
                    <option value="Dermatología">Dermatología</option>
                    <option value="Neurología">Neurología</option>
                    <option value="Traumatología">Traumatología</option>
                </select>
            </div>
        </div>

        <div class="row g-4" id="doctoresContainer">
            <c:forEach var="doctor" items="${doctores}">
                <div class="col-md-6 col-lg-4 doctor-item" data-especialidad="${doctor.especialidad}">
                    <div class="card doctor-card h-100 shadow-sm">
                        <div class="card-body">
                            <div class="d-flex align-items-start mb-3">
                                <div class="doctor-avatar me-3">
                                    ${doctor.nombre.substring(0,1)}${doctor.apellido.substring(0,1)}
                                </div>
                                <div class="flex-grow-1">
                                    <h5 class="card-title mb-1">Dr. ${doctor.nombre} ${doctor.apellido}</h5>
                                    <span class="badge badge-especialidad bg-primary">${doctor.especialidad}</span>
                                </div>
                            </div>
                            
                            <div class="mb-2">
                                <small class="text-muted"><i class="bi bi-card-text"></i> Licencia:</small>
                                <p class="mb-1">${doctor.licencia}</p>
                            </div>
                            
                            <div class="mb-2">
                                <small class="text-muted"><i class="bi bi-telephone"></i> Teléfono:</small>
                                <p class="mb-1">${doctor.telefono}</p>
                            </div>
                            
                            <div class="mb-3">
                                <small class="text-muted"><i class="bi bi-envelope"></i> Correo:</small>
                                <p class="mb-1 text-truncate">${doctor.correo}</p>
                            </div>

                            <c:if test="${not empty doctor.idUsuario}">
                                <div class="alert alert-info py-2 mb-3">
                                    <small><i class="bi bi-person-check"></i> Usuario del sistema vinculado</small>
                                </div>
                            </c:if>
                            
                            <div class="d-flex gap-2">
                                <a href="DoctoresController?accion=editar&id=${doctor.id}" class="btn btn-sm btn-outline-primary flex-grow-1">
                                    <i class="bi bi-pencil"></i> Editar
                                </a>
                                <button onclick="confirmarEliminar(${doctor.id})" class="btn btn-sm btn-outline-danger">
                                    <i class="bi bi-trash"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <c:if test="${empty doctores}">
            <div class="text-center py-5">
                <i class="bi bi-inbox" style="font-size: 4rem; color: #ccc;"></i>
                <p class="text-muted mt-3">No hay doctores registrados</p>
                <a href="DoctoresController?accion=nuevo" class="btn btn-primary">
                    <i class="bi bi-plus-circle"></i> Registrar Primer Doctor
                </a>
            </div>
        </c:if>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function confirmarEliminar(id) {
            if (confirm('¿Está seguro de eliminar este doctor? Esta acción no se puede deshacer.')) {
                window.location.href = 'DoctoresController?accion=eliminar&id=' + id;
            }
        }

        // Búsqueda en tiempo real
        document.getElementById('searchInput').addEventListener('input', filtrarDoctores);
        document.getElementById('filterEspecialidad').addEventListener('change', filtrarDoctores);

        function filtrarDoctores() {
            const searchTerm = document.getElementById('searchInput').value.toLowerCase();
            const especialidad = document.getElementById('filterEspecialidad').value;
            const items = document.querySelectorAll('.doctor-item');

            items.forEach(item => {
                const text = item.textContent.toLowerCase();
                const itemEspecialidad = item.dataset.especialidad;
                const matchSearch = text.includes(searchTerm);
                const matchEspecialidad = !especialidad || itemEspecialidad === especialidad;

                item.style.display = matchSearch && matchEspecialidad ? '' : 'none';
            });
        }
    </script>
</body>
</html>
