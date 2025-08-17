package dz.itbridge.hospital.controller.doctor;

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

import dz.itbridge.hospital.dto.doctor.DoctorRequest;
import dz.itbridge.hospital.dto.doctor.DoctorResponse;
import dz.itbridge.hospital.entity.doctor.Doctor;
import dz.itbridge.hospital.service.doctor.DoctorService;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // GET /api/doctors
    @GetMapping
    public ResponseEntity<List<DoctorResponse>> getAllDoctors() {
        List<DoctorResponse> doctors = doctorService.getAllDoctors();

        return doctors.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(doctors);
    }

    // GET /api/doctors/{id}
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> getDoctorById(@PathVariable Integer id) {
        Optional<DoctorResponse> doctor = doctorService.getDoctorById(id);
        return doctor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET /api/doctors/{username}
    @GetMapping("/username/{username}")
    public ResponseEntity<DoctorResponse> getDoctorByuserName(@PathVariable String username) {
        Optional<DoctorResponse> doctor = doctorService.getDoctorByuserName(username);
        return doctor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /api/doctors
    @PostMapping
    public ResponseEntity<DoctorResponse> addDoctor(@RequestBody Doctor doctor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.addDoctor(doctor));
    }

    // PUT /api/doctors/{id}
    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponse> updateDoctor(
            @PathVariable Integer id, @RequestBody DoctorRequest doctorDetails) {

        Optional<DoctorResponse> optionalDoctor = doctorService.updateDoctor(id, doctorDetails);
        if (optionalDoctor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(optionalDoctor.get());
    }

    // DELETE /api/doctors/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletedoctor(@PathVariable Integer id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

}
