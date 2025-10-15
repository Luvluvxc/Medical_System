package DAO;

import Model.PacientesModel;
import Config.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacientesDAO {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    // LISTAR todos los pacientes
    public List<PacientesModel> listar() {
        List<PacientesModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM pacientes ORDER BY creado_en DESC";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                PacientesModel p = new PacientesModel();
                p.setId(rs.getLong("id"));
                p.setUsuarioId(rs.getLong("usuario_id"));
                p.setCodigoPaciente(rs.getString("codigo_paciente"));
                p.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                p.setGenero(rs.getString("genero"));
                p.setDireccion(rs.getString("direccion"));
                p.setContactoEmergenciaNombre(rs.getString("contacto_emergencia_nombre"));
                p.setContactoEmergenciaTelefono(rs.getString("contacto_emergencia_telefono"));
                p.setHistorialMedico(rs.getString("historial_medico"));
                p.setAlergias(rs.getString("alergias"));
                p.setCreadoEn(rs.getTimestamp("creado_en").toLocalDateTime());
                p.setActualizadoEn(rs.getTimestamp("actualizado_en").toLocalDateTime());
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
    public PacientesModel buscarPorId(long id) {
        PacientesModel p = new PacientesModel();
        String sql = "SELECT * FROM pacientes WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                p.setId(rs.getLong("id"));
                p.setUsuarioId(rs.getLong("usuario_id"));
                p.setCodigoPaciente(rs.getString("codigo_paciente"));
                p.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                p.setGenero(rs.getString("genero"));
                p.setDireccion(rs.getString("direccion"));
                p.setContactoEmergenciaNombre(rs.getString("contacto_emergencia_nombre"));
                p.setContactoEmergenciaTelefono(rs.getString("contacto_emergencia_telefono"));
                p.setHistorialMedico(rs.getString("historial_medico"));
                p.setAlergias(rs.getString("alergias"));
                p.setCreadoEn(rs.getTimestamp("creado_en").toLocalDateTime());
                p.setActualizadoEn(rs.getTimestamp("actualizado_en").toLocalDateTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return p;
    }
    
    // BUSCAR por codigo_paciente
    public PacientesModel buscarPorCodigo(String codigoPaciente) {
        PacientesModel p = new PacientesModel();
        String sql = "SELECT * FROM pacientes WHERE codigo_paciente = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, codigoPaciente);
            rs = ps.executeQuery();
            if (rs.next()) {
                p.setId(rs.getLong("id"));
                p.setUsuarioId(rs.getLong("usuario_id"));
                p.setCodigoPaciente(rs.getString("codigo_paciente"));
                p.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                p.setGenero(rs.getString("genero"));
                p.setDireccion(rs.getString("direccion"));
                p.setContactoEmergenciaNombre(rs.getString("contacto_emergencia_nombre"));
                p.setContactoEmergenciaTelefono(rs.getString("contacto_emergencia_telefono"));
                p.setHistorialMedico(rs.getString("historial_medico"));
                p.setAlergias(rs.getString("alergias"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return p;
    }
    
    // BUSCAR por usuario_id
    public PacientesModel buscarPorUsuarioId(long usuarioId) {
        PacientesModel p = new PacientesModel();
        String sql = "SELECT * FROM pacientes WHERE usuario_id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, usuarioId);
            rs = ps.executeQuery();
            if (rs.next()) {
                p.setId(rs.getLong("id"));
                p.setUsuarioId(rs.getLong("usuario_id"));
                p.setCodigoPaciente(rs.getString("codigo_paciente"));
                p.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                p.setGenero(rs.getString("genero"));
                p.setDireccion(rs.getString("direccion"));
                p.setContactoEmergenciaNombre(rs.getString("contacto_emergencia_nombre"));
                p.setContactoEmergenciaTelefono(rs.getString("contacto_emergencia_telefono"));
                p.setHistorialMedico(rs.getString("historial_medico"));
                p.setAlergias(rs.getString("alergias"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return p;
    }
    
    // AGREGAR nuevo paciente
    public boolean agregar(PacientesModel p) {
        String sql = "INSERT INTO pacientes (usuario_id, codigo_paciente, fecha_nacimiento, genero, " +
                     "direccion, contacto_emergencia_nombre, contacto_emergencia_telefono, " +
                     "historial_medico, alergias) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, p.getUsuarioId());
            ps.setString(2, p.getCodigoPaciente());
            ps.setDate(3, Date.valueOf(p.getFechaNacimiento()));
            ps.setString(4, p.getGenero());
            ps.setString(5, p.getDireccion());
            ps.setString(6, p.getContactoEmergenciaNombre());
            ps.setString(7, p.getContactoEmergenciaTelefono());
            ps.setString(8, p.getHistorialMedico());
            ps.setString(9, p.getAlergias());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexiones();
        }
    }
    
    // ACTUALIZAR paciente
    public boolean actualizar(PacientesModel p) {
        String sql = "UPDATE pacientes SET fecha_nacimiento=?, genero=?, direccion=?, " +
                     "contacto_emergencia_nombre=?, contacto_emergencia_telefono=?, " +
                     "historial_medico=?, alergias=?, actualizado_en=CURRENT_TIMESTAMP WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(p.getFechaNacimiento()));
            ps.setString(2, p.getGenero());
            ps.setString(3, p.getDireccion());
            ps.setString(4, p.getContactoEmergenciaNombre());
            ps.setString(5, p.getContactoEmergenciaTelefono());
            ps.setString(6, p.getHistorialMedico());
            ps.setString(7, p.getAlergias());
            ps.setLong(8, p.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexiones();
        }
    }
    
    // ELIMINAR paciente (soft delete - marcar usuario como inactivo)
    public boolean eliminar(long id) {
        String sql = "UPDATE usuarios SET activo=false WHERE id=(SELECT usuario_id FROM pacientes WHERE id=?)";
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
    
    // BUSCAR pacientes por nombre
    public List<PacientesModel> buscarPorNombre(String nombre) {
        List<PacientesModel> lista = new ArrayList<>();
        String sql = "SELECT p.* FROM pacientes p " +
                     "JOIN usuarios u ON p.usuario_id = u.id " +
                     "WHERE LOWER(u.nombre) LIKE LOWER(?) OR LOWER(u.apellido) LIKE LOWER(?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + nombre + "%");
            ps.setString(2, "%" + nombre + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                PacientesModel p = new PacientesModel();
                p.setId(rs.getLong("id"));
                p.setUsuarioId(rs.getLong("usuario_id"));
                p.setCodigoPaciente(rs.getString("codigo_paciente"));
                p.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                p.setGenero(rs.getString("genero"));
                p.setDireccion(rs.getString("direccion"));
                p.setContactoEmergenciaNombre(rs.getString("contacto_emergencia_nombre"));
                p.setContactoEmergenciaTelefono(rs.getString("contacto_emergencia_telefono"));
                p.setHistorialMedico(rs.getString("historial_medico"));
                p.setAlergias(rs.getString("alergias"));
                lista.add(p);
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