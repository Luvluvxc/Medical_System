package DAO;

import Model.PacientesModel;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class PacientesDAO {
    
    private static final String BASE_URL = "http://localhost:8080/proyectop/webresources/pacientes";
    
    public List<PacientesModel> Listar() {
        List<PacientesModel> lista = new ArrayList<>();
        
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
                    PacientesModel p = new PacientesModel();
                    
                    p.setId(obj.getLong("id"));
                    p.setUsuarioId(obj.getLong("usuarioId"));
                    p.setCodigoPaciente(obj.getString("codigoPaciente"));
                    
                    if (obj.has("fechaNacimiento") && !obj.isNull("fechaNacimiento")) {
                        p.setFechaNacimiento(LocalDate.parse(obj.getString("fechaNacimiento")));
                    }
                    
                    p.setGenero(obj.optString("genero", ""));
                    p.setDireccion(obj.optString("direccion", ""));
                    p.setContactoEmergenciaNombre(obj.optString("contactoEmergenciaNombre", ""));
                    p.setContactoEmergenciaTelefono(obj.optString("contactoEmergenciaTelefono", ""));
                    p.setHistorialMedico(obj.optString("historialMedico", ""));
                    p.setAlergias(obj.optString("alergias", ""));
                    
                    p.setUsuarioNombre(obj.optString("usuarioNombre", ""));
                    p.setUsuarioApellido(obj.optString("usuarioApellido", ""));
                    p.setUsuarioCorreo(obj.optString("usuarioCorreo", ""));
                    p.setUsuarioTelefono(obj.optString("usuarioTelefono", ""));
                    p.setUsuarioRol(obj.optString("usuarioRol", ""));
                    p.setUsuarioActivo(obj.optBoolean("usuarioActivo", true));
                    
                    lista.add(p);
                }
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al listar pacientes: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lista;
    }
    
    public PacientesModel BuscarPorId(long id) {
        PacientesModel paciente = null;
        
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
                paciente = new PacientesModel();
                
                paciente.setId(obj.getLong("id"));
                paciente.setUsuarioId(obj.getLong("usuarioId"));
                paciente.setCodigoPaciente(obj.getString("codigoPaciente"));
                
                if (obj.has("fechaNacimiento") && !obj.isNull("fechaNacimiento")) {
                    paciente.setFechaNacimiento(LocalDate.parse(obj.getString("fechaNacimiento")));
                }
                
                paciente.setGenero(obj.optString("genero", ""));
                paciente.setDireccion(obj.optString("direccion", ""));
                paciente.setContactoEmergenciaNombre(obj.optString("contactoEmergenciaNombre", ""));
                paciente.setContactoEmergenciaTelefono(obj.optString("contactoEmergenciaTelefono", ""));
                paciente.setHistorialMedico(obj.optString("historialMedico", ""));
                paciente.setAlergias(obj.optString("alergias", ""));
                
                paciente.setUsuarioNombre(obj.optString("usuarioNombre", ""));
                paciente.setUsuarioApellido(obj.optString("usuarioApellido", ""));
                paciente.setUsuarioCorreo(obj.optString("usuarioCorreo", ""));
                paciente.setUsuarioTelefono(obj.optString("usuarioTelefono", ""));
                paciente.setUsuarioRol(obj.optString("usuarioRol", ""));
                paciente.setUsuarioActivo(obj.optBoolean("usuarioActivo", true));
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al buscar paciente: " + e.getMessage());
            e.printStackTrace();
        }
        
        return paciente;
    }
    
    public int Agregar(PacientesModel p) {
        int resultado = 0;
        
        try {
            URL url = new URL(BASE_URL + "/agregar");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            
            JSONObject json = new JSONObject();
            json.put("usuarioId", p.getUsuarioId());
            json.put("codigoPaciente", p.getCodigoPaciente());
            
            if (p.getFechaNacimiento() != null) {
                json.put("fechaNacimiento", p.getFechaNacimiento().toString());
            }
            
            json.put("genero", p.getGenero());
            json.put("direccion", p.getDireccion());
            json.put("contactoEmergenciaNombre", p.getContactoEmergenciaNombre());
            json.put("contactoEmergenciaTelefono", p.getContactoEmergenciaTelefono());
            json.put("historialMedico", p.getHistorialMedico());
            json.put("alergias", p.getAlergias());
            
            String jsonString = json.toString();
            System.out.println("[v0] Enviando paciente: " + jsonString);
            
            OutputStream os = con.getOutputStream();
            os.write(jsonString.getBytes("UTF-8"));
            os.flush();
            os.close();
            
            int responseCode = con.getResponseCode();
            System.out.println("[v0] Código de respuesta: " + responseCode);
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String response = in.readLine();
                in.close();
                
                resultado = Integer.parseInt(response);
                System.out.println("[v0] Paciente agregado, resultado: " + resultado);
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al agregar paciente: " + e.getMessage());
            e.printStackTrace();
        }
        
        return resultado;
    }
    
    public int Actualizar(PacientesModel p) {
        int resultado = 0;
        
        try {
            URL url = new URL(BASE_URL + "/modificar");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            
            JSONObject json = new JSONObject();
            json.put("id", p.getId());
            json.put("usuarioId", p.getUsuarioId());
            
            if (p.getFechaNacimiento() != null) {
                json.put("fechaNacimiento", p.getFechaNacimiento().toString());
            }
            
            json.put("genero", p.getGenero());
            json.put("direccion", p.getDireccion());
            json.put("contactoEmergenciaNombre", p.getContactoEmergenciaNombre());
            json.put("contactoEmergenciaTelefono", p.getContactoEmergenciaTelefono());
            json.put("historialMedico", p.getHistorialMedico());
            json.put("alergias", p.getAlergias());
            
            String jsonString = json.toString();
            System.out.println("[v0] Actualizando paciente: " + jsonString);
            
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
                System.out.println("[v0] Paciente actualizado, resultado: " + resultado);
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al actualizar paciente: " + e.getMessage());
            e.printStackTrace();
        }
        
        return resultado;
    }
}
