package lt.techin.exam.service;

import lombok.extern.slf4j.Slf4j;
import lt.techin.exam.dto.UserDTO;
import lt.techin.exam.entity.User;
import lt.techin.exam.utilities.UserRole;
import lt.techin.exam.utilities.mapper.UserMapper;
import lt.techin.exam.repository.UserRepository;
import lt.techin.exam.request.user.UserListRequest;
import lt.techin.exam.response.user.UserListResponse;
import lt.techin.exam.response.user.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse addUser(UserDTO dto) {
        final Optional<User> user = userRepository.findByEmailIgnoreCase(dto.getEmail());

        if (user.isEmpty()) {

            if (dto.getUserRole() == null) {
                dto.setUserRole(UserRole.USER);
            }

            final User userToAdd = UserMapper.DTOToUser(dto);
            final User savedUser = userRepository.save(userToAdd);
            log.info("added user, with id {}", savedUser.getId());
            return new UserResponse(savedUser, HttpStatus.CREATED);
        }

        final User userInRepository = user.get();
        log.info("user already in repository, id {}", userInRepository.getId());
        return new UserResponse(userInRepository, HttpStatus.OK);
    }

    public UserListResponse retrieveAllUsers(UserListRequest request) {
        final Sort.Direction direction = request.isSortAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
        final Sort sort = Sort.by(direction, request.getSortBy());
        int pageNumber = request.getPageNumber();

        final int pageSize = request.getPageSize();
        final String contains = request.getContains();
        final Pageable pageable = PageRequest.of(--pageNumber, pageSize, sort);

        if (contains == null || contains.isEmpty()) {
            log.info("retrieving all users in repository");
            Page<User> page = userRepository.findAll(pageable);
            return new UserListResponse(page, HttpStatus.OK);
        }

        log.info("retrieving users containing {}", contains);
        Page<User> page = userRepository.findByEmailContainingIgnoreCase(pageable, request.getContains());
        return new UserListResponse(page, HttpStatus.OK);
    }

    public UserResponse retrieveUserByEmail(String email) {
        final Optional<User> user = userRepository.findByEmailIgnoreCase(email);

        if (user.isPresent()) {
            final User userToRetrieve = user.get();
            log.info("retrieving user with email {}", userToRetrieve.getEmail());
            return new UserResponse(userToRetrieve, HttpStatus.OK);
        }

        log.info("user with email {} was not found", email);
        return new UserResponse(new User(), HttpStatus.NOT_FOUND);
    }

    public UserResponse updateUserByEmail(String email, UserDTO dto) {
        final Optional<User> user = userRepository.findByEmailIgnoreCase(email);

        if (user.isPresent()) {
            final User userToUpdate = user.get();
            userToUpdate.setEmail(dto.getEmail());
            userToUpdate.setFirstName(dto.getFirstName());
            userToUpdate.setLastName(dto.getLastName());
            userToUpdate.setUserRole(dto.getUserRole());
            log.info("user with email {} has been updated", userToUpdate.getEmail());
            return new UserResponse(userToUpdate, HttpStatus.OK);
        }

        log.info("update failed, user with email {} was not found", email);
        return new UserResponse(new User(), HttpStatus.NOT_FOUND);
    }

    public HttpStatus deleteUserByEmail(String email) {
        final Optional<User> user = userRepository.findByEmailIgnoreCase(email);

        if (user.isPresent()) {
            final User userToDelete = user.get();
            userRepository.deleteById(userToDelete.getId());
            log.info("user with isbn {} has been deleted", email);
            return HttpStatus.NO_CONTENT;
        }

        log.info("deletion failed, user with email {} was not found", email);
        return HttpStatus.NOT_FOUND;
    }
}