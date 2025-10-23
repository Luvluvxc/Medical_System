<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nuevo Medicamento - Sistema Médico</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
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
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="mb-4">
                    <h2><i class="bi bi-capsule-pill"></i> Registrar Nuevo Medicamento</h2>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="../dashboard">Inicio</a></li>
                            <li class="breadcrumb-item"><a href="MedicamentosController?accion=listar">Medicamentos</a></li>
                            <li class="breadcrumb-item active">Nuevo Medicamento</li>
                        </ol>
                    </nav>
                </div>

                <c:if test="${not empty mensaje}">
                    <div class="alert alert-success alert-dismissible fade show">
                        <i class="bi bi-check-circle"></i> ${mensaje}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show">
                        <i class="bi bi-exclamation-triangle"></i> ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <div class="card shadow-sm">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0">Datos del Medicamento</h5>
                    </div>
                    <div class="card-body">
                        <form action="MedicamentosController" method="post">
                            <input type="hidden" name="accion" value="registrar">

                            <div class="mb-3">
                                <label class="form-label">Nombre del Medicamento <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" name="nombre" required placeholder="Ej: Paracetamol">
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Descripción <span class="text-danger">*</span></label>
                                <textarea class="form-control" name="descripcion" rows="3" required placeholder="Descripción del medicamento, indicaciones generales..."></textarea>
                            </div>

                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label class="form-label">Presentación <span class="text-danger">*</span></label>
                                    <select class="form-select" name="presentacion" required>
                                        <option value="">Seleccione...</option>
                                        <option value="Tabletas">Tabletas</option>
                                        <option value="Cápsulas">Cápsulas</option>
                                        <option value="Jarabe">Jarabe</option>
                                        <option value="Suspensión">Suspensión</option>
                                        <option value="Inyectable">Inyectable</option>
                                        <option value="Crema">Crema</option>
                                        <option value="Ungüento">Ungüento</option>
                                        <option value="Gotas">Gotas</option>
                                        <option value="Spray">Spray</option>
                                    </select>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Laboratorio</label>
                                    <input type="text" class="form-control" name="laboratorio" placeholder="Ej: Bayer, Pfizer...">
                                </div>
                            </div>

                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label class="form-label">Precio <span class="text-danger">*</span></label>
                                    <div class="input-group">
                                        <span class="input-group-text">$</span>
                                        <input type="number" class="form-control" name="precio" step="0.01" min="0" required>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Stock Inicial <span class="text-danger">*</span></label>
                                    <input type="number" class="form-control" name="stock" min="0" required>
                                    <small class="text-muted">Cantidad disponible en inventario</small>
                                </div>
                            </div>

                            <div class="alert alert-info">
                                <i class="bi bi-info-circle"></i> <strong>Nota:</strong> Asegúrese de verificar toda la información antes de registrar el medicamento.
                            </div>

                            <div class="d-flex justify-content-end gap-2 mt-4">
                                <a href="MedicamentosController?accion=listar" class="btn btn-secondary">
                                    <i class="bi bi-x-circle"></i> Cancelar
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="bi bi-save"></i> Registrar Medicamento
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
