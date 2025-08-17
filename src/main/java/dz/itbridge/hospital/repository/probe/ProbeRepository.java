package dz.itbridge.hospital.repository.probe;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import dz.itbridge.hospital.entity.probe.Probe;

public interface ProbeRepository extends JpaRepository<Probe, Integer> {

    Optional<Probe> findByName(String name);

    Optional<Probe> findByDescription(String description);

}
