package lt.techin.exam.response.advertisementCategory;

import lombok.Getter;
import lombok.Setter;
import lt.techin.exam.entity.AdvertisementCategory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class AdvertisementCategoryListResponse {
    private Page<AdvertisementCategory> advertisementCategories;
    private HttpStatus status;

    public AdvertisementCategoryListResponse() {
    }

    public AdvertisementCategoryListResponse(Page<AdvertisementCategory> advertisementCategories, HttpStatus status) {
        this.advertisementCategories = advertisementCategories;
        this.status = status;
    }
}
