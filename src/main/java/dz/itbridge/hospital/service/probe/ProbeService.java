package dz.itbridge.hospital.service.probe;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import dz.itbridge.hospital.dto.probe.ProbeResponse;
import dz.itbridge.hospital.entity.probe.Probe;
import dz.itbridge.hospital.repository.probe.ProbeRepository;
import dz.itbridge.hospital.utils.MessageUtils;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProbeService {

    private final ProbeRepository probeRepository;

    public ProbeService(ProbeRepository probeRepository) {
        this.probeRepository = probeRepository;
    }

    public List<ProbeResponse> getAllProbes() {
        return probeRepository.findAll().stream()
                .map(probe -> new ProbeResponse(probe.getProbeId(), probe.getName(), probe.getDescription()))
                .toList();
    }

    public Optional<ProbeResponse> getProbeById(Integer id) {
        Optional<ProbeResponse> probe = probeRepository.findById(id)
                .map(prob -> new ProbeResponse(prob.getProbeId(), prob.getName(), prob.getDescription()));

        return probe;
    }

    public Probe addProbe(Probe probe) {
        return probeRepository.save(probe);
    }

    public Optional<Probe> updateProbe(Integer id, Probe probeDetails) {

        Optional<Probe> optionalProbe = probeRepository.findById(id);
        if (optionalProbe.isEmpty()) {
            return Optional.empty();
        }

        Probe probe = optionalProbe.get();
        probe.setName(probeDetails.getName());
        probe.setDescription(probeDetails.getDescription());
        Probe updatedProbe = probeRepository.save(probe);

        return Optional.of(updatedProbe);
    }

    public void deleteProbe(Integer id) {
        if (!probeRepository.existsById(id)) {
            throw new EntityNotFoundException(MessageUtils.ERROR_RESOURCE_NOT_FOUND);
        }
        probeRepository.deleteById(id);
    }

}
