/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package servicio.API;

import Model.PacientesModel;
import Config.Conexion;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("pacientes")
public class Pacientes {
    Conexion cn = new Conexion();
    
    @GET
    @Path("/lista")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PacientesModel> listarPacientes() {
        List<PacientesModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM pacientes";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                PacientesModel p = new PacientesModel();
                p.setId(rs.getLong("id"));
                p.setUsuarioId(rs.getLong("usuario_id"));
                p.setCodigoPaciente(rs.getString("codigo_paciente"));
                
                Date fechaNac = rs.getDate("fecha_nacimiento");
                p.setFechaNacimiento(fechaNac != null ? fechaNac.toLocalDate() : null);
                
                p.setGenero(rs.getString("genero"));
                p.setDireccion(rs.getString("direccion"));
                p.setContactoEmergenciaNombre(rs.getString("contacto_emergencia_nombre"));
                p.setContactoEmergenciaTelefono(rs.getString("contacto_emergencia_telefono"));
                p.setHistorialMedico(rs.getString("historial_medico"));
                p.setAlergias(rs.getString("alergias"));
                
                Timestamp creado = rs.getTimestamp("creado_en");
                p.setCreadoEn(creado != null ? creado.toLocalDateTime() : null);
                
                Timestamp actualizado = rs.getTimestamp("actualizado_en");
                p.setActualizadoEn(actualizado != null ? actualizado.toLocalDateTime() : null);
                
                lista.add(p);
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
    public int agregarPaciente(PacientesModel paciente) {
        String sql = "INSERT INTO pacientes (usuario_id, codigo_paciente, fecha_nacimiento, genero, "
                   + "direccion, contacto_emergencia_nombre, contacto_emergencia_telefono, "
                   + "historial_medico, alergias, creado_en, actualizado_en) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setLong(1, paciente.getUsuarioId());
            ps.setString(2, paciente.getCodigoPaciente());
            ps.setDate(3, paciente.getFechaNacimiento() != null ? 
                      Date.valueOf(paciente.getFechaNacimiento()) : null);
            ps.setString(4, paciente.getGenero());
            ps.setString(5, paciente.getDireccion());
            ps.setString(6, paciente.getContactoEmergenciaNombre());
            ps.setString(7, paciente.getContactoEmergenciaTelefono());
            ps.setString(8, paciente.getHistorialMedico());
            ps.setString(9, paciente.getAlergias());
            
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
    public int modificarPaciente(PacientesModel paciente) {
        String sql = "UPDATE pacientes SET fecha_nacimiento=?, genero=?, direccion=?, "
                   + "contacto_emergencia_nombre=?, contacto_emergencia_telefono=?, "
                   + "historial_medico=?, alergias=?, actualizado_en=CURRENT_TIMESTAMP "
                   + "WHERE id=?";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setDate(1, paciente.getFechaNacimiento() != null ? 
                      Date.valueOf(paciente.getFechaNacimiento()) : null);
            ps.setString(2, paciente.getGenero());
            ps.setString(3, paciente.getDireccion());
            ps.setString(4, paciente.getContactoEmergenciaNombre());
            ps.setString(5, paciente.getContactoEmergenciaTelefono());
            ps.setString(6, paciente.getHistorialMedico());
            ps.setString(7, paciente.getAlergias());
            ps.setLong(8, paciente.getId());
            
            return ps.executeUpdate() > 0 ? 1 : 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}