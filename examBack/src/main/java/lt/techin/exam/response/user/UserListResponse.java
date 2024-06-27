package lt.techin.exam.response.user;

import lombok.Getter;
import lombok.Setter;
import lt.techin.exam.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.List;

@Setter
@Getter
public class UserListResponse {
    private Page<User> users;
    private HttpStatus status;

    public UserListResponse() {
    }

    public UserListResponse(Page<User> users, HttpStatus status) {
        this.users = users;
        this.status = status;
    }
}
