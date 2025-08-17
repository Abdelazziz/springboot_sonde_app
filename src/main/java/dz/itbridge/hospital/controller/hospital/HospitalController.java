package dz.itbridge.hospital.controller.hospital;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dz.itbridge.hospital.dto.hospital.HospitalResponse;
import dz.itbridge.hospital.entity.hospital.Hospital;
import dz.itbridge.hospital.service.hospital.HospitalService;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    // GET /api/hospitals
    @GetMapping
    public ResponseEntity<List<HospitalResponse>> getAllHospitals() {
        List<HospitalResponse> hospitals = hospitalService.getAllHospitals();
        return hospitals.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(hospitals);
    }

    // GET /api/hospitals/{id}
    @GetMapping("/{id}")
    public ResponseEntity<HospitalResponse> getHospitalById(@PathVariable Integer id) {
        return hospitalService.getHospitalById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /api/hospitals
    @PostMapping
    public ResponseEntity<Hospital> addHospital(@RequestBody Hospital hospital) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hospitalService.addHospital(hospital));
    }

    // PUT /api/hospitals/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Hospital> updateHospital(@PathVariable Integer id, @RequestBody Hospital hospitalDetails) {
        Optional<Hospital> updatedHospital = hospitalService.updateHospital(id, hospitalDetails);
        if (updatedHospital.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedHospital.get());
    }

    // DELETE /api/hospitals/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHospital(@PathVariable Integer id) {
        hospitalService.deleteHospital(id);
        return ResponseEntity.noContent().build();
    }

}
