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
        :root {
            --primary-color: #2563eb;
            --secondary-color: #10b981;
            --accent-color: #8b5cf6;
            --danger-color: #ef4444;
            --light-bg: #f8fafc;
        }

        body {
            background-color: var(--light-bg);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .navbar {
            background: linear-gradient(135deg, var(--primary-color) 0%, #1e40af 100%);
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
            background-color: #f1f5f9;
            color: var(--primary-color);
            border-left-color: var(--primary-color);
        }

        .sidebar .nav-link.active {
            background-color: #eff6ff;
            color: var(--primary-color);
            border-left-color: var(--primary-color);
        }

        .action-card {
            border: none;
            border-radius: 12px;
            transition: all 0.3s ease;
            cursor: pointer;
            overflow: hidden;
            height: 100%;
        }

        .action-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 25px rgba(0,0,0,0.15);
        }

        .action-card .card-body {
            padding: 2rem;
            text-align: center;
        }

        .action-card i {
            font-size: 3rem;
            margin-bottom: 1rem;
        }

        .action-card.primary {
            background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
            color: white;
        }

        .action-card.success {
            background: linear-gradient(135deg, #10b981 0%, #059669 100%);
            color: white;
        }

        .action-card.purple {
            background: linear-gradient(135deg, #8b5cf6 0%, #7c3aed 100%);
            color: white;
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

        .welcome-card {
            background: linear-gradient(135deg, var(--primary-color) 0%, #1e40af 100%);
            color: white;
            border-radius: 12px;
            padding: 2rem;
            margin-bottom: 2rem;
            box-shadow: 0 4px 15px rgba(37, 99, 235, 0.3);
        }

        .welcome-card h2 {
            font-weight: 600;
            margin-bottom: 0.5rem;
        }

        .welcome-card p {
            opacity: 0.9;
            margin-bottom: 0;
        }

        .stats-card {
            background: white;
            border-radius: 12px;
            padding: 1.5rem;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            border-left: 4px solid;
            transition: all 0.3s ease;
        }

        .stats-card:hover {
            box-shadow: 0 4px 15px rgba(0,0,0,0.12);
            transform: translateX(5px);
        }

        .stats-card.blue { border-left-color: #3b82f6; }
        .stats-card.green { border-left-color: #10b981; }
        .stats-card.purple { border-left-color: #8b5cf6; }

        .stats-card .icon {
            width: 50px;
            height: 50px;
            border-radius: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.5rem;
        }

        .stats-card.blue .icon {
            background-color: #dbeafe;
            color: #3b82f6;
        }

        .stats-card.green .icon {
            background-color: #d1fae5;
            color: #10b981;
        }

        .stats-card.purple .icon {
            background-color: #ede9fe;
            color: #8b5cf6;
        }

        .btn-custom {
            padding: 0.75rem 1.5rem;
            border-radius: 8px;
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .btn-custom:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        }
    </style>
</head>
<body>
    <!-- Navbar -->
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
                            <i class="bi bi-person-circle"></i> ${usuario.nombre}
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
            <!-- Sidebar -->
            <div class="col-md-2 sidebar">
                <nav class="nav flex-column">
                    <a class="nav-link active" href="dashboard_recepcionista.jsp">
                        <i class="bi bi-speedometer2"></i> Dashboard
                    </a>
                    <a class="nav-link" href="${pageContext.request.contextPath}/UsuariosController?accion=listar">
                        <i class="bi bi-person-plus"></i> Crear Usuarios
                    </a>
                    <a class="nav-link" href="${pageContext.request.contextPath}/PacientesController?accion=listar">
                        <i class="bi bi-people"></i> Ver Pacientes
                    </a>
                    <a class="nav-link" href="${pageContext.request.contextPath}/DoctoresController?accion=listar">
                        <i class="bi bi-person-badge"></i> Ver Doctores
                    </a>
                    <a class="nav-link" href="${pageContext.request.contextPath}/CitasController?accion=listar">
                        <i class="bi bi-calendar-check"></i> Todas las Citas
                    </a>
                </nav>
            </div>

            <!-- Main Content -->
            <div class="col-md-10 p-4">
                <!-- Welcome Section -->
                <div class="welcome-card">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h2>Bienvenido, ${usuario.nombre}</h2>
                            <p>Panel de Recepción - Sistema de Gestión Médica</p>
                        </div>
                        <div>
                            <span class="badge bg-white text-primary fs-6 px-3 py-2">
                                <i class="bi bi-calendar3"></i> 
                                <%= new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()) %>
                            </span>
                        </div>
                    </div>
                </div>
                
                <!-- Main Actions -->
                <h3 class="section-title">Acciones Principales</h3>
                <div class="row mb-5">
                    <div class="col-md-4 mb-3">
                        <div class="card action-card primary" onclick="location.href='${pageContext.request.contextPath}/UsuariosController?accion=listar'">
                            <div class="card-body">
                                <i class="bi bi-person-plus-fill"></i>
                                <h4 class="fw-bold">Usuarios</h4>
                                <p class="mb-0">Registrar nuevo usuario en el sistema</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4 mb-3">
                        <div class="card action-card success" onclick="location.href='${pageContext.request.contextPath}/PacientesController?accion=listar'">
                            <div class="card-body">
                                <i class="bi bi-people-fill"></i>
                                <h4 class="fw-bold">Ver Pacientes</h4>
                                <p class="mb-0">Gestionar pacientes y programar citas</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4 mb-3">
                        <div class="card action-card purple" onclick="location.href='${pageContext.request.contextPath}/DoctoresController?accion=listar'">
                            <div class="card-body">
                                <i class="bi bi-person-badge-fill"></i>
                                <h4 class="fw-bold">Ver Doctores</h4>
                                <p class="mb-0">Consultar información de doctores</p>
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
