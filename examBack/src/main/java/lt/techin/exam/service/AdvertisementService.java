package lt.techin.exam.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import lt.techin.exam.dto.AdvertisementDTO;
import lt.techin.exam.entity.Advertisement;
import lt.techin.exam.entity.AdvertisementCategory;
import lt.techin.exam.repository.AdvertisementCategoryRepository;
import lt.techin.exam.repository.AdvertisementRepository;
import lt.techin.exam.request.advertisement.AdvertisementListRequest;
import lt.techin.exam.response.advertisement.AdvertisementListResponse;
import lt.techin.exam.response.advertisement.AdvertisementResponse;
import lt.techin.exam.utilities.mapper.AdvertisementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

        final Advertisement advertToAdd = AdvertisementMapper.DTOToAdvert(dto);

            if (dto.getCategories() != null) {
                final List<AdvertisementCategory> categories = new ArrayList<>();
                for (AdvertisementCategory c : dto.getCategories()) {
                    final Optional<Advertisement> advertisementCategory = advertisementRepository
                            .findById(c.getId());
//                    advertisementCategory.ifPresent(advertToAdd::addCategory);
                }
                advertToAdd.setCategories(categories);
            }

        final Advertisement savedAd = advertisementRepository.save(advertToAdd);
        log.info("added ad, with id {}", savedAd.getId());
        return new AdvertisementResponse(savedAd, HttpStatus.CREATED);

    }

    public AdvertisementListResponse retrieveAllAdverts(AdvertisementListRequest request) {
        final Sort.Direction direction = request.isSortAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
        final Sort sort = Sort.by(direction, request.getSortBy());

        int pageNumber = request.getPageNumber();
        final int pageSize = request.getPageSize();
        final String nameContains = request.getNameContains();
        final String categoryContains = request.getCategoryContains();

        final Pageable pageable = PageRequest.of(--pageNumber, pageSize, sort);
        if ((nameContains == null || nameContains.isEmpty()) && (categoryContains == null || categoryContains.isEmpty())) {
            log.info("retrieving all ads in repository");
            Page<Advertisement> page = advertisementRepository.findAll(pageable);
            return new AdvertisementListResponse(page, HttpStatus.OK);
        }

        log.info("retrieving containing {}", nameContains);
        Page<Advertisement> page = advertisementRepository
                .findByAdNameContainingIgnoreCaseOrCategoriesCategoryNameContainingIgnoreCase(
                        pageable,
                        request.getNameContains(),
                        request.getCategoryContains());
        return new AdvertisementListResponse(page, HttpStatus.OK);
    }

    public AdvertisementResponse retrieveAdByID(UUID id) {
        final Optional<Advertisement> advertisement = advertisementRepository.findById(id);

        if (advertisement.isPresent()) {
            final Advertisement adToRetrieve = advertisement.get();
            log.info("retrieving ad with id {}", adToRetrieve.getId());
            return new AdvertisementResponse(adToRetrieve, HttpStatus.OK);
        }

        log.info("ad with id {} was not found", id);
        return new AdvertisementResponse(new Advertisement(), HttpStatus.NOT_FOUND);
    }

    public AdvertisementResponse updateAdByID(UUID id, AdvertisementDTO dto) {
        final Optional<Advertisement> advertisement = advertisementRepository.findById(id);

        if (advertisement.isPresent()) {
            final Advertisement adToUpdate = advertisement.get();
            adToUpdate.setAdName(dto.getAdName());
            adToUpdate.setAdDescription(dto.getAdDescription());
            adToUpdate.setPrice(dto.getPrice());
            adToUpdate.setCity(dto.getCity());
            advertisementRepository.save(adToUpdate);

            log.info("ad with id {} has been updated", adToUpdate.getId());
            return new AdvertisementResponse(adToUpdate, HttpStatus.OK);
        }

        log.info("update failed, ad with id {} was not found", id);
        return new AdvertisementResponse(new Advertisement(), HttpStatus.NOT_FOUND);
    }


    @Transactional
    public HttpStatus deleteAdByID(UUID id) {
        final Optional<Advertisement> advertisement = advertisementRepository.findById(id);

        if (advertisement.isPresent()) {
            final Advertisement adToDelete = advertisement.get();

            for (AdvertisementCategory category : adToDelete.getCategories()) {
                category.getAdvertisements().remove(adToDelete);
                advertisementCategoryRepository.save(category);
            }
            adToDelete.getCategories().clear();
            advertisementRepository.save(adToDelete);

            advertisementRepository.delete(adToDelete);
            log.info("ad with id {} has been deleted", id);
            return HttpStatus.NO_CONTENT;
        }

        log.info("deletion failed, ad with id {} was not found", id);
        return HttpStatus.NOT_FOUND;
    }
}