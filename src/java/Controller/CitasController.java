package Controller;

import DAO.CitasDAO;
import DAO.PacientesDAO;
import DAO.DoctoresDAO;
import Model.CitasModel;
import Model.PacientesModel;
import Model.DoctoresModel;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CitasController extends HttpServlet {
    
    CitasDAO dao = new CitasDAO();
    CitasModel cita = new CitasModel();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        switch (accion) {
            case "listar":
                listar(request, response);
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
            default:
                listar(request, response);
                break;
        }
    }
    
    protected void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<CitasModel> lista = dao.Listar();
            request.setAttribute("citas", lista);
            request.getRequestDispatcher("Citas/citas_lista.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al listar citas: " + e.getMessage());
            request.getRequestDispatcher("Citas/citas_lista.jsp").forward(request, response);
        }
    }
    
    protected void nuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            PacientesDAO pacientesDao = new PacientesDAO();
            DoctoresDAO doctoresDao = new DoctoresDAO();
            
            String pacienteIdParam = request.getParameter("pacienteId");
            if (pacienteIdParam != null && !pacienteIdParam.isEmpty()) {
                long pacienteId = Long.parseLong(pacienteIdParam);
                PacientesModel pacienteSeleccionado = pacientesDao.BuscarPorId(pacienteId);
                request.setAttribute("pacienteSeleccionado", pacienteSeleccionado);
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
        
        // Obtener y validar parámetros
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
        
        // Validar que no sean nulos
        if (pacienteIdStr == null || doctorIdStr == null || fechaCitaStr == null || 
            horaCitaStr == null || estado == null || motivo == null) {
            System.out.println("[v0] ERROR: Parámetros faltantes");
            request.setAttribute("error", "Todos los campos son obligatorios");
            nuevo(request, response);
            return;
        }
        
        int pacienteId = Integer.parseInt(pacienteIdStr);
        int doctorId = Integer.parseInt(doctorIdStr);
        LocalDate fechaCita = LocalDate.parse(fechaCitaStr);
        LocalTime horaCita = LocalTime.parse(horaCitaStr);
        
        System.out.println("[v0] Datos parseados correctamente");
        
        cita.setPacienteId(pacienteId);
        cita.setDoctorId(doctorId);
        cita.setFechaCita(fechaCita);
        cita.setHoraCita(horaCita);
        cita.setEstado(estado);
        cita.setMotivo(motivo);
        
        System.out.println("[v0] Llamando a dao.Agregar()...");
        int resultado = dao.Agregar(cita);
        System.out.println("[v0] Resultado del DAO: " + resultado);
        
        if (resultado > 0) {
            System.out.println("[v0] Cita registrada exitosamente");
            request.setAttribute("mensaje", "Cita registrada exitosamente");
            listar(request, response);
        } else {
            System.out.println("[v0] Error: resultado = 0");
            request.setAttribute("error", "Error al registrar cita. Verifica que la API esté funcionando.");
            nuevo(request, response);
        }
    } catch (Exception e) {
        System.out.println("[v0] EXCEPCIÓN en registrar: " + e.getMessage());
        e.printStackTrace();
        request.setAttribute("error", "Error: " + e.getMessage());
        nuevo(request, response);
    }
}
    
    protected void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            CitasModel citaEditar = dao.BuscarPorId(id);
            
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
            long id = Long.parseLong(request.getParameter("id"));
            int pacienteId = Integer.parseInt(request.getParameter("pacienteId"));
            int doctorId = Integer.parseInt(request.getParameter("doctorId"));
            LocalDate fechaCita = LocalDate.parse(request.getParameter("fechaCita"));
            LocalTime horaCita = LocalTime.parse(request.getParameter("horaCita"));
            String estado = request.getParameter("estado");
            String motivo = request.getParameter("motivo");
            
            CitasModel citaActualizar = new CitasModel();
            citaActualizar.setId(id);
            citaActualizar.setPacienteId(pacienteId);
            citaActualizar.setDoctorId(doctorId);
            citaActualizar.setFechaCita(fechaCita);
            citaActualizar.setHoraCita(horaCita);
            citaActualizar.setEstado(estado);
            citaActualizar.setMotivo(motivo);
            
            int resultado = dao.Actualizar(citaActualizar);
            
            if (resultado > 0) {
                request.setAttribute("mensaje", "Cita actualizada exitosamente");
            } else {
                request.setAttribute("error", "No se pudo actualizar la cita");
            }
            
            listar(request, response);
        } catch (Exception e) {
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
