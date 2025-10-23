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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;

@Path("generic")
public class Usuarios {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    @Context
    private UriInfo context;

    @GET
    @Path("/lista")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UsuariosResourse> listar(@QueryParam("rol") String rol) {
        List<UsuariosResourse> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        
        if (rol != null && !rol.trim().isEmpty()) {
            sql += " WHERE rol = ?";
        }

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            
            if (rol != null && !rol.trim().isEmpty()) {
                ps.setString(1, rol);
            }
            
            rs = ps.executeQuery();

            while (rs.next()) {
                UsuariosResourse u = new UsuariosResourse();
                u.setId(rs.getLong("id"));
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
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return lista;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public UsuariosResourse buscarPorId(@PathParam("id") long id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        UsuariosResourse user = null;

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = new UsuariosResourse();
                user.setId(rs.getLong("id"));
                user.setCorreo(rs.getString("correo"));
                user.setContrasenaHash(rs.getString("contrasena_hash"));
                user.setRol(rs.getString("rol"));
                user.setNombre(rs.getString("nombre"));
                user.setApellido(rs.getString("apellido"));
                user.setTelefono(rs.getString("telefono"));
                user.setActivo(rs.getBoolean("activo"));

                java.sql.Timestamp creadoTs = rs.getTimestamp("creado_en");
                user.setCreadoEn(creadoTs != null ? creadoTs.toLocalDateTime() : null);

                java.sql.Timestamp actualizadoTs = rs.getTimestamp("actualizado_en");
                user.setActualizadoEn(actualizadoTs != null ? actualizadoTs.toLocalDateTime() : null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return user;
    }

    @POST
    @Path("/agregar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UsuariosResourse agregar(String jsonString) {
        System.out.println("[v0] API recibió: " + jsonString);  // <-- AGREGAR ESTO
        String sql = "INSERT INTO usuarios (correo, contrasena_hash, rol, nombre, apellido, telefono, activo, creado_en, actualizado_en) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try {
            // Parsear JSON manualmente
            org.json.JSONObject json = new org.json.JSONObject(jsonString);

            con = cn.getConnection();
            ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, json.getString("correo"));
            ps.setString(2, json.getString("contrasenaHash"));
            ps.setString(3, json.getString("rol"));
            ps.setString(4, json.optString("nombre", ""));
            ps.setString(5, json.optString("apellido", ""));
            ps.setString(6, json.optString("telefono", ""));
            ps.setBoolean(7, json.optBoolean("activo", true));

            int affectedRows = ps.executeUpdate();

            UsuariosResourse u = new UsuariosResourse();
            if (affectedRows > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    u.setId(generatedKeys.getLong(1));
                    u.setCorreo(json.getString("correo"));
                    u.setRol(json.getString("rol"));
                    u.setNombre(json.optString("nombre", ""));
                    u.setApellido(json.optString("apellido", ""));
                    u.setTelefono(json.optString("telefono", ""));
                    u.setActivo(json.optBoolean("activo", true));
                }
            }

            return u;

        } catch (Exception e) {
            System.out.println("[v0] Error en agregar: " + e.getMessage());
            e.printStackTrace();
            System.out.println("[v0] API recibió: " + jsonString);  // <-- AGREGAR ESTO
            return null;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @PUT
    @Path("/modificar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public int modificar(UsuariosResourse u) {
        String sql = "UPDATE usuarios "
                + "SET rol=?, nombre=?, apellido=?, telefono=?, activo=?, contrasena_hash=?, actualizado_en=CURRENT_TIMESTAMP "
                + "WHERE id=?";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, u.getRol());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getApellido());
            ps.setString(4, u.getTelefono());
            ps.setBoolean(5, u.isActivo());
            ps.setString(6, u.getContrasenaHash());
            ps.setLong(7, u.getId());

            int rowsAffected = ps.executeUpdate();
            return (rowsAffected > 0) ? 1 : 0;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
