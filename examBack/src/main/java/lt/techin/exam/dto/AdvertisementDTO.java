package lt.techin.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.techin.exam.entity.AdvertisementCategory;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdvertisementDTO {
    private String adName;
    private String adDescription;
    private double price;
    private String city;
    private List<AdvertisementCategory> categories;
}
