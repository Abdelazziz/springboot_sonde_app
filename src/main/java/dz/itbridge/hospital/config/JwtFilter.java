package dz.itbridge.hospital.config;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import dz.itbridge.hospital.repository.doctor.DoctorRepository;
import dz.itbridge.hospital.utils.EnvConfig;
import dz.itbridge.hospital.utils.MessageUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final DoctorRepository doctorRepository;

    public JwtFilter(JwtUtil jwtUtil, DoctorRepository doctorRepository) {
        this.jwtUtil = jwtUtil;
        this.doctorRepository = doctorRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();

        if (path.startsWith(EnvConfig.API_AUTH)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(MessageUtils.AUTHORIZATION_TXT);

        if (authHeader != null && authHeader.startsWith(MessageUtils.BEARER_TXT)) {
            String token = authHeader.substring(7);
            String username = jwtUtil.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                var doctorOpt = doctorRepository.findByUsername(username);

                if (doctorOpt.isPresent() && jwtUtil.validateAccessToken(token, username)) {
                    List<String> roles = extractRoles(jwtUtil.getClaims(token));

                    List<SimpleGrantedAuthority> authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            username, null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    List<String> extractRoles(Claims claims) {
        Object rolesObject = claims.get(MessageUtils.ROLE_TXT);
        if (rolesObject instanceof List<?>) {
            return ((List<?>) rolesObject).stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
