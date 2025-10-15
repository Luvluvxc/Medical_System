package DAO;

import Model.ConsultasModel;
import Config.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultasDAO {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    // LISTAR todas las consultas
    public List<ConsultasModel> listar() {
        List<ConsultasModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM consultas ORDER BY fecha_consulta DESC";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ConsultasModel c = mapearConsulta(rs);
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
    public ConsultasModel buscarPorId(long id) {
        ConsultasModel c = new ConsultasModel();
        String sql = "SELECT * FROM consultas WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                c = mapearConsulta(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return c;
    }
    
    // AGREGAR nueva consulta
    public boolean agregar(ConsultasModel c) {
        String sql = "INSERT INTO consultas (cita_id, paciente_id, doctor_id, fecha_consulta, " +
                     "sintomas, diagnostico, plan_tratamiento, notas, signos_vitales, " +
                     "fecha_proxima_visita, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, c.getCitaId());
            ps.setLong(2, c.getPacienteId());
            ps.setLong(3, c.getDoctorId());
            ps.setTimestamp(4, Timestamp.valueOf(c.getFechaConsulta()));
            ps.setString(5, c.getSintomas());
            ps.setString(6, c.getDiagnostico());
            ps.setString(7, c.getPlanTratamiento());
            ps.setString(8, c.getNotas());
            ps.setString(9, c.getSignosVitales());
            ps.setDate(10, c.getFechaProximaVisita() != null ? Date.valueOf(c.getFechaProximaVisita()) : null);
            ps.setString(11, c.getEstado());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexiones();
        }
    }
    
    // ACTUALIZAR consulta
    public boolean actualizar(ConsultasModel c) {
        String sql = "UPDATE consultas SET sintomas=?, diagnostico=?, plan_tratamiento=?, " +
                     "notas=?, signos_vitales=?, fecha_proxima_visita=?, estado=?, " +
                     "actualizado_en=CURRENT_TIMESTAMP WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getSintomas());
            ps.setString(2, c.getDiagnostico());
            ps.setString(3, c.getPlanTratamiento());
            ps.setString(4, c.getNotas());
            ps.setString(5, c.getSignosVitales());
            ps.setDate(6, c.getFechaProximaVisita() != null ? Date.valueOf(c.getFechaProximaVisita()) : null);
            ps.setString(7, c.getEstado());
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
    
    // ELIMINAR consulta
    public boolean eliminar(long id) {
        String sql = "DELETE FROM consultas WHERE id=?";
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
    
    // LISTAR consultas por paciente
    public List<ConsultasModel> listarPorPaciente(long pacienteId) {
        List<ConsultasModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM consultas WHERE paciente_id = ? ORDER BY fecha_consulta DESC";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, pacienteId);
            rs = ps.executeQuery();
            while (rs.next()) {
                ConsultasModel c = mapearConsulta(rs);
                lista.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return lista;
    }
    
    // LISTAR consultas por doctor
    public List<ConsultasModel> listarPorDoctor(long doctorId) {
        List<ConsultasModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM consultas WHERE doctor_id = ? ORDER BY fecha_consulta DESC";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, doctorId);
            rs = ps.executeQuery();
            while (rs.next()) {
                ConsultasModel c = mapearConsulta(rs);
                lista.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return lista;
    }
    
    // BUSCAR consulta por cita_id
    public ConsultasModel buscarPorCitaId(long citaId) {
        ConsultasModel c = new ConsultasModel();
        String sql = "SELECT * FROM consultas WHERE cita_id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, citaId);
            rs = ps.executeQuery();
            if (rs.next()) {
                c = mapearConsulta(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return c;
    }
    
    // Método auxiliar para mapear ResultSet a ConsultasModel
    private ConsultasModel mapearConsulta(ResultSet rs) throws SQLException {
        ConsultasModel c = new ConsultasModel();
        c.setId(rs.getLong("id"));
        c.setCitaId(rs.getLong("cita_id"));
        c.setPacienteId(rs.getLong("paciente_id"));
        c.setDoctorId(rs.getLong("doctor_id"));
        c.setFechaConsulta(rs.getTimestamp("fecha_consulta").toLocalDateTime());
        c.setSintomas(rs.getString("sintomas"));
        c.setDiagnostico(rs.getString("diagnostico"));
        c.setPlanTratamiento(rs.getString("plan_tratamiento"));
        c.setNotas(rs.getString("notas"));
        c.setSignosVitales(rs.getString("signos_vitales"));
        Date fechaProxima = rs.getDate("fecha_proxima_visita");
        c.setFechaProximaVisita(fechaProxima != null ? fechaProxima.toLocalDate() : null);
        c.setEstado(rs.getString("estado"));
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