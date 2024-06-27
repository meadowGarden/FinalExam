package lt.techin.exam.response.bookCategory;

import lombok.Getter;
import lombok.Setter;
import lt.techin.exam.entity.AdvertisementCategory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class AdvertisementCategoryListResponse {
    private Page<AdvertisementCategory> bookCategories;
    private HttpStatus status;

    public AdvertisementCategoryListResponse() {
    }

    public AdvertisementCategoryListResponse(Page<AdvertisementCategory> bookCategories, HttpStatus status) {
        this.bookCategories = bookCategories;
        this.status = status;
    }
}
