package Controller;

import DAO.CitasDAO;
import DAO.DoctoresDAO;
import DAO.MedicamentosDAO;
import DAO.ConsultasDAO;
import Model.CitasModel;
import Model.DoctoresModel;
import Model.MedicamentosModel;
import Model.ConsultasModel;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DashboardController", urlPatterns = {"/DashboardController"})
public class DashboardController extends HttpServlet {

    CitasDAO citasDAO = new CitasDAO();
    DoctoresDAO doctoresDAO = new DoctoresDAO();
    MedicamentosDAO medicamentosDAO = new MedicamentosDAO();
    ConsultasDAO consultasDAO = new ConsultasDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        
        if (accion == null || accion.equals("dashboard")) {
            cargarDashboard(request, response);
        }
    }

    private void cargarDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener listas de datos
            List<CitasModel> citas = citasDAO.Listar();
            List<DoctoresModel> doctores = doctoresDAO.Listar();
            List<MedicamentosModel> medicamentos = medicamentosDAO.Listar();
            List<ConsultasModel> consultas = consultasDAO.Listar();

            // Calcular totales
            request.setAttribute("totalCitas", citas != null ? citas.size() : 0);
            request.setAttribute("totalDoctores", doctores != null ? doctores.size() : 0);
            request.setAttribute("totalMedicamentos", medicamentos != null ? medicamentos.size() : 0);
            request.setAttribute("totalConsultas", consultas != null ? consultas.size() : 0);

            // Pasar las listas completas
            request.setAttribute("citas", citas);
            request.setAttribute("doctores", doctores);
            request.setAttribute("medicamentos", medicamentos);

            // Calcular alertas
            int citasPendientes = 0;
            if (citas != null) {
                for (CitasModel cita : citas) {
                    if ("Pendiente".equals(cita.getEstado())) {
                        citasPendientes++;
                    }
                }
            }
            request.setAttribute("citasPendientes", citasPendientes);

            // Medicamentos con stock bajo (menos de 10)
            int medicamentosStockBajo = 0;
            if (medicamentos != null) {
                for (MedicamentosModel med : medicamentos) {
                    if (med.getCantidadStock() < 10) {
                        medicamentosStockBajo++;
                    }
                }
            }
            request.setAttribute("medicamentosStockBajo", medicamentosStockBajo);

            request.getRequestDispatcher("views/dashboard_recepcionista.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
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
