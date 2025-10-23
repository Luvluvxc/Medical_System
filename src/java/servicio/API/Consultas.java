package servicio.API;

import Model.ConsultasModel;
import Config.Conexion;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

@Path("consultas")
public class Consultas {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    @GET
    @Path("/lista")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ConsultasModel> listar() {
        List<ConsultasModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM consultas ORDER BY id DESC";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                ConsultasModel c = new ConsultasModel();
                c.setId(rs.getLong("id"));
                c.setCitaId(rs.getInt("cita_id"));
                c.setDiagnostico(rs.getString("diagnostico"));
                c.setPlanTratamiento(rs.getString("plan_tratamiento"));
                c.setObservaciones(rs.getString("observaciones"));
                
                lista.add(c);
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
    public ConsultasModel buscarPorId(@PathParam("id") long id) {
        String sql = "SELECT * FROM consultas WHERE id = ?";
        ConsultasModel c = null;
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                c = new ConsultasModel();
                c.setId(rs.getLong("id"));
                c.setCitaId(rs.getInt("cita_id"));
                c.setDiagnostico(rs.getString("diagnostico"));
                c.setPlanTratamiento(rs.getString("plan_tratamiento"));
                c.setObservaciones(rs.getString("observaciones"));
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
        return c;
    }
    
    @GET
    @Path("/cita/{citaId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ConsultasModel buscarPorCitaId(@PathParam("citaId") int citaId) {
        String sql = "SELECT * FROM consultas WHERE cita_id = ?";
        ConsultasModel c = null;
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, citaId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                c = new ConsultasModel();
                c.setId(rs.getLong("id"));
                c.setCitaId(rs.getInt("cita_id"));
                c.setDiagnostico(rs.getString("diagnostico"));
                c.setPlanTratamiento(rs.getString("plan_tratamiento"));
                c.setObservaciones(rs.getString("observaciones"));
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
        return c;
    }
    
    @POST
    @Path("/agregar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public int agregar(String jsonString) {
        System.out.println("[v0] API Consultas recibió: " + jsonString);
        
        String sql = "INSERT INTO consultas (cita_id, diagnostico, plan_tratamiento, observaciones) "
                   + "VALUES (?, ?, ?, ?)";
        
        try {
            JSONObject json = new JSONObject(jsonString);
            
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setInt(1, json.getInt("citaId"));
            ps.setString(2, json.optString("diagnostico", null));
            ps.setString(3, json.optString("planTratamiento", null));
            ps.setString(4, json.optString("observaciones", null));
            
            int result = ps.executeUpdate();
            System.out.println("[v0] Consulta insertada, filas afectadas: " + result);
            
            return result > 0 ? 1 : 0;
            
        } catch (Exception e) {
            System.out.println("[v0] Error en agregar consulta: " + e.getMessage());
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
    public int modificar(ConsultasModel consulta) {
        String sql = "UPDATE consultas SET diagnostico=?, plan_tratamiento=?, observaciones=? "
                   + "WHERE id=?";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, consulta.getDiagnostico());
            ps.setString(2, consulta.getPlanTratamiento());
            ps.setString(3, consulta.getObservaciones());
            ps.setLong(4, consulta.getId());
            
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
        String sql = "DELETE FROM consultas WHERE id = ?";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            
            int result = ps.executeUpdate();
            System.out.println("[v0] Consulta eliminada, filas afectadas: " + result);
            
            return result > 0 ? 1 : 0;
            
        } catch (Exception e) {
            System.out.println("[v0] Error en eliminar consulta: " + e.getMessage());
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
