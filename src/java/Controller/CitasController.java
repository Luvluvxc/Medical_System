package Controller;
import Model.UsuariosResourse;
import Model.CitasModel;
import DAO.CitasDAO;
import DAO.PacientesDAO;
import DAO.DoctoresDAO;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "CitasController", urlPatterns = {"/CitasController"})
public class CitasController extends HttpServlet {

    CitasDAO citasDAO = new CitasDAO();
    PacientesDAO pacientesDAO = new PacientesDAO();
    DoctoresDAO doctoresDAO = new DoctoresDAO();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        if (accion == null) {
            accion = "listar";
        }
        
        switch (accion) {
            case "listar":
                listar(request, response);
                break;
            case "nueva":
                mostrarFormularioNueva(request, response);
                break;
            case "registrar":
                registrarCita(request, response);
                break;
            case "editar":
                mostrarFormularioEditar(request, response);
                break;
            case "actualizar":
                actualizar(request, response);
                break;
            case "cambiarEstado":
                cambiarEstado(request, response);
                break;
            case "eliminar":
                eliminar(request, response);
                break;
            case "listarPorPaciente":
                listarPorPaciente(request, response);
                break;
            case "listarPorDoctor":
                listarPorDoctor(request, response);
                break;
            case "listarPorFecha":
                listarPorFecha(request, response);
                break;
            default:
                listar(request, response);
                break;
        }
    }
    
    // LISTAR todas las citas
    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("citas", citasDAO.listar());
        request.setAttribute("pacientes", pacientesDAO.listar());
        request.setAttribute("doctores", doctoresDAO.listar());
        request.getRequestDispatcher("citas_lista.jsp").forward(request, response);
    }
    
    // MOSTRAR formulario para nueva cita
    private void mostrarFormularioNueva(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("pacientes", pacientesDAO.listar());
        request.setAttribute("doctores", doctoresDAO.listar());
        request.getRequestDispatcher("citas_nueva.jsp").forward(request, response);
    }
    
    // REGISTRAR NUEVA CITA (Caso de uso: Recepcionista registra cita)
    private void registrarCita(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            HttpSession session = request.getSession();
            UsuariosResourse usuario = (UsuariosResourse) session.getAttribute("usuario");
            
            long pacienteId = Long.parseLong(request.getParameter("pacienteId"));
            long doctorId = Long.parseLong(request.getParameter("doctorId"));
            LocalDate fechaCita = LocalDate.parse(request.getParameter("fechaCita"));
            LocalTime horaCita = LocalTime.parse(request.getParameter("horaCita"));
            String motivo = request.getParameter("motivo");
            String notas = request.getParameter("notas");
            
            CitasModel c = new CitasModel();
            c.setPacienteId(pacienteId);
            c.setDoctorId(doctorId);
            c.setFechaCita(fechaCita);
            c.setHoraCita(horaCita);
            c.setEstado("programada");
            c.setMotivo(motivo);
            c.setNotas(notas);
            c.setCreadoPor(usuario != null ? usuario.getId() : 1L);
            
            if (citasDAO.agregar(c)) {
                request.setAttribute("mensaje", "Cita registrada exitosamente");
            } else {
                request.setAttribute("error", "Error al registrar cita");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
        }
        
        listar(request, response);
    }
    
    // MOSTRAR formulario para editar
    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        long id = Long.parseLong(request.getParameter("id"));
        CitasModel c = citasDAO.buscarPorId(id);
        
        request.setAttribute("cita", c);
        request.setAttribute("pacientes", pacientesDAO.listar());
        request.setAttribute("doctores", doctoresDAO.listar());
        request.getRequestDispatcher("citas_editar.jsp").forward(request, response);
    }
    
    // ACTUALIZAR cita
    private void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            long id = Long.parseLong(request.getParameter("id"));
            long pacienteId = Long.parseLong(request.getParameter("pacienteId"));
            long doctorId = Long.parseLong(request.getParameter("doctorId"));
            LocalDate fechaCita = LocalDate.parse(request.getParameter("fechaCita"));
            LocalTime horaCita = LocalTime.parse(request.getParameter("horaCita"));
            String estado = request.getParameter("estado");
            String motivo = request.getParameter("motivo");
            String notas = request.getParameter("notas");
            
            CitasModel c = new CitasModel();
            c.setId(id);
            c.setPacienteId(pacienteId);
            c.setDoctorId(doctorId);
            c.setFechaCita(fechaCita);
            c.setHoraCita(horaCita);
            c.setEstado(estado);
            c.setMotivo(motivo);
            c.setNotas(notas);
            
            if (citasDAO.actualizar(c)) {
                request.setAttribute("mensaje", "Cita actualizada exitosamente");
            } else {
                request.setAttribute("error", "Error al actualizar cita");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
        }
        
        listar(request, response);
    }
    
    // CAMBIAR ESTADO de la cita
    private void cambiarEstado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        long id = Long.parseLong(request.getParameter("id"));
        String nuevoEstado = request.getParameter("estado");
        
        if (citasDAO.cambiarEstado(id, nuevoEstado)) {
            request.setAttribute("mensaje", "Estado de cita actualizado");
        } else {
            request.setAttribute("error", "Error al cambiar estado");
        }
        
        listar(request, response);
    }
    
    // ELIMINAR cita
    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        long id = Long.parseLong(request.getParameter("id"));
        
        if (citasDAO.eliminar(id)) {
            request.setAttribute("mensaje", "Cita eliminada exitosamente");
        } else {
            request.setAttribute("error", "Error al eliminar cita");
        }
        
        listar(request, response);
    }
    
    // LISTAR citas por paciente
    private void listarPorPaciente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        long pacienteId = Long.parseLong(request.getParameter("pacienteId"));
        request.setAttribute("citas", citasDAO.listarPorPaciente(pacienteId));
        request.getRequestDispatcher("citas_lista.jsp").forward(request, response);
    }
    
    // LISTAR citas por doctor
    private void listarPorDoctor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        long doctorId = Long.parseLong(request.getParameter("doctorId"));
        request.setAttribute("citas", citasDAO.listarPorDoctor(doctorId));
        request.getRequestDispatcher("citas_lista.jsp").forward(request, response);
    }
    
    // LISTAR citas por fecha
    private void listarPorFecha(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        java.sql.Date fecha = java.sql.Date.valueOf(request.getParameter("fecha"));
        request.setAttribute("citas", citasDAO.listarPorFecha(fecha));
        request.getRequestDispatcher("citas_lista.jsp").forward(request, response);
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