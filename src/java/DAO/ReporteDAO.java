    package DAO;

    import Config.Conexion;
    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Map;
    import java.util.HashMap;

    public class ReporteDAO {
        Conexion cn = new Conexion();

        // Reporte de medicamentos recetados
        public List<Map<String, Object>> getMedicamentosRecetados(Date fechaInicio, Date fechaFin) {
            List<Map<String, Object>> resultados = new ArrayList<>();
            String sql = "SELECT m.nombre, m.nombre_generico, COUNT(p.id) as veces_recetado, " +
                        "SUM(p.cantidad) as total_unidades, " +
                        "u.nombre as unidad_medida " +
                        "FROM prescripciones p " +
                        "JOIN medicamentos m ON p.medicamento_id = m.id " +
                        "LEFT JOIN unidades_medida u ON m.unidad_medida_id = u.id " +
                        "JOIN consultas c ON p.consulta_id = c.id " +
                        "WHERE c.fecha_consulta BETWEEN ? AND ? " +
                        "GROUP BY m.id, m.nombre, m.nombre_generico, u.nombre " +
                        "ORDER BY veces_recetado DESC";

            try (Connection con = cn.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setDate(1, fechaInicio);
                ps.setDate(2, fechaFin);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Map<String, Object> fila = new HashMap<>();
                    fila.put("nombre", rs.getString("nombre"));
                    fila.put("nombreGenerico", rs.getString("nombre_generico"));
                    fila.put("vecesRecetado", rs.getInt("veces_recetado"));
                    fila.put("totalUnidades", rs.getInt("total_unidades"));
                    fila.put("unidadMedida", rs.getString("unidad_medida"));
                    resultados.add(fila);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultados;
        }

        // Consulta médica - filtrar por ID o nombre
        public ResultSet buscarConsultas(String filtro, String tipo) {
            String sql = "";

            if ("id".equals(tipo)) {
                sql = "SELECT con.*, c.fecha_cita, c.hora_cita, " +
                      "p.nombre as paciente_nombre, p.apellido as paciente_apellido, " +
                      "d.nombre as doctor_nombre, d.apellido as doctor_apellido " +
                      "FROM consultas con " +
                      "JOIN citas c ON con.cita_id = c.id " +
                      "JOIN pacientes pt ON con.paciente_id = pt.id " +
                      "JOIN usuarios p ON pt.usuario_id = p.id " +
                      "JOIN doctores doc ON con.doctor_id = doc.id " +
                      "JOIN usuarios d ON doc.usuario_id = d.id " +
                      "WHERE con.id = ? " +
                      "ORDER BY con.fecha_consulta DESC";
            } else {
                sql = "SELECT con.*, c.fecha_cita, c.hora_cita, " +
                      "p.nombre as paciente_nombre, p.apellido as paciente_apellido, " +
                      "d.nombre as doctor_nombre, d.apellido as doctor_apellido " +
                      "FROM consultas con " +
                      "JOIN citas c ON con.cita_id = c.id " +
                      "JOIN pacientes pt ON con.paciente_id = pt.id " +
                      "JOIN usuarios p ON pt.usuario_id = p.id " +
                      "JOIN doctores doc ON con.doctor_id = doc.id " +
                      "JOIN usuarios d ON doc.usuario_id = d.id " +
                      "WHERE LOWER(p.nombre) LIKE LOWER(?) OR LOWER(p.apellido) LIKE LOWER(?) " +
                      "ORDER BY con.fecha_consulta DESC";
            }

            try {
                Connection con = cn.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);

                if ("id".equals(tipo)) {
                    ps.setLong(1, Long.parseLong(filtro));
                } else {
                    ps.setString(1, "%" + filtro + "%");
                    ps.setString(2, "%" + filtro + "%");
                }

                return ps.executeQuery();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }