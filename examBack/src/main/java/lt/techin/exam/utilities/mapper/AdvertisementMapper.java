package lt.techin.exam.utilities.mapper;

import lt.techin.exam.dto.AdvertisementDTO;
import lt.techin.exam.entity.Advertisement;

public class AdvertisementMapper {

    public static Advertisement DTOToAdvert(AdvertisementDTO dto) {
        return Advertisement.builder()
                .adName(dto.getAdName())
                .adDescription(dto.getAdDescription())
                .price(dto.getPrice())
                .city(dto.getCity())
                .build();
    }
}
