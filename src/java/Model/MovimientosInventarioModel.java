    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package Model;
    import java.time.LocalDateTime;

    public class MovimientosInventarioModel {
        private long id;
        private long medicamentoId;
        private String tipoMovimiento;
        private int cantidad;
        private String tipoReferencia;
        private Long idReferencia;
        private String notas;
        private Long creadoPor;
        private LocalDateTime creadoEn;

        // Constructores
        public MovimientosInventarioModel() {}

        public MovimientosInventarioModel(long id, long medicamentoId, String tipoMovimiento,
                                    int cantidad, String tipoReferencia, Long idReferencia,
                                    String notas, Long creadoPor, LocalDateTime creadoEn) {
            this.id = id;
            this.medicamentoId = medicamentoId;
            this.tipoMovimiento = tipoMovimiento;
            this.cantidad = cantidad;
            this.tipoReferencia = tipoReferencia;
            this.idReferencia = idReferencia;
            this.notas = notas;
            this.creadoPor = creadoPor;
            this.creadoEn = creadoEn;
        }

        // Getters y Setters
        public long getId() { return id; }
        public void setId(long id) { this.id = id; }

        public long getMedicamentoId() { return medicamentoId; }
        public void setMedicamentoId(long medicamentoId) { this.medicamentoId = medicamentoId; }

        public String getTipoMovimiento() { return tipoMovimiento; }
        public void setTipoMovimiento(String tipoMovimiento) { this.tipoMovimiento = tipoMovimiento; }

        public int getCantidad() { return cantidad; }
        public void setCantidad(int cantidad) { this.cantidad = cantidad; }

        public String getTipoReferencia() { return tipoReferencia; }
        public void setTipoReferencia(String tipoReferencia) { this.tipoReferencia = tipoReferencia; }

        public Long getIdReferencia() { return idReferencia; }
        public void setIdReferencia(Long idReferencia) { this.idReferencia = idReferencia; }

        public String getNotas() { return notas; }
        public void setNotas(String notas) { this.notas = notas; }

        public Long getCreadoPor() { return creadoPor; }
        public void setCreadoPor(Long creadoPor) { this.creadoPor = creadoPor; }

        public LocalDateTime getCreadoEn() { return creadoEn; }
        public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }
    }