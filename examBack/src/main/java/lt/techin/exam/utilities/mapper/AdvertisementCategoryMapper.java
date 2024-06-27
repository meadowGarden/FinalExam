package lt.techin.exam.utilities.mapper;

import lt.techin.exam.dto.AdvertisementCategoryDTO;
import lt.techin.exam.entity.AdvertisementCategory;

public class AdvertisementCategoryMapper {

    public static AdvertisementCategory DTOToAdvertCategory(AdvertisementCategoryDTO dto) {
        return new AdvertisementCategory(
                dto.getCategoryName(),
                dto.getAdvertisements()
        );
    }
}
