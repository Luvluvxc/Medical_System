package Controller;

import Model.UsuariosResourse;
import DAO.UsuarioDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Validar", urlPatterns = {"/Validar"})
public class Validar extends HttpServlet {

    UsuarioDAO udao = new UsuarioDAO();
    UsuariosResourse u = new UsuariosResourse();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        System.out.println("=== VALIDAR ===");
        System.out.println("Acción: " + accion);

        if (accion != null && accion.equals("Ingresar")) {
            String correo = request.getParameter("txtCorreo");
            String contrasenaHash = request.getParameter("txtPassword");

            u = udao.Validar(correo, contrasenaHash);

            if (u.getNombre() != null) {
                System.out.println("✅ LOGIN EXITOSO - Usuario: " + u.getNombre());
                System.out.println("✅ Rol: " + u.getRol());
                System.out.println("✅ ID: " + u.getId()); // Agrega este log

                // Crear sesión y guardar el usuario
                HttpSession session = request.getSession();
                session.setAttribute("usuario", u);
                
                // GUARDAR EL ID SEGÚN EL ROL
                if ("doctor".equalsIgnoreCase(u.getRol()) || "médico".equalsIgnoreCase(u.getRol())) {
                    session.setAttribute("doctorId", u.getId());
                    System.out.println("✅ Doctor ID guardado en sesión: " + u.getId());
                } else if ("paciente".equalsIgnoreCase(u.getRol())) {
                    session.setAttribute("pacienteId", u.getId());
                    System.out.println("✅ Paciente ID guardado en sesión: " + u.getId());
                }

                // Redirigir según el rol
                String rol = u.getRol();
                String dashboardPage = "";

                switch (rol.toLowerCase()) {
                    case "admin":
                    case "administrador":
                        dashboardPage = "dashboard_admin.jsp";
                        break;
                    case "doctor":
                    case "medico":
                    case "médico":
                        dashboardPage = "dashboard_doctor.jsp";
                        break;
                    case "paciente":
                        dashboardPage = "dashboard_paciente.jsp";
                        break;
                    case "recepcionista":
                        dashboardPage = "dashboard_recepcionista.jsp";
                        break;
                    default:
                        dashboardPage = "dashboard_simple.jsp";
                        System.out.println("⚠️ Rol no reconocido, usando dashboard simple");
                        break;
                }

                System.out.println("Redirigiendo a: " + dashboardPage);
                request.getRequestDispatcher(dashboardPage).forward(request, response);

            } else {
                System.out.println("❌ LOGIN FALLIDO");
                request.setAttribute("error", "Usuario o contraseña incorrectos");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } else if (accion != null && accion.equals("salir")) {
            // Cerrar sesión
            HttpSession session = request.getSession();
            session.invalidate();
            response.sendRedirect("index.jsp");
        } else {
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