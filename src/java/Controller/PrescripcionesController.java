package Controller;

import Model.PrescripcionesModel;
import DAO.PrescripcionesDAO;
import DAO.ConsultasDAO;
import DAO.MedicamentosDAO;
import DAO.MovimientosInventariosDAO;
import Model.MovimientosInventarioModel;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Model.UsuariosResourse;
import Model.MedicamentosModel;

@WebServlet(name = "PrescripcionesController", urlPatterns = {"/PrescripcionesController"})
public class PrescripcionesController extends HttpServlet {

    PrescripcionesDAO prescripcionesDAO = new PrescripcionesDAO();
    ConsultasDAO consultasDAO = new ConsultasDAO();
    MedicamentosDAO medicamentosDAO = new MedicamentosDAO();
    MovimientosInventariosDAO movimientosDAO = new MovimientosInventariosDAO();
    
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
                registrar(request, response);
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
            case "listarPorConsulta":
                listarPorConsulta(request, response);
                break;
            case "listarPorMedicamento":
                listarPorMedicamento(request, response);
                break;
            case "dispensar":
                dispensarMedicamento(request, response);
                break;
            default:
                listar(request, response);
                break;
        }
    }
    
    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("prescripciones", prescripcionesDAO.listar());
        request.getRequestDispatcher("prescripciones_lista.jsp").forward(request, response);
    }
    
    private void mostrarFormularioNueva(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String consultaIdParam = request.getParameter("consultaId");
        if (consultaIdParam != null) {
            long consultaId = Long.parseLong(consultaIdParam);
            request.setAttribute("consultaId", consultaId);
            request.setAttribute("consulta", consultasDAO.buscarPorId(consultaId));
        }
        
        request.setAttribute("medicamentos", medicamentosDAO.listar());
        request.getRequestDispatcher("prescripciones_nueva.jsp").forward(request, response);
    }
    
    private void registrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            long consultaId = Long.parseLong(request.getParameter("consultaId"));
            long medicamentoId = Long.parseLong(request.getParameter("medicamentoId"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            String dosis = request.getParameter("dosis");
            String frecuencia = request.getParameter("frecuencia");
            String duracion = request.getParameter("duracion");
            String instrucciones = request.getParameter("instrucciones");
            
            PrescripcionesModel p = new PrescripcionesModel();
            p.setConsultaId(consultaId);
            p.setMedicamentoId(medicamentoId);
            p.setCantidad(cantidad);
            p.setDosis(dosis);
            p.setFrecuencia(frecuencia);
            p.setDuracion(duracion);
            p.setInstrucciones(instrucciones);
            
            if (prescripcionesDAO.agregar(p)) {
                request.setAttribute("mensaje", "Prescripción registrada exitosamente");
            } else {
                request.setAttribute("error", "Error al registrar prescripción");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
        }
        
        listar(request, response);
    }
    
    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        long id = Long.parseLong(request.getParameter("id"));
        PrescripcionesModel p = prescripcionesDAO.buscarPorId(id);
        
        request.setAttribute("prescripcion", p);
        request.setAttribute("medicamentos", medicamentosDAO.listar());
        request.getRequestDispatcher("prescripciones_editar.jsp").forward(request, response);
    }
    
    private void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            long id = Long.parseLong(request.getParameter("id"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            String dosis = request.getParameter("dosis");
            String frecuencia = request.getParameter("frecuencia");
            String duracion = request.getParameter("duracion");
            String instrucciones = request.getParameter("instrucciones");
            
            PrescripcionesModel p = new PrescripcionesModel();
            p.setId(id);
            p.setCantidad(cantidad);
            p.setDosis(dosis);
            p.setFrecuencia(frecuencia);
            p.setDuracion(duracion);
            p.setInstrucciones(instrucciones);
            
            if (prescripcionesDAO.actualizar(p)) {
                request.setAttribute("mensaje", "Prescripción actualizada exitosamente");
            } else {
                request.setAttribute("error", "Error al actualizar prescripción");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
        }
        
        listar(request, response);
    }
    
    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        long id = Long.parseLong(request.getParameter("id"));
        
        if (prescripcionesDAO.eliminar(id)) {
            request.setAttribute("mensaje", "Prescripción eliminada exitosamente");
        } else {
            request.setAttribute("error", "Error al eliminar prescripción");
        }
        
        listar(request, response);
    }
    
    private void listarPorConsulta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        long consultaId = Long.parseLong(request.getParameter("consultaId"));
        request.setAttribute("prescripciones", prescripcionesDAO.listarPorConsulta(consultaId));
        request.getRequestDispatcher("prescripciones_lista.jsp").forward(request, response);
    }
    
    private void listarPorMedicamento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        long medicamentoId = Long.parseLong(request.getParameter("medicamentoId"));
        request.setAttribute("prescripciones", prescripcionesDAO.listarPorMedicamento(medicamentoId));
        request.getRequestDispatcher("prescripciones_lista.jsp").forward(request, response);
    }
    
    // DISPENSAR MEDICAMENTO - Caso de uso importante
    // Cuando se dispensa una prescripción, se debe:
    // 1. Reducir el stock del medicamento
    // 2. Registrar el movimiento de inventario
    private void dispensarMedicamento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            HttpSession session = request.getSession();
            UsuariosResourse usuario = (UsuariosResourse) session.getAttribute("usuario");
            
            long prescripcionId = Long.parseLong(request.getParameter("prescripcionId"));
            PrescripcionesModel prescripcion = prescripcionesDAO.buscarPorId(prescripcionId);
            
            if (prescripcion != null && prescripcion.getId() > 0) {
                long medicamentoId = prescripcion.getMedicamentoId();
                int cantidadDispensada = prescripcion.getCantidad();
                
                // Obtener medicamento actual
                MedicamentosModel medicamento = medicamentosDAO.buscarPorId(medicamentoId);
                
                if (medicamento != null && medicamento.getId() > 0) {
                    int stockActual = medicamento.getCantidadStock();
                    
                    // Verificar si hay suficiente stock
                    if (stockActual >= cantidadDispensada) {
                        int nuevoStock = stockActual - cantidadDispensada;
                        
                        // Actualizar stock
                        if (medicamentosDAO.actualizarStock(medicamentoId, nuevoStock)) {
                            // Registrar movimiento de inventario
                            MovimientosInventarioModel movimiento = new MovimientosInventarioModel();
                            movimiento.setMedicamentoId(medicamentoId);
                            movimiento.setTipoMovimiento("salida");
                            movimiento.setCantidad(cantidadDispensada);
                            movimiento.setTipoReferencia("prescripcion");
                            movimiento.setIdReferencia(prescripcionId);
                            movimiento.setNotas("Dispensación de prescripción ID: " + prescripcionId);
                            movimiento.setCreadoPor(usuario != null ? usuario.getId() : 1L);
                            
                            if (movimientosDAO.agregar(movimiento)) {
                                request.setAttribute("mensaje", "Medicamento dispensado exitosamente. Nuevo stock: " + nuevoStock);
                            } else {
                                request.setAttribute("error", "Stock actualizado pero error al registrar movimiento");
                            }
                        } else {
                            request.setAttribute("error", "Error al actualizar stock");
                        }
                    } else {
                        request.setAttribute("error", "Stock insuficiente. Stock actual: " + stockActual + ", Requerido: " + cantidadDispensada);
                    }
                } else {
                    request.setAttribute("error", "Medicamento no encontrado");
                }
            } else {
                request.setAttribute("error", "Prescripción no encontrada");
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