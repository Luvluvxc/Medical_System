/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package servicio.API;

import Model.DoctoresModel;
import Config.Conexion;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Path("doctores")
public class Doctores {
    Conexion cn = new Conexion();
    
    @GET
    @Path("/lista")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DoctoresModel> listarDoctores() {
        List<DoctoresModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM doctores";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                DoctoresModel d = new DoctoresModel();
                d.setId(rs.getLong("id"));
                d.setUsuarioId(rs.getLong("usuario_id"));
                d.setNumeroLicencia(rs.getString("numero_licencia"));
                d.setEspecializacion(rs.getString("especializacion"));
                d.setTarifaConsulta(rs.getBigDecimal("tarifa_consulta"));
                
                Time horarioInicio = rs.getTime("horario_inicio");
                d.setHorarioInicio(horarioInicio != null ? horarioInicio.toLocalTime() : null);
                
                Time horarioFin = rs.getTime("horario_fin");
                d.setHorarioFin(horarioFin != null ? horarioFin.toLocalTime() : null);
                
                Timestamp creado = rs.getTimestamp("creado_en");
                d.setCreadoEn(creado != null ? creado.toLocalDateTime() : null);
                
                Timestamp actualizado = rs.getTimestamp("actualizado_en");
                d.setActualizadoEn(actualizado != null ? actualizado.toLocalDateTime() : null);
                
                lista.add(d);
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
    public int agregarDoctor(DoctoresModel doctor) {
        String sql = "INSERT INTO doctores (usuario_id, numero_licencia, especializacion, "
                   + "tarifa_consulta, horario_inicio, horario_fin, creado_en, actualizado_en) "
                   + "VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setLong(1, doctor.getUsuarioId());
            ps.setString(2, doctor.getNumeroLicencia());
            ps.setString(3, doctor.getEspecializacion());
            ps.setBigDecimal(4, doctor.getTarifaConsulta());
            ps.setTime(5, doctor.getHorarioInicio() != null ? 
                      Time.valueOf(doctor.getHorarioInicio()) : null);
            ps.setTime(6, doctor.getHorarioFin() != null ? 
                      Time.valueOf(doctor.getHorarioFin()) : null);
            
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
    public int modificarDoctor(DoctoresModel doctor) {
        String sql = "UPDATE doctores SET especializacion=?, tarifa_consulta=?, "
                   + "horario_inicio=?, horario_fin=?, actualizado_en=CURRENT_TIMESTAMP "
                   + "WHERE id=?";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, doctor.getEspecializacion());
            ps.setBigDecimal(2, doctor.getTarifaConsulta());
            ps.setTime(3, doctor.getHorarioInicio() != null ? 
                      Time.valueOf(doctor.getHorarioInicio()) : null);
            ps.setTime(4, doctor.getHorarioFin() != null ? 
                      Time.valueOf(doctor.getHorarioFin()) : null);
            ps.setLong(5, doctor.getId());
            
            return ps.executeUpdate() > 0 ? 1 : 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}