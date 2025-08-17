package dz.itbridge.hospital.controller.medicaldatapatient;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dz.itbridge.hospital.dto.medicaldatapatient.FilterRequest;
import dz.itbridge.hospital.dto.medicaldatapatient.MedicalDataPatientRequest;
import dz.itbridge.hospital.dto.medicaldatapatient.MedicalDataPatientResponse;
import dz.itbridge.hospital.service.medicaldatapatient.MedicalDataPatientService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/medical-data-patient")
public class MedicalDataPatientController {

        private final MedicalDataPatientService medicalDataPatientService;

        public MedicalDataPatientController(MedicalDataPatientService medicalDataPatientService) {
                this.medicalDataPatientService = medicalDataPatientService;
        }

        @GetMapping
        public ResponseEntity<List<MedicalDataPatientResponse>> getAllMedicalDataPatients(
                        @PageableDefault(size = 25, sort = "name") Pageable pageable) {
                List<MedicalDataPatientResponse> medicalDataPatients = medicalDataPatientService
                                .getAllMedicalDataPatients(
                                                pageable);
                return medicalDataPatients.isEmpty() ? ResponseEntity.noContent().build()
                                : ResponseEntity.ok(
                                                medicalDataPatients);
        }

        @PostMapping("/filter")
        public ResponseEntity<List<MedicalDataPatientResponse>> getMedicalDataPatientsActiveFilter(
                        @RequestBody FilterRequest filterRequest,
                        @PageableDefault(size = 25, sort = "name") Pageable pageable) {
                List<MedicalDataPatientResponse> medicalDataPatients = medicalDataPatientService
                                .getMedicalDataPatientsActiveFilter(
                                                filterRequest, pageable);
                return medicalDataPatients.isEmpty() ? ResponseEntity.noContent().build()
                                : ResponseEntity.ok(
                                                medicalDataPatients);
        }

        @PostMapping("/history")
        public ResponseEntity<List<MedicalDataPatientResponse>> getMedicalDataPatientsHistoryFilter(
                        @RequestBody FilterRequest filterRequest,
                        @PageableDefault(size = 25, sort = "name") Pageable pageable) {
                List<MedicalDataPatientResponse> medicalDataPatients = medicalDataPatientService
                                .getMedicalDataPatientsHistoryFilter(filterRequest, pageable);
                return medicalDataPatients.isEmpty() ? ResponseEntity.noContent().build()
                                : ResponseEntity.ok(
                                                medicalDataPatients);
        }

        @GetMapping("/{id}")
        public ResponseEntity<MedicalDataPatientResponse> getMedicalDataPatientById(@PathVariable Integer id) {
                return medicalDataPatientService.getMedicalDataPatientById(id)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
        }

        @PostMapping
        public ResponseEntity<MedicalDataPatientResponse> addMedicalDataPatient(
                        @RequestBody MedicalDataPatientRequest medicalDataPatientRequest) {
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(medicalDataPatientService.addMedicalDataPatient(medicalDataPatientRequest));
        }

        @PutMapping("/{id}")
        public ResponseEntity<MedicalDataPatientResponse> updateMedicalDataPatient(@PathVariable Integer id,
                        @RequestBody MedicalDataPatientRequest medicalDataPatientDetails) {
                Optional<MedicalDataPatientResponse> medicalDataPatient = medicalDataPatientService
                                .updateMedicalDataPatient(id,
                                                medicalDataPatientDetails);
                if (medicalDataPatient.isEmpty()) {
                        return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok(medicalDataPatient.get());
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteHospital(@PathVariable Integer id) {
                medicalDataPatientService.deleteMedicalDataPatient(id);
                return ResponseEntity.noContent().build();
        }

}
