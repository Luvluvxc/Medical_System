/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MedicamentosModel {
    private long id;
    private String nombre;
    private String nombreGenerico;
    private String marca;
    private Long unidadMedidaId;
    private int cantidadStock;
    private int nivelMinimoStock;
    private BigDecimal precioUnitario;
    private LocalDate fechaExpiracion;
    private String numeroLote;
    private String descripcion;
    private boolean activo;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;

    // Constructores
    public MedicamentosModel() {}

    public MedicamentosModel(long id, String nombre, String nombreGenerico, String marca,
                       Long unidadMedidaId, int cantidadStock, int nivelMinimoStock,
                       BigDecimal precioUnitario, LocalDate fechaExpiracion, 
                       String numeroLote, String descripcion, boolean activo,
                       LocalDateTime creadoEn, LocalDateTime actualizadoEn) {
        this.id = id;
        this.nombre = nombre;
        this.nombreGenerico = nombreGenerico;
        this.marca = marca;
        this.unidadMedidaId = unidadMedidaId;
        this.cantidadStock = cantidadStock;
        this.nivelMinimoStock = nivelMinimoStock;
        this.precioUnitario = precioUnitario;
        this.fechaExpiracion = fechaExpiracion;
        this.numeroLote = numeroLote;
        this.descripcion = descripcion;
        this.activo = activo;
        this.creadoEn = creadoEn;
        this.actualizadoEn = actualizadoEn;
    }

    // Getters y Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getNombreGenerico() { return nombreGenerico; }
    public void setNombreGenerico(String nombreGenerico) { this.nombreGenerico = nombreGenerico; }
    
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    
    public Long getUnidadMedidaId() { return unidadMedidaId; }
    public void setUnidadMedidaId(Long unidadMedidaId) { this.unidadMedidaId = unidadMedidaId; }
    
    public int getCantidadStock() { return cantidadStock; }
    public void setCantidadStock(int cantidadStock) { this.cantidadStock = cantidadStock; }
    
    public int getNivelMinimoStock() { return nivelMinimoStock; }
    public void setNivelMinimoStock(int nivelMinimoStock) { this.nivelMinimoStock = nivelMinimoStock; }
    
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
    
    public LocalDate getFechaExpiracion() { return fechaExpiracion; }
    public void setFechaExpiracion(LocalDate fechaExpiracion) { this.fechaExpiracion = fechaExpiracion; }
    
    public String getNumeroLote() { return numeroLote; }
    public void setNumeroLote(String numeroLote) { this.numeroLote = numeroLote; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    
    public LocalDateTime getCreadoEn() { return creadoEn; }
    public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }
    
    public LocalDateTime getActualizadoEn() { return actualizadoEn; }
    public void setActualizadoEn(LocalDateTime actualizadoEn) { this.actualizadoEn = actualizadoEn; }
}