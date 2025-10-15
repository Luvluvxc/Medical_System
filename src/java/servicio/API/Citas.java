/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package servicio.API;

import Model.CitasModel;
import Config.Conexion;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Path("citas")
public class Citas {
    Conexion cn = new Conexion();
    
    @GET
    @Path("/lista")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CitasModel> listarCitas() {
        List<CitasModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM citas";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                CitasModel c = new CitasModel();
                c.setId(rs.getLong("id"));
                c.setPacienteId(rs.getLong("paciente_id"));
                c.setDoctorId(rs.getLong("doctor_id"));
                
                Date fechaCita = rs.getDate("fecha_cita");
                c.setFechaCita(fechaCita != null ? fechaCita.toLocalDate() : null);
                
                Time horaCita = rs.getTime("hora_cita");
                c.setHoraCita(horaCita != null ? horaCita.toLocalTime() : null);
                
                c.setEstado(rs.getString("estado"));
                c.setMotivo(rs.getString("motivo"));
                c.setNotas(rs.getString("notas"));
                c.setCreadoPor(rs.getLong("creado_por"));
                
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
    public int agregarCita(CitasModel cita) {
        String sql = "INSERT INTO citas (paciente_id, doctor_id, fecha_cita, hora_cita, "
                   + "estado, motivo, notas, creado_por, creado_en, actualizado_en) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setLong(1, cita.getPacienteId());
            ps.setLong(2, cita.getDoctorId());
            ps.setDate(3, cita.getFechaCita() != null ? 
                      Date.valueOf(cita.getFechaCita()) : null);
            ps.setTime(4, cita.getHoraCita() != null ? 
                      Time.valueOf(cita.getHoraCita()) : null);
            ps.setString(5, cita.getEstado() != null ? cita.getEstado() : "programada");
            ps.setString(6, cita.getMotivo());
            ps.setString(7, cita.getNotas());
            ps.setObject(8, cita.getCreadoPor());
            
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
    public int modificarCita(CitasModel cita) {
        String sql = "UPDATE citas SET fecha_cita=?, hora_cita=?, estado=?, "
                   + "motivo=?, notas=?, actualizado_en=CURRENT_TIMESTAMP "
                   + "WHERE id=?";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setDate(1, cita.getFechaCita() != null ? 
                      Date.valueOf(cita.getFechaCita()) : null);
            ps.setTime(2, cita.getHoraCita() != null ? 
                      Time.valueOf(cita.getHoraCita()) : null);
            ps.setString(3, cita.getEstado());
            ps.setString(4, cita.getMotivo());
            ps.setString(5, cita.getNotas());
            ps.setLong(6, cita.getId());
            
            return ps.executeUpdate() > 0 ? 1 : 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    @PUT
    @Path("/cambiar-estado/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public int cambiarEstadoCita(@PathParam("id") long id, String nuevoEstado) {
        String sql = "UPDATE citas SET estado=?, actualizado_en=CURRENT_TIMESTAMP WHERE id=?";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, nuevoEstado);
            ps.setLong(2, id);
            
            return ps.executeUpdate() > 0 ? 1 : 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}