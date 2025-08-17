package dz.itbridge.hospital.service.hospital;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dz.itbridge.hospital.dto.hospital.HospitalResponse;
import dz.itbridge.hospital.entity.hospital.Hospital;
import dz.itbridge.hospital.repository.hospital.HospitalRepository;
import dz.itbridge.hospital.utils.MessageUtils;
import jakarta.persistence.EntityNotFoundException;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    public List<HospitalResponse> getAllHospitals() {
        return hospitalRepository.findAll().stream()
                .map(hospital -> new HospitalResponse(hospital.getName(), hospital.getWilaya()))
                .toList();
    }

    public Optional<HospitalResponse> getHospitalById(Integer id) {
        return hospitalRepository.findById(id)
                .map(hospital -> new HospitalResponse(hospital.getName(), hospital.getWilaya()));
    }

    public Hospital addHospital(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    public Optional<Hospital> updateHospital(Integer id, Hospital hospitalDetails) {
        Optional<Hospital> optionalHospital = hospitalRepository.findById(id);
        if (optionalHospital.isEmpty()) {
            return Optional.empty();
        }

        Hospital hospital = optionalHospital.get();
        hospital.setName(hospitalDetails.getName());
        hospital.setWilaya(hospitalDetails.getWilaya());
        Hospital updatedHospital = hospitalRepository.save(hospital);

        return Optional.of(updatedHospital);
    }

    public void deleteHospital(Integer id) {
        if (!hospitalRepository.existsById(id)) {
            throw new EntityNotFoundException(MessageUtils.ERROR_RESOURCE_NOT_FOUND);
        }

        hospitalRepository.deleteById(id);
    }

}
