package servicio.API;

import Model.ConsultasModel;
import Config.Conexion;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Path("consultas")
public class Consultas {
    Conexion cn = new Conexion();
    
    @GET
    @Path("/lista")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ConsultasModel> listarConsultas() {
        List<ConsultasModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM consultas";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ConsultasModel c = new ConsultasModel();
                c.setId(rs.getLong("id"));
                c.setCitaId(rs.getLong("cita_id"));
                c.setPacienteId(rs.getLong("paciente_id"));
                c.setDoctorId(rs.getLong("doctor_id"));
                
                Timestamp fechaConsulta = rs.getTimestamp("fecha_consulta");
                c.setFechaConsulta(fechaConsulta != null ? fechaConsulta.toLocalDateTime() : null);
                
                c.setSintomas(rs.getString("sintomas"));
                c.setDiagnostico(rs.getString("diagnostico"));
                c.setPlanTratamiento(rs.getString("plan_tratamiento"));
                c.setNotas(rs.getString("notas"));
                c.setSignosVitales(rs.getString("signos_vitales"));
                
                Date fechaProxima = rs.getDate("fecha_proxima_visita");
                c.setFechaProximaVisita(fechaProxima != null ? fechaProxima.toLocalDate() : null);
                
                c.setEstado(rs.getString("estado"));
                
                Timestamp creado = rs.getTimestamp("creado_en");
                c.setCreadoEn(creado != null ? creado.toLocalDateTime() : null);
                
                Timestamp actualizado = rs.getTimestamp("actualizado_en");
                c.setActualizadoEn(actualizado != null ? actualizado.toLocalDateTime() : null);
                
                lista.add(c);
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
    public int agregarConsulta(ConsultasModel consulta) {
        String sql = "INSERT INTO consultas (cita_id, paciente_id, doctor_id, fecha_consulta, "
                   + "sintomas, diagnostico, plan_tratamiento, notas, signos_vitales, "
                   + "fecha_proxima_visita, estado, creado_en, actualizado_en) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setLong(1, consulta.getCitaId());
            ps.setLong(2, consulta.getPacienteId());
            ps.setLong(3, consulta.getDoctorId());
            ps.setTimestamp(4, consulta.getFechaConsulta() != null ? 
                           Timestamp.valueOf(consulta.getFechaConsulta()) : null);
            ps.setString(5, consulta.getSintomas());
            ps.setString(6, consulta.getDiagnostico());
            ps.setString(7, consulta.getPlanTratamiento());
            ps.setString(8, consulta.getNotas());
            ps.setString(9, consulta.getSignosVitales());
            ps.setDate(10, consulta.getFechaProximaVisita() != null ? 
                      Date.valueOf(consulta.getFechaProximaVisita()) : null);
            ps.setString(11, consulta.getEstado() != null ? consulta.getEstado() : "activo");
            
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
    public int modificarConsulta(ConsultasModel consulta) {
        String sql = "UPDATE consultas SET sintomas=?, diagnostico=?, plan_tratamiento=?, "
                   + "notas=?, signos_vitales=?, fecha_proxima_visita=?, estado=?, "
                   + "actualizado_en=CURRENT_TIMESTAMP WHERE id=?";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, consulta.getSintomas());
            ps.setString(2, consulta.getDiagnostico());
            ps.setString(3, consulta.getPlanTratamiento());
            ps.setString(4, consulta.getNotas());
            ps.setString(5, consulta.getSignosVitales());
            ps.setDate(6, consulta.getFechaProximaVisita() != null ? 
                      Date.valueOf(consulta.getFechaProximaVisita()) : null);
            ps.setString(7, consulta.getEstado());
            ps.setLong(8, consulta.getId());
            
            return ps.executeUpdate() > 0 ? 1 : 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    @GET
    @Path("/por-paciente/{pacienteId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ConsultasModel> consultasPorPaciente(@PathParam("pacienteId") long pacienteId) {
        List<ConsultasModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM consultas WHERE paciente_id = ? ORDER BY fecha_consulta DESC";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setLong(1, pacienteId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                ConsultasModel c = new ConsultasModel();
                c.setId(rs.getLong("id"));
                c.setCitaId(rs.getLong("cita_id"));
                c.setPacienteId(rs.getLong("paciente_id"));
                c.setDoctorId(rs.getLong("doctor_id"));
                
                Timestamp fechaConsulta = rs.getTimestamp("fecha_consulta");
                c.setFechaConsulta(fechaConsulta != null ? fechaConsulta.toLocalDateTime() : null);
                
                c.setSintomas(rs.getString("sintomas"));
                c.setDiagnostico(rs.getString("diagnostico"));
                c.setPlanTratamiento(rs.getString("plan_tratamiento"));
                c.setNotas(rs.getString("notas"));
                c.setSignosVitales(rs.getString("signos_vitales"));
                
                Date fechaProxima = rs.getDate("fecha_proxima_visita");
                c.setFechaProximaVisita(fechaProxima != null ? fechaProxima.toLocalDate() : null);
                
                c.setEstado(rs.getString("estado"));
                
                Timestamp creado = rs.getTimestamp("creado_en");
                c.setCreadoEn(creado != null ? creado.toLocalDateTime() : null);
                
                Timestamp actualizado = rs.getTimestamp("actualizado_en");
                c.setActualizadoEn(actualizado != null ? actualizado.toLocalDateTime() : null);
                
                lista.add(c);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}