package dz.itbridge.hospital.service.doctor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dz.itbridge.hospital.entity.doctor.Doctor;
import dz.itbridge.hospital.repository.doctor.DoctorRepository;
import dz.itbridge.hospital.utils.MessageUtils;

@Service
public class DoctorUserDetailsService implements UserDetailsService {

    private final DoctorRepository doctorRepository;

    public DoctorUserDetailsService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Doctor doctor = doctorRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(MessageUtils.ERROR_INVALID_CREDENTIALS + username));

        return new User(
                doctor.getUsername(),
                doctor.getPassword(),
                getAuthorities(doctor.getRole()));
    }

    private List<GrantedAuthority> getAuthorities(String roles) {
        if (roles == null || roles.isBlank()) {
            return List.of();
        }
        return Arrays.stream(roles.split(","))
                .map(String::trim)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
