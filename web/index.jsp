<%-- 
    Document   : index
    Created on : 3/10/2025, 10:43:26 p. m.
    Author     : marli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login - Sistema</title>


        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

        <style>
            body {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                min-height: 100vh;
                display: flex;
                align-items: center;
                justify-content: center;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }

            .login-container {
                max-width: 450px;
                width: 100%;
                padding: 15px;
            }

            .login-card {
                border: none;
                border-radius: 20px;
                box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
                overflow: hidden;
                animation: fadeInUp 0.6s ease-out;
            }

            @keyframes fadeInUp {
                from {
                    opacity: 0;
                    transform: translateY(30px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            .card-header-custom {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                padding: 2.5rem 2rem;
                text-align: center;
                color: white;
            }

            .card-header-custom h3 {
                margin: 0;
                font-weight: 700;
                font-size: 1.8rem;
                margin-bottom: 0.5rem;
            }

            .card-header-custom p {
                margin: 0;
                opacity: 0.9;
                font-size: 0.95rem;
            }

            .logo-container {
                width: 100px;
                height: 100px;
                margin: 0 auto 1rem;
                background: white;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            }

            .logo-container img {
                max-width: 70px;
                max-height: 70px;
                object-fit: contain;
            }

            .logo-container i {
                font-size: 3rem;
                color: #667eea;
            }

            .card-body-custom {
                padding: 2.5rem 2rem;
            }

            .form-label {
                font-weight: 600;
                color: #495057;
                margin-bottom: 0.5rem;
                font-size: 0.9rem;
            }

            .input-group {
                margin-bottom: 1.5rem;
            }

            .input-group-text {
                background-color: #f8f9fa;
                border-right: none;
                color: #667eea;
                font-size: 1.1rem;
            }

            .form-control {
                border-left: none;
                padding: 0.75rem 1rem;
                font-size: 0.95rem;
                transition: all 0.3s ease;
            }

            .form-control:focus {
                box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
                border-color: #667eea;
            }

            .form-control:focus + .input-group-text {
                border-color: #667eea;
            }

            .btn-login {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                border: none;
                padding: 0.75rem;
                font-weight: 600;
                font-size: 1rem;
                border-radius: 10px;
                transition: all 0.3s ease;
                text-transform: uppercase;
                letter-spacing: 0.5px;
            }

            .btn-login:hover {
                transform: translateY(-2px);
                box-shadow: 0 5px 20px rgba(102, 126, 234, 0.4);
                background: linear-gradient(135deg, #764ba2 0%, #667eea 100%);
            }

            .btn-login:active {
                transform: translateY(0);
            }

            .forgot-password {
                text-align: center;
                margin-top: 1.5rem;
            }

            .forgot-password a {
                color: #667eea;
                text-decoration: none;
                font-size: 0.9rem;
                transition: color 0.3s ease;
            }

            .forgot-password a:hover {
                color: #764ba2;
                text-decoration: underline;
            }

            .divider {
                display: flex;
                align-items: center;
                text-align: center;
                margin: 1.5rem 0;
                color: #6c757d;
                font-size: 0.85rem;
            }

            .divider::before,
            .divider::after {
                content: '';
                flex: 1;
                border-bottom: 1px solid #dee2e6;
            }

            .divider span {
                padding: 0 1rem;
            }

            @media (max-width: 576px) {
                .login-container {
                    padding: 10px;
                }

                .card-body-custom {
                    padding: 2rem 1.5rem;
                }

                .card-header-custom {
                    padding: 2rem 1.5rem;
                }
            }
        </style>
    </head>
    <body>
        <div class="login-container">
            <div class="card login-card">
                <div class="card-header-custom">
                    <div class="logo-container">
                        <i class="bi bi-shield-lock"></i>
                    </div>
                    <h3>Bienvenido</h3>
                    <p>Ingresa tus credenciales para continuar</p>
                </div>

                <div class="card-body-custom">
                    <form class="form-sign" action="Validar" method="POST">
                        <div class="mb-3">
                            <label for="txtNombre" class="form-label">
                                <i class="bi bi-person-circle me-1"></i>Correo
                            </label>
                            <div class="input-group">
                                <span class="input-group-text">
                                    <i class="bi bi-person"></i>
                                </span>
                                <input 
                                    type="text" 
                                    name="txtCorreo" 
                                    id="txtCorreo"
                                    class="form-control" 
                                    placeholder="Ingresa tu Correo"
                                    required
                                    autocomplete="username">
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="txtPassword" class="form-label">
                                <i class="bi bi-lock-fill me-1"></i>Contraseña
                            </label>
                            <div class="input-group">
                                <span class="input-group-text">
                                    <i class="bi bi-key"></i>
                                </span>
                                <input 
                                    type="password" 
                                    name="txtPassword" 
                                    id="txtPassword"
                                    class="form-control" 
                                    placeholder="Ingresa tu contraseña"
                                    required
                                    autocomplete="current-password">
                            </div>
                        </div>

                        

                        <button type="submit" name="accion" value="Ingresar" class="btn btn-primary btn-login w-100">
                            <i class="bi bi-box-arrow-in-right me-2"></i>Ingresar al Sistema
                        </button>


                    </form>
                </div>
            </div>

            <div class="text-center mt-3">
                <small class="text-white">© 2025</small>
            </div>
        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
