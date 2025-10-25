package servicio.API;

import Model.CitasModel;
import Config.Conexion;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

@Path("citas")
public class Citas {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    @GET
    @Path("/lista")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CitasModel> listar() {
        List<CitasModel> lista = new ArrayList<>();
        String sql = "SELECT c.*, "
                + "up.nombre as paciente_nombre, up.apellido as paciente_apellido, "
                + "ud.nombre as doctor_nombre, ud.apellido as doctor_apellido, "
                + "d.especializacion as doctor_especializacion "
                + "FROM citas c "
                + "INNER JOIN pacientes p ON c.paciente_id = p.id "
                + "INNER JOIN usuarios up ON p.usuario_id = up.id "
                + "INNER JOIN doctores d ON c.doctor_id = d.id "
                + "INNER JOIN usuarios ud ON d.usuario_id = ud.id "
                + "ORDER BY c.fecha_cita DESC, c.hora_cita DESC";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                CitasModel cita = new CitasModel();
                cita.setId(rs.getLong("id"));
                cita.setPacienteId(rs.getInt("paciente_id"));
                cita.setDoctorId(rs.getInt("doctor_id"));

                Date fecha = rs.getDate("fecha_cita");
                cita.setFechaCita(fecha != null ? fecha.toLocalDate() : null);

                Time hora = rs.getTime("hora_cita");
                cita.setHoraCita(hora != null ? hora.toLocalTime() : null);

                cita.setEstado(rs.getString("estado"));
                cita.setMotivo(rs.getString("motivo"));

                cita.setPacienteNombre(rs.getString("paciente_nombre"));
                cita.setPacienteApellido(rs.getString("paciente_apellido"));
                cita.setDoctorNombre(rs.getString("doctor_nombre"));
                cita.setDoctorApellido(rs.getString("doctor_apellido"));
                cita.setDoctorEspecializacion(rs.getString("doctor_especializacion"));

                lista.add(cita);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
        return lista;
    }

    @GET
    @Path("/disponibilidad")
    @Produces(MediaType.APPLICATION_JSON)
    public String verificarDisponibilidad(@QueryParam("doctorId") int doctorId,
            @QueryParam("fecha") String fechaStr) {
        JSONObject response = new JSONObject();
        JSONArray ocupados = new JSONArray();

        String sql = "SELECT hora_cita FROM citas WHERE doctor_id = ? AND fecha_cita = ? AND estado != 'cancelada'";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, doctorId);
            ps.setDate(2, Date.valueOf(fechaStr));
            rs = ps.executeQuery();

            while (rs.next()) {
                Time hora = rs.getTime("hora_cita");
                if (hora != null) {
                    ocupados.put(hora.toString().substring(0, 5)); // Formato HH:mm
                }
            }

            response.put("ocupados", ocupados);
            response.put("success", true);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("error", e.getMessage());
        } finally {
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

        return response.toString();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CitasModel buscarPorId(@PathParam("id") long id) {
        String sql = "SELECT c.*, "
                + "up.nombre as paciente_nombre, up.apellido as paciente_apellido, "
                + "ud.nombre as doctor_nombre, ud.apellido as doctor_apellido, "
                + "d.especializacion as doctor_especializacion "
                + "FROM citas c "
                + "INNER JOIN pacientes p ON c.paciente_id = p.id "
                + "INNER JOIN usuarios up ON p.usuario_id = up.id "
                + "INNER JOIN doctores d ON c.doctor_id = d.id "
                + "INNER JOIN usuarios ud ON d.usuario_id = ud.id "
                + "WHERE c.id = ?";
        CitasModel cita = null;

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                cita = new CitasModel();
                cita.setId(rs.getLong("id"));
                cita.setPacienteId(rs.getInt("paciente_id"));
                cita.setDoctorId(rs.getInt("doctor_id"));

                Date fecha = rs.getDate("fecha_cita");
                cita.setFechaCita(fecha != null ? fecha.toLocalDate() : null);

                Time hora = rs.getTime("hora_cita");
                cita.setHoraCita(hora != null ? hora.toLocalTime() : null);

                cita.setEstado(rs.getString("estado"));
                cita.setMotivo(rs.getString("motivo"));

                cita.setPacienteNombre(rs.getString("paciente_nombre"));
                cita.setPacienteApellido(rs.getString("paciente_apellido"));
                cita.setDoctorNombre(rs.getString("doctor_nombre"));
                cita.setDoctorApellido(rs.getString("doctor_apellido"));
                cita.setDoctorEspecializacion(rs.getString("doctor_especializacion"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
        return cita;
    }

    @POST
    @Path("/agregar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String agregar(String jsonString) {
        System.out.println("[v0] ========================================");
        System.out.println("[v0] API Citas - Método agregar iniciado");
        System.out.println("[v0] JSON recibido: " + jsonString);

        // Primero verificar disponibilidad
        String sqlCheck = "SELECT COUNT(*) as count FROM citas WHERE doctor_id = ? AND fecha_cita = ? AND hora_cita = ? AND estado != 'cancelada'";
        String sqlInsert = "INSERT INTO citas (paciente_id, doctor_id, fecha_cita, hora_cita, estado, motivo) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            JSONObject json = new JSONObject(jsonString);

            // Validar campos requeridos
            if (!json.has("pacienteId") || json.isNull("pacienteId")) {
                throw new Exception("Campo pacienteId es requerido");
            }
            if (!json.has("doctorId") || json.isNull("doctorId")) {
                throw new Exception("Campo doctorId es requerido");
            }
            if (!json.has("fechaCita") || json.isNull("fechaCita")) {
                throw new Exception("Campo fechaCita es requerido");
            }
            if (!json.has("horaCita") || json.isNull("horaCita")) {
                throw new Exception("Campo horaCita es requerido");
            }

            int pacienteId = json.getInt("pacienteId");
            int doctorId = json.getInt("doctorId");
            String fechaStr = json.getString("fechaCita");
            String horaStr = json.getString("horaCita");
            String estado = json.optString("estado", "programada");
            String motivo = json.optString("motivo", "");

            System.out.println("[v0] Datos parseados:");
            System.out.println("[v0]   - pacienteId: " + pacienteId);
            System.out.println("[v0]   - doctorId: " + doctorId);
            System.out.println("[v0]   - fechaCita: " + fechaStr);
            System.out.println("[v0]   - horaCita: " + horaStr);
            System.out.println("[v0]   - estado: " + estado);
            System.out.println("[v0]   - motivo: " + motivo);

            con = cn.getConnection();
            System.out.println("[v0] Conexión a BD establecida");

            // PRIMERO: Verificar si ya existe una cita en ese horario
            Date fecha = Date.valueOf(fechaStr);

            // Ajustar formato de hora si es necesario
            if (horaStr.length() == 5 && horaStr.matches("\\d{2}:\\d{2}")) {
                horaStr = horaStr + ":00";
                System.out.println("[v0] Hora ajustada a formato completo: " + horaStr);
            }

            Time hora = Time.valueOf(horaStr);

            PreparedStatement psCheck = con.prepareStatement(sqlCheck);
            psCheck.setInt(1, doctorId);
            psCheck.setDate(2, fecha);
            psCheck.setTime(3, hora);

            ResultSet rs = psCheck.executeQuery();
            if (rs.next() && rs.getInt("count") > 0) {
                System.out.println("[v0] ERROR: Ya existe una cita para este doctor en la misma fecha y hora");

                JSONObject errorResponse = new JSONObject();
                errorResponse.put("success", false);
                errorResponse.put("message", "El doctor ya tiene una cita programada en esta fecha y hora. Por favor seleccione otro horario.");
                errorResponse.put("result", 0);
                errorResponse.put("errorCode", "SCHEDULE_CONFLICT");

                return errorResponse.toString();
            }
            rs.close();
            psCheck.close();

            // SEGUNDO: Si no hay conflicto, insertar la cita
            ps = con.prepareStatement(sqlInsert);
            ps.setInt(1, pacienteId);
            ps.setInt(2, doctorId);
            ps.setDate(3, fecha);
            ps.setTime(4, hora);
            ps.setString(5, estado);
            ps.setString(6, motivo.isEmpty() ? null : motivo);

            System.out.println("[v0] Ejecutando INSERT...");
            int result = ps.executeUpdate();
            System.out.println("[v0] INSERT ejecutado, filas afectadas: " + result);

            JSONObject response = new JSONObject();
            if (result > 0) {
                response.put("success", true);
                response.put("message", "Cita creada exitosamente");
                response.put("result", 1);
                System.out.println("[v0] Cita creada exitosamente");
            } else {
                response.put("success", false);
                response.put("message", "No se pudo crear la cita - ninguna fila afectada");
                response.put("result", 0);
                System.out.println("[v0] No se insertó ninguna fila");
            }

            System.out.println("[v0] ========================================");
            return response.toString();

        } catch (Exception e) {
            System.out.println("[v0] ERROR en agregar cita:");
            System.out.println("[v0] Tipo de error: " + e.getClass().getName());
            System.out.println("[v0] Mensaje: " + e.getMessage());
            e.printStackTrace();
            System.out.println("[v0] ========================================");

            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error al crear cita: " + (e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName()));
            errorResponse.put("result", 0);
            return errorResponse.toString();
        } finally {
            try {
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

    @PUT
    @Path("/cancelar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String cancelar(String jsonString) {
        System.out.println("[v0] ========================================");
        System.out.println("[v0] API Citas - Método cancelar iniciado");
        System.out.println("[v0] JSON recibido: " + jsonString);

        String sql = "UPDATE citas SET estado = 'cancelada', motivo = CONCAT(IFNULL(motivo, ''), ?) WHERE id = ?";

        try {
            JSONObject json = new JSONObject(jsonString);

            if (!json.has("id")) {
                throw new Exception("Campo id es requerido");
            }
            if (!json.has("motivoCancelacion")) {
                throw new Exception("Campo motivoCancelacion es requerido");
            }

            long id = json.getLong("id");
            String motivoCancelacion = json.getString("motivoCancelacion");

            System.out.println("[v0] Cancelando cita ID: " + id);
            System.out.println("[v0] Motivo cancelación: " + motivoCancelacion);

            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            String textoCancelacion = " [CANCELADA - Motivo: " + motivoCancelacion + "]";
            ps.setString(1, textoCancelacion);
            ps.setLong(2, id);

            System.out.println("[v0] Ejecutando cancelación...");
            int result = ps.executeUpdate();
            System.out.println("[v0] Cancelación ejecutada, filas afectadas: " + result);

            JSONObject response = new JSONObject();
            if (result > 0) {
                response.put("success", true);
                response.put("message", "Cita cancelada exitosamente");
                response.put("result", 1);
            } else {
                response.put("success", false);
                response.put("message", "No se pudo cancelar la cita");
                response.put("result", 0);
            }

            System.out.println("[v0] ========================================");
            return response.toString();

        } catch (Exception e) {
            System.out.println("[v0] ERROR en cancelar cita:");
            System.out.println("[v0] Mensaje: " + e.getMessage());
            e.printStackTrace();
            System.out.println("[v0] ========================================");

            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error al cancelar cita: " + e.getMessage());
            errorResponse.put("result", 0);
            return errorResponse.toString();
        } finally {
            try {
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

    @PUT
    @Path("/modificar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String modificar(String jsonString) {
        System.out.println("[v0] ========================================");
        System.out.println("[v0] API Citas - Método modificar iniciado");
        System.out.println("[v0] JSON recibido: " + jsonString);

        String sqlCheck = "SELECT COUNT(*) as count FROM citas WHERE doctor_id = ? AND fecha_cita = ? AND hora_cita = ? AND estado != 'cancelada' AND id != ?";
        String sqlUpdate = "UPDATE citas SET paciente_id=?, doctor_id=?, fecha_cita=?, hora_cita=?, estado=?, motivo=? WHERE id=?";

        JSONObject response = new JSONObject();

        try {
            JSONObject json = new JSONObject(jsonString);

            // Validar campos requeridos
            if (!json.has("id")) {
                throw new Exception("Campo id es requerido");
            }

            long id = json.getLong("id");
            int pacienteId = json.getInt("pacienteId");
            int doctorId = json.getInt("doctorId");
            String fechaStr = json.getString("fechaCita");
            String horaStr = json.getString("horaCita");
            String estado = json.getString("estado");
            String motivo = json.optString("motivo", "");

            System.out.println("[v0] Datos para actualizar:");
            System.out.println("[v0]   - id: " + id);
            System.out.println("[v0]   - pacienteId: " + pacienteId);
            System.out.println("[v0]   - doctorId: " + doctorId);
            System.out.println("[v0]   - fechaCita: " + fechaStr);
            System.out.println("[v0]   - horaCita: " + horaStr);
            System.out.println("[v0]   - estado: " + estado);
            System.out.println("[v0]   - motivo: " + motivo);

            con = cn.getConnection();

            // PRIMERO: Verificar si hay conflicto de horario
            Date fecha = Date.valueOf(fechaStr);

            // Ajustar formato de hora si es necesario
            if (horaStr.length() == 5 && horaStr.matches("\\d{2}:\\d{2}")) {
                horaStr = horaStr + ":00";
                System.out.println("[v0] Hora ajustada: " + horaStr);
            }
            Time hora = Time.valueOf(horaStr);

            PreparedStatement psCheck = con.prepareStatement(sqlCheck);
            psCheck.setInt(1, doctorId);
            psCheck.setDate(2, fecha);
            psCheck.setTime(3, hora);
            psCheck.setLong(4, id); // Excluir la cita actual del chequeo

            ResultSet rs = psCheck.executeQuery();
            if (rs.next() && rs.getInt("count") > 0) {
                System.out.println("[v0] CONFLICTO: Ya existe una cita para este doctor en la misma fecha y hora");

                response.put("success", false);
                response.put("message", "El doctor ya tiene una cita programada en esta fecha y hora. Por favor seleccione otro horario.");
                response.put("result", 0);
                response.put("errorCode", "SCHEDULE_CONFLICT");

                rs.close();
                psCheck.close();
                return response.toString();
            }
            rs.close();
            psCheck.close();

            // SEGUNDO: Si no hay conflicto, proceder con la actualización
            ps = con.prepareStatement(sqlUpdate);
            ps.setInt(1, pacienteId);
            ps.setInt(2, doctorId);
            ps.setDate(3, fecha);
            ps.setTime(4, hora);
            ps.setString(5, estado);
            ps.setString(6, motivo.isEmpty() ? null : motivo);
            ps.setLong(7, id);

            System.out.println("[v0] Ejecutando UPDATE...");
            int result = ps.executeUpdate();
            System.out.println("[v0] UPDATE ejecutado, filas afectadas: " + result);

            if (result > 0) {
                response.put("success", true);
                response.put("message", "Cita actualizada exitosamente");
                response.put("result", 1);
            } else {
                response.put("success", false);
                response.put("message", "No se encontró la cita o no hubo cambios");
                response.put("result", 0);
            }

            System.out.println("[v0] ========================================");
            return response.toString();

        } catch (Exception e) {
            System.out.println("[v0] ERROR en modificar cita:");
            System.out.println("[v0] Mensaje: " + e.getMessage());
            e.printStackTrace();
            System.out.println("[v0] ========================================");

            response.put("success", false);
            response.put("message", "Error al actualizar cita: " + e.getMessage());
            response.put("result", 0);
            return response.toString();
        } finally {
            try {
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

    @DELETE
    @Path("/eliminar/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public int eliminar(@PathParam("id") long id) {
        String sql = "DELETE FROM citas WHERE id = ?";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);

            int result = ps.executeUpdate();
            System.out.println("[v0] Cita eliminada, filas afectadas: " + result);

            return result > 0 ? 1 : 0;

        } catch (Exception e) {
            System.out.println("[v0] Error en eliminar cita: " + e.getMessage());
            e.printStackTrace();
            return 0;
        } finally {
            try {
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
}
