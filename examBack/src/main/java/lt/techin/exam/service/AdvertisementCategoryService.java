package lt.techin.exam.service;

import lombok.extern.slf4j.Slf4j;
import lt.techin.exam.dto.AdvertisementCategoryDTO;
import lt.techin.exam.entity.Advertisement;
import lt.techin.exam.entity.AdvertisementCategory;
import lt.techin.exam.utilities.mapper.AdvertisementCategoryMapper;
import lt.techin.exam.repository.AdvertisementCategoryRepository;
import lt.techin.exam.repository.AdvertisementRepository;
import lt.techin.exam.request.category.AdvertisementCategoryListRequest;
import lt.techin.exam.response.bookCategory.AdvertisementCategoryListResponse;
import lt.techin.exam.response.bookCategory.AdvertisementCategoryResponse;
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

    private final AdvertisementCategoryRepository bookCategoryRepository;
    private final AdvertisementRepository bookRepository;

    @Autowired
    public AdvertisementCategoryService(AdvertisementCategoryRepository bookCategoryRepository, AdvertisementRepository bookRepository) {
        this.bookCategoryRepository = bookCategoryRepository;
        this.bookRepository = bookRepository;
    }


    @Transactional
    public AdvertisementCategoryResponse addBookCategory(AdvertisementCategoryDTO dto) {
        final Optional<AdvertisementCategory> bookCategory = bookCategoryRepository
                .findByCategoryNameIgnoreCase(dto.getCategoryName());

        if (bookCategory.isEmpty()) {
            final AdvertisementCategory bookCategoryToAdd = AdvertisementCategoryMapper.DTOToAdvertCategory(dto);

            if (dto.getAdvertisements() != null) {
                final List<Advertisement> advertisements = new ArrayList<>();
                for (Advertisement a : dto.getAdvertisements()) {
                    final Optional<Advertisement> bookToAdd = bookRepository
                            .findById(a.getId());
                    bookToAdd.ifPresent(advertisements::add);
                }
                bookCategoryToAdd.setAdvertisements(advertisements);
            }

            final AdvertisementCategory savedBookCategory = bookCategoryRepository.save(bookCategoryToAdd);
            log.info("added book category, with id {}", savedBookCategory.getId());
            return new AdvertisementCategoryResponse(savedBookCategory, HttpStatus.CREATED);
        }
        final AdvertisementCategory bookCategoryInRepository = bookCategory.get();
        log.info("book category already in repository, id {}", bookCategoryInRepository.getId());
        return new AdvertisementCategoryResponse(bookCategoryInRepository, HttpStatus.OK);
    }


    public AdvertisementCategoryListResponse retrieveAllBookCategories(AdvertisementCategoryListRequest request) {
        final Sort.Direction direction = request.isSortAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
        final Sort sort = Sort.by(direction, request.getSortBy());

        int pageNumber = request.getPageNumber();
        final int pageSize = request.getPageSize();
        final String contains = request.getContains();

        final Pageable pageable = PageRequest.of(--pageNumber, pageSize, sort);
        if (contains == null || contains.isEmpty()) {
            log.info("retrieving all book categories in repository");
            Page<AdvertisementCategory> page = bookCategoryRepository.findAll(pageable);
            return new AdvertisementCategoryListResponse(page, HttpStatus.OK);
        }

        log.info("retrieving book categories containing {}", contains);
        Page<AdvertisementCategory> page = bookCategoryRepository
                .findByCategoryNameContainingIgnoreCase(pageable, request.getContains());
        return new AdvertisementCategoryListResponse(page, HttpStatus.OK);
    }


    public AdvertisementCategoryResponse retrieveBookCategoryByName(String categoryName) {
        final Optional<AdvertisementCategory> bookCategory = bookCategoryRepository.findByCategoryNameIgnoreCase(categoryName);

        if (bookCategory.isPresent()) {
            final AdvertisementCategory bookCategoryToRetrieve = bookCategory.get();
            log.info("retrieving book category with name {}", bookCategoryToRetrieve.getCategoryName());
            return new AdvertisementCategoryResponse(bookCategoryToRetrieve, HttpStatus.OK);
        }

        log.info("book category with name {} was not found", categoryName);
        return new AdvertisementCategoryResponse(new AdvertisementCategory(), HttpStatus.NOT_FOUND);
    }


    public AdvertisementCategoryResponse updateBookCategoryByName(String categoryName, AdvertisementCategoryDTO dto) {
        final Optional<AdvertisementCategory> bookCategory = bookCategoryRepository.findByCategoryNameIgnoreCase(categoryName);

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
        final Optional<AdvertisementCategory> bookCategory = bookCategoryRepository.findByCategoryNameIgnoreCase(categoryName);

        if (bookCategory.isPresent()) {
            final AdvertisementCategory categoryToDelete = bookCategory.get();

            for (Advertisement book : categoryToDelete.getAdvertisements()) {
                book.getCategories().remove(categoryToDelete);
                bookRepository.save(book);
            }
            categoryToDelete.getAdvertisements().clear();
            bookCategoryRepository.save(categoryToDelete);

            bookCategoryRepository.deleteById(categoryToDelete.getId());
            log.info("book category with name {} has been deleted", categoryName);
            return HttpStatus.NO_CONTENT;
        }

        log.info("deletion failed, book category with name {} was not found", categoryName);
        return HttpStatus.NOT_FOUND;
    }
}
