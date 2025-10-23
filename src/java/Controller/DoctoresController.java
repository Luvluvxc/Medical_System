package Controller;



import Model.UsuariosResourse;
import DAO.UsuarioDAO;
import DAO.DoctoresDAO;
import Model.UsuariosResourse;
import DAO.UsuarioDAO;
import Model.DoctoresModel;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DoctoresController extends HttpServlet {
    
    DoctoresDAO dao = new DoctoresDAO();
    DoctoresModel doctor = new DoctoresModel();
    
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
            default:
                listar(request, response);
                break;
        }
    }
    
    protected void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<DoctoresModel> lista = dao.Listar();
            request.setAttribute("doctores", lista);
            request.getRequestDispatcher("Doctores/doctores_lista.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al listar doctores: " + e.getMessage());
            request.getRequestDispatcher("Doctores/doctores_lista.jsp").forward(request, response);
        }
    }
    
    
    protected void listardoctores(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsuarioDAO dao = new UsuarioDAO();
        UsuariosResourse u = new UsuariosResourse();
        try {
            String rol = request.getParameter("rol");
            List<UsuariosResourse> lista;
            
            if (rol != null && !rol.trim().isEmpty()) {
                lista = dao.ListarPorRol(rol);
            } else {
                lista = dao.Listar();
            }
            
            request.setAttribute("doctores", lista);
            request.getRequestDispatcher("Doctores/usuarios_doctores.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al listar usuarios: " + e.getMessage());
            request.getRequestDispatcher("Usuarios/usuarios_lista.jsp").forward(request, response);
        }
    }

    protected void registrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int usuarioId = Integer.parseInt(request.getParameter("usuarioId"));
            String numeroLicencia = request.getParameter("numeroLicencia");
            String especializacion = request.getParameter("especializacion");
            
            doctor.setUsuarioId(usuarioId);
            doctor.setNumeroLicencia(numeroLicencia);
            doctor.setEspecializacion(especializacion);
            
            int resultado = dao.Agregar(doctor);
            
            if (resultado > 0) {
                request.setAttribute("mensaje", "Doctor registrado exitosamente");
                listar(request, response);
            } else {
                request.setAttribute("error", "Error al registrar doctor");
                request.getRequestDispatcher("Doctores/doctores_nuevo.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("Doctores/doctores_nuevo.jsp").forward(request, response);
        }
    }
    
    protected void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            DoctoresModel doctorEditar = dao.BuscarPorId(id);
            
            if (doctorEditar != null) {
                request.setAttribute("doctor", doctorEditar);
                request.getRequestDispatcher("Doctores/doctores_editar.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Doctor no encontrado");
                listar(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar doctor: " + e.getMessage());
            listar(request, response);
        }
    }
    
    protected void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            int usuarioId = Integer.parseInt(request.getParameter("usuarioId"));
            String numeroLicencia = request.getParameter("numeroLicencia");
            String especializacion = request.getParameter("especializacion");
            
            DoctoresModel doctorActualizar = new DoctoresModel();
            doctorActualizar.setId(id);
            doctorActualizar.setUsuarioId(usuarioId);
            doctorActualizar.setNumeroLicencia(numeroLicencia);
            doctorActualizar.setEspecializacion(especializacion);
            
            int resultado = dao.Actualizar(doctorActualizar);
            
            if (resultado > 0) {
                request.setAttribute("mensaje", "Doctor actualizado exitosamente");
            } else {
                request.setAttribute("error", "No se pudo actualizar el doctor");
            }
            
            listar(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al actualizar doctor: " + e.getMessage());
            listar(request, response);
        }
    }
    
    protected void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            int resultado = dao.Eliminar(id);
            
            if (resultado > 0) {
                request.setAttribute("mensaje", "Doctor eliminado exitosamente");
            } else {
                request.setAttribute("error", "No se pudo eliminar el doctor");
            }
            
            listar(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al eliminar doctor: " + e.getMessage());
            listar(request, response);
        }
    }
    protected void nuevo(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    try {
        UsuarioDAO usuarioDao = new UsuarioDAO();
        String usuarioIdParam = request.getParameter("usuarioId");
        
        if (usuarioIdParam != null && !usuarioIdParam.isEmpty()) {
            long usuarioId = Long.parseLong(usuarioIdParam);
            UsuariosResourse usuario = usuarioDao.BuscarPorId(usuarioId);
            request.setAttribute("usuarioSeleccionado", usuario);
        }
        
        List<UsuariosResourse> usuarios = usuarioDao.Listar();
        request.setAttribute("usuarios", usuarios);
        request.getRequestDispatcher("Doctores/doctores_nuevo.jsp").forward(request, response);
    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("error", "Error al cargar formulario: " + e.getMessage());
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
