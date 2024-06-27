package lt.techin.exam.controller;

import lombok.extern.slf4j.Slf4j;
import lt.techin.exam.dto.AdvertisementCategoryDTO;
import lt.techin.exam.entity.AdvertisementCategory;
import lt.techin.exam.request.category.AdvertisementCategoryListRequest;
import lt.techin.exam.response.bookCategory.AdvertisementCategoryListResponse;
import lt.techin.exam.response.bookCategory.AdvertisementCategoryResponse;
import lt.techin.exam.service.AdvertisementCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/categories")
@Slf4j
public class AdvertisementCategoryController {

    private final AdvertisementCategoryService advertisementCategoryService;

    @Autowired
    public AdvertisementCategoryController(AdvertisementCategoryService advertisementCategoryService) {
        this.advertisementCategoryService = advertisementCategoryService;
    }

    @PostMapping
    public ResponseEntity<?> addAdvertisementCategory(@RequestBody AdvertisementCategoryDTO dto) {
        log.info("request to add ad category, name {}", dto.getCategoryName());
        final AdvertisementCategoryResponse response = advertisementCategoryService.addAdvertCategory(dto);
        final AdvertisementCategory advertisementCategory = response.getBookCategory();
        final HttpStatus status = response.getStatus();
        return new ResponseEntity<>(advertisementCategory, status);
    }

    @GetMapping
    public ResponseEntity<?> retrieveAllBookCategories(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String contains,
            @RequestParam(defaultValue = "categoryName") String sortBy,
            @RequestParam(defaultValue = "true") boolean sortAsc
    ) {
        final var request = new AdvertisementCategoryListRequest(pageNumber, pageSize, contains, sortBy, sortAsc);
        log.info("request to retrieve list of book categories");
        final AdvertisementCategoryListResponse response = advertisementCategoryService.retrieveAllAdCategories(request);
        final Page<AdvertisementCategory> page = response.getBookCategories();
        final HttpStatus status = response.getStatus();
        return new ResponseEntity<>(page, status);
    }

    @GetMapping(path = "/{categoryName}")
    public ResponseEntity<?> retrieveBookCategoryByName(@PathVariable String categoryName) {
        log.info("request to retrieve book categories with name {}", categoryName);
        final AdvertisementCategoryResponse response = advertisementCategoryService.retrieveBookCategoryByName(categoryName);
        final AdvertisementCategory bookCategory = response.getBookCategory();
        final HttpStatus status = response.getStatus();
        return new ResponseEntity<>(bookCategory, status);
    }

    @PutMapping(path = "/{categoryName}")
    public ResponseEntity<?> updateBookCategoryByName(@PathVariable String categoryName, @RequestBody AdvertisementCategoryDTO dto) {
        log.info("request to update book category with name {}", categoryName);
        final AdvertisementCategoryResponse response = advertisementCategoryService.updateBookCategoryByName(categoryName, dto);
        final AdvertisementCategory bookCategory = response.getBookCategory();
        final HttpStatus status = response.getStatus();
        return new ResponseEntity<>(bookCategory, status);
    }

    @DeleteMapping(path = "/{categoryName}")
    public ResponseEntity<?> deleteBookCategoryByName(@PathVariable String categoryName) {
        log.info("request to delete book category with name {}", categoryName);
        final HttpStatus status = advertisementCategoryService.deleteBookCategoryByName(categoryName);
        return new ResponseEntity<>(status);
    }
}