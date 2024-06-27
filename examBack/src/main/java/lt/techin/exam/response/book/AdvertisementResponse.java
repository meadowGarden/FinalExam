package lt.techin.exam.response.book;

import lombok.Getter;
import lombok.Setter;
import lt.techin.exam.entity.Advertisement;
import org.springframework.http.HttpStatus;

public class AdvertisementResponse {
    private Advertisement advertisement;
    @Setter
    @Getter
    private HttpStatus status;

    public AdvertisementResponse() {
    }

    public AdvertisementResponse(Advertisement advertisement, HttpStatus status) {
        this.advertisement = advertisement;
        this.status = status;
    }

    public Advertisement getBook() {
        return advertisement;
    }

    public void setBook(Advertisement book) {
        this.advertisement = book;
    }
}
