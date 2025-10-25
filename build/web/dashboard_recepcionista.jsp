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
            --primary-dark: #1e40af;
            --primary-light: #3b82f6;
            --secondary-color: #0ea5e9;
            --accent-color: #06b6d4;
            --light-bg: #f0f9ff;
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
            background-color: #eff6ff;
            color: var(--primary-color);
            border-left-color: var(--primary-color);
        }

        .sidebar .nav-link.active {
            background-color: #dbeafe;
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

        .action-card.secondary {
            background: linear-gradient(135deg, #0ea5e9 0%, #0284c7 100%);
            color: white;
        }

        .action-card.accent {
            background: linear-gradient(135deg, #06b6d4 0%, #0891b2 100%);
            color: white;
        }

        .action-card.info {
            background: linear-gradient(135deg, #6366f1 0%, #4f46e5 100%);
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
            background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-dark) 100%);
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

        .search-card {
            background: white;
            border-radius: 12px;
            padding: 1.5rem;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            margin-bottom: 2rem;
        }

        .search-card .form-control {
            border-radius: 8px;
            border: 2px solid #e2e8f0;
            padding: 0.75rem 1rem;
        }

        .search-card .form-control:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 0.2rem rgba(37, 99, 235, 0.25);
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
            <div class="col-md-2 sidebar">
                <nav class="nav flex-column">
                    <a class="nav-link active" href="dashboard_recepcionista.jsp">
                        <i class="bi bi-speedometer2"></i> Dashboard
                    </a>
                    <a class="nav-link" href="${pageContext.request.contextPath}/UsuariosController?accion=listar">
                        <i class="bi bi-people"></i> Usuarios
                    </a>
                    <a class="nav-link" href="${pageContext.request.contextPath}/CitasController?accion=listar">
                        <i class="bi bi-calendar-check"></i> Citas
                    </a>
                    <a class="nav-link" href="${pageContext.request.contextPath}/ConsultasController?accion=listarCerradas">
                        <i class="bi bi-file-medical"></i> Consultas Cerradas
                    </a>
                </nav>
            </div>

            <div class="col-md-10 p-4">
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

                <!-- Added search by patient code -->
                <div class="search-card">
                    <h5 class="mb-3"><i class="bi bi-search"></i> Buscar por Código de Paciente</h5>
                    <form action="${pageContext.request.contextPath}/PacientesController" method="get" class="row g-3">
                        <input type="hidden" name="accion" value="buscarPorCodigo">
                        <div class="col-md-8">
                            <input type="text" class="form-control" name="codigo" placeholder="Ingrese el código del paciente" required>
                        </div>
                        <div class="col-md-4">
                            <button type="submit" class="btn btn-primary btn-custom w-100">
                                <i class="bi bi-search"></i> Buscar
                            </button>
                        </div>
                    </form>
                </div>
                
                <h3 class="section-title">Acciones Principales</h3>
                <div class="row mb-5">
                    <div class="col-md-3 mb-3">
                        <div class="card action-card primary" onclick="location.href='${pageContext.request.contextPath}/UsuariosController?accion=listar'">
                            <div class="card-body">
                                <i class="bi bi-people-fill"></i>
                                <h4 class="fw-bold">Usuarios</h4>
                                <p class="mb-0">Gestionar usuarios del sistema</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 mb-3">
                        <div class="card action-card secondary" onclick="location.href='${pageContext.request.contextPath}/CitasController?accion=listar'">
                            <div class="card-body">
                                <i class="bi bi-calendar-check-fill"></i>
                                <h4 class="fw-bold">Citas</h4>
                                <p class="mb-0">Ver y gestionar citas médicas</p>
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
