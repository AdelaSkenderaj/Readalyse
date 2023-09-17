package com.readalyse.controllers;

import com.readalyse.api.AuthApi;
import com.readalyse.model.AuthenticationRequest;
import com.readalyse.model.AuthenticationResponse;
import com.readalyse.model.RegisterRequest;
import com.readalyse.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthApi {

  private final AuthenticationService service;

  @Override
  public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) {
    return ResponseEntity.ok(service.register(request));
  }

  @Override
  public ResponseEntity<AuthenticationResponse> login(AuthenticationRequest request) {
    return ResponseEntity.ok(service.login(request));
  }
}
