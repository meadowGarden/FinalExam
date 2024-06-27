package lt.techin.exam.utilities.mapper;

import lt.techin.exam.dto.AdvertisementDTO;
import lt.techin.exam.entity.Advertisement;

public class AdvertisementMapper {

    public static Advertisement DTOToAdvert(AdvertisementDTO dto) {
        return new Advertisement(
                dto.getAdName(),
                dto.getAdDescription(),
                dto.getPrice(),
                dto.getCity(),
                dto.getCategories()
        );
    }
}
