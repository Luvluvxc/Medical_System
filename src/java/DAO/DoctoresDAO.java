package DAO;

import Model.DoctoresModel;
import Config.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctoresDAO {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    // LISTAR todos los doctores
    public List<DoctoresModel> listar() {
        List<DoctoresModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM doctores ORDER BY creado_en DESC";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                DoctoresModel d = new DoctoresModel();
                d.setId(rs.getLong("id"));
                d.setUsuarioId(rs.getLong("usuario_id"));
                d.setNumeroLicencia(rs.getString("numero_licencia"));
                d.setEspecializacion(rs.getString("especializacion"));
                d.setTarifaConsulta(rs.getBigDecimal("tarifa_consulta"));
                d.setHorarioInicio(rs.getTime("horario_inicio").toLocalTime());
                d.setHorarioFin(rs.getTime("horario_fin").toLocalTime());
                d.setCreadoEn(rs.getTimestamp("creado_en").toLocalDateTime());
                d.setActualizadoEn(rs.getTimestamp("actualizado_en").toLocalDateTime());
                lista.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return lista;
    }
    
    // BUSCAR por ID
    public DoctoresModel buscarPorId(long id) {
        DoctoresModel d = new DoctoresModel();
        String sql = "SELECT * FROM doctores WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                d.setId(rs.getLong("id"));
                d.setUsuarioId(rs.getLong("usuario_id"));
                d.setNumeroLicencia(rs.getString("numero_licencia"));
                d.setEspecializacion(rs.getString("especializacion"));
                d.setTarifaConsulta(rs.getBigDecimal("tarifa_consulta"));
                d.setHorarioInicio(rs.getTime("horario_inicio").toLocalTime());
                d.setHorarioFin(rs.getTime("horario_fin").toLocalTime());
                d.setCreadoEn(rs.getTimestamp("creado_en").toLocalDateTime());
                d.setActualizadoEn(rs.getTimestamp("actualizado_en").toLocalDateTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return d;
    }
    
    // BUSCAR por usuario_id
    public DoctoresModel buscarPorUsuarioId(long usuarioId) {
        DoctoresModel d = new DoctoresModel();
        String sql = "SELECT * FROM doctores WHERE usuario_id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, usuarioId);
            rs = ps.executeQuery();
            if (rs.next()) {
                d.setId(rs.getLong("id"));
                d.setUsuarioId(rs.getLong("usuario_id"));
                d.setNumeroLicencia(rs.getString("numero_licencia"));
                d.setEspecializacion(rs.getString("especializacion"));
                d.setTarifaConsulta(rs.getBigDecimal("tarifa_consulta"));
                d.setHorarioInicio(rs.getTime("horario_inicio").toLocalTime());
                d.setHorarioFin(rs.getTime("horario_fin").toLocalTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return d;
    }
    
    // AGREGAR nuevo doctor
    public boolean agregar(DoctoresModel d) {
        String sql = "INSERT INTO doctores (usuario_id, numero_licencia, especializacion, " +
                     "tarifa_consulta, horario_inicio, horario_fin) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, d.getUsuarioId());
            ps.setString(2, d.getNumeroLicencia());
            ps.setString(3, d.getEspecializacion());
            ps.setBigDecimal(4, d.getTarifaConsulta());
            ps.setTime(5, Time.valueOf(d.getHorarioInicio()));
            ps.setTime(6, Time.valueOf(d.getHorarioFin()));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexiones();
        }
    }
    
    // ACTUALIZAR doctor
    public boolean actualizar(DoctoresModel d) {
        String sql = "UPDATE doctores SET numero_licencia=?, especializacion=?, tarifa_consulta=?, " +
                     "horario_inicio=?, horario_fin=?, actualizado_en=CURRENT_TIMESTAMP WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, d.getNumeroLicencia());
            ps.setString(2, d.getEspecializacion());
            ps.setBigDecimal(3, d.getTarifaConsulta());
            ps.setTime(4, Time.valueOf(d.getHorarioInicio()));
            ps.setTime(5, Time.valueOf(d.getHorarioFin()));
            ps.setLong(6, d.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexiones();
        }
    }
    
    // ELIMINAR doctor (soft delete)
    public boolean eliminar(long id) {
        String sql = "UPDATE usuarios SET activo=false WHERE id=(SELECT usuario_id FROM doctores WHERE id=?)";
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
    
    // LISTAR doctores por especialización
    public List<DoctoresModel> listarPorEspecializacion(String especializacion) {
        List<DoctoresModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM doctores WHERE LOWER(especializacion) LIKE LOWER(?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + especializacion + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                DoctoresModel d = new DoctoresModel();
                d.setId(rs.getLong("id"));
                d.setUsuarioId(rs.getLong("usuario_id"));
                d.setNumeroLicencia(rs.getString("numero_licencia"));
                d.setEspecializacion(rs.getString("especializacion"));
                d.setTarifaConsulta(rs.getBigDecimal("tarifa_consulta"));
                d.setHorarioInicio(rs.getTime("horario_inicio").toLocalTime());
                d.setHorarioFin(rs.getTime("horario_fin").toLocalTime());
                lista.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return lista;
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