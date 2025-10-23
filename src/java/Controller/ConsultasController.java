package Controller;

import DAO.ConsultasDAO;
import Model.ConsultasModel;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
            default:
                listar(request, response);
                break;
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
