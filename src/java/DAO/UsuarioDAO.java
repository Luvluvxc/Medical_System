package DAO;

import Config.Conexion;
import java.sql.*;
import Model.UsuariosResourse;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // MÉTODO EXISTENTE - Validar login
    public UsuariosResourse Validar(String nombre, String contrasenaHash) {
        UsuariosResourse u = new UsuariosResourse();
        String sql = "select * from usuarios where nombre=? and contrasena_hash=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, contrasenaHash);
            rs = ps.executeQuery();
            while (rs.next()) {
                u.setId(rs.getLong("id"));
                u.setNombre(rs.getString("nombre"));
                u.setContrasenaHash(rs.getString("contrasena_hash"));
                u.setCorreo(rs.getString("correo"));
                u.setRol(rs.getString("rol"));
                u.setApellido(rs.getString("apellido"));
                u.setTelefono(rs.getString("telefono"));
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return u;
    }
    
    // NUEVO - Agregar usuario
    public boolean agregar(UsuariosResourse u) {
        String sql = "INSERT INTO usuarios (correo, contrasena_hash, rol, nombre, apellido, telefono, activo) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getCorreo());
            ps.setString(2, u.getContrasenaHash());
            ps.setString(3, u.getRol());
            ps.setString(4, u.getNombre());
            ps.setString(5, u.getApellido());
            ps.setString(6, u.getTelefono());
            ps.setBoolean(7, u.isActivo());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexiones();
        }
    }
    
    // NUEVO - Buscar por correo
    public UsuariosResourse buscarPorCorreo(String correo) {
        UsuariosResourse u = new UsuariosResourse();
        String sql = "SELECT * FROM usuarios WHERE correo = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            rs = ps.executeQuery();
            if (rs.next()) {
                u.setId(rs.getLong("id"));
                u.setCorreo(rs.getString("correo"));
                u.setContrasenaHash(rs.getString("contrasena_hash"));
                u.setRol(rs.getString("rol"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setTelefono(rs.getString("telefono"));
                u.setActivo(rs.getBoolean("activo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return u;
    }
    
    // NUEVO - Buscar por ID
    // BUSCAR usuario por ID
    // BUSCAR usuario por ID (VERSIÓN CORREGIDA)
public UsuariosResourse buscarPorId(long id) {
    String sql = "SELECT * FROM usuarios WHERE id = ?";
    UsuariosResourse usuario = null;
    Connection conexion = null;
    PreparedStatement statement = null;
    ResultSet resultado = null;
    
    try {
        System.out.println("Buscando usuario con ID: " + id);
        
        // Obtener una nueva conexión
        conexion = cn.getConnection();
        
        if (conexion == null) {
            System.err.println("ERROR: No se pudo obtener la conexión a la base de datos");
            return null;
        }
        
        System.out.println("Conexión obtenida exitosamente");
        
        // Preparar y ejecutar la consulta
        statement = conexion.prepareStatement(sql);
        statement.setLong(1, id);
        resultado = statement.executeQuery();
        
        if (resultado.next()) {
            usuario = new UsuariosResourse();
            usuario.setId(resultado.getLong("id"));
            usuario.setNombre(resultado.getString("nombre"));
            usuario.setApellido(resultado.getString("apellido"));
            usuario.setCorreo(resultado.getString("correo"));
            usuario.setContrasenaHash(resultado.getString("contrasena_hash"));
            usuario.setTelefono(resultado.getString("telefono"));
            usuario.setRol(resultado.getString("rol"));
            usuario.setActivo(resultado.getBoolean("activo"));
            
            System.out.println("Usuario encontrado: " + usuario.getNombre() + " " + usuario.getApellido());
        } else {
            System.out.println("No se encontró usuario con ID: " + id);
        }
        
    } catch (Exception e) {
        System.err.println("Error al buscar usuario por ID: " + id);
        e.printStackTrace();
    } finally {
        // Cerrar recursos en el orden correcto
        try {
            if (resultado != null) resultado.close();
            if (statement != null) statement.close();
            if (conexion != null) conexion.close();
        } catch (Exception ex) {
            System.err.println("Error al cerrar recursos:");
            ex.printStackTrace();
        }
    }
    return usuario;
}
    
    // NUEVO - Listar todos los usuarios
    public List<UsuariosResourse> listar() {
        List<UsuariosResourse> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY creado_en DESC";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                UsuariosResourse u = new UsuariosResourse();
                u.setId(rs.getLong("id"));
                u.setCorreo(rs.getString("correo"));
                u.setContrasenaHash(rs.getString("contrasena_hash"));
                u.setRol(rs.getString("rol"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setTelefono(rs.getString("telefono"));
                u.setActivo(rs.getBoolean("activo"));
                lista.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return lista;
    }
    
    // NUEVO - Actualizar usuario
    public boolean actualizar(UsuariosResourse u) {
        String sql = "UPDATE usuarios SET nombre=?, apellido=?, telefono=?, " +
                     "correo=?, rol=?, activo=?, actualizado_en=CURRENT_TIMESTAMP WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getTelefono());
            ps.setString(4, u.getCorreo());
            ps.setString(5, u.getRol());
            ps.setBoolean(6, u.isActivo());
            ps.setLong(7, u.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexiones();
        }
    }
    
    // Método auxiliar para cerrar conexiones
    private void cerrarConexiones() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}