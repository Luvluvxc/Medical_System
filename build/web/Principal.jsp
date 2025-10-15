<%-- 
    Document   : Principal
    Created on : 3/10/2025, 10:43:35 p. m.
    Author     : marli
--%>

<!DOCTYPE html>
<html>
    <!-- ... resto del código ... -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.UsuariosResourse"%>
<%
    System.out.println("=== PRINCIPAL.JSP CARGADO ===");
    UsuariosResourse usuario = (UsuariosResourse) request.getAttribute("usuario");
    System.out.println("Usuario en Principal.jsp: " + usuario);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">

        <title>JSP Page</title>
    </head>
    <body>
        <h1>gagagag</h1>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>

    </body>
</html>
