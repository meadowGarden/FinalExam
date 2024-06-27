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

import java.util.UUID;

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
        final Advertisement book = response.getAdvertisement();
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
        final AdvertisementListResponse response = advertisementService.retrieveAllAdverts(request);
        final Page<Advertisement> page = response.getAdvertisements();
        final HttpStatus status = response.getStatus();
        return new ResponseEntity<>(page, status);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> retrieveAdByID(@PathVariable UUID id) {
        log.info("request to retrieve ad with id {}", id);
        final AdvertisementResponse response = advertisementService.retrieveAdByID(id);
        final Advertisement advertisement = response.getAdvertisement();
        final HttpStatus status = response.getStatus();
        return new ResponseEntity<>(advertisement, status);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateAdByID(@PathVariable UUID id, @RequestBody AdvertisementDTO dto) {
        log.info("request to update ad with id {}", id);
        final AdvertisementResponse response = advertisementService.updateAdByID(id, dto);
        final Advertisement advertisement = response.getAdvertisement();
        final HttpStatus status = response.getStatus();
        return new ResponseEntity<>(advertisement, status);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteAdByID(@PathVariable UUID id) {
        log.info("request to delete ad with id {}", id);
        final HttpStatus status = advertisementService.deleteAdByID(id);
        return new ResponseEntity<>(status);
    }
}
