/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package servicio.API;

import Model.UsuariosResourse;
import Config.Conexion;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;


/**
 * REST Web Service
 *
 * @author marli
 */




@Path("generic")
public class Usuarios {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    @Context
    private UriInfo context;

    public List<UsuariosResourse> Consultar() {
    List<UsuariosResourse> lista = new ArrayList<>();

    String sql = "SELECT * FROM usuarios";
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            UsuariosResourse u = new UsuariosResourse();
            u.setId(rs.getInt("id"));
            u.setCorreo(rs.getString("correo"));
            u.setContrasenaHash(rs.getString("contrasena_hash"));
            u.setRol(rs.getString("rol"));
            u.setNombre(rs.getString("nombre"));
            u.setApellido(rs.getString("apellido"));
            u.setTelefono(rs.getString("telefono"));
            u.setActivo(rs.getBoolean("activo"));
            java.sql.Timestamp creadoTs = rs.getTimestamp("creado_en");
            u.setCreadoEn(creadoTs != null ? creadoTs.toLocalDateTime() : null);

            java.sql.Timestamp actualizadoTs = rs.getTimestamp("actualizado_en");
            u.setActualizadoEn(actualizadoTs != null ? actualizadoTs.toLocalDateTime() : null);

            lista.add(u);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    return lista;
}
    
@GET
@Path("/lista")
@Produces(MediaType.APPLICATION_JSON)
public List<UsuariosResourse> listar() {
    return Consultar();
}


@POST
@Path("/agregar")
@Consumes(MediaType.APPLICATION_JSON) // Espera JSON en el cuerpo
@Produces(MediaType.TEXT_PLAIN)       // Devuelve 1 o 0 según éxito
public int agregar(UsuariosResourse u) {
    // SQL adaptado a tu tabla
    String sql = "INSERT INTO usuarios (correo, contrasena_hash, rol, nombre, apellido, telefono, activo, creado_en, actualizado_en) "
           + "VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";


    try (
        Connection con = cn.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
    ) {
        ps.setString(1, u.getCorreo());
        ps.setString(2, u.getContrasenaHash());
        ps.setString(3, u.getRol());
        ps.setString(4, u.getNombre());
        ps.setString(5, u.getApellido());
        ps.setString(6, u.getTelefono());
        ps.setBoolean(7, true); // para que siempre este activo al crearlo


        // Campos adicionales
        ps.setTimestamp(8, u.getCreadoEn() != null ? java.sql.Timestamp.valueOf(u.getCreadoEn()) : java.sql.Timestamp.valueOf(LocalDateTime.now()));
        ps.setTimestamp(9, u.getActualizadoEn() != null ? java.sql.Timestamp.valueOf(u.getActualizadoEn()) : java.sql.Timestamp.valueOf(LocalDateTime.now()));

        ps.executeUpdate();
        return 1;

    } catch (Exception e) {
        e.printStackTrace();
        return 0;
    }
}

@PUT
@Path("/modificar")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN)
public int modificar(UsuariosResourse u) {

    // Ahora actualizamos: rol, nombre, apellido, telefono, activo, contrasena_hash
    String sql = "UPDATE usuarios "
               + "SET rol=?, nombre=?, apellido=?, telefono=?, activo=?, contrasena_hash=?, actualizado_en=CURRENT_TIMESTAMP "
               + "WHERE id=?";

    try (
        Connection con = cn.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
    ) {
        // Asignamos los valores desde el objeto Usuario
        ps.setString(1, u.getRol());
        ps.setString(2, u.getNombre());
        ps.setString(3, u.getApellido());
        ps.setString(4, u.getTelefono());
        ps.setBoolean(5, u.isActivo()); // activo
        ps.setString(6, u.getContrasenaHash()); // contraseña (ya debería estar hasheada idealmente)
        ps.setLong(7, u.getId()); // WHERE id=?

        int rowsAffected = ps.executeUpdate(); 
        return (rowsAffected > 0) ? 1 : 0; 

    } catch (Exception e) {
        e.printStackTrace();
        return 0;
    }
}



}
