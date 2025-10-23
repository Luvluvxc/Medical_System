package servicio.API;

import Model.DoctoresModel;
import Config.Conexion;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

@Path("doctores")
public class Doctores {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    @GET
    @Path("/lista")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DoctoresModel> listar() {
        List<DoctoresModel> lista = new ArrayList<>();
        String sql = "SELECT d.*, u.nombre as usuario_nombre, u.apellido as usuario_apellido, "
                   + "u.correo as usuario_correo, u.telefono as usuario_telefono, "
                   + "u.rol as usuario_rol, u.activo as usuario_activo "
                   + "FROM doctores d "
                   + "INNER JOIN usuarios u ON d.usuario_id = u.id "
                   + "ORDER BY d.creado_en DESC";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                DoctoresModel d = new DoctoresModel();
                d.setId(rs.getLong("id"));
                d.setUsuarioId(rs.getInt("usuario_id"));
                d.setNumeroLicencia(rs.getString("numero_licencia"));
                d.setEspecializacion(rs.getString("especializacion"));
                
                Timestamp creado = rs.getTimestamp("creado_en");
                d.setCreadoEn(creado != null ? creado.toLocalDateTime() : null);
                
                Timestamp actualizado = rs.getTimestamp("actualizado_en");
                d.setActualizadoEn(actualizado != null ? actualizado.toLocalDateTime() : null);
                
                d.setUsuarioNombre(rs.getString("usuario_nombre"));
                d.setUsuarioApellido(rs.getString("usuario_apellido"));
                d.setUsuarioCorreo(rs.getString("usuario_correo"));
                d.setUsuarioTelefono(rs.getString("usuario_telefono"));
                d.setUsuarioRol(rs.getString("usuario_rol"));
                d.setUsuarioActivo(rs.getBoolean("usuario_activo"));
                
                lista.add(d);
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
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DoctoresModel buscarPorId(@PathParam("id") long id) {
        String sql = "SELECT d.*, u.nombre as usuario_nombre, u.apellido as usuario_apellido, "
                   + "u.correo as usuario_correo, u.telefono as usuario_telefono, "
                   + "u.rol as usuario_rol, u.activo as usuario_activo "
                   + "FROM doctores d "
                   + "INNER JOIN usuarios u ON d.usuario_id = u.id "
                   + "WHERE d.id = ?";
        DoctoresModel d = null;
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                d = new DoctoresModel();
                d.setId(rs.getLong("id"));
                d.setUsuarioId(rs.getInt("usuario_id"));
                d.setNumeroLicencia(rs.getString("numero_licencia"));
                d.setEspecializacion(rs.getString("especializacion"));
                
                Timestamp creado = rs.getTimestamp("creado_en");
                d.setCreadoEn(creado != null ? creado.toLocalDateTime() : null);
                
                Timestamp actualizado = rs.getTimestamp("actualizado_en");
                d.setActualizadoEn(actualizado != null ? actualizado.toLocalDateTime() : null);
                
                d.setUsuarioNombre(rs.getString("usuario_nombre"));
                d.setUsuarioApellido(rs.getString("usuario_apellido"));
                d.setUsuarioCorreo(rs.getString("usuario_correo"));
                d.setUsuarioTelefono(rs.getString("usuario_telefono"));
                d.setUsuarioRol(rs.getString("usuario_rol"));
                d.setUsuarioActivo(rs.getBoolean("usuario_activo"));
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
        return d;
    }
    
    @POST
    @Path("/agregar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public int agregar(String jsonString) {
        System.out.println("[v0] API Doctores recibió: " + jsonString);
        
        String sql = "INSERT INTO doctores (usuario_id, numero_licencia, especializacion, "
                   + "creado_en, actualizado_en) "
                   + "VALUES (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        
        try {
            JSONObject json = new JSONObject(jsonString);
            
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setInt(1, json.getInt("usuarioId"));
            ps.setString(2, json.getString("numeroLicencia"));
            ps.setString(3, json.optString("especializacion", null));
            
            int result = ps.executeUpdate();
            System.out.println("[v0] Doctor insertado, filas afectadas: " + result);
            
            return result > 0 ? 1 : 0;
            
        } catch (Exception e) {
            System.out.println("[v0] Error en agregar doctor: " + e.getMessage());
            e.printStackTrace();
            return 0;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    @PUT
    @Path("/modificar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public int modificar(DoctoresModel doctor) {
        String sql = "UPDATE doctores SET numero_licencia=?, especializacion=?, "
                   + "actualizado_en=CURRENT_TIMESTAMP WHERE id=?";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, doctor.getNumeroLicencia());
            ps.setString(2, doctor.getEspecializacion());
            ps.setLong(3, doctor.getId());
            
            return ps.executeUpdate() > 0 ? 1 : 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    @DELETE
    @Path("/eliminar/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public int eliminar(@PathParam("id") long id) {
        String sql = "DELETE FROM doctores WHERE id = ?";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            
            int result = ps.executeUpdate();
            System.out.println("[v0] Doctor eliminado, filas afectadas: " + result);
            
            return result > 0 ? 1 : 0;
            
        } catch (Exception e) {
            System.out.println("[v0] Error en eliminar doctor: " + e.getMessage());
            e.printStackTrace();
            return 0;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
