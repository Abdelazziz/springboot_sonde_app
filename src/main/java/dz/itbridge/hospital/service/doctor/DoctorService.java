package dz.itbridge.hospital.service.doctor;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dz.itbridge.hospital.dto.doctor.DoctorRequest;
import dz.itbridge.hospital.dto.doctor.DoctorResponse;
import dz.itbridge.hospital.entity.doctor.Doctor;
import dz.itbridge.hospital.repository.doctor.DoctorRepository;
import dz.itbridge.hospital.utils.MessageUtils;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    public DoctorService(DoctorRepository doctorRepository, PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<DoctorResponse> getAllDoctors() {

        List<DoctorResponse> doctors = doctorRepository.findAll().stream()
                .map(doctor -> new DoctorResponse(
                        doctor.getDoctorId(),
                        doctor.getName(),
                        doctor.getUsername(),
                        doctor.getRole()))
                .toList();

        return doctors;
    }

    public Optional<DoctorResponse> getDoctorById(Integer id) {

        Optional<DoctorResponse> doctor = doctorRepository.findById(id).map(doc -> new DoctorResponse(doc.getDoctorId(),
                doc.getName(),
                doc.getUsername(),
                doc.getRole()));

        return doctor;
    }

    public Optional<DoctorResponse> getDoctorByuserName(String username) {

        Optional<DoctorResponse> doctor = doctorRepository.findByUsername(
                username).map(
                        doc -> new DoctorResponse(
                                doc.getDoctorId(),
                                doc.getName(),
                                doc.getUsername(),
                                doc.getRole()));

        return doctor;
    }

    public DoctorResponse addDoctor(Doctor doctor) {
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        Doctor savedEncryptDoctor = doctorRepository.save(doctor);

        DoctorResponse doctorResponse = new DoctorResponse(
                savedEncryptDoctor.getDoctorId(), savedEncryptDoctor.getName(), savedEncryptDoctor.getUsername(),
                savedEncryptDoctor.getRole());

        return doctorResponse;
    }

    public Optional<DoctorResponse> updateDoctor(Integer id, DoctorRequest doctorDetails) {

        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if (optionalDoctor.isEmpty()) {
            return Optional.empty();
        }

        Doctor doctor = optionalDoctor.get();
        doctor.setName(doctorDetails.getName());
        doctor.setUsername(doctorDetails.getUserName());
        doctor.setPassword(doctorDetails.isPasswordUpdated() ? passwordEncoder.encode(
                doctorDetails.getPassword()) : doctor.getPassword());
        doctor.setRole(doctorDetails.getRole());
        Doctor updatedDoctor = doctorRepository.save(doctor);
        DoctorResponse doctorResponse = new DoctorResponse(
                updatedDoctor.getDoctorId(), updatedDoctor.getName(), updatedDoctor.getUsername(),
                updatedDoctor.getRole());

        return Optional.of(doctorResponse);
    }

    public void deleteDoctor(Integer id) {

        if (!doctorRepository.existsById(id)) {
            throw new EntityNotFoundException(MessageUtils.ERROR_RESOURCE_NOT_FOUND);
        }

        doctorRepository.deleteById(id);
    }

}
