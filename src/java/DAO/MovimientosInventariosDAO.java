package DAO;

import Model.MovimientosInventarioModel;
import Config.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovimientosInventariosDAO {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    // LISTAR todos los movimientos
    public List<MovimientosInventarioModel> listar() {
        List<MovimientosInventarioModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM movimientos_inventario ORDER BY creado_en DESC";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                MovimientosInventarioModel m = mapearMovimiento(rs);
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
    public MovimientosInventarioModel buscarPorId(long id) {
        MovimientosInventarioModel m = new MovimientosInventarioModel();
        String sql = "SELECT * FROM movimientos_inventario WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                m = mapearMovimiento(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return m;
    }
    
    // AGREGAR nuevo movimiento
    public boolean agregar(MovimientosInventarioModel m) {
        String sql = "INSERT INTO movimientos_inventario (medicamento_id, tipo_movimiento, cantidad, " +
                     "tipo_referencia, id_referencia, notas, creado_por) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, m.getMedicamentoId());
            ps.setString(2, m.getTipoMovimiento());
            ps.setInt(3, m.getCantidad());
            ps.setString(4, m.getTipoReferencia());
            ps.setLong(5, m.getIdReferencia());
            ps.setString(6, m.getNotas());
            ps.setLong(7, m.getCreadoPor());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexiones();
        }
    }
    
    // ELIMINAR movimiento
    public boolean eliminar(long id) {
        String sql = "DELETE FROM movimientos_inventario WHERE id=?";
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
    
    // LISTAR movimientos por medicamento
    public List<MovimientosInventarioModel> listarPorMedicamento(long medicamentoId) {
        List<MovimientosInventarioModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM movimientos_inventario WHERE medicamento_id = ? ORDER BY creado_en DESC";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, medicamentoId);
            rs = ps.executeQuery();
            while (rs.next()) {
                MovimientosInventarioModel m = mapearMovimiento(rs);
                lista.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return lista;
    }
    
    // LISTAR movimientos por tipo
    public List<MovimientosInventarioModel> listarPorTipo(String tipoMovimiento) {
        List<MovimientosInventarioModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM movimientos_inventario WHERE tipo_movimiento = ? ORDER BY creado_en DESC";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tipoMovimiento);
            rs = ps.executeQuery();
            while (rs.next()) {
                MovimientosInventarioModel m = mapearMovimiento(rs);
                lista.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return lista;
    }
    
    // Método auxiliar para mapear ResultSet a MovimientosInventarioModel
    private MovimientosInventarioModel mapearMovimiento(ResultSet rs) throws SQLException {
        MovimientosInventarioModel m = new MovimientosInventarioModel();
        m.setId(rs.getLong("id"));
        m.setMedicamentoId(rs.getLong("medicamento_id"));
        m.setTipoMovimiento(rs.getString("tipo_movimiento"));
        m.setCantidad(rs.getInt("cantidad"));
        m.setTipoReferencia(rs.getString("tipo_referencia"));
        m.setIdReferencia(rs.getLong("id_referencia"));
        m.setNotas(rs.getString("notas"));
        m.setCreadoPor(rs.getLong("creado_por"));
        m.setCreadoEn(rs.getTimestamp("creado_en").toLocalDateTime());
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