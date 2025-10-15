package Controller;

import Model.UsuariosResourse;
import DAO.UsuarioDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UsuariosController", urlPatterns = {"/UsuariosController"})
public class UsuariosController extends HttpServlet {

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
                registrar(request, response);
                break;
            case "editar":
                mostrarFormularioEditar(request, response);
                break;
            case "actualizar":
                actualizar(request, response);
                break;
            default:
                listar(request, response);
                break;
        }
    }
    
    // LISTAR todos los usuarios
    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("usuarios", usuarioDAO.listar());
        request.getRequestDispatcher("Usuarios/usuarios_lista.jsp").forward(request, response);
    }
    
    // MOSTRAR formulario para nuevo usuario
    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Usuarios/usuarios_nuevo.jsp").forward(request, response);
    }
    
    // REGISTRAR nuevo usuario
  // REGISTRAR nuevo usuario (MODIFICADO con redirección)
private void registrar(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
    try {
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");
        String rol = request.getParameter("rol");
        
        UsuariosResourse u = new UsuariosResourse();
        u.setNombre(nombre);
        u.setApellido(apellido);
        u.setCorreo(correo);
        u.setContrasenaHash(correo);
        u.setTelefono(telefono);
        u.setRol(rol);
        u.setActivo(true);
        
        if (usuarioDAO.agregar(u)) {
            // Buscar el usuario recién creado para obtener su ID
            UsuariosResourse usuarioCreado = usuarioDAO.buscarPorCorreo(correo);
            
            // Si es paciente, redirigir a crear perfil de paciente
            if ("paciente".equals(rol) && usuarioCreado != null) {
                request.setAttribute("mensaje", "Usuario creado exitosamente. Ahora complete los datos del paciente.");
                request.setAttribute("usuarioId", usuarioCreado.getId());
                request.setAttribute("usuarioNombre", usuarioCreado.getNombre() + " " + usuarioCreado.getApellido());
                
                // Redirigir al formulario de paciente con el usuario pre-seleccionado
                response.sendRedirect("PacientesController?accion=nuevoConUsuario&usuarioId=" + usuarioCreado.getId());
                return;
            } else {
                request.setAttribute("mensaje", "Usuario registrado exitosamente");
            }
        } else {
            request.setAttribute("error", "Error al registrar usuario");
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
        UsuariosResourse u = usuarioDAO.buscarPorId(id);
        
        request.setAttribute("usuario", u);
        request.getRequestDispatcher("Usuarios/usuarios_editar.jsp").forward(request, response);
    }
    
    // ACTUALIZAR usuario
    private void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            long id = Long.parseLong(request.getParameter("id"));
            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            String correo = request.getParameter("correo");
            String telefono = request.getParameter("telefono");
            String rol = request.getParameter("rol");
            boolean activo = Boolean.parseBoolean(request.getParameter("activo"));
            
            UsuariosResourse u = new UsuariosResourse();
            u.setId(id);
            u.setNombre(nombre);
            u.setApellido(apellido);
            u.setCorreo(correo);
            u.setTelefono(telefono);
            u.setRol(rol);
            u.setActivo(activo);
            
            if (usuarioDAO.actualizar(u)) {
                request.setAttribute("mensaje", "Usuario actualizado exitosamente");
            } else {
                request.setAttribute("error", "Error al actualizar usuario");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
        }
        
        listar(request, response);
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