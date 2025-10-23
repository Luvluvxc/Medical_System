package DAO;


import Config.Conexion;
import Model.UsuariosResourse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class UsuarioDAO {

    private static final String BASE_URL = "http://localhost:8080/proyectop/webresources/generic";

    public List<UsuariosResourse> Listar() {
        return ListarPorRol(null);
    }

    public List<UsuariosResourse> ListarPorRol(String rol) {
        List<UsuariosResourse> lista = new ArrayList<>();

        try {
            String urlString = BASE_URL + "/lista";
            if (rol != null && !rol.trim().isEmpty()) {
                urlString += "?rol=" + rol;
            }

            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONArray jsonArray = new JSONArray(response.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    UsuariosResourse u = new UsuariosResourse();

                    u.setId(obj.getLong("id"));
                    u.setCorreo(obj.getString("correo"));
                    u.setContrasenaHash(obj.optString("contrasenaHash", ""));
                    u.setRol(obj.getString("rol"));
                    u.setNombre(obj.optString("nombre", ""));
                    u.setApellido(obj.optString("apellido", ""));
                    u.setTelefono(obj.optString("telefono", ""));
                    u.setActivo(obj.getBoolean("activo"));

                    lista.add(u);
                }
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al listar usuarios: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    // MÉTODO EXISTENTE - Validar login
    public UsuariosResourse Validar(String correo, String contrasenaHash) {

        Conexion cn = new Conexion();
        Connection con;
        PreparedStatement ps;
        ResultSet rs;

        UsuariosResourse u = new UsuariosResourse();

        String sql = "SELECT * FROM usuarios WHERE correo = ? AND contrasena_hash = ?";

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
            // Puedes cerrar aquí la conexión si tu clase Conexion no lo hace automáticamente
        }

        return u;
    }

    public UsuariosResourse BuscarPorId(long id) {
        UsuariosResourse usuario = null;

        try {
            URL url = new URL(BASE_URL + "/" + id);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject obj = new JSONObject(response.toString());
                usuario = new UsuariosResourse();

                usuario.setId(obj.getLong("id"));
                usuario.setCorreo(obj.getString("correo"));
                usuario.setContrasenaHash(obj.optString("contrasenaHash", ""));
                usuario.setRol(obj.getString("rol"));
                usuario.setNombre(obj.optString("nombre", ""));
                usuario.setApellido(obj.optString("apellido", ""));
                usuario.setTelefono(obj.optString("telefono", ""));
                usuario.setActivo(obj.getBoolean("activo"));
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al buscar usuario: " + e.getMessage());
            e.printStackTrace();
        }

        return usuario;
    }

    public UsuariosResourse Agregar(UsuariosResourse u) {
        UsuariosResourse usuarioCreado = null;

        try {
            URL url = new URL(BASE_URL + "/agregar");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            JSONObject json = new JSONObject();
            json.put("correo", u.getCorreo());
            json.put("contrasenaHash", u.getContrasenaHash());
            json.put("rol", u.getRol());
            json.put("nombre", u.getNombre());
            json.put("apellido", u.getApellido());
            json.put("telefono", u.getTelefono());
            json.put("activo", u.isActivo());

            String jsonString = json.toString();
            System.out.println("[v0] Enviando JSON: " + jsonString);

            OutputStream os = con.getOutputStream();
            os.write(jsonString.getBytes("UTF-8"));
            os.flush();
            os.close();

            int responseCode = con.getResponseCode();
            System.out.println("[v0] Código de respuesta: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println("[v0] Respuesta exitosa: " + response.toString());

                JSONObject responseObj = new JSONObject(response.toString());
                usuarioCreado = new UsuariosResourse();
                usuarioCreado.setId(responseObj.getLong("id"));
                usuarioCreado.setCorreo(responseObj.getString("correo"));
                usuarioCreado.setRol(responseObj.getString("rol"));
                usuarioCreado.setNombre(responseObj.optString("nombre", ""));
                usuarioCreado.setApellido(responseObj.optString("apellido", ""));
                usuarioCreado.setTelefono(responseObj.optString("telefono", ""));
                usuarioCreado.setActivo(responseObj.getBoolean("activo"));

                System.out.println("[v0] Usuario creado con ID: " + usuarioCreado.getId());
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al agregar usuario: " + e.getMessage());
            e.printStackTrace();
        }

        return usuarioCreado;
    }

    public int Actualizar(UsuariosResourse u) {
        int resultado = 0;

        try {
            URL url = new URL(BASE_URL + "/modificar");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            JSONObject json = new JSONObject();
            json.put("id", u.getId());
            json.put("correo", u.getCorreo());
            json.put("contrasenaHash", u.getContrasenaHash());
            json.put("rol", u.getRol());
            json.put("nombre", u.getNombre());
            json.put("apellido", u.getApellido());
            json.put("telefono", u.getTelefono());
            json.put("activo", u.isActivo());

            String jsonString = json.toString();
            System.out.println("[v0] Actualizando usuario: " + jsonString);

            OutputStream os = con.getOutputStream();
            os.write(jsonString.getBytes("UTF-8"));
            os.flush();
            os.close();

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String response = in.readLine();
                in.close();

                resultado = Integer.parseInt(response);
                System.out.println("[v0] Usuario actualizado, resultado: " + resultado);
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al actualizar usuario: " + e.getMessage());
            e.printStackTrace();
        }

        return resultado;
    }

    public int CambiarEstado(long id, boolean activo) {
        int resultado = 0;

        try {
            UsuariosResourse usuario = BuscarPorId(id);

            if (usuario != null) {
                usuario.setActivo(activo);
                resultado = Actualizar(usuario);
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al cambiar estado: " + e.getMessage());
            e.printStackTrace();
        }

        return resultado;
    }
}
