package lt.techin.exam.service;

import lombok.extern.slf4j.Slf4j;
import lt.techin.exam.config.security.JWTService;
import lt.techin.exam.dto.AuthDTO;
import lt.techin.exam.entity.Token;
import lt.techin.exam.entity.User;
import lt.techin.exam.repository.TokenRepository;
import lt.techin.exam.repository.UserRepository;
import lt.techin.exam.request.auth.AuthenticationRequest;
import lt.techin.exam.request.auth.LogOutRequest;
import lt.techin.exam.request.auth.RegisterRequest;
import lt.techin.exam.utilities.TokenType;
import lt.techin.exam.utilities.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    @Autowired
    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JWTService jwtService,
                                 AuthenticationManager authenticationManager,
                                 TokenRepository tokenRepository
                                 ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
    }

    public AuthDTO register(RegisterRequest request) {
        final Optional<User> user = userRepository.findByEmailIgnoreCase(request.getEmail());

        if (user.isEmpty()) {
            final User userToAdd = UserMapper.RequestDTOToUser(encodePassword(request));
            final User savedUser = userRepository.save(userToAdd);
            final String token = jwtService.generateToken(savedUser);
            saveUserToken(savedUser, token);
            log.info("added user, with id {}", savedUser.getId());
            return new AuthDTO(token, savedUser, HttpStatus.CREATED);
        }

        final User userInRepository = user.get();
        log.info("user already in repository, id {}", userInRepository.getId());
        return new AuthDTO("", new User(), HttpStatus.ALREADY_REPORTED);
    }

    public AuthDTO authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        final Optional<User> user = userRepository.findByEmailIgnoreCase(request.getEmail());
        if (user.isPresent()) {
            final String token = jwtService.generateToken(user.get());
            revokeAllUserTokens(user.get());
            saveUserToken(user.get(), token);
            log.info("logged in user, with id {}", request.getEmail());
            return new AuthDTO(token, user.get(), HttpStatus.OK);
        }
        log.info("login failed, with email {}", request.getEmail());
        return new AuthDTO("", new User(), HttpStatus.NOT_FOUND);
    }

    public RegisterRequest encodePassword(RegisterRequest request) {
        final String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);
        return request;
    }

    private void saveUserToken(User user, String token) {
        final Token tokenToSave = Token.builder()
                .user(user)
                .token(token)
                .tokenType(TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(tokenToSave);
    }

    public HttpStatus logout(LogOutRequest request) {
        return null;
    }

    private void revokeAllUserTokens(User user) {
        final List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty()) return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
