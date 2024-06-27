package lt.techin.exam.controller;

import lombok.extern.slf4j.Slf4j;
import lt.techin.exam.dto.UserDTO;
import lt.techin.exam.entity.User;
import lt.techin.exam.request.user.UserListRequest;
import lt.techin.exam.response.user.UserListResponse;
import lt.techin.exam.response.user.UserResponse;
import lt.techin.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/users")
@Slf4j
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
    public ResponseEntity<?> addUser(@RequestBody UserDTO dto) {
        log.info("request to add user, email {}", dto.getEmail());
        final UserResponse response = userService.addUser(dto);
        final User user = response.getUser();
        final HttpStatus status = response.getStatus();
        return new ResponseEntity<>(user, status);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
    public ResponseEntity<?> retrieveAllUsers(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String contains,
            @RequestParam(defaultValue = "lastName") String sortBy,
            @RequestParam(defaultValue = "true") boolean sortAsc
    ) {
        final var request = new UserListRequest(pageNumber, pageSize, contains, sortBy, sortAsc);
        log.info("request to retrieve list of users");
        final UserListResponse response = userService.retrieveAllUsers(request);
        final Page<User> page = response.getUsers();
        final HttpStatus status = response.getStatus();
        return new ResponseEntity<>(page, status);
    }

    @GetMapping(path = "/{isbn}")
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
    public ResponseEntity<?> retrieveUserByEmail(@PathVariable String email) {
        log.info("request to retrieve user with email {}", email);
        final UserResponse response = userService.retrieveUserByEmail(email);
        final User user = response.getUser();
        final HttpStatus status = response.getStatus();
        return new ResponseEntity<>(user, status);
    }

    @PutMapping(path = "/{isbn}")
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
    public ResponseEntity<?> updateBookByISBN(@PathVariable String email, @RequestBody UserDTO dto) {
        log.info("request to update book with isbn {}", email);
        final UserResponse response = userService.updateUserByEmail(email, dto);
        final User user = response.getUser();
        final HttpStatus status = response.getStatus();
        return new ResponseEntity<>(user, status);
    }

}
