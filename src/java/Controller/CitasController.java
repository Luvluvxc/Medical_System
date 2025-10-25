package Controller;

import DAO.CitasDAO;
import DAO.PacientesDAO;
import DAO.DoctoresDAO;
import Model.CitasModel;
import Model.PacientesModel;
import Model.DoctoresModel;
import Model.UsuariosResourse;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;

public class CitasController extends HttpServlet {

    CitasDAO dao = new CitasDAO();
    CitasModel cita = new CitasModel();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        System.out.println("[v0] processRequest - accion: " + accion);

        // Si no hay acción, redirigir a listar
        if (accion == null || accion.trim().isEmpty()) {
            System.out.println("[v0] Acción nula, redirigiendo a listar");
            listar(request, response);
            return;
        }

        try {
            switch (accion) {
                case "listar":
                    listar(request, response);
                    break;
                case "listarDoctor":
                    listarDoctor(request, response);
                    break;
                case "nuevo":
                    nuevo(request, response);
                    break;
                case "registrar":
                    registrar(request, response);
                    break;
                case "editar":
                    editar(request, response);
                    break;
                case "actualizar":
                    actualizar(request, response);
                    break;
                case "eliminar":
                    eliminar(request, response);
                    break;
                case "nuevoPaciente":
                    nuevoPaciente(request, response);
                    break;
                case "verificarDisponibilidad":
                    verificarDisponibilidad(request, response);
                    break;
                case "cancelar":
                    cancelar(request, response);
                    break;
                case "cancelarSimple":
                    cancelarSimple(request, response);
                    break;
                case "obtenerCitasDoctor":
                    obtenerCitasDoctor(request, response);
                    break;
                default:
                    System.out.println("[v0] Acción no reconocida: " + accion);
                    listar(request, response);
                    break;
            }
        } catch (Exception e) {
            System.out.println("[v0] ERROR en processRequest: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error interno del servidor: " + e.getMessage());
            listar(request, response);
        }
    }

    protected void cancelarSimple(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long citaId = Long.parseLong(request.getParameter("id"));

            System.out.println("[v0] CancelarSimple - ID: " + citaId);

            // Usar el método directo de cancelación
            int resultado = dao.CancelarDirecto(citaId, "Cancelación rápida desde lista");

            if (resultado > 0) {
                request.setAttribute("mensaje", "Cita cancelada exitosamente");
            } else {
                request.setAttribute("error", "No se pudo cancelar la cita");
            }

            listar(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cancelar cita: " + e.getMessage());
            listar(request, response);
        }
    }

// Método para obtener citas del doctor (puede usarse para AJAX)
    protected void obtenerCitasDoctor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Integer doctorId = (Integer) session.getAttribute("doctorId");

            if (doctorId == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Doctor no identificado");
                return;
            }

            List<CitasModel> todasLasCitas = dao.Listar();
            List<CitasModel> citasDelDoctor = new ArrayList<>();
            List<CitasModel> citasHoy = new ArrayList<>();
            List<CitasModel> proximasCitas = new ArrayList<>();

            LocalDate hoy = LocalDate.now();

            // Filtrar citas del doctor
            for (CitasModel cita : todasLasCitas) {
                if (cita.getDoctorId() == doctorId) {
                    citasDelDoctor.add(cita);

                    if (cita.getFechaCita().equals(hoy)) {
                        citasHoy.add(cita);
                    } else if (cita.getFechaCita().isAfter(hoy) && !cita.getEstado().equals("cancelada")) {
                        proximasCitas.add(cita);
                    }
                }
            }

            // Preparar respuesta JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            JSONObject jsonResponse = new JSONObject();
            JSONArray citasHoyJson = new JSONArray();
            JSONArray proximasCitasJson = new JSONArray();

            // Convertir citas de hoy a JSON
            for (CitasModel cita : citasHoy) {
                JSONObject citaJson = new JSONObject();
                citaJson.put("id", cita.getId());
                citaJson.put("pacienteNombre", cita.getPacienteNombre() + " " + cita.getPacienteApellido());
                citaJson.put("horaCita", cita.getHoraCita().toString());
                citaJson.put("motivo", cita.getMotivo());
                citaJson.put("estado", cita.getEstado());
                citasHoyJson.put(citaJson);
            }

            // Convertir próximas citas a JSON
            for (CitasModel cita : proximasCitas) {
                JSONObject citaJson = new JSONObject();
                citaJson.put("id", cita.getId());
                citaJson.put("pacienteNombre", cita.getPacienteNombre() + " " + cita.getPacienteApellido());
                citaJson.put("fechaCita", cita.getFechaCita().toString());
                citaJson.put("horaCita", cita.getHoraCita().toString());
                citaJson.put("motivo", cita.getMotivo());
                citaJson.put("estado", cita.getEstado());
                proximasCitasJson.put(citaJson);
            }

            jsonResponse.put("citasHoy", citasHoyJson);
            jsonResponse.put("proximasCitas", proximasCitasJson);
            jsonResponse.put("totalCitas", citasDelDoctor.size());

            response.getWriter().write(jsonResponse.toString());

        } catch (Exception e) {
            System.out.println("[v0] ERROR en obtenerCitasDoctor: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error al obtener citas\"}");
        }
    }

    protected void cancelar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String citaIdStr = request.getParameter("citaId");
            String motivoCancelacion = request.getParameter("motivoCancelacion");

            System.out.println("[v0] ===== INICIANDO CANCELACIÓN =====");
            System.out.println("[v0] citaId parameter: " + citaIdStr);
            System.out.println("[v0] motivoCancelacion parameter: " + motivoCancelacion);

            if (citaIdStr == null || citaIdStr.trim().isEmpty()) {
                System.out.println("[v0] ERROR: citaId es nulo o vacío");
                request.setAttribute("error", "ID de cita no proporcionado");
                listar(request, response);
                return;
            }

            long citaId = Long.parseLong(citaIdStr);

            if (motivoCancelacion == null || motivoCancelacion.trim().isEmpty()) {
                System.out.println("[v0] ERROR: motivoCancelacion es nulo o vacío");
                request.setAttribute("error", "El motivo de cancelación es obligatorio");
                listar(request, response);
                return;
            }

            System.out.println("[v0] Cancelando cita ID: " + citaId);
            System.out.println("[v0] Motivo de cancelación: " + motivoCancelacion);

            // Buscar la cita actual para verificar que existe
            CitasModel cita = dao.BuscarPorId(citaId);
            if (cita == null) {
                System.out.println("[v0] ERROR: Cita no encontrada en la base de datos");
                request.setAttribute("error", "Cita no encontrada");
                listar(request, response);
                return;
            }

            System.out.println("[v0] Cita encontrada - Estado actual: " + cita.getEstado());
            System.out.println("[v0] Motivo actual: " + cita.getMotivo());

            // Actualizar el objeto cita
            cita.setEstado("cancelada");

            // Construir el nuevo motivo
            String nuevoMotivo = cita.getMotivo() != null ? cita.getMotivo() : "";
            nuevoMotivo += " [CANCELADA - Motivo: " + motivoCancelacion + "]";
            cita.setMotivo(nuevoMotivo);

            System.out.println("[v0] Nuevo estado: " + cita.getEstado());
            System.out.println("[v0] Nuevo motivo: " + cita.getMotivo());

            // Llamar al DAO para actualizar - USAR MÉTODO DIRECTO
            System.out.println("[v0] Llamando a dao.ActualizarDirecto()...");
            int resultado = dao.ActualizarDirecto(cita);
            System.out.println("[v0] Resultado de actualización: " + resultado);

            if (resultado > 0) {
                System.out.println("[v0] Cita cancelada exitosamente");
                request.setAttribute("mensaje", "Cita cancelada exitosamente");
            } else {
                System.out.println("[v0] ERROR: No se pudo cancelar la cita - resultado 0");
                request.setAttribute("error", "No se pudo cancelar la cita. Intente nuevamente.");
            }

            System.out.println("[v0] ===== FIN CANCELACIÓN =====");

            listar(request, response);
        } catch (NumberFormatException e) {
            System.out.println("[v0] ERROR: Formato inválido para citaId");
            e.printStackTrace();
            request.setAttribute("error", "ID de cita inválido");
            listar(request, response);
        } catch (Exception e) {
            System.out.println("[v0] ERROR en cancelar: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al cancelar cita: " + e.getMessage());
            listar(request, response);
        }
    }

    protected void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener parámetros de filtro
            String pacienteIdParam = request.getParameter("pacienteId");
            String estadoParam = request.getParameter("estado");
            String fechaParam = request.getParameter("fecha");

            System.out.println("[v0] Filtros recibidos:");
            System.out.println("[v0]   - pacienteId: " + pacienteIdParam);
            System.out.println("[v0]   - estado: " + estadoParam);
            System.out.println("[v0]   - fecha: " + fechaParam);

            // Obtener todas las citas
            List<CitasModel> todasLasCitas = dao.Listar();
            List<CitasModel> citasFiltradas = new ArrayList<>();

            // Aplicar filtros
            for (CitasModel cita : todasLasCitas) {
                boolean coincide = true;

                // Filtro por paciente
                if (pacienteIdParam != null && !pacienteIdParam.isEmpty()) {
                    long pacienteId = Long.parseLong(pacienteIdParam);
                    if (cita.getPacienteId() != pacienteId) {
                        coincide = false;
                    }
                }

                // Filtro por estado
                if (coincide && estadoParam != null && !estadoParam.isEmpty()) {
                    if (!cita.getEstado().equalsIgnoreCase(estadoParam)) {
                        coincide = false;
                    }
                }

                // Filtro por fecha
                if (coincide && fechaParam != null && !fechaParam.isEmpty()) {
                    LocalDate fechaFiltro = LocalDate.parse(fechaParam);
                    if (!cita.getFechaCita().equals(fechaFiltro)) {
                        coincide = false;
                    }
                }

                if (coincide) {
                    citasFiltradas.add(cita);
                }
            }

            System.out.println("[v0] Citas encontradas: " + todasLasCitas.size());
            System.out.println("[v0] Citas después de filtro: " + citasFiltradas.size());

            // Obtener lista de pacientes para el dropdown
            PacientesDAO pacientesDao = new PacientesDAO();
            List<PacientesModel> pacientes = pacientesDao.Listar();

            request.setAttribute("citas", citasFiltradas);
            request.setAttribute("pacientes", pacientes);
            request.setAttribute("filtroPaciente", pacienteIdParam);
            request.setAttribute("filtroEstado", estadoParam);
            request.setAttribute("filtroFecha", fechaParam);

            request.getRequestDispatcher("Citas/citas_lista.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al listar citas: " + e.getMessage());
            request.getRequestDispatcher("Citas/citas_lista.jsp").forward(request, response);
        }
    }

    protected void verificarDisponibilidad(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int doctorId = Integer.parseInt(request.getParameter("doctorId"));
            String fecha = request.getParameter("fecha");

            // Aquí llamarías al DAO para verificar disponibilidad
            // Por ahora simulamos una respuesta
            JSONObject jsonResponse = new JSONObject();
            JSONArray ocupados = new JSONArray();

            // Simular algunos horarios ocupados (en producción, consultar BD)
            ocupados.put("09:00");
            ocupados.put("10:30");
            ocupados.put("14:00");

            jsonResponse.put("ocupados", ocupados);

            response.setContentType("application/json");
            response.getWriter().write(jsonResponse.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void nuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            PacientesDAO pacientesDao = new PacientesDAO();
            DoctoresDAO doctoresDao = new DoctoresDAO();

            // Obtener parámetros que puedan venir de un conflicto
            String pacienteIdParam = request.getParameter("pacienteId");
            String doctorIdParam = request.getParameter("doctorId");
            String fechaCitaParam = request.getParameter("fechaCita");
            String horaCitaParam = request.getParameter("horaCita");
            String motivoParam = request.getParameter("motivo");

            System.out.println("[v0] Cargando formulario nuevo con parámetros:");
            System.out.println("[v0]   - pacienteId: " + pacienteIdParam);
            System.out.println("[v0]   - doctorId: " + doctorIdParam);
            System.out.println("[v0]   - fechaCita: " + fechaCitaParam);
            System.out.println("[v0]   - horaCita: " + horaCitaParam);
            System.out.println("[v0]   - motivo: " + motivoParam);

            // Si hay parámetros de conflicto, establecerlos como atributos
            if (pacienteIdParam != null && !pacienteIdParam.isEmpty()) {
                request.setAttribute("pacienteId", pacienteIdParam);
            }
            if (doctorIdParam != null && !doctorIdParam.isEmpty()) {
                request.setAttribute("doctorId", doctorIdParam);
            }
            if (fechaCitaParam != null && !fechaCitaParam.isEmpty()) {
                request.setAttribute("fechaCita", fechaCitaParam);
            }
            if (horaCitaParam != null && !horaCitaParam.isEmpty()) {
                request.setAttribute("horaCita", horaCitaParam);
            }
            if (motivoParam != null && !motivoParam.isEmpty()) {
                request.setAttribute("motivo", motivoParam);
            }

            List<PacientesModel> pacientes = pacientesDao.Listar();
            List<DoctoresModel> doctores = doctoresDao.Listar();

            request.setAttribute("pacientes", pacientes);
            request.setAttribute("doctores", doctores);
            request.getRequestDispatcher("Citas/citas_nuevo.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar formulario: " + e.getMessage());
            listar(request, response);
        }
    }

    protected void registrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            System.out.println("[v0] === INICIANDO REGISTRO DE CITA ===");

            // Obtener parámetros
            String pacienteIdStr = request.getParameter("pacienteId");
            String doctorIdStr = request.getParameter("doctorId");
            String fechaCitaStr = request.getParameter("fechaCita");
            String horaCitaStr = request.getParameter("horaCita");
            String estado = request.getParameter("estado");
            String motivo = request.getParameter("motivo");

            System.out.println("[v0] Parámetros recibidos:");
            System.out.println("[v0]   - pacienteId: " + pacienteIdStr);
            System.out.println("[v0]   - doctorId: " + doctorIdStr);
            System.out.println("[v0]   - fechaCita: " + fechaCitaStr);
            System.out.println("[v0]   - horaCita: " + horaCitaStr);
            System.out.println("[v0]   - estado: " + estado);
            System.out.println("[v0]   - motivo: " + motivo);

            // Validaciones básicas
            if (pacienteIdStr == null || doctorIdStr == null || fechaCitaStr == null
                    || horaCitaStr == null || motivo == null) {
                request.setAttribute("error", "Todos los campos son obligatorios");

                // Mantener los datos que sí vinieron
                if (pacienteIdStr != null) {
                    request.setAttribute("pacienteId", pacienteIdStr);
                }
                if (doctorIdStr != null) {
                    request.setAttribute("doctorId", doctorIdStr);
                }
                if (fechaCitaStr != null) {
                    request.setAttribute("fechaCita", fechaCitaStr);
                }
                if (horaCitaStr != null) {
                    request.setAttribute("horaCita", horaCitaStr);
                }
                if (motivo != null) {
                    request.setAttribute("motivo", motivo);
                }

                nuevo(request, response);
                return;
            }

            int pacienteId = Integer.parseInt(pacienteIdStr);
            int doctorId = Integer.parseInt(doctorIdStr);
            LocalDate fechaCita = LocalDate.parse(fechaCitaStr);

            // Ajustar formato de hora
            if (horaCitaStr.length() == 5) {
                horaCitaStr = horaCitaStr + ":00";
            }
            LocalTime horaCita = LocalTime.parse(horaCitaStr);

            // Si no hay estado, usar "programada" por defecto
            if (estado == null) {
                estado = "programada";
            }

            // Crear objeto cita
            cita.setPacienteId(pacienteId);
            cita.setDoctorId(doctorId);
            cita.setFechaCita(fechaCita);
            cita.setHoraCita(horaCita);
            cita.setEstado(estado);
            cita.setMotivo(motivo);

            // Llamar al DAO (que ya tiene la validación interna de horarios ocupados)
            System.out.println("[v0] Llamando a dao.Agregar()...");
            int resultado = dao.Agregar(cita);
            System.out.println("[v0] Resultado del DAO: " + resultado);

            if (resultado > 0) {
                System.out.println("[v0] Cita registrada exitosamente");
                request.setAttribute("mensaje", "Cita registrada exitosamente");
                listar(request, response);
            } else if (resultado == -1) {
                // Conflicto de horario detectado por el DAO
                System.out.println("[v0] Conflicto de horario detectado");

                // Mantener los valores en el formulario para que el usuario no tenga que reingresar todo
                String mensajeConflicto = "El doctor ya tiene una cita programada para el " + fechaCitaStr + " a las "
                        + horaCitaStr.substring(0, 5) + ". Por favor seleccione otro horario.";

                request.setAttribute("conflictoHorario", mensajeConflicto);
                request.setAttribute("pacienteId", pacienteIdStr);
                request.setAttribute("doctorId", doctorIdStr);
                request.setAttribute("fechaCita", fechaCitaStr);
                request.setAttribute("horaCita", horaCitaStr.substring(0, 5)); // Sin segundos
                request.setAttribute("motivo", motivo);

                // En lugar de llamar a nuevo(), cargar directamente los datos necesarios
                PacientesDAO pacientesDao = new PacientesDAO();
                DoctoresDAO doctoresDao = new DoctoresDAO();
                List<PacientesModel> pacientes = pacientesDao.Listar();
                List<DoctoresModel> doctores = doctoresDao.Listar();

                request.setAttribute("pacientes", pacientes);
                request.setAttribute("doctores", doctores);

                // Redirigir directamente a la JSP
                request.getRequestDispatcher("Citas/citas_nuevo.jsp").forward(request, response);
            } else {
                System.out.println("[v0] Error: resultado = 0");
                request.setAttribute("error", "Error al registrar cita. Verifica que la API esté funcionando.");

                // Mantener los datos
                request.setAttribute("pacienteId", pacienteIdStr);
                request.setAttribute("doctorId", doctorIdStr);
                request.setAttribute("fechaCita", fechaCitaStr);
                request.setAttribute("horaCita", horaCitaStr.substring(0, 5));
                request.setAttribute("motivo", motivo);

                nuevo(request, response);
            }
        } catch (Exception e) {
            System.out.println("[v0] EXCEPCIÓN en registrar: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());

            // Intentar mantener los datos en caso de error
            try {
                request.setAttribute("pacienteId", request.getParameter("pacienteId"));
                request.setAttribute("doctorId", request.getParameter("doctorId"));
                request.setAttribute("fechaCita", request.getParameter("fechaCita"));
                request.setAttribute("horaCita", request.getParameter("horaCita"));
                request.setAttribute("motivo", request.getParameter("motivo"));
            } catch (Exception ex) {
                System.out.println("[v0] No se pudieron mantener los datos por error: " + ex.getMessage());
            }

            nuevo(request, response);
        }
    }

    protected void listarDoctor(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    try {
        HttpSession session = request.getSession();
        
        // Obtener doctorId de diferentes formas
        Integer doctorId = (Integer) session.getAttribute("doctorId");
        
        // Si no está en doctorId, obtenerlo del objeto usuario
        if (doctorId == null) {
            Object usuarioObj = session.getAttribute("usuario");
            if (usuarioObj != null && usuarioObj instanceof UsuariosResourse) {
                UsuariosResourse usuario = (UsuariosResourse) usuarioObj;
                doctorId = (int) usuario.getId(); // Cast a int
                System.out.println("[DEBUG] Obteniendo doctorId del objeto usuario: " + doctorId);
                
                // Guardar en sesión para futuras requests
                session.setAttribute("doctorId", doctorId);
            }
        }

        System.out.println("[DEBUG] Doctor ID final: " + doctorId);

        if (doctorId == null) {
            System.out.println("[DEBUG] No se pudo obtener doctorId, redirigiendo...");
            response.sendRedirect("index.jsp");
            return;
        }

        // Resto del código sin cambios...
        List<CitasModel> citasDelDoctor = dao.ListarPorDoctor(doctorId);
        System.out.println("[DEBUG] Total citas del doctor: " + citasDelDoctor.size());

        List<CitasModel> citasHoy = new ArrayList<>();
        List<CitasModel> proximasCitas = new ArrayList<>();
        LocalDate hoy = LocalDate.now();

        for (CitasModel cita : citasDelDoctor) {
            System.out.println("[DEBUG] Cita - Fecha: " + cita.getFechaCita() + ", Estado: " + cita.getEstado());
            
            if (cita.getFechaCita() != null && cita.getFechaCita().equals(hoy)) {
                citasHoy.add(cita);
            } else if (cita.getFechaCita() != null && cita.getFechaCita().isAfter(hoy) && !"cancelada".equals(cita.getEstado())) {
                proximasCitas.add(cita);
            }
        }

        System.out.println("[DEBUG] Citas hoy: " + citasHoy.size());
        System.out.println("[DEBUG] Próximas citas: " + proximasCitas.size());

        request.setAttribute("citasHoy", citasHoy);
        request.setAttribute("proximasCitas", proximasCitas);
        request.getRequestDispatcher("dashboard_doctor.jsp").forward(request, response);

    } catch (Exception e) {
        System.out.println("[DEBUG] Error en listarDoctor: " + e.getMessage());
        e.printStackTrace();
        request.setAttribute("error", "Error al cargar citas: " + e.getMessage());
        request.getRequestDispatcher("dashboard_doctor.jsp").forward(request, response);
    }
}

    protected void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            System.out.println("[v0] Editando cita ID: " + id);

            CitasModel citaEditar = dao.BuscarPorId(id);
            System.out.println("[v0] Cita encontrada: " + (citaEditar != null));

            if (citaEditar != null) {
                PacientesDAO pacientesDao = new PacientesDAO();
                DoctoresDAO doctoresDao = new DoctoresDAO();

                List<PacientesModel> pacientes = pacientesDao.Listar();
                List<DoctoresModel> doctores = doctoresDao.Listar();

                request.setAttribute("cita", citaEditar);
                request.setAttribute("pacientes", pacientes);
                request.setAttribute("doctores", doctores);
                request.getRequestDispatcher("Citas/citas_editar.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Cita no encontrada");
                listar(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar cita: " + e.getMessage());
            listar(request, response);
        }
    }

    protected void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            System.out.println("[v0] === INICIANDO ACTUALIZACIÓN DE CITA (API REST) ===");

            // Obtener parámetros
            long id = Long.parseLong(request.getParameter("id"));
            int pacienteId = Integer.parseInt(request.getParameter("pacienteId"));
            int doctorId = Integer.parseInt(request.getParameter("doctorId"));
            String fechaCitaStr = request.getParameter("fechaCita");
            String horaCitaStr = request.getParameter("horaCita");
            String estado = request.getParameter("estado");
            String motivo = request.getParameter("motivo");

            System.out.println("[v0] Parámetros recibidos:");
            System.out.println("[v0]   - ID: " + id);
            System.out.println("[v0]   - Paciente ID: " + pacienteId);
            System.out.println("[v0]   - Doctor ID: " + doctorId);
            System.out.println("[v0]   - Fecha: " + fechaCitaStr);
            System.out.println("[v0]   - Hora: " + horaCitaStr);
            System.out.println("[v0]   - Estado: " + estado);
            System.out.println("[v0]   - Motivo: " + motivo);

            // Validar que todos los campos requeridos estén presentes
            if (fechaCitaStr == null || fechaCitaStr.trim().isEmpty()
                    || horaCitaStr == null || horaCitaStr.trim().isEmpty()) {
                throw new Exception("Fecha y hora son campos obligatorios");
            }

            // Parsear fechas y horas
            LocalDate fechaCita = LocalDate.parse(fechaCitaStr);

            // Ajustar formato de hora si es necesario (HH:mm -> HH:mm:00)
            if (horaCitaStr.length() == 5) {
                horaCitaStr = horaCitaStr + ":00";
            }
            LocalTime horaCita = LocalTime.parse(horaCitaStr);

            // Crear objeto cita
            CitasModel citaActualizar = new CitasModel();
            citaActualizar.setId(id);
            citaActualizar.setPacienteId(pacienteId);
            citaActualizar.setDoctorId(doctorId);
            citaActualizar.setFechaCita(fechaCita);
            citaActualizar.setHoraCita(horaCita);
            citaActualizar.setEstado(estado != null ? estado : "programada");
            citaActualizar.setMotivo(motivo != null ? motivo : "");

            System.out.println("[v0] Objeto cita creado:");
            System.out.println("[v0]   - Fecha (LocalDate): " + citaActualizar.getFechaCita());
            System.out.println("[v0]   - Hora (LocalTime): " + citaActualizar.getHoraCita());

            // Llamar al DAO (que usa API REST)
            int resultado = dao.Actualizar(citaActualizar);
            System.out.println("[v0] Resultado del DAO (API REST): " + resultado);

            if (resultado > 0) {
                request.setAttribute("mensaje", "Cita actualizada exitosamente");
            } else if (resultado == -1) {
                // Conflicto de horario detectado
                String mensajeError = "El doctor ya tiene una cita programada para el " + fechaCitaStr + " a las "
                        + horaCitaStr.substring(0, 5) + ". Por favor seleccione otro horario.";
                request.setAttribute("error", mensajeError);

                // Recargar datos para mostrar el formulario nuevamente
                CitasModel citaEditar = dao.BuscarPorId(id);
                PacientesDAO pacientesDao = new PacientesDAO();
                DoctoresDAO doctoresDao = new DoctoresDAO();
                List<PacientesModel> pacientes = pacientesDao.Listar();
                List<DoctoresModel> doctores = doctoresDao.Listar();

                request.setAttribute("cita", citaEditar);
                request.setAttribute("pacientes", pacientes);
                request.setAttribute("doctores", doctores);
                request.getRequestDispatcher("Citas/citas_editar.jsp").forward(request, response);
                return;
            } else {
                request.setAttribute("error", "No se pudo actualizar la cita. Verifique los datos.");
            }

            listar(request, response);

        } catch (NumberFormatException e) {
            System.out.println("[v0] ERROR: Formato numérico inválido");
            e.printStackTrace();
            request.setAttribute("error", "Error en el formato de los datos: " + e.getMessage());
            listar(request, response);
        } catch (Exception e) {
            System.out.println("[v0] ERROR en actualizar: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al actualizar cita: " + e.getMessage());
            listar(request, response);
        }
    }

    protected void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            int resultado = dao.Eliminar(id);

            if (resultado > 0) {
                request.setAttribute("mensaje", "Cita eliminada exitosamente");
            } else {
                request.setAttribute("error", "No se pudo eliminar la cita");
            }

            listar(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al eliminar cita: " + e.getMessage());
            listar(request, response);
        }
    }

    protected void nuevoPaciente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String pacienteIdParam = request.getParameter("pacienteId");
            System.out.println("[v0] Cargando formulario para paciente: " + pacienteIdParam);

            if (pacienteIdParam == null || pacienteIdParam.isEmpty()) {
                request.setAttribute("error", "ID de paciente no proporcionado");
                response.sendRedirect(request.getContextPath() + "/dashboard/dashboard_paciente.jsp");
                return;
            }

            long pacienteId = Long.parseLong(pacienteIdParam);

            // Obtener lista de doctores disponibles
            DoctoresDAO doctoresDao = new DoctoresDAO();
            List<DoctoresModel> doctores = doctoresDao.Listar();

            System.out.println("[v0] Doctores encontrados: " + doctores.size());

            request.setAttribute("pacienteId", pacienteId);
            request.setAttribute("doctores", doctores);
            request.getRequestDispatcher("/Pacientes/paciente_crear_cita.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("[v0] Error al cargar formulario: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar formulario: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/dashboard/dashboard_paciente.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
