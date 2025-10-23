package servicio.API;

import Model.MedicamentosModel;
import Config.Conexion;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

@Path("medicamentos")
public class Medicamentos {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    @GET
    @Path("/lista")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MedicamentosModel> listar() {
        List<MedicamentosModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM medicamentos ORDER BY nombre ASC";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                MedicamentosModel m = new MedicamentosModel();
                m.setId(rs.getLong("id"));
                m.setNombre(rs.getString("nombre"));
                m.setCantidadStock(rs.getInt("cantidad_stock"));
                m.setPrecioUnitario(rs.getDouble("precio_unitario"));
                
                Date fechaExp = rs.getDate("fecha_expiracion");
                m.setFechaExpiracion(fechaExp != null ? fechaExp.toLocalDate() : null);
                
                m.setDescripcion(rs.getString("descripcion"));
                
                lista.add(m);
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
    public MedicamentosModel buscarPorId(@PathParam("id") long id) {
        String sql = "SELECT * FROM medicamentos WHERE id = ?";
        MedicamentosModel m = null;
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                m = new MedicamentosModel();
                m.setId(rs.getLong("id"));
                m.setNombre(rs.getString("nombre"));
                m.setCantidadStock(rs.getInt("cantidad_stock"));
                m.setPrecioUnitario(rs.getDouble("precio_unitario"));
                
                Date fechaExp = rs.getDate("fecha_expiracion");
                m.setFechaExpiracion(fechaExp != null ? fechaExp.toLocalDate() : null);
                
                m.setDescripcion(rs.getString("descripcion"));
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
        return m;
    }
    
    @POST
    @Path("/agregar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public int agregar(String jsonString) {
        System.out.println("[v0] API Medicamentos recibió: " + jsonString);
        
        String sql = "INSERT INTO medicamentos (nombre, cantidad_stock, precio_unitario, "
                   + "fecha_expiracion, descripcion) VALUES (?, ?, ?, ?, ?)";
        
        try {
            JSONObject json = new JSONObject(jsonString);
            
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, json.getString("nombre"));
            ps.setInt(2, json.getInt("cantidadStock"));
            ps.setDouble(3, json.getDouble("precioUnitario"));
            
            if (json.has("fechaExpiracion") && !json.isNull("fechaExpiracion")) {
                String fechaStr = json.getString("fechaExpiracion");
                ps.setDate(4, Date.valueOf(fechaStr));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }
            
            ps.setString(5, json.optString("descripcion", null));
            
            int result = ps.executeUpdate();
            System.out.println("[v0] Medicamento insertado, filas afectadas: " + result);
            
            return result > 0 ? 1 : 0;
            
        } catch (Exception e) {
            System.out.println("[v0] Error en agregar medicamento: " + e.getMessage());
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
    public int modificar(MedicamentosModel medicamento) {
        String sql = "UPDATE medicamentos SET nombre=?, cantidad_stock=?, precio_unitario=?, "
                   + "fecha_expiracion=?, descripcion=? WHERE id=?";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, medicamento.getNombre());
            ps.setInt(2, medicamento.getCantidadStock());
            ps.setDouble(3, medicamento.getPrecioUnitario());
            ps.setDate(4, medicamento.getFechaExpiracion() != null ? 
                      Date.valueOf(medicamento.getFechaExpiracion()) : null);
            ps.setString(5, medicamento.getDescripcion());
            ps.setLong(6, medicamento.getId());
            
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
        String sql = "DELETE FROM medicamentos WHERE id = ?";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            
            int result = ps.executeUpdate();
            System.out.println("[v0] Medicamento eliminado, filas afectadas: " + result);
            
            return result > 0 ? 1 : 0;
            
        } catch (Exception e) {
            System.out.println("[v0] Error en eliminar medicamento: " + e.getMessage());
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
