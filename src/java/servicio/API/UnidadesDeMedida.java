    package servicio.API;
    import Model.UnidadesMedidaModel;
    import Config.Conexion;
    import javax.ws.rs.*;
    import javax.ws.rs.core.MediaType;
    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;

    @Path("unidades-medida")
    public class UnidadesDeMedida {
        Conexion cn = new Conexion();

        @GET
        @Path("/lista")
        @Produces(MediaType.APPLICATION_JSON)
        public List<UnidadesMedidaModel> listarUnidadesMedida() {
            List<UnidadesMedidaModel> lista = new ArrayList<>();
            String sql = "SELECT * FROM unidades_medida ORDER BY nombre";

            try (Connection con = cn.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    UnidadesMedidaModel u = new UnidadesMedidaModel();
                    u.setId(rs.getLong("id"));
                    u.setNombre(rs.getString("nombre"));
                    u.setAbreviatura(rs.getString("abreviatura"));
                    u.setDescripcion(rs.getString("descripcion"));

                    Timestamp creado = rs.getTimestamp("creado_en");
                    u.setCreadoEn(creado != null ? creado.toLocalDateTime() : null);

                    lista.add(u);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return lista;
        }

        @POST
        @Path("/agregar")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.TEXT_PLAIN)
        public int agregarUnidadMedida(UnidadesMedidaModel unidadMedida) {
            String sql = "INSERT INTO unidades_medida (nombre, abreviatura, descripcion, creado_en) "
                       + "VALUES (?, ?, ?, CURRENT_TIMESTAMP)";

            try (Connection con = cn.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, unidadMedida.getNombre());
                ps.setString(2, unidadMedida.getAbreviatura());
                ps.setString(3, unidadMedida.getDescripcion());

                return ps.executeUpdate() > 0 ? 1 : 0;

            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        @PUT
        @Path("/modificar")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.TEXT_PLAIN)
        public int modificarUnidadMedida(UnidadesMedidaModel unidadMedida) {
            String sql = "UPDATE unidades_medida SET nombre=?, abreviatura=?, descripcion=? "
                       + "WHERE id=?";

            try (Connection con = cn.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, unidadMedida.getNombre());
                ps.setString(2, unidadMedida.getAbreviatura());
                ps.setString(3, unidadMedida.getDescripcion());
                ps.setLong(4, unidadMedida.getId());

                return ps.executeUpdate() > 0 ? 1 : 0;

            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        @DELETE
        @Path("/eliminar/{id}")
        @Produces(MediaType.TEXT_PLAIN)
        public int eliminarUnidadMedida(@PathParam("id") long id) {
            // Verificar si la unidad de medida está siendo usada en medicamentos
            String verificarUso = "SELECT COUNT(*) as count FROM medicamentos WHERE unidad_medida_id = ?";
            String sql = "DELETE FROM unidades_medida WHERE id = ?";

            try (Connection con = cn.getConnection();
                 PreparedStatement psVerificar = con.prepareStatement(verificarUso);
                 PreparedStatement psEliminar = con.prepareStatement(sql)) {

                // Verificar si hay medicamentos usando esta unidad
                psVerificar.setLong(1, id);
                ResultSet rs = psVerificar.executeQuery();

                if (rs.next() && rs.getInt("count") > 0) {
                    return -1; // Código de error: unidad en uso
                }

                // Si no está en uso, proceder con la eliminación
                psEliminar.setLong(1, id);
                return psEliminar.executeUpdate() > 0 ? 1 : 0;

            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        @GET
        @Path("/buscar-por-nombre/{nombre}")
        @Produces(MediaType.APPLICATION_JSON)
        public List<UnidadesMedidaModel> buscarPorNombre(@PathParam("nombre") String nombre) {
            List<UnidadesMedidaModel> lista = new ArrayList<>();
            String sql = "SELECT * FROM unidades_medida WHERE LOWER(nombre) LIKE LOWER(?) ORDER BY nombre";

            try (Connection con = cn.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, "%" + nombre + "%");
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    UnidadesMedidaModel u = new UnidadesMedidaModel();
                    u.setId(rs.getLong("id"));
                    u.setNombre(rs.getString("nombre"));
                    u.setAbreviatura(rs.getString("abreviatura"));
                    u.setDescripcion(rs.getString("descripcion"));

                    Timestamp creado = rs.getTimestamp("creado_en");
                    u.setCreadoEn(creado != null ? creado.toLocalDateTime() : null);

                    lista.add(u);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return lista;
        }

        @GET
        @Path("/{id}")
        @Produces(MediaType.APPLICATION_JSON)
        public UnidadesMedidaModel obtenerPorId(@PathParam("id") long id) {
            UnidadesMedidaModel unidad = null;
            String sql = "SELECT * FROM unidades_medida WHERE id = ?";

            try (Connection con = cn.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    unidad = new UnidadesMedidaModel();
                    unidad.setId(rs.getLong("id"));
                    unidad.setNombre(rs.getString("nombre"));
                    unidad.setAbreviatura(rs.getString("abreviatura"));
                    unidad.setDescripcion(rs.getString("descripcion"));

                    Timestamp creado = rs.getTimestamp("creado_en");
                    unidad.setCreadoEn(creado != null ? creado.toLocalDateTime() : null);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return unidad;
        }
    }