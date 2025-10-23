package servicio.API;

import Model.PacientesModel;
import Config.Conexion;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

@Path("pacientes")
public class Pacientes {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    @GET
    @Path("/lista")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PacientesModel> listar() {
        List<PacientesModel> lista = new ArrayList<>();
        String sql = "SELECT p.*, u.nombre as usuario_nombre, u.apellido as usuario_apellido, "
                   + "u.correo as usuario_correo, u.telefono as usuario_telefono, "
                   + "u.rol as usuario_rol, u.activo as usuario_activo "
                   + "FROM pacientes p "
                   + "INNER JOIN usuarios u ON p.usuario_id = u.id "
                   + "ORDER BY p.creado_en DESC";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                PacientesModel p = new PacientesModel();
                p.setId(rs.getLong("id"));
                p.setUsuarioId(rs.getLong("usuario_id"));
                p.setCodigoPaciente(rs.getString("codigo_paciente"));
                
                Date fechaNac = rs.getDate("fecha_nacimiento");
                p.setFechaNacimiento(fechaNac != null ? fechaNac.toLocalDate() : null);
                
                p.setGenero(rs.getString("genero"));
                p.setDireccion(rs.getString("direccion"));
                p.setContactoEmergenciaNombre(rs.getString("contacto_emergencia_nombre"));
                p.setContactoEmergenciaTelefono(rs.getString("contacto_emergencia_telefono"));
                p.setHistorialMedico(rs.getString("historial_medico"));
                p.setAlergias(rs.getString("alergias"));
                
                Timestamp creado = rs.getTimestamp("creado_en");
                p.setCreadoEn(creado != null ? creado.toLocalDateTime() : null);
                
                Timestamp actualizado = rs.getTimestamp("actualizado_en");
                p.setActualizadoEn(actualizado != null ? actualizado.toLocalDateTime() : null);
                
                p.setUsuarioNombre(rs.getString("usuario_nombre"));
                p.setUsuarioApellido(rs.getString("usuario_apellido"));
                p.setUsuarioCorreo(rs.getString("usuario_correo"));
                p.setUsuarioTelefono(rs.getString("usuario_telefono"));
                p.setUsuarioRol(rs.getString("usuario_rol"));
                p.setUsuarioActivo(rs.getBoolean("usuario_activo"));
                
                lista.add(p);
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
    public PacientesModel buscarPorId(@PathParam("id") long id) {
        String sql = "SELECT p.*, u.nombre as usuario_nombre, u.apellido as usuario_apellido, "
                   + "u.correo as usuario_correo, u.telefono as usuario_telefono, "
                   + "u.rol as usuario_rol, u.activo as usuario_activo "
                   + "FROM pacientes p "
                   + "INNER JOIN usuarios u ON p.usuario_id = u.id "
                   + "WHERE p.id = ?";
        PacientesModel p = null;
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                p = new PacientesModel();
                p.setId(rs.getLong("id"));
                p.setUsuarioId(rs.getLong("usuario_id"));
                p.setCodigoPaciente(rs.getString("codigo_paciente"));
                
                Date fechaNac = rs.getDate("fecha_nacimiento");
                p.setFechaNacimiento(fechaNac != null ? fechaNac.toLocalDate() : null);
                
                p.setGenero(rs.getString("genero"));
                p.setDireccion(rs.getString("direccion"));
                p.setContactoEmergenciaNombre(rs.getString("contacto_emergencia_nombre"));
                p.setContactoEmergenciaTelefono(rs.getString("contacto_emergencia_telefono"));
                p.setHistorialMedico(rs.getString("historial_medico"));
                p.setAlergias(rs.getString("alergias"));
                
                Timestamp creado = rs.getTimestamp("creado_en");
                p.setCreadoEn(creado != null ? creado.toLocalDateTime() : null);
                
                Timestamp actualizado = rs.getTimestamp("actualizado_en");
                p.setActualizadoEn(actualizado != null ? actualizado.toLocalDateTime() : null);
                
                p.setUsuarioNombre(rs.getString("usuario_nombre"));
                p.setUsuarioApellido(rs.getString("usuario_apellido"));
                p.setUsuarioCorreo(rs.getString("usuario_correo"));
                p.setUsuarioTelefono(rs.getString("usuario_telefono"));
                p.setUsuarioRol(rs.getString("usuario_rol"));
                p.setUsuarioActivo(rs.getBoolean("usuario_activo"));
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
        return p;
    }
    
    @POST
    @Path("/agregar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public int agregar(String jsonString) {
        System.out.println("[v0] API Pacientes recibió: " + jsonString);
        
        String sql = "INSERT INTO pacientes (usuario_id, codigo_paciente, fecha_nacimiento, genero, "
                   + "direccion, contacto_emergencia_nombre, contacto_emergencia_telefono, "
                   + "historial_medico, alergias, creado_en, actualizado_en) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        
        try {
            // Parse JSON manually
            JSONObject json = new JSONObject(jsonString);
            
            con = cn.getConnection();
            
            String codigoPaciente = generarCodigoPaciente(con);
            System.out.println("[v0] Código de paciente generado: " + codigoPaciente);
            
            ps = con.prepareStatement(sql);
            
            ps.setLong(1, json.getLong("usuarioId"));
            ps.setString(2, codigoPaciente);
            
            // Handle fecha_nacimiento
            if (json.has("fechaNacimiento") && !json.isNull("fechaNacimiento")) {
                String fechaStr = json.getString("fechaNacimiento");
                ps.setDate(3, Date.valueOf(fechaStr));
            } else {
                ps.setNull(3, java.sql.Types.DATE);
            }
            
            ps.setString(4, json.optString("genero", null));
            ps.setString(5, json.optString("direccion", null));
            ps.setString(6, json.optString("contactoEmergenciaNombre", null));
            ps.setString(7, json.optString("contactoEmergenciaTelefono", null));
            ps.setString(8, json.optString("historialMedico", null));
            ps.setString(9, json.optString("alergias", null));
            
            int result = ps.executeUpdate();
            System.out.println("[v0] Paciente insertado, filas afectadas: " + result);
            
            return result > 0 ? 1 : 0;
            
        } catch (Exception e) {
            System.out.println("[v0] Error en agregar paciente: " + e.getMessage());
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
    public int modificar(PacientesModel paciente) {
        String sql = "UPDATE pacientes SET fecha_nacimiento=?, genero=?, direccion=?, "
                   + "contacto_emergencia_nombre=?, contacto_emergencia_telefono=?, "
                   + "historial_medico=?, alergias=?, actualizado_en=CURRENT_TIMESTAMP "
                   + "WHERE id=?";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setDate(1, paciente.getFechaNacimiento() != null ? 
                      Date.valueOf(paciente.getFechaNacimiento()) : null);
            ps.setString(2, paciente.getGenero());
            ps.setString(3, paciente.getDireccion());
            ps.setString(4, paciente.getContactoEmergenciaNombre());
            ps.setString(5, paciente.getContactoEmergenciaTelefono());
            ps.setString(6, paciente.getHistorialMedico());
            ps.setString(7, paciente.getAlergias());
            ps.setLong(8, paciente.getId());
            
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
        String sql = "DELETE FROM pacientes WHERE id = ?";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            
            int result = ps.executeUpdate();
            System.out.println("[v0] Paciente eliminado, filas afectadas: " + result);
            
            return result > 0 ? 1 : 0;
            
        } catch (Exception e) {
            System.out.println("[v0] Error en eliminar paciente: " + e.getMessage());
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
    
    private String generarCodigoPaciente(Connection con) throws SQLException {
        String sql = "SELECT COUNT(*) + 1 as siguiente FROM pacientes";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                int siguiente = rs.getInt("siguiente");
                return String.format("PAC-%05d", siguiente);
            }
            
            return "PAC-00001";
            
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }
    }
}
