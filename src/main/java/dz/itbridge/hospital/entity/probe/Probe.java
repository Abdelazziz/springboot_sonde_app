package dz.itbridge.hospital.entity.probe;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.List;

import dz.itbridge.hospital.entity.medicaldatapatient.MedicalDataPatient;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "probe")
public class Probe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "probe_id", nullable = false)
    private Integer probeId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    // Relations
    @OneToMany(mappedBy = "probe")
    private List<MedicalDataPatient> medicalDataPatients;

    public List<MedicalDataPatient> getMedicalDataPatients() {
        return medicalDataPatients;
    }

    public Integer getProbeId() {
        return probeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
