package lt.techin.exam.controller;

import lombok.extern.slf4j.Slf4j;
import lt.techin.exam.dto.AuthDTO;
import lt.techin.exam.dto.UserLoginDTO;
import lt.techin.exam.request.auth.AuthenticationRequest;
import lt.techin.exam.request.auth.RegisterRequest;
import lt.techin.exam.response.auth.AuthenticationResponse;
import lt.techin.exam.service.AuthenticationService;
import lt.techin.exam.utilities.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/auth")
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        log.info("request to register an user, with an email {}", request.getEmail());
        final AuthDTO response = authenticationService.register(request);
        final String token = response.getToken();
        final UserLoginDTO userLoginDTO = UserMapper.UserToLoginDTO(response.getUser());
        final HttpStatus status = response.getHttpStatus();
        final var authResponse = new AuthenticationResponse (token, userLoginDTO);
        return new ResponseEntity<>(authResponse, status);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> logIn(@RequestBody AuthenticationRequest request) {
        log.info("request to log in for a user with an email {}", request.getEmail());
        final AuthDTO response = authenticationService.authenticate(request);
        final String token = response.getToken();
        final UserLoginDTO userLoginDTO = UserMapper.UserToLoginDTO(response.getUser());
        final HttpStatus status = response.getHttpStatus();
        final var authResponse = new AuthenticationResponse (token, userLoginDTO);
        return new ResponseEntity<>(authResponse, status);
    }
}
