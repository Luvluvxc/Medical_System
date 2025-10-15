package Controller;

import Model.MovimientosInventarioModel;
import DAO.MovimientosInventariosDAO;
import DAO.MedicamentosDAO;
import Model.MedicamentosModel;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Model.UsuariosResourse;

@WebServlet(name = "MovimientosInventarioController", urlPatterns = {"/MovimientosInventarioController"})
public class MovimientosInventarioController extends HttpServlet {

    MovimientosInventariosDAO movimientosDAO = new MovimientosInventariosDAO();
    MedicamentosDAO medicamentosDAO = new MedicamentosDAO();
    
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
            case "eliminar":
                eliminar(request, response);
                break;
            case "listarPorMedicamento":
                listarPorMedicamento(request, response);
                break;
            case "listarPorTipo":
                listarPorTipo(request, response);
                break;
            case "registrarEntrada":
                registrarEntrada(request, response);
                break;
            case "registrarSalida":
                registrarSalida(request, response);
                break;
            default:
                listar(request, response);
                break;
        }
    }
    
    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("movimientos", movimientosDAO.listar());
        request.setAttribute("medicamentos", medicamentosDAO.listar());
        request.getRequestDispatcher("movimientos_inventario_lista.jsp").forward(request, response);
    }
    
    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("medicamentos", medicamentosDAO.listar());
        request.getRequestDispatcher("movimientos_inventario_nuevo.jsp").forward(request, response);
    }
    
    private void registrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            HttpSession session = request.getSession();
            UsuariosResourse usuario = (UsuariosResourse) session.getAttribute("usuario");
            
            long medicamentoId = Long.parseLong(request.getParameter("medicamentoId"));
            String tipoMovimiento = request.getParameter("tipoMovimiento");
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            String tipoReferencia = request.getParameter("tipoReferencia");
            String idReferenciaStr = request.getParameter("idReferencia");
            String notas = request.getParameter("notas");
            
            long idReferencia = 0;
            if (idReferenciaStr != null && !idReferenciaStr.isEmpty()) {
                idReferencia = Long.parseLong(idReferenciaStr);
            }
            
            MovimientosInventarioModel m = new MovimientosInventarioModel();
            m.setMedicamentoId(medicamentoId);
            m.setTipoMovimiento(tipoMovimiento);
            m.setCantidad(cantidad);
            m.setTipoReferencia(tipoReferencia);
            m.setIdReferencia(idReferencia);
            m.setNotas(notas);
            m.setCreadoPor(usuario != null ? usuario.getId() : 1L);
            
            if (movimientosDAO.agregar(m)) {
                // Actualizar stock del medicamento
                MedicamentosModel medicamento = medicamentosDAO.buscarPorId(medicamentoId);
                if (medicamento != null && medicamento.getId() > 0) {
                    int stockActual = medicamento.getCantidadStock();
                    int nuevoStock = stockActual;
                    
                    if (tipoMovimiento.equals("entrada")) {
                        nuevoStock = stockActual + cantidad;
                    } else if (tipoMovimiento.equals("salida")) {
                        nuevoStock = stockActual - cantidad;
                    }
                    
                    medicamentosDAO.actualizarStock(medicamentoId, nuevoStock);
                }
                
                request.setAttribute("mensaje", "Movimiento registrado exitosamente");
            } else {
                request.setAttribute("error", "Error al registrar movimiento");
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
        
        if (movimientosDAO.eliminar(id)) {
            request.setAttribute("mensaje", "Movimiento eliminado exitosamente");
        } else {
            request.setAttribute("error", "Error al eliminar movimiento");
        }
        
        listar(request, response);
    }
    
    private void listarPorMedicamento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        long medicamentoId = Long.parseLong(request.getParameter("medicamentoId"));
        request.setAttribute("movimientos", movimientosDAO.listarPorMedicamento(medicamentoId));
        request.setAttribute("medicamentos", medicamentosDAO.listar());
        request.getRequestDispatcher("movimientos_inventario_lista.jsp").forward(request, response);
    }
    
    private void listarPorTipo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String tipoMovimiento = request.getParameter("tipoMovimiento");
        request.setAttribute("movimientos", movimientosDAO.listarPorTipo(tipoMovimiento));
        request.setAttribute("medicamentos", medicamentosDAO.listar());
        request.getRequestDispatcher("movimientos_inventario_lista.jsp").forward(request, response);
    }
    
    // REGISTRAR ENTRADA - Caso de uso: Recepción de compra de medicamentos
    private void registrarEntrada(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            HttpSession session = request.getSession();
            UsuariosResourse usuario = (UsuariosResourse) session.getAttribute("usuario");
            
            long medicamentoId = Long.parseLong(request.getParameter("medicamentoId"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            String notas = request.getParameter("notas");
            
            // Obtener stock actual
            MedicamentosModel medicamento = medicamentosDAO.buscarPorId(medicamentoId);
            
            if (medicamento != null && medicamento.getId() > 0) {
                int stockActual = medicamento.getCantidadStock();
                int nuevoStock = stockActual + cantidad;
                
                // Registrar movimiento
                MovimientosInventarioModel m = new MovimientosInventarioModel();
                m.setMedicamentoId(medicamentoId);
                m.setTipoMovimiento("entrada");
                m.setCantidad(cantidad);
                m.setTipoReferencia("compra");
                m.setIdReferencia(0L);
                m.setNotas(notas != null ? notas : "Entrada de inventario - Compra");
                m.setCreadoPor(usuario != null ? usuario.getId() : 1L);
                
                if (movimientosDAO.agregar(m)) {
                    // Actualizar stock
                    if (medicamentosDAO.actualizarStock(medicamentoId, nuevoStock)) {
                        request.setAttribute("mensaje", "Entrada registrada. Nuevo stock: " + nuevoStock);
                    } else {
                        request.setAttribute("error", "Movimiento registrado pero error al actualizar stock");
                    }
                } else {
                    request.setAttribute("error", "Error al registrar entrada");
                }
            } else {
                request.setAttribute("error", "Medicamento no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
        }
        
        listar(request, response);
    }
    
    // REGISTRAR SALIDA - Caso de uso: Ajuste de inventario, merma, vencimiento
    private void registrarSalida(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            HttpSession session = request.getSession();
            UsuariosResourse usuario = (UsuariosResourse) session.getAttribute("usuario");
            
            long medicamentoId = Long.parseLong(request.getParameter("medicamentoId"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            String motivo = request.getParameter("motivo"); // vencimiento, merma, ajuste
            String notas = request.getParameter("notas");
            
            // Obtener stock actual
            MedicamentosModel medicamento = medicamentosDAO.buscarPorId(medicamentoId);
            
            if (medicamento != null && medicamento.getId() > 0) {
                int stockActual = medicamento.getCantidadStock();
                
                // Verificar si hay suficiente stock
                if (stockActual >= cantidad) {
                    int nuevoStock = stockActual - cantidad;
                    
                    // Registrar movimiento
                    MovimientosInventarioModel m = new MovimientosInventarioModel();
                    m.setMedicamentoId(medicamentoId);
                    m.setTipoMovimiento("salida");
                    m.setCantidad(cantidad);
                    m.setTipoReferencia(motivo != null ? motivo : "ajuste");
                    m.setIdReferencia(0L);
                    m.setNotas(notas != null ? notas : "Salida de inventario - " + motivo);
                    m.setCreadoPor(usuario != null ? usuario.getId() : 1L);
                    
                    if (movimientosDAO.agregar(m)) {
                        // Actualizar stock
                        if (medicamentosDAO.actualizarStock(medicamentoId, nuevoStock)) {
                            request.setAttribute("mensaje", "Salida registrada. Nuevo stock: " + nuevoStock);
                        } else {
                            request.setAttribute("error", "Movimiento registrado pero error al actualizar stock");
                        }
                    } else {
                        request.setAttribute("error", "Error al registrar salida");
                    }
                } else {
                    request.setAttribute("error", "Stock insuficiente. Stock actual: " + stockActual);
                }
            } else {
                request.setAttribute("error", "Medicamento no encontrado");
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