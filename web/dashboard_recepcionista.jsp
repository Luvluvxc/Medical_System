<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Recepcionista - Sistema Médico</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        .stat-card {
            border-left: 4px solid;
            transition: transform 0.2s;
        }
        .stat-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
        .stat-card.primary { border-left-color: #0d6efd; }
        .stat-card.success { border-left-color: #198754; }
        .stat-card.warning { border-left-color: #ffc107; }
        .stat-card.danger { border-left-color: #dc3545; }
        .quick-action {
            height: 120px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 10px;
            transition: all 0.3s;
            cursor: pointer;
        }
        .quick-action:hover {
            transform: scale(1.05);
            box-shadow: 0 5px 20px rgba(0,0,0,0.15);
        }
    </style>
</head>
<body class="bg-light">
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">
                <i class="bi bi-hospital"></i> Sistema Médico
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="dashboard_recepcionista.jsp">
                            <i class="bi bi-house-door"></i> Inicio
                        </a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                            <i class="bi bi-person-circle"></i> ${usuario.nombre}
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="#"><i class="bi bi-gear"></i> Configuración</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="Validar?accion=salir"><i class="bi bi-box-arrow-right"></i> Cerrar Sesión</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container-fluid mt-4">
        <div class="row">
            <!-- Sidebar -->
            <div class="col-md-2 bg-white shadow-sm" style="min-height: 90vh;">
                <div class="list-group list-group-flush mt-3">
                    <a href="dashboard_recepcionista.jsp" class="list-group-item list-group-item-action active">
                        <i class="bi bi-speedometer2"></i> Dashboard
                    </a>
                    <a href="PacientesController?accion=listar" class="list-group-item list-group-item-action">
                        <i class="bi bi-people"></i> Pacientes
                    </a>
                    <a href="CitasController?accion=listar" class="list-group-item list-group-item-action">
                        <i class="bi bi-calendar-check"></i> Citas
                    </a>
                    <a href="DoctoresController?accion=listar" class="list-group-item list-group-item-action">
                        <i class="bi bi-person-badge"></i> Doctores
                    </a>
                    <a href="MedicamentosController?accion=listar" class="list-group-item list-group-item-action">
                        <i class="bi bi-capsule"></i> Medicamentos
                    </a>
                </div>
            </div>

            <!-- Main Content -->
            <div class="col-md-10">
                <!-- Header -->
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <div>
                        <h2 class="mb-0">Bienvenido, ${usuario.nombre}</h2>
                        <p class="text-muted">Panel de Recepción</p>
                    </div>
                    <div>
                        <span class="badge bg-primary fs-6">
                            <i class="bi bi-calendar3"></i> 
                            <%= new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()) %>
                        </span>
                    </div>
                </div>

                <!-- Estadísticas -->
                <div class="row mb-4">
                    <div class="col-md-3">
                        <div class="card stat-card primary">
                            <div class="card-body">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <h6 class="text-muted mb-2">Citas Hoy</h6>
                                    </div>
                                    <div class="text-primary">
                                        <i class="bi bi-calendar-check" style="font-size: 3rem;"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card stat-card success">
                            <div class="card-body">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <h6 class="text-muted mb-2">Pacientes Nuevos</h6>
                                        <h2 class="mb-0">8</h2>
                                    </div>
                                    <div class="text-success">
                                        <i class="bi bi-person-plus" style="font-size: 3rem;"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card stat-card warning">
                            <div class="card-body">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <h6 class="text-muted mb-2">Citas Pendientes</h6>
                                        <h2 class="mb-0">12</h2>
                                    </div>
                                    <div class="text-warning">
                                        <i class="bi bi-clock-history" style="font-size: 3rem;"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card stat-card danger">
                            <div class="card-body">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <h6 class="text-muted mb-2">Stock Bajo</h6>
                                        <h2 class="mb-0">5</h2>
                                    </div>
                                    <div class="text-danger">
                                        <i class="bi bi-exclamation-triangle" style="font-size: 3rem;"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Acciones Rápidas -->
                <div class="row mb-4">
                    <div class="col-12">
                        <h4 class="mb-3">Acciones Rápidas</h4>
                    </div>
                    <div class="col-md-3">
                        <div class="quick-action bg-primary text-white" onclick="location.href='UsuariosController?accion=nuevo'">
                            <div class="text-center">
                                <i class="bi bi-person-plus-fill" style="font-size: 2.5rem;"></i>
                                <h5 class="mt-2">Nuevo Paciente</h5>
                                <small>Crear usuario y paciente</small>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="quick-action bg-success text-white" onclick="location.href='CitasController?accion=nueva'">
                            <div class="text-center">
                                <i class="bi bi-calendar-plus-fill" style="font-size: 2.5rem;"></i>
                                <h5 class="mt-2">Agendar Cita</h5>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="quick-action bg-info text-white" onclick="location.href='PacientesController?accion=listar'">
                            <div class="text-center">
                                <i class="bi bi-search" style="font-size: 2.5rem;"></i>
                                <h5 class="mt-2">Buscar Paciente</h5>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="quick-action bg-warning text-white" onclick="location.href='MedicamentosController?accion=stockBajo'">
                            <div class="text-center">
                                <i class="bi bi-capsule" style="font-size: 2.5rem;"></i>
                                <h5 class="mt-2">Inventario</h5>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Citas de Hoy -->
                <div class="row">
                    <div class="col-md-8">
                        <div class="card shadow-sm">
                            <div class="card-header bg-white">
                                <h5 class="mb-0"><i class="bi bi-calendar-day"></i> Citas de Hoy</h5>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                            <tr>
                                                <th>Hora</th>
                                                <th>Paciente</th>
                                                <th>Doctor</th>
                                                <th>Estado</th>
                                                <th>Acciones</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td><strong>09:00</strong></td>
                                                <td>Juan Pérez</td>
                                                <td>Dr. García</td>
                                                <td><span class="badge bg-warning">Pendiente</span></td>
                                                <td>
                                                    <button class="btn btn-sm btn-primary">
                                                        <i class="bi bi-eye"></i>
                                                    </button>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><strong>10:00</strong></td>
                                                <td>María López</td>
                                                <td>Dr. Martínez</td>
                                                <td><span class="badge bg-success">Completada</span></td>
                                                <td>
                                                    <button class="btn btn-sm btn-primary">
                                                        <i class="bi bi-eye"></i>
                                                    </button>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><strong>11:00</strong></td>
                                                <td>Carlos Ruiz</td>
                                                <td>Dr. García</td>
                                                <td><span class="badge bg-info">En Curso</span></td>
                                                <td>
                                                    <button class="btn btn-sm btn-primary">
                                                        <i class="bi bi-eye"></i>
                                                    </button>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Alertas -->
                    <div class="col-md-4">
                        <div class="card shadow-sm">
                            <div class="card-header bg-white">
                                <h5 class="mb-0"><i class="bi bi-bell"></i> Alertas</h5>
                            </div>
                            <div class="card-body">
                                <div class="alert alert-warning" role="alert">
                                    <i class="bi bi-exclamation-triangle-fill"></i>
                                    <strong>5 medicamentos</strong> con stock bajo
                                </div>
                                <div class="alert alert-danger" role="alert">
                                    <i class="bi bi-calendar-x"></i>
                                    <strong>3 medicamentos</strong> próximos a vencer
                                </div>
                                <div class="alert alert-info" role="alert">
                                    <i class="bi bi-info-circle-fill"></i>
                                    <strong>2 citas</strong> requieren confirmación
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>