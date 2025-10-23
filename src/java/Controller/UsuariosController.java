package Controller;

import DAO.UsuarioDAO;
import Model.UsuariosResourse;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UsuariosController extends HttpServlet {
    
    UsuarioDAO dao = new UsuarioDAO();
    UsuariosResourse u = new UsuariosResourse();
    
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
            case "listardoctor":
                listardoctor(request, response);
                break;
            case "listarpaciente":
                listarpaciente(request, response);
                break;
            case "desactivar":
                desactivar(request, response);
                break;
            case "activar":
                activar(request, response);
                break;
            default:
                listar(request, response);
                break;
        }
    }
    
    protected void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String rol = request.getParameter("rol");
            List<UsuariosResourse> lista;
            
            if (rol != null && !rol.trim().isEmpty()) {
                lista = dao.ListarPorRol(rol);
            } else {
                lista = dao.Listar();
            }
            
            request.setAttribute("usuarios", lista);
            request.getRequestDispatcher("Usuarios/usuarios_lista.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al listar usuarios: " + e.getMessage());
            request.getRequestDispatcher("Usuarios/usuarios_lista.jsp").forward(request, response);
        }
    }
    protected void listardoctor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String rol = request.getParameter("rol");
            List<UsuariosResourse> lista;
            
            if (rol != null && !rol.trim().isEmpty()) {
                lista = dao.ListarPorRol(rol);
            } else {
                lista = dao.Listar();
            }
            
            request.setAttribute("usuarios", lista);
            request.getRequestDispatcher("Doctores/usuarios_doctores.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al listar usuarios: " + e.getMessage());
            request.getRequestDispatcher("Doctores/usuarios_doctores.jsp").forward(request, response);
        }
    }
    
    protected void listarpaciente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String rol = request.getParameter("rol");
            List<UsuariosResourse> lista;
            
            if (rol != null && !rol.trim().isEmpty()) {
                lista = dao.ListarPorRol(rol);
            } else {
                lista = dao.Listar();
            }
            
            request.setAttribute("usuarios", lista);
            request.getRequestDispatcher("Pacientes/pacientes_usuarios.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al listar usuarios: " + e.getMessage());
            request.getRequestDispatcher("Usuarios/usuarios_lista.jsp").forward(request, response);
        }
    }
    
    
    protected void nuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Usuarios/usuarios_nuevo.jsp").forward(request, response);
    }
    
    protected void registrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String correo = request.getParameter("correo");
            String contrasena = request.getParameter("contrasena");
            String rol = request.getParameter("rol");
            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            String telefono = request.getParameter("telefono");
            
            u.setCorreo(correo);
            u.setContrasenaHash(contrasena);
            u.setRol(rol);
            u.setNombre(nombre);
            u.setApellido(apellido);
            u.setTelefono(telefono);
            u.setActivo(true);
            
            UsuariosResourse usuarioCreado = dao.Agregar(u);
            
            if (usuarioCreado != null && usuarioCreado.getId() > 0) {
                request.setAttribute("mensaje", "Usuario registrado exitosamente");
                listar(request, response);
            } else {
                request.setAttribute("error", "Error al registrar usuario");
                request.getRequestDispatcher("Usuarios/usuarios_nuevo.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("Usuarios/usuarios_nuevo.jsp").forward(request, response);
        }
    }
    
    protected void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            UsuariosResourse usuario = dao.BuscarPorId(id);
            
            if (usuario != null) {
                request.setAttribute("usuario", usuario);
                request.getRequestDispatcher("Usuarios/usuarios_editar.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Usuario no encontrado");
                listar(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar usuario: " + e.getMessage());
            listar(request, response);
        }
    }
    
    protected void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            String telefono = request.getParameter("telefono");
            String rol = request.getParameter("rol");
            String contrasena = request.getParameter("contrasena");
            
            UsuariosResourse usuario = dao.BuscarPorId(id);
            
            if (usuario != null) {
                usuario.setNombre(nombre);
                usuario.setApellido(apellido);
                usuario.setTelefono(telefono);
                usuario.setRol(rol);
                
                // Solo actualizar contraseña si se proporcionó una nueva
                if (contrasena != null && !contrasena.trim().isEmpty()) {
                    usuario.setContrasenaHash(contrasena);
                }
                
                int resultado = dao.Actualizar(usuario);
                
                if (resultado > 0) {
                    request.setAttribute("mensaje", "Usuario actualizado exitosamente");
                } else {
                    request.setAttribute("error", "No se pudo actualizar el usuario");
                }
            } else {
                request.setAttribute("error", "Usuario no encontrado");
            }
            
            listar(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al actualizar usuario: " + e.getMessage());
            listar(request, response);
        }
    }
    
    protected void desactivar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            int resultado = dao.CambiarEstado(id, false);
            
            if (resultado > 0) {
                request.setAttribute("mensaje", "Usuario desactivado exitosamente");
            } else {
                request.setAttribute("error", "No se pudo desactivar el usuario");
            }
            
            listar(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al desactivar usuario: " + e.getMessage());
            listar(request, response);
        }
    }
    
    protected void activar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            int resultado = dao.CambiarEstado(id, true);
            
            if (resultado > 0) {
                request.setAttribute("mensaje", "Usuario activado exitosamente");
            } else {
                request.setAttribute("error", "No se pudo activar el usuario");
            }
            
            listar(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al activar usuario: " + e.getMessage());
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
