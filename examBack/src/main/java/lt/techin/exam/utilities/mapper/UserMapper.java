package lt.techin.exam.utilities.mapper;

import lt.techin.exam.dto.UserDTO;
import lt.techin.exam.dto.UserLoginDTO;
import lt.techin.exam.entity.User;
import lt.techin.exam.request.auth.RegisterRequest;
import lt.techin.exam.utilities.UserRole;

public class UserMapper {

    public static User DTOToUser(UserDTO dto) {
        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .userRole(dto.getUserRole())
                .password(dto.getPassword())
                .build();
    }

    public static UserLoginDTO UserToLoginDTO(User user) {
        return UserLoginDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .userRole(user.getUserRole())
                .build();
    }

    public static User RequestDTOToUser(RegisterRequest dto) {
        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .userRole(UserRole.USER)
                .password(dto.getPassword())
                .build();
    }
}
