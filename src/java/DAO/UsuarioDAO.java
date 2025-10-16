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
    public UsuariosResourse Validar(String correo, String contrasenaHash) {
        UsuariosResourse u = new UsuariosResourse();
        String sql = "select * from usuarios where correo=? and contrasena_hash=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return u;
    }

    // Método auxiliar para cerrar conexiones
    private void cerrarConexiones() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
