package lt.techin.exam.response.user;

import lombok.Getter;
import lombok.Setter;
import lt.techin.exam.entity.User;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class UserResponse {
    private User user;
    private HttpStatus status;

    public UserResponse() {
    }

    public UserResponse(User user, HttpStatus status) {
        this.user = user;
        this.status = status;
    }
}
