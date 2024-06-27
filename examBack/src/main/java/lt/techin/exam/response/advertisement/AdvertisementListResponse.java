package lt.techin.exam.response.advertisement;

import lombok.Getter;
import lombok.Setter;
import lt.techin.exam.entity.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class AdvertisementListResponse {
    private Page<Advertisement> advertisements;
    private HttpStatus status;

    public AdvertisementListResponse() {
    }

    public AdvertisementListResponse(Page<Advertisement> advertisements, HttpStatus status) {
        this.advertisements = advertisements;
        this.status = status;
    }
}
