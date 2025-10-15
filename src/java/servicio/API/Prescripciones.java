package servicio.API;
import Model.PrescripcionesModel;
import Config.Conexion;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("prescripciones")
public class Prescripciones {
    Conexion cn = new Conexion();
    
    @GET
    @Path("/lista")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PrescripcionesModel> listarPrescripciones() {
        List<PrescripcionesModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM prescripciones";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                PrescripcionesModel p = new PrescripcionesModel();
                p.setId(rs.getLong("id"));
                p.setConsultaId(rs.getLong("consulta_id"));
                p.setMedicamentoId(rs.getLong("medicamento_id"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setDosis(rs.getString("dosis"));
                p.setFrecuencia(rs.getString("frecuencia"));
                p.setDuracion(rs.getString("duracion"));
                p.setInstrucciones(rs.getString("instrucciones"));
                
                Timestamp creado = rs.getTimestamp("creado_en");
                p.setCreadoEn(creado != null ? creado.toLocalDateTime() : null);
                
                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    @POST
    @Path("/agregar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public int agregarPrescripcion(PrescripcionesModel prescripcion) {
        String sql = "INSERT INTO prescripciones (consulta_id, medicamento_id, cantidad, "
                   + "dosis, frecuencia, duracion, instrucciones, creado_en) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setLong(1, prescripcion.getConsultaId());
            ps.setLong(2, prescripcion.getMedicamentoId());
            ps.setInt(3, prescripcion.getCantidad());
            ps.setString(4, prescripcion.getDosis());
            ps.setString(5, prescripcion.getFrecuencia());
            ps.setString(6, prescripcion.getDuracion());
            ps.setString(7, prescripcion.getInstrucciones());
            
            return ps.executeUpdate() > 0 ? 1 : 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    @GET
    @Path("/por-consulta/{consultaId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PrescripcionesModel> prescripcionesPorConsulta(@PathParam("consultaId") long consultaId) {
        List<PrescripcionesModel> lista = new ArrayList<>();
        String sql = "SELECT p.*, m.nombre as nombre_medicamento " +
                    "FROM prescripciones p " +
                    "JOIN medicamentos m ON p.medicamento_id = m.id " +
                    "WHERE p.consulta_id = ?";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setLong(1, consultaId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                PrescripcionesModel p = new PrescripcionesModel();
                p.setId(rs.getLong("id"));
                p.setConsultaId(rs.getLong("consulta_id"));
                p.setMedicamentoId(rs.getLong("medicamento_id"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setDosis(rs.getString("dosis"));
                p.setFrecuencia(rs.getString("frecuencia"));
                p.setDuracion(rs.getString("duracion"));
                p.setInstrucciones(rs.getString("instrucciones"));
                
                Timestamp creado = rs.getTimestamp("creado_en");
                p.setCreadoEn(creado != null ? creado.toLocalDateTime() : null);
                
                lista.add(p);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}