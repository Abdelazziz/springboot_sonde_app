package dz.itbridge.hospital.repository.doctor;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dz.itbridge.hospital.entity.doctor.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    Optional<Doctor> findByUsername(String username);

    Optional<Doctor> findByName(String name);

    Optional<Doctor> findByRole(String role);

}
