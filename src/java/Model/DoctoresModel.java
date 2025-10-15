/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class DoctoresModel {
    private long id;
    private long usuarioId;
    private String numeroLicencia;
    private String especializacion;
    private BigDecimal tarifaConsulta;
    private LocalTime horarioInicio;
    private LocalTime horarioFin;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;

    // Constructores, Getters y Setters
    public DoctoresModel() {}

    public DoctoresModel(long id, long usuarioId, String numeroLicencia, String especializacion,
                   BigDecimal tarifaConsulta, LocalTime horarioInicio, LocalTime horarioFin,
                   LocalDateTime creadoEn, LocalDateTime actualizadoEn) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.numeroLicencia = numeroLicencia;
        this.especializacion = especializacion;
        this.tarifaConsulta = tarifaConsulta;
        this.horarioInicio = horarioInicio;
        this.horarioFin = horarioFin;
        this.creadoEn = creadoEn;
        this.actualizadoEn = actualizadoEn;
    }

    // Getters y Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    
    public long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(long usuarioId) { this.usuarioId = usuarioId; }
    
    public String getNumeroLicencia() { return numeroLicencia; }
    public void setNumeroLicencia(String numeroLicencia) { this.numeroLicencia = numeroLicencia; }
    
    public String getEspecializacion() { return especializacion; }
    public void setEspecializacion(String especializacion) { this.especializacion = especializacion; }
    
    public BigDecimal getTarifaConsulta() { return tarifaConsulta; }
    public void setTarifaConsulta(BigDecimal tarifaConsulta) { this.tarifaConsulta = tarifaConsulta; }
    
    public LocalTime getHorarioInicio() { return horarioInicio; }
    public void setHorarioInicio(LocalTime horarioInicio) { this.horarioInicio = horarioInicio; }
    
    public LocalTime getHorarioFin() { return horarioFin; }
    public void setHorarioFin(LocalTime horarioFin) { this.horarioFin = horarioFin; }
    
    public LocalDateTime getCreadoEn() { return creadoEn; }
    public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }
    
    public LocalDateTime getActualizadoEn() { return actualizadoEn; }
    public void setActualizadoEn(LocalDateTime actualizadoEn) { this.actualizadoEn = actualizadoEn; }
}