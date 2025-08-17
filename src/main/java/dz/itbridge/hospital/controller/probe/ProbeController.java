package dz.itbridge.hospital.controller.probe;

import dz.itbridge.hospital.dto.probe.ProbeResponse;
import dz.itbridge.hospital.entity.probe.Probe;
import dz.itbridge.hospital.service.probe.ProbeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/probes")
public class ProbeController {

    private final ProbeService probeService;

    public ProbeController(ProbeService probeService) {
        this.probeService = probeService;
    }

    // GET /api/probes
    @GetMapping
    public ResponseEntity<List<ProbeResponse>> getAllProbes() {
        List<ProbeResponse> probes = probeService.getAllProbes();
        return probes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(probes);
    }

    // GET /api/probes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ProbeResponse> getProbeById(@PathVariable Integer id) {
        Optional<ProbeResponse> probe = probeService.getProbeById(id);
        return probe.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /api/probes
    @PostMapping
    public ResponseEntity<Probe> addProbe(@RequestBody Probe probe) {
        return ResponseEntity.status(HttpStatus.CREATED).body(probeService.addProbe(probe));
    }

    // PUT /api/probes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Probe> updateProbe(@PathVariable Integer id, @RequestBody Probe probeDetails) {
        Optional<Probe> updatedProbe = probeService.updateProbe(id, probeDetails);
        if (updatedProbe.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedProbe.get());
    }

    // DELETE /api/probes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProbe(@PathVariable Integer id) {
        probeService.deleteProbe(id);
        return ResponseEntity.noContent().build();
    }
}
