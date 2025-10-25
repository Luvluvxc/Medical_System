package Controller;

import DAO.CitasDAO;
import DAO.ConsultasDAO;
import Model.CitasModel;
import Model.ConsultasModel;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpSession;

public class ConsultasController extends HttpServlet {

    ConsultasDAO dao = new ConsultasDAO();
    ConsultasModel consulta = new ConsultasModel();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        switch (accion) {
            case "listar":
                listar(request, response);
                break;
            case "listarCerradas":
                listarCerradas(request, response);
                break;
            case "listarMisConsultas":
                listarMisConsultas(request, response);
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
            case "buscarPorCita":
                buscarPorCita(request, response);
                break;
            case "registrarDesdeDoctor":
                registrarDesdeDoctor(request, response);
                break;
            default:
                listar(request, response);
                break;
        }
    }

// Método específico para registrar consultas desde el dashboard del doctor
    protected void registrarDesdeDoctor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Integer doctorId = (Integer) session.getAttribute("doctorId");

            if (doctorId == null) {
                request.setAttribute("error", "No se pudo identificar al doctor");
                response.sendRedirect("login.jsp");
                return;
            }

            int citaId = Integer.parseInt(request.getParameter("citaId"));
            String diagnostico = request.getParameter("diagnostico");
            String planTratamiento = request.getParameter("planTratamiento");
            String observaciones = request.getParameter("observaciones");

            // Verificar que la cita pertenece al doctor
            CitasDAO citasDao = new CitasDAO();
            CitasModel cita = citasDao.BuscarPorId(citaId);

            if (cita == null || cita.getDoctorId() != doctorId) {
                request.setAttribute("error", "No tiene permisos para acceder a esta cita");
                request.getRequestDispatcher("Doctores/dashboard_doctor.jsp").forward(request, response);
                return;
            }

            ConsultasModel consulta = new ConsultasModel();
            consulta.setCitaId(citaId);
            consulta.setDiagnostico(diagnostico);
            consulta.setPlanTratamiento(planTratamiento);
            consulta.setObservaciones(observaciones);

            int resultado = dao.Agregar(consulta);

            if (resultado > 0) {
                // Actualizar el estado de la cita a "completada"
                cita.setEstado("completada");
                citasDao.Actualizar(cita);

                request.setAttribute("mensaje", "Consulta registrada exitosamente y cita marcada como completada");
            } else {
                request.setAttribute("error", "Error al registrar consulta");
            }

            // Redirigir al dashboard del doctor
            request.getRequestDispatcher("Doctores/dashboard_doctor.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("Doctores/dashboard_doctor.jsp").forward(request, response);
        }
    }

    protected void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<ConsultasModel> lista = dao.Listar();
            request.setAttribute("consultas", lista);
            request.getRequestDispatcher("Consultas/consultas_lista.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al listar consultas: " + e.getMessage());
            request.getRequestDispatcher("Consultas/consultas_lista.jsp").forward(request, response);
        }
    }

    // Para doctores - listar sus consultas
    protected void listarMisConsultas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Integer doctorId = (Integer) session.getAttribute("doctorId");

            if (doctorId == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            // Obtener todas las consultas y filtrar por las citas del doctor
            List<ConsultasModel> todasConsultas = dao.Listar();
            List<ConsultasModel> misConsultas = new ArrayList<>();

            // Necesitaríamos un método para obtener las citas del doctor y luego filtrar consultas
            CitasDAO citasDao = new CitasDAO();
            List<CitasModel> misCitas = citasDao.ListarPorDoctor(doctorId);

            for (ConsultasModel consulta : todasConsultas) {
                for (CitasModel cita : misCitas) {
                    if (Objects.equals(consulta.getCitaId(), cita.getId())) {
                        misConsultas.add(consulta);
                        break;
                    }
                }
            }

            request.setAttribute("consultas", misConsultas);
            request.getRequestDispatcher("Doctores/consultas_lista.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al listar consultas: " + e.getMessage());
            request.getRequestDispatcher("Doctores/consultas_lista.jsp").forward(request, response);
        }
    }

// Para recepcionistas - ver consultas cerradas (modificar el existente)
    protected void listarCerradas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<ConsultasModel> todasConsultas = dao.Listar();
            List<ConsultasModel> consultasCerradas = new ArrayList<>();

            // Aquí podrías agregar lógica para filtrar por estado si lo tienes
            // Por ahora mostramos todas
            consultasCerradas = todasConsultas;

            request.setAttribute("consultas", consultasCerradas);
            request.getRequestDispatcher("Consultas/consultas_cerradas.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al listar consultas cerradas: " + e.getMessage());
            request.getRequestDispatcher("Consultas/consultas_cerradas.jsp").forward(request, response);
        }
    }

    protected void nuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Consultas/consultas_nuevo.jsp").forward(request, response);
    }

    protected void registrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int citaId = Integer.parseInt(request.getParameter("citaId"));
            String diagnostico = request.getParameter("diagnostico");
            String planTratamiento = request.getParameter("planTratamiento");
            String observaciones = request.getParameter("observaciones");

            consulta.setCitaId(citaId);
            consulta.setDiagnostico(diagnostico);
            consulta.setPlanTratamiento(planTratamiento);
            consulta.setObservaciones(observaciones);

            int resultado = dao.Agregar(consulta);

            if (resultado > 0) {
                request.setAttribute("mensaje", "Consulta registrada exitosamente");
                listar(request, response);
            } else {
                request.setAttribute("error", "Error al registrar consulta");
                request.getRequestDispatcher("Consultas/consultas_nuevo.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("Consultas/consultas_nuevo.jsp").forward(request, response);
        }
    }

    protected void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            ConsultasModel consultaEditar = dao.BuscarPorId(id);

            if (consultaEditar != null) {
                request.setAttribute("consulta", consultaEditar);
                request.getRequestDispatcher("Consultas/consultas_editar.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Consulta no encontrada");
                listar(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar consulta: " + e.getMessage());
            listar(request, response);
        }
    }

    protected void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            int citaId = Integer.parseInt(request.getParameter("citaId"));
            String diagnostico = request.getParameter("diagnostico");
            String planTratamiento = request.getParameter("planTratamiento");
            String observaciones = request.getParameter("observaciones");

            ConsultasModel consultaActualizar = new ConsultasModel();
            consultaActualizar.setId(id);
            consultaActualizar.setCitaId(citaId);
            consultaActualizar.setDiagnostico(diagnostico);
            consultaActualizar.setPlanTratamiento(planTratamiento);
            consultaActualizar.setObservaciones(observaciones);

            int resultado = dao.Actualizar(consultaActualizar);

            if (resultado > 0) {
                request.setAttribute("mensaje", "Consulta actualizada exitosamente");
            } else {
                request.setAttribute("error", "No se pudo actualizar la consulta");
            }

            listar(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al actualizar consulta: " + e.getMessage());
            listar(request, response);
        }
    }

    protected void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            int resultado = dao.Eliminar(id);

            if (resultado > 0) {
                request.setAttribute("mensaje", "Consulta eliminada exitosamente");
            } else {
                request.setAttribute("error", "No se pudo eliminar la consulta");
            }

            listar(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al eliminar consulta: " + e.getMessage());
            listar(request, response);
        }
    }

    protected void buscarPorCita(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int citaId = Integer.parseInt(request.getParameter("citaId"));
            ConsultasModel consultaBuscada = dao.BuscarPorCitaId(citaId);

            if (consultaBuscada != null) {
                request.setAttribute("consulta", consultaBuscada);
                request.getRequestDispatcher("Consultas/consultas_detalle.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "No se encontró consulta para esta cita");
                listar(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al buscar consulta: " + e.getMessage());
            listar(request, response);
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
