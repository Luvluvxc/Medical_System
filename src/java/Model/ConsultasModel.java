/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ConsultasModel {
    private long id;
    private long citaId;
    private long pacienteId;
    private long doctorId;
    private LocalDateTime fechaConsulta;
    private String sintomas;
    private String diagnostico;
    private String planTratamiento;
    private String notas;
    private String signosVitales; // JSON como String
    private LocalDate fechaProximaVisita;
    private String estado;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;

    // Constructores
    public ConsultasModel() {}

    public ConsultasModel(long id, long citaId, long pacienteId, long doctorId, 
                    LocalDateTime fechaConsulta, String sintomas, String diagnostico,
                    String planTratamiento, String notas, String signosVitales,
                    LocalDate fechaProximaVisita, String estado, 
                    LocalDateTime creadoEn, LocalDateTime actualizadoEn) {
        this.id = id;
        this.citaId = citaId;
        this.pacienteId = pacienteId;
        this.doctorId = doctorId;
        this.fechaConsulta = fechaConsulta;
        this.sintomas = sintomas;
        this.diagnostico = diagnostico;
        this.planTratamiento = planTratamiento;
        this.notas = notas;
        this.signosVitales = signosVitales;
        this.fechaProximaVisita = fechaProximaVisita;
        this.estado = estado;
        this.creadoEn = creadoEn;
        this.actualizadoEn = actualizadoEn;
    }

    // Getters y Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    
    public long getCitaId() { return citaId; }
    public void setCitaId(long citaId) { this.citaId = citaId; }
    
    public long getPacienteId() { return pacienteId; }
    public void setPacienteId(long pacienteId) { this.pacienteId = pacienteId; }
    
    public long getDoctorId() { return doctorId; }
    public void setDoctorId(long doctorId) { this.doctorId = doctorId; }
    
    public LocalDateTime getFechaConsulta() { return fechaConsulta; }
    public void setFechaConsulta(LocalDateTime fechaConsulta) { this.fechaConsulta = fechaConsulta; }
    
    public String getSintomas() { return sintomas; }
    public void setSintomas(String sintomas) { this.sintomas = sintomas; }
    
    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
    
    public String getPlanTratamiento() { return planTratamiento; }
    public void setPlanTratamiento(String planTratamiento) { this.planTratamiento = planTratamiento; }
    
    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
    
    public String getSignosVitales() { return signosVitales; }
    public void setSignosVitales(String signosVitales) { this.signosVitales = signosVitales; }
    
    public LocalDate getFechaProximaVisita() { return fechaProximaVisita; }
    public void setFechaProximaVisita(LocalDate fechaProximaVisita) { this.fechaProximaVisita = fechaProximaVisita; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public LocalDateTime getCreadoEn() { return creadoEn; }
    public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }
    
    public LocalDateTime getActualizadoEn() { return actualizadoEn; }
    public void setActualizadoEn(LocalDateTime actualizadoEn) { this.actualizadoEn = actualizadoEn; }
}