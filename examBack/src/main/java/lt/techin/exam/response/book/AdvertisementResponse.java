package lt.techin.exam.response.book;

import lombok.Getter;
import lombok.Setter;
import lt.techin.exam.entity.Advertisement;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class AdvertisementResponse {
    private Advertisement advertisement;

    private HttpStatus status;

    public AdvertisementResponse() {
    }

    public AdvertisementResponse(Advertisement advertisement, HttpStatus status) {
        this.advertisement = advertisement;
        this.status = status;
    }

}
