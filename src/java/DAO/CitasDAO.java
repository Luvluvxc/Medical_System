package DAO;

import Model.CitasModel;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class CitasDAO {

    private static final String BASE_URL = "http://localhost:8080/proyectop/webresources/citas";

    public List<CitasModel> Listar() {
        List<CitasModel> lista = new ArrayList<>();

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
                    CitasModel cita = new CitasModel();

                    cita.setId(obj.getLong("id"));
                    cita.setPacienteId(obj.getInt("pacienteId"));
                    cita.setDoctorId(obj.getInt("doctorId"));

                    if (obj.has("fechaCita") && !obj.isNull("fechaCita")) {
                        cita.setFechaCita(LocalDate.parse(obj.getString("fechaCita")));
                    }

                    if (obj.has("horaCita") && !obj.isNull("horaCita")) {
                        cita.setHoraCita(LocalTime.parse(obj.getString("horaCita")));
                    }

                    cita.setEstado(obj.optString("estado", ""));
                    cita.setMotivo(obj.optString("motivo", ""));

                    cita.setPacienteNombre(obj.optString("pacienteNombre", ""));
                    cita.setPacienteApellido(obj.optString("pacienteApellido", ""));
                    cita.setDoctorNombre(obj.optString("doctorNombre", ""));
                    cita.setDoctorApellido(obj.optString("doctorApellido", ""));
                    cita.setDoctorEspecializacion(obj.optString("doctorEspecializacion", ""));

                    lista.add(cita);
                }
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al listar citas: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    public CitasModel BuscarPorId(long id) {
        CitasModel cita = null;

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
                cita = new CitasModel();

                cita.setId(obj.getLong("id"));
                cita.setPacienteId(obj.getInt("pacienteId"));
                cita.setDoctorId(obj.getInt("doctorId"));

                if (obj.has("fechaCita") && !obj.isNull("fechaCita")) {
                    cita.setFechaCita(LocalDate.parse(obj.getString("fechaCita")));
                }

                if (obj.has("horaCita") && !obj.isNull("horaCita")) {
                    cita.setHoraCita(LocalTime.parse(obj.getString("horaCita")));
                }

                cita.setEstado(obj.optString("estado", ""));
                cita.setMotivo(obj.optString("motivo", ""));

                cita.setPacienteNombre(obj.optString("pacienteNombre", ""));
                cita.setPacienteApellido(obj.optString("pacienteApellido", ""));
                cita.setDoctorNombre(obj.optString("doctorNombre", ""));
                cita.setDoctorApellido(obj.optString("doctorApellido", ""));
                cita.setDoctorEspecializacion(obj.optString("doctorEspecializacion", ""));
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al buscar cita: " + e.getMessage());
            e.printStackTrace();
        }

        return cita;
    }

    public int Agregar(CitasModel cita) {
        try {
            URL url = new URL(BASE_URL + "/agregar");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JSONObject jsonCita = new JSONObject();
            jsonCita.put("pacienteId", cita.getPacienteId());
            jsonCita.put("doctorId", cita.getDoctorId());
            jsonCita.put("fechaCita", cita.getFechaCita().toString());
            jsonCita.put("horaCita", cita.getHoraCita().toString());
            jsonCita.put("estado", cita.getEstado());
            jsonCita.put("motivo", cita.getMotivo());

            System.out.println("[v0] DAO enviando JSON: " + jsonCita.toString());

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonCita.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = con.getResponseCode();
            System.out.println("[v0] Código de respuesta: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                // <CHANGE> Leer la respuesta JSON
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                br.close();

                System.out.println("[v0] Respuesta de la API: " + response.toString());

                // <CHANGE> Parsear el JSON de respuesta
                JSONObject jsonResponse = new JSONObject(response.toString());
                int result = jsonResponse.getInt("result");

                System.out.println("[v0] Cita agregada, resultado: " + result);
                return result;
            } else {
                System.out.println("[v0] Error: código de respuesta " + responseCode);
                return 0;
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al agregar cita: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    public int Actualizar(CitasModel cita) {
        int resultado = 0;

        try {
            URL url = new URL(BASE_URL + "/modificar");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            JSONObject json = new JSONObject();
            json.put("id", cita.getId());
            json.put("pacienteId", cita.getPacienteId());
            json.put("doctorId", cita.getDoctorId());

            if (cita.getFechaCita() != null) {
                json.put("fechaCita", cita.getFechaCita().toString());
            }

            if (cita.getHoraCita() != null) {
                json.put("horaCita", cita.getHoraCita().toString());
            }

            json.put("estado", cita.getEstado());
            json.put("motivo", cita.getMotivo());

            String jsonString = json.toString();
            System.out.println("[v0] Actualizando cita: " + jsonString);

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
                System.out.println("[v0] Cita actualizada, resultado: " + resultado);
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al actualizar cita: " + e.getMessage());
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
                System.out.println("[v0] Cita eliminada, resultado: " + resultado);
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al eliminar cita: " + e.getMessage());
            e.printStackTrace();
        }

        return resultado;
    }
}
