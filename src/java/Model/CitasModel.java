package Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class CitasModel {
    private Long id;
    private Integer pacienteId;
    private Integer doctorId;
    private LocalDate fechaCita;
    private LocalTime horaCita;
    private String estado;
    private String motivo;
    
    // Additional fields for joined data
    private String pacienteNombre;
    private String pacienteApellido;
    private String doctorNombre;
    private String doctorApellido;
    private String doctorEspecializacion;

    // Constructors
    public CitasModel() {
    }

    public CitasModel(Long id, Integer pacienteId, Integer doctorId, LocalDate fechaCita, 
                     LocalTime horaCita, String estado, String motivo) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.doctorId = doctorId;
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
        this.estado = estado;
        this.motivo = motivo;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Integer pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDate getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
    }

    public LocalTime getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(LocalTime horaCita) {
        this.horaCita = horaCita;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getPacienteNombre() {
        return pacienteNombre;
    }

    public void setPacienteNombre(String pacienteNombre) {
        this.pacienteNombre = pacienteNombre;
    }

    public String getPacienteApellido() {
        return pacienteApellido;
    }

    public void setPacienteApellido(String pacienteApellido) {
        this.pacienteApellido = pacienteApellido;
    }

    public String getDoctorNombre() {
        return doctorNombre;
    }

    public void setDoctorNombre(String doctorNombre) {
        this.doctorNombre = doctorNombre;
    }

    public String getDoctorApellido() {
        return doctorApellido;
    }

    public void setDoctorApellido(String doctorApellido) {
        this.doctorApellido = doctorApellido;
    }

    public String getDoctorEspecializacion() {
        return doctorEspecializacion;
    }

    public void setDoctorEspecializacion(String doctorEspecializacion) {
        this.doctorEspecializacion = doctorEspecializacion;
    }
}
