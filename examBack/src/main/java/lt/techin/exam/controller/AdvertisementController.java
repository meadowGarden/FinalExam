package lt.techin.exam.controller;

import lombok.extern.slf4j.Slf4j;
import lt.techin.exam.dto.AdvertisementDTO;
import lt.techin.exam.entity.Advertisement;
import lt.techin.exam.request.book.AdvertisementListRequest;
import lt.techin.exam.response.book.AdvertisementListResponse;
import lt.techin.exam.response.book.AdvertisementResponse;
import lt.techin.exam.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/adverts")
@Slf4j
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @Autowired
    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @PostMapping
    public ResponseEntity<?> addAdvert(@RequestBody AdvertisementDTO dto) {
        log.info("request to add advertisement");
        final AdvertisementResponse response = advertisementService.addAdvert(dto);
        final Advertisement book = response.getBook();
        final HttpStatus status = response.getStatus();
        return new ResponseEntity<>(book, status);
    }

    @GetMapping
    public ResponseEntity<?> retrieveAllAds(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String nameContains,
            @RequestParam(required = false) String categoryContains,
            @RequestParam(defaultValue = "adName") String sortBy,
            @RequestParam(defaultValue = "true") boolean sortAsc
    ) {
        final AdvertisementListRequest request = new AdvertisementListRequest(
                pageNumber, pageSize, nameContains, categoryContains, sortBy, sortAsc
        );
        log.info("request to retrieve list of books");
        final AdvertisementListResponse response = advertisementService.retrieveAllBooks(request);
        final Page<Advertisement> page = response.getBooks();
        final HttpStatus status = response.getStatus();
        return new ResponseEntity<>(page, status);
    }

//    @GetMapping(path = "/{id}")
//    public ResponseEntity<?> retrieveAdByID(@PathVariable String id) {
//        log.info("request to retrieve ad with id {}", id);
//        final AdvertisementResponse response = advertisementService.retrieveBookByISBN(id);
//        final Advertisement book = response.getBook();
//        final HttpStatus status = response.getStatus();
//        return new ResponseEntity<>(book, status);
//    }
//
//    @PutMapping(path = "/{id}")
//    public ResponseEntity<?> updateAdByID(@PathVariable String id, @RequestBody AdvertisementDTO dto) {
//        log.info("request to update ad with id {}", id);
//        final AdvertisementResponse response = advertisementService.updateBookByISBN(id, dto);
//        final Advertisement book = response.getBook();
//        final HttpStatus status = response.getStatus();
//        return new ResponseEntity<>(book, status);
//    }
//
//    @DeleteMapping(path = "/{id}")
//    public ResponseEntity<?> deleteAdByID(@PathVariable String id) {
//        log.info("request to delete ad with id {}", id);
//        final HttpStatus status = advertisementService.deleteBookByISBN(id);
//        return new ResponseEntity<>(status);
//    }

}
