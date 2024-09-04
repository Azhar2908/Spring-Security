package com.spring_security_project.config;

import com.spring_security_project.model.AppUser;
import com.spring_security_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class AppUserUsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        AppUser appUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("No user registered with this email!"));

        if (passwordEncoder.matches(password, appUser.getPassword())) {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + appUser.getRole().name());
            return new UsernamePasswordAuthenticationToken(username, password, Collections.singletonList(authority));
        } else {
            throw new BadCredentialsException("Invalid password!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
