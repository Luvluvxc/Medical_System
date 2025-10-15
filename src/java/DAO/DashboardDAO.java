package DAO;

import Config.Conexion;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DashboardDAO {
    Conexion cn = new Conexion();
    
    // Datos para dashboard de Recepcionista
    public Map<String, Integer> getEstadisticasRecepcionista() {
        Map<String, Integer> stats = new HashMap<>();
        String sql = "SELECT " +
                    "(SELECT COUNT(*) FROM citas WHERE fecha_cita = CURRENT_DATE) as citas_hoy, " +
                    "(SELECT COUNT(*) FROM pacientes) as total_pacientes, " +
                    "(SELECT COUNT(*) FROM citas WHERE estado = 'programada') as citas_programadas, " +
                    "(SELECT COUNT(*) FROM citas WHERE fecha_cita = CURRENT_DATE AND estado = 'completada') as citas_completadas_hoy";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                stats.put("citasHoy", rs.getInt("citas_hoy"));
                stats.put("totalPacientes", rs.getInt("total_pacientes"));
                stats.put("citasProgramadas", rs.getInt("citas_programadas"));
                stats.put("citasCompletadasHoy", rs.getInt("citas_completadas_hoy"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stats;
    }
    
    // Datos para dashboard de Doctor
    public Map<String, Integer> getEstadisticasDoctor(long doctorId) {
        Map<String, Integer> stats = new HashMap<>();
        String sql = "SELECT " +
                    "(SELECT COUNT(*) FROM citas WHERE doctor_id = ? AND fecha_cita = CURRENT_DATE) as citas_hoy, " +
                    "(SELECT COUNT(*) FROM consultas WHERE doctor_id = ?) as total_consultas, " +
                    "(SELECT COUNT(*) FROM citas WHERE doctor_id = ? AND estado = 'programada') as citas_pendientes, " +
                    "(SELECT COUNT(*) FROM prescripciones p " +
                    " JOIN consultas c ON p.consulta_id = c.id " +
                    " WHERE c.doctor_id = ? AND DATE(c.creado_en) = CURRENT_DATE) as recetas_hoy";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setLong(1, doctorId);
            ps.setLong(2, doctorId);
            ps.setLong(3, doctorId);
            ps.setLong(4, doctorId);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                stats.put("citasHoy", rs.getInt("citas_hoy"));
                stats.put("totalConsultas", rs.getInt("total_consultas"));
                stats.put("citasPendientes", rs.getInt("citas_pendientes"));
                stats.put("recetasHoy", rs.getInt("recetas_hoy"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stats;
    }
    
    // Citas del día para doctor
    public ResultSet getCitasHoyDoctor(long doctorId) {
        String sql = "SELECT c.*, p.nombre as paciente_nombre, p.apellido as paciente_apellido " +
                    "FROM citas c " +
                    "JOIN pacientes pt ON c.paciente_id = pt.id " +
                    "JOIN usuarios p ON pt.usuario_id = p.id " +
                    "WHERE c.doctor_id = ? AND c.fecha_cita = CURRENT_DATE " +
                    "ORDER BY c.hora_cita";
        
        try {
            Connection con = cn.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, doctorId);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}