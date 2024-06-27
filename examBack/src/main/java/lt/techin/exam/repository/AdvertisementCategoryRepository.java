package lt.techin.exam.repository;

import lt.techin.exam.entity.AdvertisementCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdvertisementCategoryRepository extends JpaRepository<AdvertisementCategory, UUID> {

    Optional<AdvertisementCategory> findByCategoryNameIgnoreCase(String name);
    Page<AdvertisementCategory> findByCategoryNameContainingIgnoreCase(Pageable pageable, String name);
}
