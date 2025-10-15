package servicio.API;

import Model.MedicamentosModel;
import Config.Conexion;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Path("medicamentos")
public class Medicamentos {
    Conexion cn = new Conexion();
    
    @GET
    @Path("/lista")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MedicamentosModel> listarMedicamentos() {
        List<MedicamentosModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM medicamentos WHERE activo = true";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                MedicamentosModel m = new MedicamentosModel();
                m.setId(rs.getLong("id"));
                m.setNombre(rs.getString("nombre"));
                m.setNombreGenerico(rs.getString("nombre_generico"));
                m.setMarca(rs.getString("marca"));
                m.setUnidadMedidaId(rs.getLong("unidad_medida_id"));
                m.setCantidadStock(rs.getInt("cantidad_stock"));
                m.setNivelMinimoStock(rs.getInt("nivel_minimo_stock"));
                m.setPrecioUnitario(rs.getBigDecimal("precio_unitario"));
                
                Date fechaExp = rs.getDate("fecha_expiracion");
                m.setFechaExpiracion(fechaExp != null ? fechaExp.toLocalDate() : null);
                
                m.setNumeroLote(rs.getString("numero_lote"));
                m.setDescripcion(rs.getString("descripcion"));
                m.setActivo(rs.getBoolean("activo"));
                
                Timestamp creado = rs.getTimestamp("creado_en");
                m.setCreadoEn(creado != null ? creado.toLocalDateTime() : null);
                
                Timestamp actualizado = rs.getTimestamp("actualizado_en");
                m.setActualizadoEn(actualizado != null ? actualizado.toLocalDateTime() : null);
                
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
    public int agregarMedicamento(MedicamentosModel medicamento) {
        String sql = "INSERT INTO medicamentos (nombre, nombre_generico, marca, unidad_medida_id, "
                   + "cantidad_stock, nivel_minimo_stock, precio_unitario, fecha_expiracion, "
                   + "numero_lote, descripcion, activo, creado_en, actualizado_en) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, medicamento.getNombre());
            ps.setString(2, medicamento.getNombreGenerico());
            ps.setString(3, medicamento.getMarca());
            ps.setObject(4, medicamento.getUnidadMedidaId());
            ps.setInt(5, medicamento.getCantidadStock());
            ps.setInt(6, medicamento.getNivelMinimoStock());
            ps.setBigDecimal(7, medicamento.getPrecioUnitario());
            ps.setDate(8, medicamento.getFechaExpiracion() != null ? 
                      Date.valueOf(medicamento.getFechaExpiracion()) : null);
            ps.setString(9, medicamento.getNumeroLote());
            ps.setString(10, medicamento.getDescripcion());
            ps.setBoolean(11, true);
            
            return ps.executeUpdate() > 0 ? 1 : 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    @PUT
    @Path("/modificar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public int modificarMedicamento(MedicamentosModel medicamento) {
        String sql = "UPDATE medicamentos SET nombre=?, nombre_generico=?, marca=?, "
                   + "unidad_medida_id=?, nivel_minimo_stock=?, precio_unitario=?, "
                   + "fecha_expiracion=?, numero_lote=?, descripcion=?, activo=?, "
                   + "actualizado_en=CURRENT_TIMESTAMP WHERE id=?";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, medicamento.getNombre());
            ps.setString(2, medicamento.getNombreGenerico());
            ps.setString(3, medicamento.getMarca());
            ps.setObject(4, medicamento.getUnidadMedidaId());
            ps.setInt(5, medicamento.getNivelMinimoStock());
            ps.setBigDecimal(6, medicamento.getPrecioUnitario());
            ps.setDate(7, medicamento.getFechaExpiracion() != null ? 
                      Date.valueOf(medicamento.getFechaExpiracion()) : null);
            ps.setString(8, medicamento.getNumeroLote());
            ps.setString(9, medicamento.getDescripcion());
            ps.setBoolean(10, medicamento.isActivo());
            ps.setLong(11, medicamento.getId());
            
            return ps.executeUpdate() > 0 ? 1 : 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    @PUT
    @Path("/actualizar-stock/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public int actualizarStock(@PathParam("id") long id, int nuevaCantidad) {
        String sql = "UPDATE medicamentos SET cantidad_stock=?, actualizado_en=CURRENT_TIMESTAMP WHERE id=?";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, nuevaCantidad);
            ps.setLong(2, id);
            
            return ps.executeUpdate() > 0 ? 1 : 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    @GET
    @Path("/bajo-stock")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MedicamentosModel> medicamentosBajoStock() {
        List<MedicamentosModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM medicamentos WHERE cantidad_stock <= nivel_minimo_stock AND activo = true";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                MedicamentosModel m = new MedicamentosModel();
                m.setId(rs.getLong("id"));
                m.setNombre(rs.getString("nombre"));
                m.setCantidadStock(rs.getInt("cantidad_stock"));
                m.setNivelMinimoStock(rs.getInt("nivel_minimo_stock"));
                m.setPrecioUnitario(rs.getBigDecimal("precio_unitario"));
                lista.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}