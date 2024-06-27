package lt.techin.exam.response.book;

import lombok.Getter;
import lombok.Setter;
import lt.techin.exam.entity.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

public class AdvertisementListResponse {
    private Page<Advertisement> advertisements;
    @Setter
    @Getter
    private HttpStatus status;

    public AdvertisementListResponse() {
    }

    public AdvertisementListResponse(Page<Advertisement> advertisements, HttpStatus status) {
        this.advertisements = advertisements;
        this.status = status;
    }

    public Page<Advertisement> getBooks() {
        return advertisements;
    }

    public void setBooks(Page<Advertisement> books) {
        this.advertisements = books;
    }
}
