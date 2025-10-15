package DAO;

import Model.PrescripcionesModel;
import Config.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescripcionesDAO {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    // LISTAR todas las prescripciones
    public List<PrescripcionesModel> listar() {
        List<PrescripcionesModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM prescripciones ORDER BY creado_en DESC";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                PrescripcionesModel p = mapearPrescripcion(rs);
                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return lista;
    }
    
    // BUSCAR por ID
    public PrescripcionesModel buscarPorId(long id) {
        PrescripcionesModel p = new PrescripcionesModel();
        String sql = "SELECT * FROM prescripciones WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                p = mapearPrescripcion(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return p;
    }
    
    // AGREGAR nueva prescripción
    public boolean agregar(PrescripcionesModel p) {
        String sql = "INSERT INTO prescripciones (consulta_id, medicamento_id, cantidad, dosis, " +
                     "frecuencia, duracion, instrucciones) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, p.getConsultaId());
            ps.setLong(2, p.getMedicamentoId());
            ps.setInt(3, p.getCantidad());
            ps.setString(4, p.getDosis());
            ps.setString(5, p.getFrecuencia());
            ps.setString(6, p.getDuracion());
            ps.setString(7, p.getInstrucciones());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexiones();
        }
    }
    
    // ACTUALIZAR prescripción
    public boolean actualizar(PrescripcionesModel p) {
        String sql = "UPDATE prescripciones SET cantidad=?, dosis=?, frecuencia=?, " +
                     "duracion=?, instrucciones=? WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, p.getCantidad());
            ps.setString(2, p.getDosis());
            ps.setString(3, p.getFrecuencia());
            ps.setString(4, p.getDuracion());
            ps.setString(5, p.getInstrucciones());
            ps.setLong(6, p.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexiones();
        }
    }
    
    // ELIMINAR prescripción
    public boolean eliminar(long id) {
        String sql = "DELETE FROM prescripciones WHERE id=?";
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
    
    // LISTAR prescripciones por consulta
    public List<PrescripcionesModel> listarPorConsulta(long consultaId) {
        List<PrescripcionesModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM prescripciones WHERE consulta_id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, consultaId);
            rs = ps.executeQuery();
            while (rs.next()) {
                PrescripcionesModel p = mapearPrescripcion(rs);
                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return lista;
    }
    
    // LISTAR prescripciones por medicamento
    public List<PrescripcionesModel> listarPorMedicamento(long medicamentoId) {
        List<PrescripcionesModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM prescripciones WHERE medicamento_id = ? ORDER BY creado_en DESC";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, medicamentoId);
            rs = ps.executeQuery();
            while (rs.next()) {
                PrescripcionesModel p = mapearPrescripcion(rs);
                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return lista;
    }
    
    // Método auxiliar para mapear ResultSet a PrescripcionesModel
    private PrescripcionesModel mapearPrescripcion(ResultSet rs) throws SQLException {
        PrescripcionesModel p = new PrescripcionesModel();
        p.setId(rs.getLong("id"));
        p.setConsultaId(rs.getLong("consulta_id"));
        p.setMedicamentoId(rs.getLong("medicamento_id"));
        p.setCantidad(rs.getInt("cantidad"));
        p.setDosis(rs.getString("dosis"));
        p.setFrecuencia(rs.getString("frecuencia"));
        p.setDuracion(rs.getString("duracion"));
        p.setInstrucciones(rs.getString("instrucciones"));
        p.setCreadoEn(rs.getTimestamp("creado_en").toLocalDateTime());
        return p;
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