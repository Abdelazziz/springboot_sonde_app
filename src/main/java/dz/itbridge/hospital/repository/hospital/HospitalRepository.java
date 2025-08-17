package dz.itbridge.hospital.repository.hospital;

import dz.itbridge.hospital.entity.hospital.Hospital;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Integer> {

    Optional<Hospital> findByName(String name);

    Optional<Hospital> findByWilaya(String wilaya);
}