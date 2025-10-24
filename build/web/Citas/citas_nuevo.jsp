<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nueva Cita - Sistema Médico</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        :root {
            --primary-color: #2563eb;
            --primary-dark: #1e40af;
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

        .form-card {
            background: white;
            border-radius: 12px;
            box-shadow: 0 2px 15px rgba(0,0,0,0.1);
            padding: 2rem;
            margin-top: 2rem;
        }

        .form-label {
            font-weight: 600;
            color: #334155;
            margin-bottom: 0.5rem;
        }

        .form-control, .form-select {
            border-radius: 8px;
            border: 2px solid #e2e8f0;
            padding: 0.75rem;
        }

        .form-control:focus, .form-select:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 0.2rem rgba(37, 99, 235, 0.25);
        }

        .btn-custom {
            padding: 0.75rem 2rem;
            border-radius: 8px;
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .btn-custom:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        }

        .section-title {
            color: var(--primary-color);
            font-weight: 600;
            margin-bottom: 1.5rem;
            padding-bottom: 0.75rem;
            border-bottom: 3px solid var(--primary-color);
        }

        .time-slot {
            padding: 0.5rem;
            margin: 0.25rem;
            border: 2px solid #e2e8f0;
            border-radius: 6px;
            cursor: pointer;
            transition: all 0.2s;
            text-align: center;
        }

        .time-slot:hover {
            border-color: var(--primary-color);
            background-color: #eff6ff;
        }

        .time-slot.occupied {
            background-color: #fee2e2;
            border-color: #ef4444;
            cursor: not-allowed;
            opacity: 0.6;
        }

        .time-slot.selected {
            background-color: #dbeafe;
            border-color: var(--primary-color);
            font-weight: 600;
        }

        #timeSlotsContainer {
            display: none;
            margin-top: 1rem;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container-fluid">
            <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/dashboard_recepcionista.jsp">
                <i class="bi bi-hospital-fill"></i> Sistema Médico
            </a>
        </div>
    </nav>

    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="form-card">
                    <h2 class="section-title">
                        <i class="bi bi-calendar-plus"></i> Registrar Nueva Cita
                    </h2>

                    <form action="${pageContext.request.contextPath}/CitasController" method="post" id="citaForm">
                        <input type="hidden" name="accion" value="registrar">
                        <input type="hidden" name="pacienteId" value="${paciente.id}">

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label">
                                    <i class="bi bi-person"></i> Paciente
                                </label>
                                <input type="text" class="form-control" value="${paciente.usuarioNombre} ${paciente.usuarioApellido}" readonly>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">
                                    <i class="bi bi-credit-card"></i> Código Paciente
                                </label>
                                <input type="text" class="form-control" value="${paciente.codigoPaciente}" readonly>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="doctorId" class="form-label">
                                <i class="bi bi-person-badge"></i> Doctor *
                            </label>
                            <select class="form-select" id="doctorId" name="doctorId" required>
                                <option value="">Seleccione un doctor</option>
                                <c:forEach var="doctor" items="${doctores}">
                                    <option value="${doctor.id}">
                                        Dr. ${doctor.usuarioNombre} ${doctor.usuarioApellido} - ${doctor.especializacion}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="fechaCita" class="form-label">
                                    <i class="bi bi-calendar"></i> Fecha de la Cita *
                                </label>
                                <input type="date" class="form-control" id="fechaCita" name="fechaCita" required min="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                            </div>
                            <div class="col-md-6">
                                <label for="horaCita" class="form-label">
                                    <i class="bi bi-clock"></i> Hora de la Cita *
                                </label>
                                <input type="time" class="form-control" id="horaCita" name="horaCita" required>
                            </div>
                        </div>

                        <!-- Added time slots visualization -->
                        <div id="timeSlotsContainer" class="mb-3">
                            <label class="form-label">
                                <i class="bi bi-calendar-week"></i> Horarios Disponibles
                            </label>
                            <div id="timeSlots" class="row"></div>
                            <small class="text-muted">Haga clic en un horario disponible o ingrese uno manualmente</small>
                        </div>

                        <div class="mb-3">
                            <label for="motivo" class="form-label">
                                <i class="bi bi-chat-left-text"></i> Motivo de la Consulta *
                            </label>
                            <textarea class="form-control" id="motivo" name="motivo" rows="3" required placeholder="Describa el motivo de la consulta"></textarea>
                        </div>

                        <div class="alert alert-info">
                            <i class="bi bi-info-circle"></i> Todos los campos marcados con * son obligatorios
                        </div>

                        <div class="d-flex justify-content-between mt-4">
                            <a href="${pageContext.request.contextPath}/UsuariosController?accion=listar" class="btn btn-secondary btn-custom">
                                <i class="bi bi-arrow-left"></i> Regresar
                            </a>
                            <button type="submit" class="btn btn-primary btn-custom">
                                <i class="bi bi-check-circle"></i> Registrar Cita
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        const form = document.getElementById('citaForm');
        const doctorSelect = document.getElementById('doctorId');
        const fechaInput = document.getElementById('fechaCita');
        const horaInput = document.getElementById('horaCita');
        const timeSlotsContainer = document.getElementById('timeSlotsContainer');
        const timeSlotsDiv = document.getElementById('timeSlots');

        // Generate time slots from 8:00 to 18:00
        function generateTimeSlots() {
            const slots = [];
            for (let hour = 8; hour < 18; hour++) {
                slots.push(`${hour.toString().padStart(2, '0')}:00`);
                slots.push(`${hour.toString().padStart(2, '0')}:30`);
            }
            return slots;
        }

        // Load occupied time slots
        async function loadOccupiedSlots() {
            const doctorId = doctorSelect.value;
            const fecha = fechaInput.value;

            if (!doctorId || !fecha) {
                timeSlotsContainer.style.display = 'none';
                return;
            }

            try {
                const response = await fetch(`${pageContext.request.contextPath}/CitasController?accion=verificarDisponibilidad&doctorId=${doctorId}&fecha=${fecha}`);
                const occupiedSlots = await response.json();

                displayTimeSlots(occupiedSlots);
                timeSlotsContainer.style.display = 'block';
            } catch (error) {
                console.error('[v0] Error loading time slots:', error);
            }
        }

        function displayTimeSlots(occupiedSlots) {
            const allSlots = generateTimeSlots();
            timeSlotsDiv.innerHTML = '';

            allSlots.forEach(slot => {
                const isOccupied = occupiedSlots.includes(slot);
                const slotDiv = document.createElement('div');
                slotDiv.className = `col-2 time-slot ${isOccupied ? 'occupied' : ''}`;
                slotDiv.textContent = slot;

                if (!isOccupied) {
                    slotDiv.onclick = () => {
                        document.querySelectorAll('.time-slot').forEach(s => s.classList.remove('selected'));
                        slotDiv.classList.add('selected');
                        horaInput.value = slot;
                    };
                }

                timeSlotsDiv.appendChild(slotDiv);
            });
        }

        doctorSelect.addEventListener('change', loadOccupiedSlots);
        fechaInput.addEventListener('change', loadOccupiedSlots);

        form.addEventListener('submit', async function(e) {
            e.preventDefault();

            const doctorId = doctorSelect.value;
            const fecha = fechaInput.value;
            const hora = horaInput.value;

            // Validate time slot availability
            try {
                const response = await fetch(`${pageContext.request.contextPath}/CitasController?accion=verificarDisponibilidad&doctorId=${doctorId}&fecha=${fecha}`);
                const occupiedSlots = await response.json();

                if (occupiedSlots.includes(hora)) {
                    alert('Lo siento, ese horario ya está ocupado. Por favor seleccione otro horario.');
                    return;
                }

                form.submit();
            } catch (error) {
                console.error('[v0] Error validating appointment:', error);
                form.submit();
            }
        });
    </script>
</body>
</html>
