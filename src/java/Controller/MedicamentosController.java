package Controller;

import DAO.MedicamentosDAO;
import Model.MedicamentosModel;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;

public class MedicamentosController extends HttpServlet {
    
    MedicamentosDAO dao = new MedicamentosDAO();
    MedicamentosModel medicamento = new MedicamentosModel();
    
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
            List<MedicamentosModel> lista = dao.Listar();
            request.setAttribute("medicamentos", lista);
            request.getRequestDispatcher("Medicamentos/medicamentos_lista.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al listar medicamentos: " + e.getMessage());
            request.getRequestDispatcher("Medicamentos/medicamentos_lista.jsp").forward(request, response);
        }
    }
    
    protected void nuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Medicamentos/medicamentos_nuevo.jsp").forward(request, response);
    }
    
    protected void registrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String nombre = request.getParameter("nombre");
            int cantidadStock = Integer.parseInt(request.getParameter("cantidadStock"));
            double precioUnitario = Double.parseDouble(request.getParameter("precioUnitario"));
            String fechaExpiracionStr = request.getParameter("fechaExpiracion");
            String descripcion = request.getParameter("descripcion");
            
            medicamento.setNombre(nombre);
            medicamento.setCantidadStock(cantidadStock);
            medicamento.setPrecioUnitario(precioUnitario);
            
            if (fechaExpiracionStr != null && !fechaExpiracionStr.trim().isEmpty()) {
                medicamento.setFechaExpiracion(LocalDate.parse(fechaExpiracionStr));
            }
            
            medicamento.setDescripcion(descripcion);
            
            int resultado = dao.Agregar(medicamento);
            
            if (resultado > 0) {
                request.setAttribute("mensaje", "Medicamento registrado exitosamente");
                listar(request, response);
            } else {
                request.setAttribute("error", "Error al registrar medicamento");
                request.getRequestDispatcher("Medicamentos/medicamentos_nuevo.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("Medicamentos/medicamentos_nuevo.jsp").forward(request, response);
        }
    }
    
    protected void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            MedicamentosModel medicamentoEditar = dao.BuscarPorId(id);
            
            if (medicamentoEditar != null) {
                request.setAttribute("medicamento", medicamentoEditar);
                request.getRequestDispatcher("Medicamentos/medicamentos_editar.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Medicamento no encontrado");
                listar(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar medicamento: " + e.getMessage());
            listar(request, response);
        }
    }
    
    protected void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            String nombre = request.getParameter("nombre");
            int cantidadStock = Integer.parseInt(request.getParameter("cantidadStock"));
            double precioUnitario = Double.parseDouble(request.getParameter("precioUnitario"));
            String fechaExpiracionStr = request.getParameter("fechaExpiracion");
            String descripcion = request.getParameter("descripcion");
            
            MedicamentosModel medicamentoActualizar = new MedicamentosModel();
            medicamentoActualizar.setId(id);
            medicamentoActualizar.setNombre(nombre);
            medicamentoActualizar.setCantidadStock(cantidadStock);
            medicamentoActualizar.setPrecioUnitario(precioUnitario);
            
            if (fechaExpiracionStr != null && !fechaExpiracionStr.trim().isEmpty()) {
                medicamentoActualizar.setFechaExpiracion(LocalDate.parse(fechaExpiracionStr));
            }
            
            medicamentoActualizar.setDescripcion(descripcion);
            
            int resultado = dao.Actualizar(medicamentoActualizar);
            
            if (resultado > 0) {
                request.setAttribute("mensaje", "Medicamento actualizado exitosamente");
            } else {
                request.setAttribute("error", "No se pudo actualizar el medicamento");
            }
            
            listar(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al actualizar medicamento: " + e.getMessage());
            listar(request, response);
        }
    }
    
    protected void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            int resultado = dao.Eliminar(id);
            
            if (resultado > 0) {
                request.setAttribute("mensaje", "Medicamento eliminado exitosamente");
            } else {
                request.setAttribute("error", "No se pudo eliminar el medicamento");
            }
            
            listar(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al eliminar medicamento: " + e.getMessage());
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
