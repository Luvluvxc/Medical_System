package DAO;

import Model.CitasModel;
import Config.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitasDAO {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    // LISTAR todas las citas
    public List<CitasModel> listar() {
        List<CitasModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM citas ORDER BY fecha_cita DESC, hora_cita DESC";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                CitasModel c = mapearCita(rs);
                lista.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return lista;
    }
    
    // BUSCAR por ID
    public CitasModel buscarPorId(long id) {
        CitasModel c = new CitasModel();
        String sql = "SELECT * FROM citas WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                c = mapearCita(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return c;
    }
    
    // AGREGAR nueva cita
    public boolean agregar(CitasModel c) {
        String sql = "INSERT INTO citas (paciente_id, doctor_id, fecha_cita, hora_cita, " +
                     "estado, motivo, notas, creado_por) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, c.getPacienteId());
            ps.setLong(2, c.getDoctorId());
            ps.setDate(3, Date.valueOf(c.getFechaCita()));
            ps.setTime(4, Time.valueOf(c.getHoraCita()));
            ps.setString(5, c.getEstado());
            ps.setString(6, c.getMotivo());
            ps.setString(7, c.getNotas());
            ps.setLong(8, c.getCreadoPor());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexiones();
        }
    }
    
    // ACTUALIZAR cita
    public boolean actualizar(CitasModel c) {
        String sql = "UPDATE citas SET paciente_id=?, doctor_id=?, fecha_cita=?, hora_cita=?, " +
                     "estado=?, motivo=?, notas=?, actualizado_en=CURRENT_TIMESTAMP WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, c.getPacienteId());
            ps.setLong(2, c.getDoctorId());
            ps.setDate(3, Date.valueOf(c.getFechaCita()));
            ps.setTime(4, Time.valueOf(c.getHoraCita()));
            ps.setString(5, c.getEstado());
            ps.setString(6, c.getMotivo());
            ps.setString(7, c.getNotas());
            ps.setLong(8, c.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexiones();
        }
    }
    
    // CAMBIAR ESTADO de la cita
    public boolean cambiarEstado(long id, String nuevoEstado) {
        String sql = "UPDATE citas SET estado=?, actualizado_en=CURRENT_TIMESTAMP WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nuevoEstado);
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
    
    // ELIMINAR cita
    public boolean eliminar(long id) {
        String sql = "DELETE FROM citas WHERE id=?";
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
    
    // LISTAR citas por paciente
    public List<CitasModel> listarPorPaciente(long pacienteId) {
        List<CitasModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM citas WHERE paciente_id = ? ORDER BY fecha_cita DESC, hora_cita DESC";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, pacienteId);
            rs = ps.executeQuery();
            while (rs.next()) {
                CitasModel c = mapearCita(rs);
                lista.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return lista;
    }
    
    // LISTAR citas por doctor
    public List<CitasModel> listarPorDoctor(long doctorId) {
        List<CitasModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM citas WHERE doctor_id = ? ORDER BY fecha_cita DESC, hora_cita DESC";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, doctorId);
            rs = ps.executeQuery();
            while (rs.next()) {
                CitasModel c = mapearCita(rs);
                lista.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return lista;
    }
    
    // LISTAR citas por fecha
    public List<CitasModel> listarPorFecha(Date fecha) {
        List<CitasModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM citas WHERE fecha_cita = ? ORDER BY hora_cita";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setDate(1, fecha);
            rs = ps.executeQuery();
            while (rs.next()) {
                CitasModel c = mapearCita(rs);
                lista.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return lista;
    }
    
    // LISTAR citas por estado
    public List<CitasModel> listarPorEstado(String estado) {
        List<CitasModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM citas WHERE estado = ? ORDER BY fecha_cita DESC, hora_cita DESC";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, estado);
            rs = ps.executeQuery();
            while (rs.next()) {
                CitasModel c = mapearCita(rs);
                lista.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return lista;
    }
    
    // Método auxiliar para mapear ResultSet a CitasModel
    private CitasModel mapearCita(ResultSet rs) throws SQLException {
        CitasModel c = new CitasModel();
        c.setId(rs.getLong("id"));
        c.setPacienteId(rs.getLong("paciente_id"));
        c.setDoctorId(rs.getLong("doctor_id"));
        c.setFechaCita(rs.getDate("fecha_cita").toLocalDate());
        c.setHoraCita(rs.getTime("hora_cita").toLocalTime());
        c.setEstado(rs.getString("estado"));
        c.setMotivo(rs.getString("motivo"));
        c.setNotas(rs.getString("notas"));
        c.setCreadoPor(rs.getLong("creado_por"));
        c.setCreadoEn(rs.getTimestamp("creado_en").toLocalDateTime());
        c.setActualizadoEn(rs.getTimestamp("actualizado_en").toLocalDateTime());
        return c;
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