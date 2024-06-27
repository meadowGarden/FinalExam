package lt.techin.exam.service;

import lombok.extern.slf4j.Slf4j;
import lt.techin.exam.dto.AdvertisementCategoryDTO;
import lt.techin.exam.entity.Advertisement;
import lt.techin.exam.entity.AdvertisementCategory;
import lt.techin.exam.utilities.mapper.AdvertisementCategoryMapper;
import lt.techin.exam.repository.AdvertisementCategoryRepository;
import lt.techin.exam.repository.AdvertisementRepository;
import lt.techin.exam.request.category.AdvertisementCategoryListRequest;
import lt.techin.exam.response.advertisementCategory.AdvertisementCategoryListResponse;
import lt.techin.exam.response.advertisementCategory.AdvertisementCategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AdvertisementCategoryService {

    private final AdvertisementCategoryRepository advertisementCategoryRepository;
    private final AdvertisementRepository advertisementRepository;

    @Autowired
    public AdvertisementCategoryService(AdvertisementCategoryRepository advertisementCategoryRepository, AdvertisementRepository advertisementRepository) {
        this.advertisementCategoryRepository = advertisementCategoryRepository;
        this.advertisementRepository = advertisementRepository;
    }


    @Transactional
    public AdvertisementCategoryResponse addAdvertCategory(AdvertisementCategoryDTO dto) {
        final Optional<AdvertisementCategory> advertisementCategory = advertisementCategoryRepository
                .findByCategoryNameIgnoreCase(dto.getCategoryName());

        if (advertisementCategory.isEmpty()) {
            final AdvertisementCategory advertCategoryToAdd = AdvertisementCategoryMapper.DTOToAdvertCategory(dto);

            if (dto.getAdvertisements() != null) {
                final List<Advertisement> advertisements = new ArrayList<>();
                for (Advertisement a : dto.getAdvertisements()) {
                    final Optional<Advertisement> bookToAdd = advertisementRepository
                            .findById(a.getId());
                    bookToAdd.ifPresent(advertisements::add);
                }
                advertCategoryToAdd.setAdvertisements(advertisements);
            }

            final AdvertisementCategory savedBookCategory = advertisementCategoryRepository.save(advertCategoryToAdd);
            log.info("added ad category, with id {}", savedBookCategory.getId());
            return new AdvertisementCategoryResponse(savedBookCategory, HttpStatus.CREATED);
        }
        final AdvertisementCategory bookCategoryInRepository = advertisementCategory.get();
        log.info("ad category already in repository, id {}", bookCategoryInRepository.getId());
        return new AdvertisementCategoryResponse(bookCategoryInRepository, HttpStatus.OK);
    }


    public AdvertisementCategoryListResponse retrieveAllAdCategories(AdvertisementCategoryListRequest request) {
        final Sort.Direction direction = request.isSortAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
        final Sort sort = Sort.by(direction, request.getSortBy());

        int pageNumber = request.getPageNumber();
        final int pageSize = request.getPageSize();
        final String contains = request.getContains();

        final Pageable pageable = PageRequest.of(--pageNumber, pageSize, sort);
        if (contains == null || contains.isEmpty()) {
            log.info("retrieving all ad categories in repository");
            Page<AdvertisementCategory> page = advertisementCategoryRepository.findAll(pageable);
            return new AdvertisementCategoryListResponse(page, HttpStatus.OK);
        }

        log.info("retrieving ad categories containing {}", contains);
        Page<AdvertisementCategory> page = advertisementCategoryRepository
                .findByCategoryNameContainingIgnoreCase(pageable, request.getContains());
        return new AdvertisementCategoryListResponse(page, HttpStatus.OK);
    }


    public AdvertisementCategoryResponse retrieveBookCategoryByName(String categoryName) {
        final Optional<AdvertisementCategory> advertisementCategory = advertisementCategoryRepository
                .findByCategoryNameIgnoreCase(categoryName);

        if (advertisementCategory.isPresent()) {
            final AdvertisementCategory bookCategoryToRetrieve = advertisementCategory.get();
            log.info("retrieving book category with name {}", bookCategoryToRetrieve.getCategoryName());
            return new AdvertisementCategoryResponse(bookCategoryToRetrieve, HttpStatus.OK);
        }

        log.info("book category with name {} was not found", categoryName);
        return new AdvertisementCategoryResponse(new AdvertisementCategory(), HttpStatus.NOT_FOUND);
    }


    public AdvertisementCategoryResponse updateBookCategoryByName(String categoryName, AdvertisementCategoryDTO dto) {
        final Optional<AdvertisementCategory> bookCategory = advertisementCategoryRepository
                .findByCategoryNameIgnoreCase(categoryName);

        if (bookCategory.isPresent()) {
            final AdvertisementCategory bookCategoryToUpdate = bookCategory.get();
            bookCategoryToUpdate.setCategoryName(dto.getCategoryName());
            log.info("book category with name {} has been updated", bookCategoryToUpdate.getCategoryName());
            return new AdvertisementCategoryResponse(bookCategoryToUpdate, HttpStatus.OK);
        }

        log.info("update failed, book category with name {} was not found", categoryName);
        return new AdvertisementCategoryResponse(new AdvertisementCategory(), HttpStatus.NOT_FOUND);
    }


    public HttpStatus deleteBookCategoryByName(String categoryName) {
        final Optional<AdvertisementCategory> advertisementCategory = advertisementCategoryRepository
                .findByCategoryNameIgnoreCase(categoryName);

        if (advertisementCategory.isPresent()) {
            final AdvertisementCategory categoryToDelete = advertisementCategory.get();

            for (Advertisement book : categoryToDelete.getAdvertisements()) {
                book.getCategories().remove(categoryToDelete);
                advertisementRepository.save(book);
            }
            categoryToDelete.getAdvertisements().clear();
            advertisementCategoryRepository.save(categoryToDelete);

            advertisementCategoryRepository.deleteById(categoryToDelete.getId());
            log.info("ad category with name {} has been deleted", categoryName);
            return HttpStatus.NO_CONTENT;
        }

        log.info("deletion failed, ad category with name {} was not found", categoryName);
        return HttpStatus.NOT_FOUND;
    }
}
