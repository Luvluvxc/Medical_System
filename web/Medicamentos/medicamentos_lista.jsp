<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Medicamentos - Sistema Médico</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        .medicamento-card {
            transition: transform 0.2s, box-shadow 0.2s;
            border-left: 4px solid #0d6efd;
        }
        .medicamento-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 16px rgba(0,0,0,0.1);
        }
        .stock-badge {
            font-size: 0.9rem;
            padding: 0.5rem 1rem;
        }
        .stock-bajo {
            background-color: #dc3545;
        }
        .stock-medio {
            background-color: #ffc107;
        }
        .stock-alto {
            background-color: #198754;
        }
        .precio-tag {
            font-size: 1.5rem;
            font-weight: bold;
            color: #0d6efd;
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
                <h2><i class="bi bi-capsule-pill"></i> Gestión de Medicamentos</h2>
                <nav aria-label="breadcrumb">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="../dashboard">Inicio</a></li>
                        <li class="breadcrumb-item active">Medicamentos</li>
                    </ol>
                </nav>
            </div>
            <a href="MedicamentosController?accion=nuevo" class="btn btn-primary">
                <i class="bi bi-plus-circle"></i> Nuevo Medicamento
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
                    <input type="text" class="form-control" id="searchInput" placeholder="Buscar medicamento...">
                </div>
            </div>
            <div class="col-md-3">
                <select class="form-select" id="filterStock">
                    <option value="">Todos los stocks</option>
                    <option value="bajo">Stock bajo (< 10)</option>
                    <option value="medio">Stock medio (10-50)</option>
                    <option value="alto">Stock alto (> 50)</option>
                </select>
            </div>
        </div>

        <div class="row g-4" id="medicamentosContainer">
            <c:forEach var="medicamento" items="${medicamentos}">
                <div class="col-md-6 col-lg-4 medicamento-item" data-stock="${medicamento.stock}">
                    <div class="card medicamento-card h-100 shadow-sm">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-start mb-3">
                                <h5 class="card-title mb-0">${medicamento.nombre}</h5>
                                <c:choose>
                                    <c:when test="${medicamento.stock < 10}">
                                        <span class="badge stock-badge stock-bajo">
                                            <i class="bi bi-exclamation-triangle"></i> Bajo
                                        </span>
                                    </c:when>
                                    <c:when test="${medicamento.stock <= 50}">
                                        <span class="badge stock-badge stock-medio">
                                            <i class="bi bi-dash-circle"></i> Medio
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge stock-badge stock-alto">
                                            <i class="bi bi-check-circle"></i> Alto
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            
                            <p class="text-muted mb-3">${medicamento.descripcion}</p>
                            
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <div>
                                    <small class="text-muted d-block">Precio</small>
                                    <span class="precio-tag">$${medicamento.precio}</span>
                                </div>
                                <div class="text-end">
                                    <small class="text-muted d-block">Stock disponible</small>
                                    <span class="fs-4 fw-bold">${medicamento.stock}</span>
                                </div>
                            </div>

                            <div class="mb-3">
                                <small class="text-muted"><i class="bi bi-box-seam"></i> Presentación:</small>
                                <p class="mb-0">${medicamento.presentacion}</p>
                            </div>

                            <c:if test="${not empty medicamento.laboratorio}">
                                <div class="mb-3">
                                    <small class="text-muted"><i class="bi bi-building"></i> Laboratorio:</small>
                                    <p class="mb-0">${medicamento.laboratorio}</p>
                                </div>
                            </c:if>
                            
                            <div class="d-flex gap-2 mt-3">
                                <a href="MedicamentosController?accion=editar&id=${medicamento.id}" class="btn btn-sm btn-outline-primary flex-grow-1">
                                    <i class="bi bi-pencil"></i> Editar
                                </a>
                                <button onclick="confirmarEliminar(${medicamento.id})" class="btn btn-sm btn-outline-danger">
                                    <i class="bi bi-trash"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <c:if test="${empty medicamentos}">
            <div class="text-center py-5">
                <i class="bi bi-inbox" style="font-size: 4rem; color: #ccc;"></i>
                <p class="text-muted mt-3">No hay medicamentos registrados</p>
                <a href="MedicamentosController?accion=nuevo" class="btn btn-primary">
                    <i class="bi bi-plus-circle"></i> Registrar Primer Medicamento
                </a>
            </div>
        </c:if>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function confirmarEliminar(id) {
            if (confirm('¿Está seguro de eliminar este medicamento? Esta acción no se puede deshacer.')) {
                window.location.href = 'MedicamentosController?accion=eliminar&id=' + id;
            }
        }

        // Búsqueda y filtrado
        document.getElementById('searchInput').addEventListener('input', filtrarMedicamentos);
        document.getElementById('filterStock').addEventListener('change', filtrarMedicamentos);

        function filtrarMedicamentos() {
            const searchTerm = document.getElementById('searchInput').value.toLowerCase();
            const stockFilter = document.getElementById('filterStock').value;
            const items = document.querySelectorAll('.medicamento-item');

            items.forEach(item => {
                const text = item.textContent.toLowerCase();
                const stock = parseInt(item.dataset.stock);
                const matchSearch = text.includes(searchTerm);
                
                let matchStock = true;
                if (stockFilter === 'bajo') matchStock = stock < 10;
                else if (stockFilter === 'medio') matchStock = stock >= 10 && stock <= 50;
                else if (stockFilter === 'alto') matchStock = stock > 50;

                item.style.display = matchSearch && matchStock ? '' : 'none';
            });
        }
    </script>
</body>
</html>
