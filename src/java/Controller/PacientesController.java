package Controller;

import DAO.PacientesDAO;
import DAO.UsuarioDAO;
import Model.PacientesModel;
import Model.UsuariosResourse;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.time.LocalDate;

public class PacientesController extends HttpServlet {
    
    PacientesDAO dao = new PacientesDAO();
    UsuarioDAO usuarioDao = new UsuarioDAO();
    PacientesModel p = new PacientesModel();
    
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
            case "ver":
                ver(request, response);
                break;
            default:
                listar(request, response);
                break;
        }
    }
    
    protected void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<PacientesModel> lista = dao.Listar();
            request.setAttribute("pacientes", lista);
            request.getRequestDispatcher("Pacientes/pacientes_lista.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al listar pacientes: " + e.getMessage());
            request.getRequestDispatcher("Pacientes/pacientes_lista.jsp").forward(request, response);
        }
    }
    
    protected void nuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String usuarioIdParam = request.getParameter("usuarioId");
            
            if (usuarioIdParam != null && !usuarioIdParam.isEmpty()) {
                long usuarioId = Long.parseLong(usuarioIdParam);
                UsuariosResourse usuario = usuarioDao.BuscarPorId(usuarioId);
                request.setAttribute("usuarioSeleccionado", usuario);
            }
            
            List<UsuariosResourse> usuarios = usuarioDao.Listar();
            request.setAttribute("usuarios", usuarios);
            request.getRequestDispatcher("Pacientes/pacientes_nuevo.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar formulario: " + e.getMessage());
            listar(request, response);
        }
    }
    
    protected void registrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long usuarioId = Long.parseLong(request.getParameter("usuarioId"));
            String fechaNacStr = request.getParameter("fechaNacimiento");
            String genero = request.getParameter("genero");
            String direccion = request.getParameter("direccion");
            String contactoNombre = request.getParameter("contactoEmergenciaNombre");
            String contactoTelefono = request.getParameter("contactoEmergenciaTelefono");
            String historial = request.getParameter("historialMedico");
            String alergias = request.getParameter("alergias");
            
            p.setUsuarioId(usuarioId);
            p.setFechaNacimiento(LocalDate.parse(fechaNacStr));
            p.setGenero(genero);
            p.setDireccion(direccion);
            p.setContactoEmergenciaNombre(contactoNombre);
            p.setContactoEmergenciaTelefono(contactoTelefono);
            p.setHistorialMedico(historial);
            p.setAlergias(alergias);
            
            int resultado = dao.Agregar(p);
            
            if (resultado > 0) {
                request.setAttribute("mensaje", "Paciente registrado exitosamente");
                listar(request, response);
            } else {
                request.setAttribute("error", "Error al registrar paciente");
                nuevo(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
            nuevo(request, response);
        }
    }
    
    protected void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            PacientesModel paciente = dao.BuscarPorId(id);
            
            if (paciente != null) {
                request.setAttribute("paciente", paciente);
                request.getRequestDispatcher("Pacientes/pacientes_editar.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Paciente no encontrado");
                listar(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar paciente: " + e.getMessage());
            listar(request, response);
        }
    }
    
    protected void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            long usuarioId = Long.parseLong(request.getParameter("usuarioId"));
            String fechaNacStr = request.getParameter("fechaNacimiento");
            String genero = request.getParameter("genero");
            String direccion = request.getParameter("direccion");
            String contactoNombre = request.getParameter("contactoEmergenciaNombre");
            String contactoTelefono = request.getParameter("contactoEmergenciaTelefono");
            String historial = request.getParameter("historialMedico");
            String alergias = request.getParameter("alergias");
            
            PacientesModel paciente = new PacientesModel();
            paciente.setId(id);
            paciente.setUsuarioId(usuarioId);
            paciente.setFechaNacimiento(LocalDate.parse(fechaNacStr));
            paciente.setGenero(genero);
            paciente.setDireccion(direccion);
            paciente.setContactoEmergenciaNombre(contactoNombre);
            paciente.setContactoEmergenciaTelefono(contactoTelefono);
            paciente.setHistorialMedico(historial);
            paciente.setAlergias(alergias);
            
            int resultado = dao.Actualizar(paciente);
            
            if (resultado > 0) {
                request.setAttribute("mensaje", "Paciente actualizado exitosamente");
            } else {
                request.setAttribute("error", "No se pudo actualizar el paciente");
            }
            
            listar(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al actualizar paciente: " + e.getMessage());
            listar(request, response);
        }
    }
    
    protected void ver(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            PacientesModel paciente = dao.BuscarPorId(id);
            
            if (paciente != null) {
                request.setAttribute("paciente", paciente);
                request.getRequestDispatcher("Pacientes/pacientes_ver.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Paciente no encontrado");
                listar(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar paciente: " + e.getMessage());
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
