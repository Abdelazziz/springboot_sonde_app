package dz.itbridge.hospital.service.medicaldatapatient;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dz.itbridge.hospital.dto.medicaldatapatient.FilterRequest;
import dz.itbridge.hospital.dto.medicaldatapatient.MedicalDataPatientRequest;
import dz.itbridge.hospital.dto.medicaldatapatient.MedicalDataPatientResponse;
import dz.itbridge.hospital.entity.doctor.Doctor;
import dz.itbridge.hospital.entity.hospital.Hospital;
import dz.itbridge.hospital.entity.medicaldatapatient.MedicalDataPatient;
import dz.itbridge.hospital.entity.probe.Probe;
import dz.itbridge.hospital.repository.doctor.DoctorRepository;
import dz.itbridge.hospital.repository.hospital.HospitalRepository;
import dz.itbridge.hospital.repository.medicaldatapatient.MedicalDataPatientRepository;
import dz.itbridge.hospital.repository.probe.ProbeRepository;
import dz.itbridge.hospital.utils.MessageUtils;
import jakarta.persistence.EntityNotFoundException;

@Service
public class MedicalDataPatientService {

        private final MedicalDataPatientRepository medicalDataPatientRepository;
        private final DoctorRepository doctorRepository;
        private final HospitalRepository hospitalRepository;
        private final ProbeRepository probeRepository;

        public MedicalDataPatientService(MedicalDataPatientRepository medicalDataPatientRepository,
                        DoctorRepository doctorRepository,
                        HospitalRepository hospitalRepository,
                        ProbeRepository probeRepository) {
                this.medicalDataPatientRepository = medicalDataPatientRepository;
                this.doctorRepository = doctorRepository;
                this.hospitalRepository = hospitalRepository;
                this.probeRepository = probeRepository;
        }

        // Retrieve all medical data patients
        public List<MedicalDataPatientResponse> getAllMedicalDataPatients(Pageable pageable) {
                return medicalDataPatientRepository.findAll(
                                pageable).getContent().stream()
                                .map(medicalDataPatient -> new MedicalDataPatientResponse(
                                                medicalDataPatient.getId(),
                                                medicalDataPatient.getName(),
                                                medicalDataPatient.getSex(),
                                                medicalDataPatient.getPhone(),
                                                medicalDataPatient.getAddress(),
                                                medicalDataPatient.getDescription(),
                                                medicalDataPatient.getBirthdate(),
                                                medicalDataPatient.getStartdate(),
                                                medicalDataPatient.getEnddate(),
                                                medicalDataPatient.getDoctor().getName(),
                                                medicalDataPatient.getHospital().getName(),
                                                medicalDataPatient.getProbe().getName(), 0))
                                .toList();
        }

        public List<MedicalDataPatientResponse> getMedicalDataPatientsActiveFilter(FilterRequest filterRequest,
                        Pageable pageable) {

                Integer doctor_id = filterRequest.userName() == null || filterRequest.userName().length() == 0 ? null
                                : doctorRepository.findByUsername(filterRequest.userName()).get().getDoctorId();
                String name = filterRequest.name() == null || filterRequest.name().length() == 0 ? null
                                : filterRequest.name();
                String sexCode = filterRequest.sexCode() == null || filterRequest.sexCode().length() == 0 ? null
                                : filterRequest.sexCode();

                Page<MedicalDataPatient> pageData = medicalDataPatientRepository.findWithActiveFilter(name,
                                sexCode,
                                doctor_id, filterRequest.startDate(), filterRequest.endDate(),
                                -3,
                                pageable);

                return pageData.getContent().stream()
                                .map(medicalDataPatient -> new MedicalDataPatientResponse(
                                                medicalDataPatient.getId(),
                                                medicalDataPatient.getName(),
                                                medicalDataPatient.getSex(),
                                                medicalDataPatient.getPhone(),
                                                medicalDataPatient.getAddress(),
                                                medicalDataPatient.getDescription(),
                                                medicalDataPatient.getBirthdate(),
                                                medicalDataPatient.getStartdate(),
                                                medicalDataPatient.getEnddate(),
                                                medicalDataPatient.getDoctor().getName(),
                                                medicalDataPatient.getHospital().getName(),
                                                medicalDataPatient.getProbe().getName(),
                                                pageData.getTotalElements()))
                                .toList();

        }

        public List<MedicalDataPatientResponse> getMedicalDataPatientsHistoryFilter(FilterRequest filterRequest,
                        Pageable pageable) {

                Integer doctor_id = filterRequest.userName() == null || filterRequest.userName().length() == 0 ? null
                                : doctorRepository.findByUsername(filterRequest.userName()).get().getDoctorId();
                String name = filterRequest.name() == null || filterRequest.name().length() == 0 ? null
                                : filterRequest.name();
                String sexCode = filterRequest.sexCode() == null || filterRequest.sexCode().length() == 0 ? null
                                : filterRequest.sexCode();

                Page<MedicalDataPatient> pageData = medicalDataPatientRepository.findWithHistoryFilter(
                                name,
                                sexCode,
                                doctor_id,
                                filterRequest.startDate(),
                                filterRequest.endDate(),
                                pageable);

                return pageData.getContent().stream()
                                .map(medicalDataPatient -> new MedicalDataPatientResponse(
                                                medicalDataPatient.getId(),
                                                medicalDataPatient.getName(),
                                                medicalDataPatient.getSex(),
                                                medicalDataPatient.getPhone(),
                                                medicalDataPatient.getAddress(),
                                                medicalDataPatient.getDescription(),
                                                medicalDataPatient.getBirthdate(),
                                                medicalDataPatient.getStartdate(),
                                                medicalDataPatient.getEnddate(),
                                                medicalDataPatient.getDoctor().getName(),
                                                medicalDataPatient.getHospital().getName(),
                                                medicalDataPatient.getProbe().getName(), pageData.getTotalElements()))
                                .toList();
        }

        public Optional<MedicalDataPatientResponse> getMedicalDataPatientById(Integer id) {
                return medicalDataPatientRepository.findById(id)
                                .map(medicalDataPatient -> new MedicalDataPatientResponse(
                                                medicalDataPatient.getId(),
                                                medicalDataPatient.getName(),
                                                medicalDataPatient.getSex(),
                                                medicalDataPatient.getPhone(),
                                                medicalDataPatient.getAddress(),
                                                medicalDataPatient.getDescription(),
                                                medicalDataPatient.getBirthdate(),
                                                medicalDataPatient.getStartdate(),
                                                medicalDataPatient.getEnddate(),
                                                medicalDataPatient.getDoctor().getName(),
                                                medicalDataPatient.getHospital().getName(),
                                                medicalDataPatient.getProbe().getName(), 0));
        }

        public MedicalDataPatientResponse addMedicalDataPatient(MedicalDataPatientRequest medicalDataPatientRequest) {
                Doctor doctor = doctorRepository.findById(medicalDataPatientRequest.doctorid()).get();
                Hospital hospital = hospitalRepository.findById(medicalDataPatientRequest.hospitalid()).get();
                Probe probe = probeRepository.findById(medicalDataPatientRequest.probeid()).get();

                MedicalDataPatient medicalDataPatient = new MedicalDataPatient();
                medicalDataPatient.setName(medicalDataPatientRequest.name());
                medicalDataPatient.setSex(medicalDataPatientRequest.sex());
                medicalDataPatient.setPhone(medicalDataPatientRequest.phone());
                medicalDataPatient.setAddress(medicalDataPatientRequest.address());
                medicalDataPatient.setDescription(medicalDataPatientRequest.description());
                medicalDataPatient.setBirthdate(medicalDataPatientRequest.birthdate());
                medicalDataPatient.setStartdate(medicalDataPatientRequest.startdate());
                medicalDataPatient.setEnddate(medicalDataPatientRequest.enddate());
                medicalDataPatient.setDoctor(doctor);
                medicalDataPatient.setHospital(hospital);
                medicalDataPatient.setProbe(probe);
                MedicalDataPatient medicalDataPatientSaved = medicalDataPatientRepository.save(medicalDataPatient);

                return new MedicalDataPatientResponse(
                                medicalDataPatientSaved.getId(),
                                medicalDataPatientSaved.getName(),
                                medicalDataPatientSaved.getSex(),
                                medicalDataPatientSaved.getPhone(),
                                medicalDataPatientSaved.getAddress(),
                                medicalDataPatientSaved.getDescription(),
                                medicalDataPatientSaved.getBirthdate(),
                                medicalDataPatientSaved.getStartdate(),
                                medicalDataPatientSaved.getEnddate(),
                                medicalDataPatientSaved.getDoctor().getName(),
                                medicalDataPatientSaved.getHospital().getName(),
                                medicalDataPatientSaved.getProbe().getName(), 0);
        }

        public Optional<MedicalDataPatientResponse> updateMedicalDataPatient(Integer id,
                        MedicalDataPatientRequest medicalDataPatientDetails) {

                Optional<MedicalDataPatient> optionalMedicalDataPatient = medicalDataPatientRepository.findById(id);
                if (optionalMedicalDataPatient.isEmpty()) {
                        return Optional.empty();
                }

                Doctor doctor = doctorRepository.findById(medicalDataPatientDetails.doctorid()).get();
                Hospital hospital = hospitalRepository.findById(medicalDataPatientDetails.hospitalid()).get();
                Probe probe = probeRepository.findById(medicalDataPatientDetails.probeid()).get();

                MedicalDataPatient medicalDataPatient = optionalMedicalDataPatient.get();
                medicalDataPatient.setName(medicalDataPatientDetails.name());
                medicalDataPatient.setSex(medicalDataPatientDetails.sex());
                medicalDataPatient.setPhone(medicalDataPatientDetails.phone());
                medicalDataPatient.setAddress(medicalDataPatientDetails.address());
                medicalDataPatient.setDescription(medicalDataPatientDetails.description());
                medicalDataPatient.setBirthdate(medicalDataPatientDetails.birthdate());
                medicalDataPatient.setStartdate(medicalDataPatientDetails.startdate());
                medicalDataPatient.setEnddate(medicalDataPatientDetails.enddate());
                medicalDataPatient.setDoctor(doctor);
                medicalDataPatient.setHospital(hospital);
                medicalDataPatient.setProbe(probe);

                return Optional.of(medicalDataPatientRepository.save(medicalDataPatient))
                                .map(data -> new MedicalDataPatientResponse(
                                                data.getId(),
                                                data.getName(),
                                                data.getSex(),
                                                data.getPhone(),
                                                data.getAddress(),
                                                data.getDescription(),
                                                data.getBirthdate(),
                                                data.getStartdate(),
                                                data.getEnddate(),
                                                data.getDoctor().getName(),
                                                data.getHospital().getName(),
                                                data.getProbe().getName(), 0));
        }

        public void deleteMedicalDataPatient(Integer id) {
                if (!medicalDataPatientRepository.existsById(id)) {
                        throw new EntityNotFoundException(MessageUtils.ERROR_RESOURCE_NOT_FOUND);
                }
                medicalDataPatientRepository.deleteById(id);
        }

}
