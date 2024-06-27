package lt.techin.exam.request.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogOutRequest {
    private String token;
    private String email;
}
