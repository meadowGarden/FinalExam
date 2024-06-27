package lt.techin.exam.request.advertisement;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdvertisementListRequest {
    private int pageNumber;
    private int pageSize;
    private String nameContains;
    private String categoryContains;
    private String SortBy;
    private boolean SortAsc;
}
