package dz.itbridge.hospital.dto.medicaldatapatient;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public record MedicalDataPatientResponse(
                Integer id,
                String name,
                String sex,
                String phone,
                String address,
                String description,
                @JsonFormat(pattern = "dd/MM/yyyy") LocalDate birthdate,
                @JsonFormat(pattern = "dd/MM/yyyy") LocalDate startdate,
                @JsonFormat(pattern = "dd/MM/yyyy") LocalDate enddate,
                String doctorName,
                String hospitalName,
                String probeName,
                long totalElments) {
}
