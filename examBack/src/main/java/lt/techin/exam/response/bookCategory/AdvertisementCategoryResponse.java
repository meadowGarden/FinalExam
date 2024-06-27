package lt.techin.exam.response.bookCategory;

import lombok.Getter;
import lombok.Setter;
import lt.techin.exam.entity.AdvertisementCategory;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class AdvertisementCategoryResponse {
    private AdvertisementCategory bookCategory;
    private HttpStatus status;

    public AdvertisementCategoryResponse() {
    }

    public AdvertisementCategoryResponse(AdvertisementCategory bookCategory, HttpStatus status) {
        this.bookCategory = bookCategory;
        this.status = status;
    }
}
