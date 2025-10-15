package Controller;

import Model.DoctoresModel;
import Model.UsuariosResourse;
import DAO.DoctoresDAO;
import DAO.UsuarioDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DoctoresController", urlPatterns = {"/DoctoresController"})
public class DoctoresController extends HttpServlet {

    DoctoresDAO doctoresDAO = new DoctoresDAO();
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
                registrarNuevoDoctor(request, response);
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
            case "buscarPorEspecializacion":
                buscarPorEspecializacion(request, response);
                break;
            default:
                listar(request, response);
                break;
        }
    }
    
    // LISTAR todos los doctores
    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("doctores", doctoresDAO.listar());
        request.getRequestDispatcher("doctores_lista.jsp").forward(request, response);
    }
    
    // MOSTRAR formulario para nuevo doctor
    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("doctores_nuevo.jsp").forward(request, response);
    }
    
    // REGISTRAR NUEVO DOCTOR
    private void registrarNuevoDoctor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // 1. Crear usuario
            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            String correo = request.getParameter("correo");
            String telefono = request.getParameter("telefono");
            String contrasena = request.getParameter("contrasena");
            
            UsuariosResourse u = new UsuariosResourse();
            u.setNombre(nombre);
            u.setApellido(apellido);
            u.setCorreo(correo);
            u.setContrasenaHash(contrasena);
            u.setTelefono(telefono);
            u.setRol("doctor");
            u.setActivo(true);
            
            if (usuarioDAO.agregar(u)) {
                UsuariosResourse usuarioCreado = usuarioDAO.buscarPorCorreo(correo);
                
                if (usuarioCreado != null && usuarioCreado.getId() > 0) {
                    // 2. Crear doctor
                    String numeroLicencia = request.getParameter("numeroLicencia");
                    String especializacion = request.getParameter("especializacion");
                    BigDecimal tarifaConsulta = new BigDecimal(request.getParameter("tarifaConsulta"));
                    LocalTime horarioInicio = LocalTime.parse(request.getParameter("horarioInicio"));
                    LocalTime horarioFin = LocalTime.parse(request.getParameter("horarioFin"));
                    
                    DoctoresModel d = new DoctoresModel();
                    d.setUsuarioId(usuarioCreado.getId());
                    d.setNumeroLicencia(numeroLicencia);
                    d.setEspecializacion(especializacion);
                    d.setTarifaConsulta(tarifaConsulta);
                    d.setHorarioInicio(horarioInicio);
                    d.setHorarioFin(horarioFin);
                    
                    if (doctoresDAO.agregar(d)) {
                        request.setAttribute("mensaje", "Doctor registrado exitosamente");
                    } else {
                        request.setAttribute("error", "Error al registrar datos del doctor");
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
        DoctoresModel d = doctoresDAO.buscarPorId(id);
        
        request.setAttribute("doctor", d);
        request.getRequestDispatcher("doctores_editar.jsp").forward(request, response);
    }
    
    // ACTUALIZAR doctor
    private void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            long id = Long.parseLong(request.getParameter("id"));
            String numeroLicencia = request.getParameter("numeroLicencia");
            String especializacion = request.getParameter("especializacion");
            BigDecimal tarifaConsulta = new BigDecimal(request.getParameter("tarifaConsulta"));
            LocalTime horarioInicio = LocalTime.parse(request.getParameter("horarioInicio"));
            LocalTime horarioFin = LocalTime.parse(request.getParameter("horarioFin"));
            
            DoctoresModel d = new DoctoresModel();
            d.setId(id);
            d.setNumeroLicencia(numeroLicencia);
            d.setEspecializacion(especializacion);
            d.setTarifaConsulta(tarifaConsulta);
            d.setHorarioInicio(horarioInicio);
            d.setHorarioFin(horarioFin);
            
            if (doctoresDAO.actualizar(d)) {
                request.setAttribute("mensaje", "Doctor actualizado exitosamente");
            } else {
                request.setAttribute("error", "Error al actualizar doctor");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
        }
        
        listar(request, response);
    }
    
    // ELIMINAR doctor
    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        long id = Long.parseLong(request.getParameter("id"));
        
        if (doctoresDAO.eliminar(id)) {
            request.setAttribute("mensaje", "Doctor eliminado exitosamente");
        } else {
            request.setAttribute("error", "Error al eliminar doctor");
        }
        
        listar(request, response);
    }
    
    // BUSCAR por especialización
    private void buscarPorEspecializacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String especializacion = request.getParameter("especializacion");
        request.setAttribute("doctores", doctoresDAO.listarPorEspecializacion(especializacion));
        request.getRequestDispatcher("doctores_lista.jsp").forward(request, response);
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