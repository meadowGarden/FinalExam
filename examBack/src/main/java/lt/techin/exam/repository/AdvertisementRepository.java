package lt.techin.exam.repository;

import lt.techin.exam.entity.Advertisement;
import lt.techin.exam.entity.AdvertisementCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, UUID> {

    Page<Advertisement> findByAdNameContainingIgnoreCaseOrCategoriesCategoryNameContainingIgnoreCase(
            Pageable pageable, String bookName, String bookCategory);

}
