package Controller;

import Model.UsuariosResourse;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Controlador", urlPatterns = {"/Controlador"})
public class Controller extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String menu = request.getParameter("menu");
        System.out.println("=== CONTROLADOR ===");
        System.out.println("Menu: " + menu);
        
        if (menu != null && menu.equals("Principal")) {
            System.out.println("Redirigiendo a Principal.jsp");
            request.getRequestDispatcher("Principal.jsp").forward(request, response);
        } else {
            System.out.println("Redirigiendo a index.jsp");
            response.sendRedirect("index.jsp");
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