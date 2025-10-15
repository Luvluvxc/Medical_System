package servicio.API;
import Model.MovimientosInventarioModel;
import Config.Conexion;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Path("movimientos-inventario")
public class MovimientosInventario {
    Conexion cn = new Conexion();
    
    @GET
    @Path("/lista")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MovimientosInventarioModel> listarMovimientos() {
        List<MovimientosInventarioModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM movimientos_inventario ORDER BY creado_en DESC";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                MovimientosInventarioModel m = new MovimientosInventarioModel();
                m.setId(rs.getLong("id"));
                m.setMedicamentoId(rs.getLong("medicamento_id"));
                m.setTipoMovimiento(rs.getString("tipo_movimiento"));
                m.setCantidad(rs.getInt("cantidad"));
                m.setTipoReferencia(rs.getString("tipo_referencia"));
                m.setIdReferencia(rs.getLong("id_referencia"));
                m.setNotas(rs.getString("notas"));
                m.setCreadoPor(rs.getLong("creado_por"));
                
                Timestamp creado = rs.getTimestamp("creado_en");
                m.setCreadoEn(creado != null ? creado.toLocalDateTime() : null);
                
                lista.add(m);
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
    public int agregarMovimiento(MovimientosInventarioModel movimiento) {
        String sql = "INSERT INTO movimientos_inventario (medicamento_id, tipo_movimiento, "
                   + "cantidad, tipo_referencia, id_referencia, notas, creado_por, creado_en) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setLong(1, movimiento.getMedicamentoId());
            ps.setString(2, movimiento.getTipoMovimiento());
            ps.setInt(3, movimiento.getCantidad());
            ps.setString(4, movimiento.getTipoReferencia());
            ps.setObject(5, movimiento.getIdReferencia());
            ps.setString(6, movimiento.getNotas());
            ps.setObject(7, movimiento.getCreadoPor());
            
            return ps.executeUpdate() > 0 ? 1 : 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    @GET
    @Path("/por-medicamento/{medicamentoId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MovimientosInventarioModel> movimientosPorMedicamento(@PathParam("medicamentoId") long medicamentoId) {
        List<MovimientosInventarioModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM movimientos_inventario WHERE medicamento_id = ? ORDER BY creado_en DESC";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setLong(1, medicamentoId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                MovimientosInventarioModel m = new MovimientosInventarioModel();
                m.setId(rs.getLong("id"));
                m.setMedicamentoId(rs.getLong("medicamento_id"));
                m.setTipoMovimiento(rs.getString("tipo_movimiento"));
                m.setCantidad(rs.getInt("cantidad"));
                m.setTipoReferencia(rs.getString("tipo_referencia"));
                m.setIdReferencia(rs.getLong("id_referencia"));
                m.setNotas(rs.getString("notas"));
                m.setCreadoPor(rs.getLong("creado_por"));
                
                Timestamp creado = rs.getTimestamp("creado_en");
                m.setCreadoEn(creado != null ? creado.toLocalDateTime() : null);
                
                lista.add(m);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}