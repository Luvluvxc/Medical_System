package Controller;

import Model.MedicamentosModel;
import DAO.MedicamentosDAO;
import DAO.UnidadesMedidaDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MedicamentosController", urlPatterns = {"/MedicamentosController"})
public class MedicamentosController extends HttpServlet {

    MedicamentosDAO medicamentosDAO = new MedicamentosDAO();
    UnidadesMedidaDAO unidadesDAO = new UnidadesMedidaDAO();
    
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
            case "eliminar":
                eliminar(request, response);
                break;
            case "buscar":
                buscar(request, response);
                break;
            case "stockBajo":
                listarStockBajo(request, response);
                break;
            case "proximosVencer":
                listarProximosVencer(request, response);
                break;
            case "actualizarStock":
                actualizarStock(request, response);
                break;
            default:
                listar(request, response);
                break;
        }
    }
    
    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("medicamentos", medicamentosDAO.listar());
        request.setAttribute("unidades", unidadesDAO.listar());
        request.getRequestDispatcher("medicamentos_lista.jsp").forward(request, response);
    }
    
    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("unidades", unidadesDAO.listar());
        request.getRequestDispatcher("medicamentos_nuevo.jsp").forward(request, response);
    }
    
    private void registrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String nombre = request.getParameter("nombre");
            String nombreGenerico = request.getParameter("nombreGenerico");
            String marca = request.getParameter("marca");
            long unidadMedidaId = Long.parseLong(request.getParameter("unidadMedidaId"));
            int cantidadStock = Integer.parseInt(request.getParameter("cantidadStock"));
            int nivelMinimoStock = Integer.parseInt(request.getParameter("nivelMinimoStock"));
            BigDecimal precioUnitario = new BigDecimal(request.getParameter("precioUnitario"));
            LocalDate fechaExpiracion = LocalDate.parse(request.getParameter("fechaExpiracion"));
            String numeroLote = request.getParameter("numeroLote");
            String descripcion = request.getParameter("descripcion");
            
            MedicamentosModel m = new MedicamentosModel();
            m.setNombre(nombre);
            m.setNombreGenerico(nombreGenerico);
            m.setMarca(marca);
            m.setUnidadMedidaId(unidadMedidaId);
            m.setCantidadStock(cantidadStock);
            m.setNivelMinimoStock(nivelMinimoStock);
            m.setPrecioUnitario(precioUnitario);
            m.setFechaExpiracion(fechaExpiracion);
            m.setNumeroLote(numeroLote);
            m.setDescripcion(descripcion);
            m.setActivo(true);
            
            if (medicamentosDAO.agregar(m)) {
                request.setAttribute("mensaje", "Medicamento registrado exitosamente");
            } else {
                request.setAttribute("error", "Error al registrar medicamento");
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
        MedicamentosModel m = medicamentosDAO.buscarPorId(id);
        
        request.setAttribute("medicamento", m);
        request.setAttribute("unidades", unidadesDAO.listar());
        request.getRequestDispatcher("medicamentos_editar.jsp").forward(request, response);
    }
    
    private void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            long id = Long.parseLong(request.getParameter("id"));
            String nombre = request.getParameter("nombre");
            String nombreGenerico = request.getParameter("nombreGenerico");
            String marca = request.getParameter("marca");
            long unidadMedidaId = Long.parseLong(request.getParameter("unidadMedidaId"));
            int cantidadStock = Integer.parseInt(request.getParameter("cantidadStock"));
            int nivelMinimoStock = Integer.parseInt(request.getParameter("nivelMinimoStock"));
            BigDecimal precioUnitario = new BigDecimal(request.getParameter("precioUnitario"));
            LocalDate fechaExpiracion = LocalDate.parse(request.getParameter("fechaExpiracion"));
            String numeroLote = request.getParameter("numeroLote");
            String descripcion = request.getParameter("descripcion");
            boolean activo = Boolean.parseBoolean(request.getParameter("activo"));
            
            MedicamentosModel m = new MedicamentosModel();
            m.setId(id);
            m.setNombre(nombre);
            m.setNombreGenerico(nombreGenerico);
            m.setMarca(marca);
            m.setUnidadMedidaId(unidadMedidaId);
            m.setCantidadStock(cantidadStock);
            m.setNivelMinimoStock(nivelMinimoStock);
            m.setPrecioUnitario(precioUnitario);
            m.setFechaExpiracion(fechaExpiracion);
            m.setNumeroLote(numeroLote);
            m.setDescripcion(descripcion);
            m.setActivo(activo);
            
            if (medicamentosDAO.actualizar(m)) {
                request.setAttribute("mensaje", "Medicamento actualizado exitosamente");
            } else {
                request.setAttribute("error", "Error al actualizar medicamento");
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
        
        if (medicamentosDAO.eliminar(id)) {
            request.setAttribute("mensaje", "Medicamento eliminado exitosamente");
        } else {
            request.setAttribute("error", "Error al eliminar medicamento");
        }
        
        listar(request, response);
    }
    
    private void buscar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nombre = request.getParameter("nombre");
        request.setAttribute("medicamentos", medicamentosDAO.buscarPorNombre(nombre));
        request.setAttribute("unidades", unidadesDAO.listar());
        request.getRequestDispatcher("medicamentos_lista.jsp").forward(request, response);
    }
    
    private void listarStockBajo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("medicamentos", medicamentosDAO.listarStockBajo());
        request.setAttribute("unidades", unidadesDAO.listar());
        request.setAttribute("filtro", "Stock Bajo");
        request.getRequestDispatcher("medicamentos_lista.jsp").forward(request, response);
    }
    
    private void listarProximosVencer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int dias = 30; // Por defecto 30 días
        String diasParam = request.getParameter("dias");
        if (diasParam != null && !diasParam.isEmpty()) {
            dias = Integer.parseInt(diasParam);
        }
        
        request.setAttribute("medicamentos", medicamentosDAO.listarProximosAVencer(dias));
        request.setAttribute("unidades", unidadesDAO.listar());
        request.setAttribute("filtro", "Próximos a Vencer (" + dias + " días)");
        request.getRequestDispatcher("medicamentos_lista.jsp").forward(request, response);
    }
    
    private void actualizarStock(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            long id = Long.parseLong(request.getParameter("id"));
            int nuevaCantidad = Integer.parseInt(request.getParameter("nuevaCantidad"));
            
            if (medicamentosDAO.actualizarStock(id, nuevaCantidad)) {
                request.setAttribute("mensaje", "Stock actualizado exitosamente");
            } else {
                request.setAttribute("error", "Error al actualizar stock");
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