package dz.itbridge.hospital.dto.medicaldatapatient;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public record FilterRequest(
                String name,
                String sexCode, String userName,
                @JsonFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
                @JsonFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
}
