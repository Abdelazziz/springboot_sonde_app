package dz.itbridge.hospital.entity.medicaldatapatient;

import java.time.LocalDate;

import dz.itbridge.hospital.entity.doctor.Doctor;
import dz.itbridge.hospital.entity.hospital.Hospital;
import dz.itbridge.hospital.entity.probe.Probe;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "medicaldatapatient")
public class MedicalDataPatient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicaldatapatient_id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "sex", nullable = false, length = 1)
    private String sex;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "address", columnDefinition = "text")
    private String address;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(name = "startdate", nullable = false)
    private LocalDate startdate;

    @Column(name = "enddate", nullable = false)
    private LocalDate enddate;

    // Relations
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @ManyToOne
    @JoinColumn(name = "probe_id")
    private Probe probe;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public LocalDate getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public LocalDate getEnddate() {
        return enddate;
    }

    public void setEnddate(LocalDate enddate) {
        this.enddate = enddate;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Probe getProbe() {
        return probe;
    }

    public void setProbe(Probe probe) {
        this.probe = probe;
    }

}
