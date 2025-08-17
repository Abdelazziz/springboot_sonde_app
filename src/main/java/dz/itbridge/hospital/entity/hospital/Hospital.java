package dz.itbridge.hospital.entity.hospital;

import java.util.List;

import dz.itbridge.hospital.entity.medicaldatapatient.MedicalDataPatient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "hospital")
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_id", nullable = false)
    private Integer hospitalId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "wilaya")
    private String wilaya;

    // Relations
    @OneToMany(mappedBy = "hospital")
    private List<MedicalDataPatient> medicalDataPatients;

    public List<MedicalDataPatient> getMedicalDataPatients() {
    return medicalDataPatients;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWilaya() {
        return wilaya;
    }

    public void setWilaya(String wilaya) {
        this.wilaya = wilaya;
    }
}
