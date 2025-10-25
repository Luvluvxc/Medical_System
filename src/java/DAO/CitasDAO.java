package DAO;

import Model.CitasModel;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class CitasDAO {

    private static final String BASE_URL = "http://localhost:8080/proyectop/webresources/citas";

    public List<CitasModel> ListarPorDoctor(int doctorId) {
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

                    // Filtrar por doctorId
                    if (obj.getInt("doctorId") == doctorId) {
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
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al listar citas por doctor: " + e.getMessage());
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
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                br.close();

                System.out.println("[v0] Respuesta de la API: " + response.toString());

                JSONObject jsonResponse = new JSONObject(response.toString());

                // Verificar si hay conflicto de horario
                if (jsonResponse.has("errorCode") && "SCHEDULE_CONFLICT".equals(jsonResponse.getString("errorCode"))) {
                    System.out.println("[v0] Conflicto de horario detectado: " + jsonResponse.getString("message"));
                    return -1; // Código especial para conflicto de horario
                }

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

    public int Actualizar(CitasModel cita) {
        try {
            URL url = new URL(BASE_URL + "/modificar");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);

            // Crear JSON con formato correcto
            JSONObject json = new JSONObject();
            json.put("id", cita.getId());
            json.put("pacienteId", cita.getPacienteId());
            json.put("doctorId", cita.getDoctorId());

            // Manejar fecha - asegurar formato correcto
            if (cita.getFechaCita() != null) {
                json.put("fechaCita", cita.getFechaCita().toString());
            } else {
                json.put("fechaCita", JSONObject.NULL);
            }

            // Manejar hora - asegurar formato correcto
            if (cita.getHoraCita() != null) {
                // Formato HH:mm:ss
                String horaStr = cita.getHoraCita().toString();
                if (horaStr.length() == 5) {
                    horaStr = horaStr + ":00";
                }
                json.put("horaCita", horaStr);
            } else {
                json.put("horaCita", JSONObject.NULL);
            }

            json.put("estado", cita.getEstado() != null ? cita.getEstado() : "programada");
            json.put("motivo", cita.getMotivo() != null ? cita.getMotivo() : "");

            String jsonString = json.toString();
            System.out.println("[v0] DAO enviando JSON para actualizar: " + jsonString);

            // Escribir el JSON en el cuerpo de la solicitud
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonString.getBytes("utf-8");
                os.write(input, 0, input.length);
                os.flush();
            }

            int responseCode = con.getResponseCode();
            System.out.println("[v0] Código de respuesta actualizar: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Leer respuesta
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    responseBuilder.append(line);
                }
                in.close();

                String response = responseBuilder.toString().trim();
                System.out.println("[v0] Respuesta completa de la API: " + response);

                // Parsear respuesta JSON
                JSONObject jsonResponse = new JSONObject(response);

                // Verificar si hay conflicto de horario
                if (jsonResponse.has("errorCode") && "SCHEDULE_CONFLICT".equals(jsonResponse.getString("errorCode"))) {
                    System.out.println("[v0] Conflicto de horario detectado en actualización: " + jsonResponse.getString("message"));
                    return -1; // Código especial para conflicto de horario
                }

                int resultado = jsonResponse.getInt("result");
                System.out.println("[v0] Cita actualizada, resultado: " + resultado);
                return resultado;
            } else {
                System.out.println("[v0] Error en actualizar: código " + responseCode);

                // Leer mensaje de error si existe
                try {
                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(con.getErrorStream(), "utf-8"));
                    StringBuilder errorResponse = new StringBuilder();
                    String errorLine;
                    while ((errorLine = errorReader.readLine()) != null) {
                        errorResponse.append(errorLine);
                    }
                    errorReader.close();
                    System.out.println("[v0] Error response: " + errorResponse.toString());
                } catch (Exception e) {
                    System.out.println("[v0] No se pudo leer error response: " + e.getMessage());
                }
                return 0;
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al actualizar cita: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    // Agrega este método en CitasDAO.java para actualización directa
    public int ActualizarDirecto(CitasModel cita) {
        String sql = "UPDATE citas SET paciente_id=?, doctor_id=?, fecha_cita=?, hora_cita=?, estado=?, motivo=? WHERE id=?";

        try {
            System.out.println("[v0] ActualizarDirecto - Iniciando...");
            System.out.println("[v0] ID: " + cita.getId() + ", Estado: " + cita.getEstado());
            System.out.println("[v0] Motivo: " + cita.getMotivo());

            // Usar conexión directa a la base de datos
            Config.Conexion cn = new Config.Conexion();
            Connection con = cn.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, cita.getPacienteId());
            ps.setInt(2, cita.getDoctorId());
            ps.setDate(3, java.sql.Date.valueOf(cita.getFechaCita()));
            ps.setTime(4, java.sql.Time.valueOf(cita.getHoraCita()));
            ps.setString(5, cita.getEstado());
            ps.setString(6, cita.getMotivo());
            ps.setLong(7, cita.getId());

            int resultado = ps.executeUpdate();
            System.out.println("[v0] ActualizarDirecto - Filas afectadas: " + resultado);

            ps.close();
            con.close();

            return resultado;

        } catch (Exception e) {
            System.out.println("[v0] Error en ActualizarDirecto: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

// Método para cancelar específicamente
    public int CancelarDirecto(long id, String motivoCancelacion) {
        String sql = "UPDATE citas SET estado = 'cancelada', motivo = CONCAT(COALESCE(motivo, ''), ?) WHERE id = ?";

        try {
            System.out.println("[v0] CancelarDirecto - ID: " + id + ", Motivo: " + motivoCancelacion);

            Config.Conexion cn = new Config.Conexion();
            Connection con = cn.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            String textoCancelacion = " [CANCELADA - Motivo: " + motivoCancelacion + "]";
            ps.setString(1, textoCancelacion);
            ps.setLong(2, id);

            int resultado = ps.executeUpdate();
            System.out.println("[v0] CancelarDirecto - Filas afectadas: " + resultado);

            ps.close();
            con.close();

            return resultado;

        } catch (Exception e) {
            System.out.println("[v0] Error en CancelarDirecto: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    public int Cancelar(long id, String motivoCancelacion) {
        try {
            URL url = new URL(BASE_URL + "/cancelar");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JSONObject json = new JSONObject();
            json.put("id", id);
            json.put("motivoCancelacion", motivoCancelacion);

            String jsonString = json.toString();
            System.out.println("[v0] DAO enviando cancelación: " + jsonString);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = con.getResponseCode();
            System.out.println("[v0] Código de respuesta cancelar: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                br.close();

                System.out.println("[v0] Respuesta de cancelación: " + response.toString());

                JSONObject jsonResponse = new JSONObject(response.toString());
                int result = jsonResponse.getInt("result");

                System.out.println("[v0] Cita cancelada, resultado: " + result);
                return result;
            } else {
                System.out.println("[v0] Error en cancelar: código " + responseCode);
                return 0;
            }
        } catch (Exception e) {
            System.out.println("[v0] Error al cancelar cita: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
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
