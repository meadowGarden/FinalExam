package lt.techin.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.techin.exam.entity.Advertisement;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdvertisementCategoryDTO {
    private UUID id;
    private String categoryName;
    private List<Advertisement> advertisements;
}
