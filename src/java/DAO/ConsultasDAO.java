package DAO;

import Model.ConsultasModel;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ConsultasDAO {
    
    private static final String BASE_URL = "http://localhost:8080/proyectop/webresources/consultas";
    
    public List<ConsultasModel> Listar() {
        List<ConsultasModel> lista = new ArrayList<>();
        
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
                    ConsultasModel c = new ConsultasModel();
                    
                    c.setId(obj.getLong("id"));
                    c.setCitaId(obj.getInt("citaId"));
                    c.setDiagnostico(obj.optString("diagnostico", ""));
                    c.setPlanTratamiento(obj.optString("planTratamiento", ""));
                    c.setObservaciones(obj.optString("observaciones", ""));
                    
                    lista.add(c);
                }
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al listar consultas: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lista;
    }
    
    public ConsultasModel BuscarPorId(long id) {
        ConsultasModel consulta = null;
        
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
                consulta = new ConsultasModel();
                
                consulta.setId(obj.getLong("id"));
                consulta.setCitaId(obj.getInt("citaId"));
                consulta.setDiagnostico(obj.optString("diagnostico", ""));
                consulta.setPlanTratamiento(obj.optString("planTratamiento", ""));
                consulta.setObservaciones(obj.optString("observaciones", ""));
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al buscar consulta: " + e.getMessage());
            e.printStackTrace();
        }
        
        return consulta;
    }
    
    public ConsultasModel BuscarPorCitaId(int citaId) {
        ConsultasModel consulta = null;
        
        try {
            URL url = new URL(BASE_URL + "/cita/" + citaId);
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
                consulta = new ConsultasModel();
                
                consulta.setId(obj.getLong("id"));
                consulta.setCitaId(obj.getInt("citaId"));
                consulta.setDiagnostico(obj.optString("diagnostico", ""));
                consulta.setPlanTratamiento(obj.optString("planTratamiento", ""));
                consulta.setObservaciones(obj.optString("observaciones", ""));
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al buscar consulta por cita: " + e.getMessage());
            e.printStackTrace();
        }
        
        return consulta;
    }
    
    public int Agregar(ConsultasModel c) {
        int resultado = 0;
        
        try {
            URL url = new URL(BASE_URL + "/agregar");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            
            JSONObject json = new JSONObject();
            json.put("citaId", c.getCitaId());
            json.put("diagnostico", c.getDiagnostico());
            json.put("planTratamiento", c.getPlanTratamiento());
            json.put("observaciones", c.getObservaciones());
            
            String jsonString = json.toString();
            System.out.println("[v0] Enviando consulta: " + jsonString);
            
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
                System.out.println("[v0] Consulta agregada, resultado: " + resultado);
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al agregar consulta: " + e.getMessage());
            e.printStackTrace();
        }
        
        return resultado;
    }
    
    public int Actualizar(ConsultasModel c) {
        int resultado = 0;
        
        try {
            URL url = new URL(BASE_URL + "/modificar");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            
            JSONObject json = new JSONObject();
            json.put("id", c.getId());
            json.put("citaId", c.getCitaId());
            json.put("diagnostico", c.getDiagnostico());
            json.put("planTratamiento", c.getPlanTratamiento());
            json.put("observaciones", c.getObservaciones());
            
            String jsonString = json.toString();
            System.out.println("[v0] Actualizando consulta: " + jsonString);
            
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
                System.out.println("[v0] Consulta actualizada, resultado: " + resultado);
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al actualizar consulta: " + e.getMessage());
            e.printStackTrace();
        }
        
        return resultado;
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
                System.out.println("[v0] Consulta eliminada, resultado: " + resultado);
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al eliminar consulta: " + e.getMessage());
            e.printStackTrace();
        }
        
        return resultado;
    }
}
