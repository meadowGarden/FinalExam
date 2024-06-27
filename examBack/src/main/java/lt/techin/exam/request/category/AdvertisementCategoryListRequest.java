package lt.techin.exam.request.category;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdvertisementCategoryListRequest {
    private int pageNumber;
    private int pageSize;
    private String contains;
    private String SortBy;
    private boolean SortAsc;
}
