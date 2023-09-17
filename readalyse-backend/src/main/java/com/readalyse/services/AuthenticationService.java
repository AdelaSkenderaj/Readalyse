package com.readalyse.services;

import com.readalyse.config.JwtService;
import com.readalyse.entities.Role;
import com.readalyse.entities.UserEntity;
import com.readalyse.model.AuthenticationRequest;
import com.readalyse.model.AuthenticationResponse;
import com.readalyse.model.RegisterRequest;
import com.readalyse.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
    UserEntity user =
        UserEntity.builder()
            .firstName(request.getFirstname())
            .lastName(request.getLastname())
            .password(passwordEncoder.encode(request.getPassword()))
            .email(request.getEmail())
            .username(request.getUsername())
            .role(Role.USER)
            .build();
    userRepository.save(user);
    String jwtToken = jwtService.generateToken(user);
    return new AuthenticationResponse().token(jwtToken);
  }

  public AuthenticationResponse login(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    UserEntity user = userRepository.findByUsername(request.getUsername()).orElseThrow();
    String jwtToken = jwtService.generateToken(user);
    return new AuthenticationResponse().token(jwtToken);
  }
}
