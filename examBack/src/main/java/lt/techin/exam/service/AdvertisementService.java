package lt.techin.exam.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import lt.techin.exam.dto.AdvertisementDTO;
import lt.techin.exam.entity.Advertisement;
import lt.techin.exam.repository.AdvertisementCategoryRepository;
import lt.techin.exam.repository.AdvertisementRepository;
import lt.techin.exam.request.book.AdvertisementListRequest;
import lt.techin.exam.response.book.AdvertisementListResponse;
import lt.techin.exam.response.book.AdvertisementResponse;
import lt.techin.exam.utilities.mapper.AdvertisementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementCategoryRepository advertisementCategoryRepository;

    @Autowired
    public AdvertisementService(AdvertisementRepository advertisementRepository, AdvertisementCategoryRepository advertisementCategoryRepository) {
        this.advertisementRepository = advertisementRepository;
        this.advertisementCategoryRepository = advertisementCategoryRepository;
    }

    @Transactional
    public AdvertisementResponse addAdvert(AdvertisementDTO dto) {
//        final Optional<Advertisement> book = advertisementRepository
//                .findByIsbnIgnoreCase(dto.getIsbn());

//        if (book.isEmpty()) {
            final Advertisement advertToAdd = AdvertisementMapper.DTOToAdvert(dto);

//            if (dto.getCategories() != null) {
//                final List<AdvertisementCategory> categories = new ArrayList<>();
//                for (AdvertisementCategory c : dto.getCategories()) {
//                    final Optional<AdvertisementCategory> categoryToAdd = bookCategoryRepository
//                            .findByCategoryNameIgnoreCase(c.getCategoryName());
//                    categoryToAdd.ifPresent(bookToAdd::addCategory);
//                }
//                bookToAdd.setCategories(categories);
//            }

            final Advertisement savedBook = advertisementRepository.save(advertToAdd);
            log.info("added book, with id {}", savedBook.getId());
            return new AdvertisementResponse(savedBook, HttpStatus.CREATED);
//        }
//
//        final Advertisement bookInRepository = book.get();
//        log.info("book already in repository, id {}", bookInRepository.getId());
//        return new AdvertisementResponse(bookInRepository, HttpStatus.OK);
    }

    public AdvertisementListResponse retrieveAllBooks(AdvertisementListRequest request) {
        final Sort.Direction direction = request.isSortAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
        final Sort sort = Sort.by(direction, request.getSortBy());

        int pageNumber = request.getPageNumber();
        final int pageSize = request.getPageSize();
        final String nameContains = request.getNameContains();
        final String categoryContains = request.getCategoryContains();

        final Pageable pageable = PageRequest.of(--pageNumber, pageSize, sort);
        if ((nameContains == null || nameContains.isEmpty()) && (categoryContains == null || categoryContains.isEmpty())) {
            log.info("retrieving all books in repository");
            Page<Advertisement> page = advertisementRepository.findAll(pageable);
            return new AdvertisementListResponse(page, HttpStatus.OK);
        }

        log.info("retrieving containing {}", nameContains);
        Page<Advertisement> page = advertisementRepository
                .findByAdNameContainingIgnoreCaseAndCategoriesCategoryNameContainingIgnoreCase(
                        pageable,
                        request.getNameContains(),
                        request.getCategoryContains());
        return new AdvertisementListResponse(page, HttpStatus.OK);
    }

//    public AdvertisementResponse retrieveAdByName(String adName) {
//        final Optional<Advertisement> book = advertisementRepository.findByIsbnIgnoreCase(adName);
//
//        if (book.isPresent()) {
//            final Advertisement bookToRetrieve = book.get();
//            log.info("retrieving book with name {}", bookToRetrieve.getIsbn());
//            return new AdvertisementResponse(bookToRetrieve, HttpStatus.OK);
//        }
//
//        log.info("book with name {} was not found", bookName);
//        return new AdvertisementResponse(new Advertisement(), HttpStatus.NOT_FOUND);
//    }

//    public AdvertisementResponse retrieveBookByISBN(String isbn) {
//        final Optional<Advertisement> book = bookRepository.findByIsbnIgnoreCase(isbn);
//
//        if (book.isPresent()) {
//            final Advertisement bookToRetrieve = book.get();
//            log.info("retrieving book with isbn {}", bookToRetrieve.getIsbn());
//            return new AdvertisementResponse(bookToRetrieve, HttpStatus.OK);
//        }
//
//        log.info("book with isbn {} was not found", isbn);
//        return new AdvertisementResponse(new Advertisement(), HttpStatus.NOT_FOUND);
//    }

    public AdvertisementResponse updateAdByID(UUID id, AdvertisementDTO dto) {
        final Optional<Advertisement> advertisement = advertisementRepository.findById(id);

        if (advertisement.isPresent()) {
            final Advertisement adToUpdate = advertisement.get();
            adToUpdate.setAdName(dto.getAdName());
            adToUpdate.setAdDescription(dto.getAdDescription());
            adToUpdate.setPrice(dto.getPrice());
            adToUpdate.setCity(dto.getCity());

            log.info("ad with isbn {} has been updated", adToUpdate.getId());
            return new AdvertisementResponse(adToUpdate, HttpStatus.OK);
        }

        log.info("update failed, ad with id {} was not found", id);
        return new AdvertisementResponse(new Advertisement(), HttpStatus.NOT_FOUND);
    }


//    @Transactional
//    public HttpStatus deleteBookByISBN(String isbn) {
//        final Optional<Advertisement> book = bookRepository.findByIsbnIgnoreCase(isbn);
//
//        if (book.isPresent()) {
//            final Advertisement bookToDelete = book.get();
//
//            for (AdvertisementCategory category : bookToDelete.getCategories()) {
//                category.getBooks().remove(bookToDelete);
//                bookCategoryRepository.save(category);
//            }
//            bookToDelete.getCategories().clear();
//            bookRepository.save(bookToDelete);
//
//            bookRepository.delete(bookToDelete);
//            log.info("book with isbn {} has been deleted", isbn);
//            return HttpStatus.NO_CONTENT;
//        }
//
//        log.info("deletion failed, book with isbn {} was not found", isbn);
//        return HttpStatus.NOT_FOUND;
//    }
}