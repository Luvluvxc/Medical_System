/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class CitasModel {
    private long id;
    private long pacienteId;
    private long doctorId;
    private LocalDate fechaCita;
    private LocalTime horaCita;
    private String estado;
    private String motivo;
    private String notas;
    private Long creadoPor;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;

    // Constructores, Getters y Setters
    public CitasModel() {}

    public CitasModel(long id, long pacienteId, long doctorId, LocalDate fechaCita, LocalTime horaCita,
                String estado, String motivo, String notas, Long creadoPor,
                LocalDateTime creadoEn, LocalDateTime actualizadoEn) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.doctorId = doctorId;
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
        this.estado = estado;
        this.motivo = motivo;
        this.notas = notas;
        this.creadoPor = creadoPor;
        this.creadoEn = creadoEn;
        this.actualizadoEn = actualizadoEn;
    }

    // Getters y Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    
    public long getPacienteId() { return pacienteId; }
    public void setPacienteId(long pacienteId) { this.pacienteId = pacienteId; }
    
    public long getDoctorId() { return doctorId; }
    public void setDoctorId(long doctorId) { this.doctorId = doctorId; }
    
    public LocalDate getFechaCita() { return fechaCita; }
    public void setFechaCita(LocalDate fechaCita) { this.fechaCita = fechaCita; }
    
    public LocalTime getHoraCita() { return horaCita; }
    public void setHoraCita(LocalTime horaCita) { this.horaCita = horaCita; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    
    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
    
    public Long getCreadoPor() { return creadoPor; }
    public void setCreadoPor(Long creadoPor) { this.creadoPor = creadoPor; }
    
    public LocalDateTime getCreadoEn() { return creadoEn; }
    public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }
    
    public LocalDateTime getActualizadoEn() { return actualizadoEn; }
    public void setActualizadoEn(LocalDateTime actualizadoEn) { this.actualizadoEn = actualizadoEn; }
}