package Model;

public class ConsultasModel {
    private Long id;
    private Integer citaId;
    private String diagnostico;
    private String planTratamiento;
    private String observaciones;

    // Constructors
    public ConsultasModel() {
    }

    public ConsultasModel(Long id, Integer citaId, String diagnostico, 
                         String planTratamiento, String observaciones) {
        this.id = id;
        this.citaId = citaId;
        this.diagnostico = diagnostico;
        this.planTratamiento = planTratamiento;
        this.observaciones = observaciones;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCitaId() {
        return citaId;
    }

    public void setCitaId(Integer citaId) {
        this.citaId = citaId;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getPlanTratamiento() {
        return planTratamiento;
    }

    public void setPlanTratamiento(String planTratamiento) {
        this.planTratamiento = planTratamiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
