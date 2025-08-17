package dz.itbridge.hospital.repository.medicaldatapatient;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dz.itbridge.hospital.entity.medicaldatapatient.MedicalDataPatient;

public interface MedicalDataPatientRepository extends JpaRepository<MedicalDataPatient, Integer> {

  Optional<MedicalDataPatient> findByName(String name);

  Optional<MedicalDataPatient> findBySex(String sex);

  Optional<MedicalDataPatient> findByPhone(String phone);

  Optional<MedicalDataPatient> findByAddress(String address);

  Optional<MedicalDataPatient> findByDescription(String description);

  Optional<MedicalDataPatient> findByBirthdate(LocalDate birthdate);

  Optional<MedicalDataPatient> findByStartdate(LocalDate startdate);

  Optional<MedicalDataPatient> findByEnddate(LocalDate enddate);

  @Query(value = """
      SELECT *
      FROM medicaldatapatient
      WHERE
        (:name IS NULL OR UPPER(name) LIKE CONCAT('%', UPPER(:name), '%'))
        AND (:sexCode IS NULL OR sex = :sexCode)
        AND (:doctor_id IS NULL OR doctor_id = :doctor_id)
        AND (TRUE = :#{#startDate == null} OR enddate >= :startDate)
        AND (TRUE = :#{#endDate == null} OR enddate <= :endDate)
      """, nativeQuery = true)
  Page<MedicalDataPatient> findWithHistoryFilter(@Param("name") String name,
      @Param("sexCode") String sexCode,
      @Param("doctor_id") Integer doctor_id,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate,
      Pageable pageable);

  @Query(value = """
      SELECT *
      FROM medicaldatapatient
      WHERE
        (:name IS NULL OR UPPER(name) LIKE CONCAT('%', UPPER(:name), '%'))
        AND (:sexCode IS NULL OR sex = :sexCode)
        AND (:doctor_id IS NULL OR doctor_id = :doctor_id)
        AND (TRUE = :#{#startDate == null} OR enddate >= :startDate)
        AND (TRUE = :#{#endDate == null} OR enddate <= :endDate)
        AND (DATE(enddate) - DATE(current_date)) >= :daysOff
      """, nativeQuery = true)
  Page<MedicalDataPatient> findWithActiveFilter(@Param("name") String name,
      @Param("sexCode") String sexCode,
      @Param("doctor_id") Integer doctor_id,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate,
      @Param("daysOff") int daysOff,
      Pageable pageable);

}
