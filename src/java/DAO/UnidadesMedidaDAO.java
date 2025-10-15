package DAO;

import Model.UnidadesMedidaModel;
import Config.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UnidadesMedidaDAO {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    // LISTAR todas las unidades de medida
    public List<UnidadesMedidaModel> listar() {
        List<UnidadesMedidaModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM unidades_medida ORDER BY nombre";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                UnidadesMedidaModel u = mapearUnidadMedida(rs);
                lista.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return lista;
    }
    
    // BUSCAR por ID
    public UnidadesMedidaModel buscarPorId(long id) {
        UnidadesMedidaModel u = new UnidadesMedidaModel();
        String sql = "SELECT * FROM unidades_medida WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                u = mapearUnidadMedida(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return u;
    }
    
    // AGREGAR nueva unidad de medida
    public boolean agregar(UnidadesMedidaModel u) {
        String sql = "INSERT INTO unidades_medida (nombre, abreviatura, descripcion) VALUES (?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getAbreviatura());
            ps.setString(3, u.getDescripcion());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexiones();
        }
    }
    
    // ACTUALIZAR unidad de medida
    public boolean actualizar(UnidadesMedidaModel u) {
        String sql = "UPDATE unidades_medida SET nombre=?, abreviatura=?, descripcion=? WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getAbreviatura());
            ps.setString(3, u.getDescripcion());
            ps.setLong(4, u.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexiones();
        }
    }
    
    // ELIMINAR unidad de medida
    public boolean eliminar(long id) {
        String sql = "DELETE FROM unidades_medida WHERE id=?";
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
    public UnidadesMedidaModel buscarPorNombre(String nombre) {
        UnidadesMedidaModel u = new UnidadesMedidaModel();
        String sql = "SELECT * FROM unidades_medida WHERE LOWER(nombre) = LOWER(?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            if (rs.next()) {
                u = mapearUnidadMedida(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return u;
    }
    
    // Método auxiliar para mapear ResultSet a UnidadesMedidaModel
    private UnidadesMedidaModel mapearUnidadMedida(ResultSet rs) throws SQLException {
        UnidadesMedidaModel u = new UnidadesMedidaModel();
        u.setId(rs.getLong("id"));
        u.setNombre(rs.getString("nombre"));
        u.setAbreviatura(rs.getString("abreviatura"));
        u.setDescripcion(rs.getString("descripcion"));
        u.setCreadoEn(rs.getTimestamp("creado_en").toLocalDateTime());
        return u;
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