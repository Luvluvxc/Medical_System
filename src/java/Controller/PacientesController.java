package Controller;

import Model.PacientesModel;
import Model.UsuariosResourse;
import DAO.PacientesDAO;
import DAO.UsuarioDAO;
import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "PacientesController", urlPatterns = {"/PacientesController"})
public class PacientesController extends HttpServlet {

    PacientesDAO pacientesDAO = new PacientesDAO();
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    
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
            case "nuevo":
                mostrarFormularioNuevo(request, response);
                break;
            case "registrar":
                registrarNuevoPaciente(request, response);
                break;
            case "editar":
                mostrarFormularioEditar(request, response);
                break;
            case "actualizar":
                actualizar(request, response);
                break;
            case "eliminar":
                eliminar(request, response);
                break;
            case "buscar":
                buscar(request, response);
                break;
            case "verHistorial":
                verHistorial(request, response);
                break;
            default:
                listar(request, response);
                break;
        }
    }
    
    // LISTAR todos los pacientes
    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("pacientes", pacientesDAO.listar());
        request.getRequestDispatcher("Pacientes/pacientes_lista.jsp").forward(request, response);
    }
    
    // MOSTRAR formulario para nuevo paciente
    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Pacientes/pacientes_nuevo.jsp").forward(request, response);
    }
    
    // REGISTRAR NUEVO PACIENTE (Primera visita - Caso de uso)
    // La recepcionista crea el usuario y el paciente, password = correo
    private void registrarNuevoPaciente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // 1. Crear usuario
            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            String correo = request.getParameter("correo");
            String telefono = request.getParameter("telefono");
            
            UsuariosResourse u = new UsuariosResourse();
            u.setNombre(nombre);
            u.setApellido(apellido);
            u.setCorreo(correo);
            u.setContrasenaHash(correo); // Password = correo (según requerimiento)
            u.setTelefono(telefono);
            u.setRol("paciente");
            u.setActivo(true);
            
            // Agregar usuario
            if (usuarioDAO.agregar(u)) {
                // Obtener el usuario recién creado para obtener su ID
                UsuariosResourse usuarioCreado = usuarioDAO.buscarPorCorreo(correo);
                
                if (usuarioCreado != null && usuarioCreado.getId() > 0) {
                    // 2. Crear paciente
                    String codigoPaciente = "PAC-" + usuarioCreado.getId();
                    LocalDate fechaNacimiento = LocalDate.parse(request.getParameter("fechaNacimiento"));
                    String genero = request.getParameter("genero");
                    String direccion = request.getParameter("direccion");
                    String contactoEmergenciaNombre = request.getParameter("contactoEmergenciaNombre");
                    String contactoEmergenciaTelefono = request.getParameter("contactoEmergenciaTelefono");
                    String historialMedico = request.getParameter("historialMedico");
                    String alergias = request.getParameter("alergias");
                    
                    PacientesModel p = new PacientesModel();
                    p.setUsuarioId(usuarioCreado.getId());
                    p.setCodigoPaciente(codigoPaciente);
                    p.setFechaNacimiento(fechaNacimiento);
                    p.setGenero(genero);
                    p.setDireccion(direccion);
                    p.setContactoEmergenciaNombre(contactoEmergenciaNombre);
                    p.setContactoEmergenciaTelefono(contactoEmergenciaTelefono);
                    p.setHistorialMedico(historialMedico);
                    p.setAlergias(alergias);
                    
                    if (pacientesDAO.agregar(p)) {
                        request.setAttribute("mensaje", "Paciente registrado exitosamente");
                        request.setAttribute("codigoPaciente", codigoPaciente);
                        request.setAttribute("correoAcceso", correo);
                    } else {
                        request.setAttribute("error", "Error al registrar datos del paciente");
                    }
                } else {
                    request.setAttribute("error", "Error al obtener ID del usuario creado");
                }
            } else {
                request.setAttribute("error", "Error al crear usuario");
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
        PacientesModel p = pacientesDAO.buscarPorId(id);
        
        request.setAttribute("paciente", p);
        request.getRequestDispatcher("Pacientes/pacientes_editar.jsp").forward(request, response);
    }
    
    // ACTUALIZAR paciente
    private void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            long id = Long.parseLong(request.getParameter("id"));
            LocalDate fechaNacimiento = LocalDate.parse(request.getParameter("fechaNacimiento"));
            String genero = request.getParameter("genero");
            String direccion = request.getParameter("direccion");
            String contactoEmergenciaNombre = request.getParameter("contactoEmergenciaNombre");
            String contactoEmergenciaTelefono = request.getParameter("contactoEmergenciaTelefono");
            String historialMedico = request.getParameter("historialMedico");
            String alergias = request.getParameter("alergias");
            
            PacientesModel p = new PacientesModel();
            p.setId(id);
            p.setFechaNacimiento(fechaNacimiento);
            p.setGenero(genero);
            p.setDireccion(direccion);
            p.setContactoEmergenciaNombre(contactoEmergenciaNombre);
            p.setContactoEmergenciaTelefono(contactoEmergenciaTelefono);
            p.setHistorialMedico(historialMedico);
            p.setAlergias(alergias);
            
            if (pacientesDAO.actualizar(p)) {
                request.setAttribute("mensaje", "Paciente actualizado exitosamente");
            } else {
                request.setAttribute("error", "Error al actualizar paciente");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
        }
        
        listar(request, response);
    }
    
    // ELIMINAR paciente
    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        long id = Long.parseLong(request.getParameter("id"));
        
        if (pacientesDAO.eliminar(id)) {
            request.setAttribute("mensaje", "Paciente eliminado exitosamente");
        } else {
            request.setAttribute("error", "Error al eliminar paciente");
        }
        
        listar(request, response);
    }
    
    // BUSCAR pacientes por nombre
    private void buscar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nombre = request.getParameter("nombre");
        request.setAttribute("pacientes", pacientesDAO.buscarPorNombre(nombre));
        request.getRequestDispatcher("Pacientes/pacientes_lista.jsp").forward(request, response);
    }
    
    // VER HISTORIAL del paciente
    private void verHistorial(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        long id = Long.parseLong(request.getParameter("id"));
        PacientesModel p = pacientesDAO.buscarPorId(id);
        
        request.setAttribute("paciente", p);
        request.getRequestDispatcher("Pacientes/pacientes_historial.jsp").forward(request, response);
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