package DAO;

import Model.DoctoresModel;
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

public class DoctoresDAO {

    private static final String BASE_URL = "http://localhost:8080/proyectop/webresources/doctores";

    public List<DoctoresModel> Listar() {
        List<DoctoresModel> lista = new ArrayList<>();

        try {
            URL url = new URL(BASE_URL + "/lista");
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
                    DoctoresModel d = new DoctoresModel();

                    d.setId(obj.getLong("id"));
                    d.setUsuarioId(obj.getInt("usuarioId"));
                    d.setNumeroLicencia(obj.getString("numeroLicencia"));
                    d.setEspecializacion(obj.optString("especializacion", ""));

                    d.setUsuarioNombre(obj.optString("usuarioNombre", ""));
                    d.setUsuarioApellido(obj.optString("usuarioApellido", ""));
                    d.setUsuarioCorreo(obj.optString("usuarioCorreo", ""));
                    d.setUsuarioTelefono(obj.optString("usuarioTelefono", ""));
                    d.setUsuarioRol(obj.optString("usuarioRol", ""));
                    d.setUsuarioActivo(obj.optBoolean("usuarioActivo", true));

                    lista.add(d);
                }
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al listar doctores: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    public DoctoresModel BuscarPorId(long id) {
        DoctoresModel doctor = null;

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
                doctor = new DoctoresModel();

                doctor.setId(obj.getLong("id"));
                doctor.setUsuarioId(obj.getInt("usuarioId"));
                doctor.setNumeroLicencia(obj.getString("numeroLicencia"));
                doctor.setEspecializacion(obj.optString("especializacion", ""));

                doctor.setUsuarioNombre(obj.optString("usuarioNombre", ""));
                doctor.setUsuarioApellido(obj.optString("usuarioApellido", ""));
                doctor.setUsuarioCorreo(obj.optString("usuarioCorreo", ""));
                doctor.setUsuarioTelefono(obj.optString("usuarioTelefono", ""));
                doctor.setUsuarioRol(obj.optString("usuarioRol", ""));
                doctor.setUsuarioActivo(obj.optBoolean("usuarioActivo", true));
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al buscar doctor: " + e.getMessage());
            e.printStackTrace();
        }

        return doctor;
    }

    public int Agregar(DoctoresModel d) {
    int resultado = 0;

    try {
        URL url = new URL(BASE_URL + "/agregar");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        con.setConnectTimeout(10000); // 10 segundos timeout
        con.setReadTimeout(10000);

        JSONObject json = new JSONObject();
        json.put("usuarioId", d.getUsuarioId());
        json.put("numeroLicencia", d.getNumeroLicencia());
        json.put("especializacion", d.getEspecializacion());

        String jsonString = json.toString();
        System.out.println("[DEBUG] Enviando doctor: " + jsonString);

        // Escribir el JSON
        try (OutputStream os = con.getOutputStream()) {
            os.write(jsonString.getBytes("UTF-8"));
            os.flush();
        }

        int responseCode = con.getResponseCode();
        System.out.println("[DEBUG] Código de respuesta HTTP: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String response = in.readLine();
            in.close();

            System.out.println("[DEBUG] Respuesta del servidor: " + response);
            
            resultado = Integer.parseInt(response);
        } else {
            // Leer el error stream si hay problema
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            String errorLine;
            StringBuilder errorResponse = new StringBuilder();
            while ((errorLine = errorReader.readLine()) != null) {
                errorResponse.append(errorLine);
            }
            errorReader.close();
            System.out.println("[ERROR] Respuesta de error: " + errorResponse.toString());
        }

    } catch (Exception e) {
        System.out.println("[ERROR] Excepción al agregar doctor: " + e.getMessage());
        e.printStackTrace();
        resultado = 0;
    }

    return resultado;
}

    public int Actualizar(DoctoresModel d) {
        int resultado = 0;

        try {
            URL url = new URL(BASE_URL + "/modificar");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            JSONObject json = new JSONObject();
            json.put("id", d.getId());
            json.put("usuarioId", d.getUsuarioId());
            json.put("numeroLicencia", d.getNumeroLicencia());
            json.put("especializacion", d.getEspecializacion());

            String jsonString = json.toString();
            System.out.println("[v0] Actualizando doctor: " + jsonString);

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
                System.out.println("[v0] Doctor actualizado, resultado: " + resultado);
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al actualizar doctor: " + e.getMessage());
            e.printStackTrace();
        }

        return resultado;
    }

    public DoctoresModel BuscarPorUsuarioId(int usuarioId) {
        DoctoresModel doctor = null;

        try {
            URL url = new URL(BASE_URL + "/usuario/" + usuarioId);
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
                doctor = new DoctoresModel();

                doctor.setId(obj.getLong("id"));
                doctor.setUsuarioId(obj.getInt("usuarioId"));
                doctor.setNumeroLicencia(obj.getString("numeroLicencia"));
                doctor.setEspecializacion(obj.optString("especializacion", ""));

                doctor.setUsuarioNombre(obj.optString("usuarioNombre", ""));
                doctor.setUsuarioApellido(obj.optString("usuarioApellido", ""));
                doctor.setUsuarioCorreo(obj.optString("usuarioCorreo", ""));
                doctor.setUsuarioTelefono(obj.optString("usuarioTelefono", ""));
                doctor.setUsuarioRol(obj.optString("usuarioRol", ""));
                doctor.setUsuarioActivo(obj.optBoolean("usuarioActivo", true));
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al buscar doctor por usuarioId: " + e.getMessage());
            e.printStackTrace();
        }

        return doctor;
    }

    public int Eliminar(long id) {
        int resultado = 0;

        try {
            URL url = new URL(BASE_URL + "/eliminar/" + id);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("DELETE");
            con.setRequestProperty("Accept", "application/json");

            int responseCode = con.getResponseCode();
            System.out.println("[v0] Código de respuesta eliminar: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String response = in.readLine();
                in.close();

                resultado = Integer.parseInt(response);
                System.out.println("[v0] Doctor eliminado, resultado: " + resultado);
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al eliminar doctor: " + e.getMessage());
            e.printStackTrace();
        }

        return resultado;
    }

}
