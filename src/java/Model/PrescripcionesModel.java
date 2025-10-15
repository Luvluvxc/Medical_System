/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;


import java.time.LocalDateTime;

public class PrescripcionesModel {
    private long id;
    private long consultaId;
    private long medicamentoId;
    private int cantidad;
    private String dosis;
    private String frecuencia;
    private String duracion;
    private String instrucciones;
    private LocalDateTime creadoEn;

    // Constructores
    public PrescripcionesModel() {}

    public PrescripcionesModel(long id, long consultaId, long medicamentoId, int cantidad,
                         String dosis, String frecuencia, String duracion, 
                         String instrucciones, LocalDateTime creadoEn) {
        this.id = id;
        this.consultaId = consultaId;
        this.medicamentoId = medicamentoId;
        this.cantidad = cantidad;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
        this.duracion = duracion;
        this.instrucciones = instrucciones;
        this.creadoEn = creadoEn;
    }

    // Getters y Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    
    public long getConsultaId() { return consultaId; }
    public void setConsultaId(long consultaId) { this.consultaId = consultaId; }
    
    public long getMedicamentoId() { return medicamentoId; }
    public void setMedicamentoId(long medicamentoId) { this.medicamentoId = medicamentoId; }
    
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    
    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }
    
    public String getFrecuencia() { return frecuencia; }
    public void setFrecuencia(String frecuencia) { this.frecuencia = frecuencia; }
    
    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }
    
    public String getInstrucciones() { return instrucciones; }
    public void setInstrucciones(String instrucciones) { this.instrucciones = instrucciones; }
    
    public LocalDateTime getCreadoEn() { return creadoEn; }
    public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }
}