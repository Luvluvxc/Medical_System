package DAO;

import Model.MedicamentosModel;
import Config.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentosDAO {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    // LISTAR todos los medicamentos activos
    public List<MedicamentosModel> listar() {
        List<MedicamentosModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM medicamentos WHERE activo = true ORDER BY nombre";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                MedicamentosModel m = mapearMedicamento(rs);
                lista.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return lista;
    }
    
    // BUSCAR por ID
    public MedicamentosModel buscarPorId(long id) {
        MedicamentosModel m = new MedicamentosModel();
        String sql = "SELECT * FROM medicamentos WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                m = mapearMedicamento(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return m;
    }
    
    // AGREGAR nuevo medicamento
    public boolean agregar(MedicamentosModel m) {
        String sql = "INSERT INTO medicamentos (nombre, nombre_generico, marca, unidad_medida_id, " +
                     "cantidad_stock, nivel_minimo_stock, precio_unitario, fecha_expiracion, " +
                     "numero_lote, descripcion, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getNombreGenerico());
            ps.setString(3, m.getMarca());
            ps.setLong(4, m.getUnidadMedidaId());
            ps.setInt(5, m.getCantidadStock());
            ps.setInt(6, m.getNivelMinimoStock());
            ps.setBigDecimal(7, m.getPrecioUnitario());
            ps.setDate(8, Date.valueOf(m.getFechaExpiracion()));
            ps.setString(9, m.getNumeroLote());
            ps.setString(10, m.getDescripcion());
            ps.setBoolean(11, m.isActivo());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexiones();
        }
    }
    
    // ACTUALIZAR medicamento
    public boolean actualizar(MedicamentosModel m) {
        String sql = "UPDATE medicamentos SET nombre=?, nombre_generico=?, marca=?, " +
                     "unidad_medida_id=?, cantidad_stock=?, nivel_minimo_stock=?, precio_unitario=?, " +
                     "fecha_expiracion=?, numero_lote=?, descripcion=?, activo=?, " +
                     "actualizado_en=CURRENT_TIMESTAMP WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getNombreGenerico());
            ps.setString(3, m.getMarca());
            ps.setLong(4, m.getUnidadMedidaId());
            ps.setInt(5, m.getCantidadStock());
            ps.setInt(6, m.getNivelMinimoStock());
            ps.setBigDecimal(7, m.getPrecioUnitario());
            ps.setDate(8, Date.valueOf(m.getFechaExpiracion()));
            ps.setString(9, m.getNumeroLote());
            ps.setString(10, m.getDescripcion());
            ps.setBoolean(11, m.isActivo());
            ps.setLong(12, m.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexiones();
        }
    }
    
    // ACTUALIZAR stock
    public boolean actualizarStock(long id, int nuevaCantidad) {
        String sql = "UPDATE medicamentos SET cantidad_stock=?, actualizado_en=CURRENT_TIMESTAMP WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, nuevaCantidad);
            ps.setLong(2, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexiones();
        }
    }
    
    // ELIMINAR medicamento (soft delete)
    public boolean eliminar(long id) {
        String sql = "UPDATE medicamentos SET activo=false, actualizado_en=CURRENT_TIMESTAMP WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexiones();
        }
    }
    
    // BUSCAR por nombre
    public List<MedicamentosModel> buscarPorNombre(String nombre) {
        List<MedicamentosModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM medicamentos WHERE LOWER(nombre) LIKE LOWER(?) AND activo = true";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + nombre + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                MedicamentosModel m = mapearMedicamento(rs);
                lista.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return lista;
    }
    
    // LISTAR medicamentos con stock bajo
    public List<MedicamentosModel> listarStockBajo() {
        List<MedicamentosModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM medicamentos WHERE cantidad_stock <= nivel_minimo_stock AND activo = true";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                MedicamentosModel m = mapearMedicamento(rs);
                lista.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return lista;
    }
    
    // LISTAR medicamentos próximos a vencer
    public List<MedicamentosModel> listarProximosAVencer(int diasAnticipacion) {
        List<MedicamentosModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM medicamentos WHERE fecha_expiracion <= CURRENT_DATE + INTERVAL '" + 
                     diasAnticipacion + " days' AND activo = true ORDER BY fecha_expiracion";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                MedicamentosModel m = mapearMedicamento(rs);
                lista.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return lista;
    }
    
    // Método auxiliar para mapear ResultSet a MedicamentosModel
    private MedicamentosModel mapearMedicamento(ResultSet rs) throws SQLException {
        MedicamentosModel m = new MedicamentosModel();
        m.setId(rs.getLong("id"));
        m.setNombre(rs.getString("nombre"));
        m.setNombreGenerico(rs.getString("nombre_generico"));
        m.setMarca(rs.getString("marca"));
        m.setUnidadMedidaId(rs.getLong("unidad_medida_id"));
        m.setCantidadStock(rs.getInt("cantidad_stock"));
        m.setNivelMinimoStock(rs.getInt("nivel_minimo_stock"));
        m.setPrecioUnitario(rs.getBigDecimal("precio_unitario"));
        m.setFechaExpiracion(rs.getDate("fecha_expiracion").toLocalDate());
        m.setNumeroLote(rs.getString("numero_lote"));
        m.setDescripcion(rs.getString("descripcion"));
        m.setActivo(rs.getBoolean("activo"));
        m.setCreadoEn(rs.getTimestamp("creado_en").toLocalDateTime());
        m.setActualizadoEn(rs.getTimestamp("actualizado_en").toLocalDateTime());
        return m;
    }
    
    private void cerrarConexiones() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}