package DAO;

import Model.MedicamentosModel;
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

public class MedicamentosDAO {
    
    private static final String BASE_URL = "http://localhost:8080/proyectop/webresources/medicamentos";
    
    public List<MedicamentosModel> Listar() {
        List<MedicamentosModel> lista = new ArrayList<>();
        
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
                    MedicamentosModel m = new MedicamentosModel();
                    
                    m.setId(obj.getLong("id"));
                    m.setNombre(obj.getString("nombre"));
                    m.setCantidadStock(obj.getInt("cantidadStock"));
                    m.setPrecioUnitario(obj.getDouble("precioUnitario"));
                    
                    if (obj.has("fechaExpiracion") && !obj.isNull("fechaExpiracion")) {
                        m.setFechaExpiracion(LocalDate.parse(obj.getString("fechaExpiracion")));
                    }
                    
                    m.setDescripcion(obj.optString("descripcion", ""));
                    
                    lista.add(m);
                }
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al listar medicamentos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lista;
    }
    
    public MedicamentosModel BuscarPorId(long id) {
        MedicamentosModel medicamento = null;
        
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
                medicamento = new MedicamentosModel();
                
                medicamento.setId(obj.getLong("id"));
                medicamento.setNombre(obj.getString("nombre"));
                medicamento.setCantidadStock(obj.getInt("cantidadStock"));
                medicamento.setPrecioUnitario(obj.getDouble("precioUnitario"));
                
                if (obj.has("fechaExpiracion") && !obj.isNull("fechaExpiracion")) {
                    medicamento.setFechaExpiracion(LocalDate.parse(obj.getString("fechaExpiracion")));
                }
                
                medicamento.setDescripcion(obj.optString("descripcion", ""));
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al buscar medicamento: " + e.getMessage());
            e.printStackTrace();
        }
        
        return medicamento;
    }
    
    public int Agregar(MedicamentosModel m) {
        int resultado = 0;
        
        try {
            URL url = new URL(BASE_URL + "/agregar");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            
            JSONObject json = new JSONObject();
            json.put("nombre", m.getNombre());
            json.put("cantidadStock", m.getCantidadStock());
            json.put("precioUnitario", m.getPrecioUnitario());
            
            if (m.getFechaExpiracion() != null) {
                json.put("fechaExpiracion", m.getFechaExpiracion().toString());
            }
            
            json.put("descripcion", m.getDescripcion());
            
            String jsonString = json.toString();
            System.out.println("[v0] Enviando medicamento: " + jsonString);
            
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
                System.out.println("[v0] Medicamento agregado, resultado: " + resultado);
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al agregar medicamento: " + e.getMessage());
            e.printStackTrace();
        }
        
        return resultado;
    }
    
    public int Actualizar(MedicamentosModel m) {
        int resultado = 0;
        
        try {
            URL url = new URL(BASE_URL + "/modificar");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            
            JSONObject json = new JSONObject();
            json.put("id", m.getId());
            json.put("nombre", m.getNombre());
            json.put("cantidadStock", m.getCantidadStock());
            json.put("precioUnitario", m.getPrecioUnitario());
            
            if (m.getFechaExpiracion() != null) {
                json.put("fechaExpiracion", m.getFechaExpiracion().toString());
            }
            
            json.put("descripcion", m.getDescripcion());
            
            String jsonString = json.toString();
            System.out.println("[v0] Actualizando medicamento: " + jsonString);
            
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
                System.out.println("[v0] Medicamento actualizado, resultado: " + resultado);
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al actualizar medicamento: " + e.getMessage());
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
                System.out.println("[v0] Medicamento eliminado, resultado: " + resultado);
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al eliminar medicamento: " + e.getMessage());
            e.printStackTrace();
        }
        
        return resultado;
    }
}
