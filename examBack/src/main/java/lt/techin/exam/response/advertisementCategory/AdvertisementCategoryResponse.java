package lt.techin.exam.response.advertisementCategory;

import lombok.Getter;
import lombok.Setter;
import lt.techin.exam.entity.AdvertisementCategory;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class AdvertisementCategoryResponse {
    private AdvertisementCategory advertisementCategory;
    private HttpStatus status;

    public AdvertisementCategoryResponse() {
    }

    public AdvertisementCategoryResponse(AdvertisementCategory advertisementCategory, HttpStatus status) {
        this.advertisementCategory = advertisementCategory;
        this.status = status;
    }
}
