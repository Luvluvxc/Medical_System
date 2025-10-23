package servicio.API;

import Model.CitasModel;
import Config.Conexion;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

@Path("citas")
public class Citas {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    @GET
    @Path("/lista")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CitasModel> listar() {
        List<CitasModel> lista = new ArrayList<>();
        String sql = "SELECT c.*, " +
                    "up.nombre as paciente_nombre, up.apellido as paciente_apellido, " +
                    "ud.nombre as doctor_nombre, ud.apellido as doctor_apellido, " +
                    "d.especializacion as doctor_especializacion " +
                    "FROM citas c " +
                    "INNER JOIN pacientes p ON c.paciente_id = p.id " +
                    "INNER JOIN usuarios up ON p.usuario_id = up.id " +
                    "INNER JOIN doctores d ON c.doctor_id = d.id " +
                    "INNER JOIN usuarios ud ON d.usuario_id = ud.id " +
                    "ORDER BY c.fecha_cita DESC, c.hora_cita DESC";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                CitasModel cita = new CitasModel();
                cita.setId(rs.getLong("id"));
                cita.setPacienteId(rs.getInt("paciente_id"));
                cita.setDoctorId(rs.getInt("doctor_id"));
                
                Date fecha = rs.getDate("fecha_cita");
                cita.setFechaCita(fecha != null ? fecha.toLocalDate() : null);
                
                Time hora = rs.getTime("hora_cita");
                cita.setHoraCita(hora != null ? hora.toLocalTime() : null);
                
                cita.setEstado(rs.getString("estado"));
                cita.setMotivo(rs.getString("motivo"));
                
                cita.setPacienteNombre(rs.getString("paciente_nombre"));
                cita.setPacienteApellido(rs.getString("paciente_apellido"));
                cita.setDoctorNombre(rs.getString("doctor_nombre"));
                cita.setDoctorApellido(rs.getString("doctor_apellido"));
                cita.setDoctorEspecializacion(rs.getString("doctor_especializacion"));
                
                lista.add(cita);
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
    public CitasModel buscarPorId(@PathParam("id") long id) {
        String sql = "SELECT c.*, " +
                    "up.nombre as paciente_nombre, up.apellido as paciente_apellido, " +
                    "ud.nombre as doctor_nombre, ud.apellido as doctor_apellido, " +
                    "d.especializacion as doctor_especializacion " +
                    "FROM citas c " +
                    "INNER JOIN pacientes p ON c.paciente_id = p.id " +
                    "INNER JOIN usuarios up ON p.usuario_id = up.id " +
                    "INNER JOIN doctores d ON c.doctor_id = d.id " +
                    "INNER JOIN usuarios ud ON d.usuario_id = ud.id " +
                    "WHERE c.id = ?";
        CitasModel cita = null;
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                cita = new CitasModel();
                cita.setId(rs.getLong("id"));
                cita.setPacienteId(rs.getInt("paciente_id"));
                cita.setDoctorId(rs.getInt("doctor_id"));
                
                Date fecha = rs.getDate("fecha_cita");
                cita.setFechaCita(fecha != null ? fecha.toLocalDate() : null);
                
                Time hora = rs.getTime("hora_cita");
                cita.setHoraCita(hora != null ? hora.toLocalTime() : null);
                
                cita.setEstado(rs.getString("estado"));
                cita.setMotivo(rs.getString("motivo"));
                
                cita.setPacienteNombre(rs.getString("paciente_nombre"));
                cita.setPacienteApellido(rs.getString("paciente_apellido"));
                cita.setDoctorNombre(rs.getString("doctor_nombre"));
                cita.setDoctorApellido(rs.getString("doctor_apellido"));
                cita.setDoctorEspecializacion(rs.getString("doctor_especializacion"));
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
        return cita;
    }
        @POST
@Path("/agregar")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public String agregar(String jsonString) {
    System.out.println("[v0] ========================================");
    System.out.println("[v0] API Citas - Método agregar iniciado");
    System.out.println("[v0] JSON recibido: " + jsonString);
    
    String sql = "INSERT INTO citas (paciente_id, doctor_id, fecha_cita, hora_cita, estado, motivo) "
               + "VALUES (?, ?, ?, ?, ?, ?)";
    
    try {
        JSONObject json = new JSONObject(jsonString);
        
        // Validar que los campos requeridos existan
        if (!json.has("pacienteId") || json.isNull("pacienteId")) {
            throw new Exception("Campo pacienteId es requerido");
        }
        if (!json.has("doctorId") || json.isNull("doctorId")) {
            throw new Exception("Campo doctorId es requerido");
        }
        if (!json.has("fechaCita") || json.isNull("fechaCita")) {
            throw new Exception("Campo fechaCita es requerido");
        }
        if (!json.has("horaCita") || json.isNull("horaCita")) {
            throw new Exception("Campo horaCita es requerido");
        }
        
        int pacienteId = json.getInt("pacienteId");
        int doctorId = json.getInt("doctorId");
        String fechaStr = json.getString("fechaCita");
        String horaStr = json.getString("horaCita");
        String estado = json.optString("estado", "Pendiente");
        String motivo = json.optString("motivo", "");
        
        System.out.println("[v0] Datos parseados:");
        System.out.println("[v0]   - pacienteId: " + pacienteId);
        System.out.println("[v0]   - doctorId: " + doctorId);
        System.out.println("[v0]   - fechaCita: " + fechaStr);
        System.out.println("[v0]   - horaCita: " + horaStr);
        System.out.println("[v0]   - estado: " + estado);
        System.out.println("[v0]   - motivo: " + motivo);
        
        con = cn.getConnection();
        System.out.println("[v0] Conexión a BD establecida");
        
        ps = con.prepareStatement(sql);
        
        ps.setInt(1, pacienteId);
        ps.setInt(2, doctorId);
        
        try {
            Date fecha = Date.valueOf(fechaStr);
            ps.setDate(3, fecha);
            System.out.println("[v0] Fecha parseada correctamente: " + fecha);
        } catch (Exception e) {
            throw new Exception("Formato de fecha inválido: " + fechaStr + ". Use formato YYYY-MM-DD");
        }
        
        try {
            // <CHANGE> Agregar segundos si no están presentes
            if (horaStr.length() == 5 && horaStr.matches("\\d{2}:\\d{2}")) {
                horaStr = horaStr + ":00";
                System.out.println("[v0] Hora ajustada a formato completo: " + horaStr);
            }
            
            Time hora = Time.valueOf(horaStr);
            ps.setTime(4, hora);
            System.out.println("[v0] Hora parseada correctamente: " + hora);
        } catch (Exception e) {
            throw new Exception("Formato de hora inválido: " + horaStr + ". Error: " + e.getMessage());
        }
        
        ps.setString(5, estado);
        ps.setString(6, motivo.isEmpty() ? null : motivo);
        
        System.out.println("[v0] Ejecutando INSERT...");
        int result = ps.executeUpdate();
        System.out.println("[v0] INSERT ejecutado, filas afectadas: " + result);
        
        JSONObject response = new JSONObject();
        if (result > 0) {
            response.put("success", true);
            response.put("message", "Cita creada exitosamente");
            response.put("result", 1);
            System.out.println("[v0] Cita creada exitosamente");
        } else {
            response.put("success", false);
            response.put("message", "No se pudo crear la cita - ninguna fila afectada");
            response.put("result", 0);
            System.out.println("[v0] No se insertó ninguna fila");
        }
        
        System.out.println("[v0] ========================================");
        return response.toString();
        
    } catch (Exception e) {
        System.out.println("[v0] ERROR en agregar cita:");
        System.out.println("[v0] Tipo de error: " + e.getClass().getName());
        System.out.println("[v0] Mensaje: " + e.getMessage());
        e.printStackTrace();
        System.out.println("[v0] ========================================");
        
        JSONObject errorResponse = new JSONObject();
        errorResponse.put("success", false);
        errorResponse.put("message", "Error al crear cita: " + (e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName()));
        errorResponse.put("result", 0);
        return errorResponse.toString();
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
    public int modificar(CitasModel cita) {
        String sql = "UPDATE citas SET paciente_id=?, doctor_id=?, fecha_cita=?, hora_cita=?, "
                   + "estado=?, motivo=? WHERE id=?";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setInt(1, cita.getPacienteId());
            ps.setInt(2, cita.getDoctorId());
            ps.setDate(3, cita.getFechaCita() != null ? 
                      Date.valueOf(cita.getFechaCita()) : null);
            ps.setTime(4, cita.getHoraCita() != null ? 
                      Time.valueOf(cita.getHoraCita()) : null);
            ps.setString(5, cita.getEstado());
            ps.setString(6, cita.getMotivo());
            ps.setLong(7, cita.getId());
            
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
        String sql = "DELETE FROM citas WHERE id = ?";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            
            int result = ps.executeUpdate();
            System.out.println("[v0] Cita eliminada, filas afectadas: " + result);
            
            return result > 0 ? 1 : 0;
            
        } catch (Exception e) {
            System.out.println("[v0] Error en eliminar cita: " + e.getMessage());
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
