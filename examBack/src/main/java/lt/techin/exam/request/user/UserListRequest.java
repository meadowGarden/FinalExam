package lt.techin.exam.request.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserListRequest {
    private int pageNumber;
    private int pageSize;
    private String contains;
    private String SortBy;
    private boolean SortAsc;

    public UserListRequest() {
    }

    public UserListRequest(int pageNumber, int pageSize, String contains, String sortBy, boolean sortAsc) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.contains = contains;
        SortBy = sortBy;
        SortAsc = sortAsc;
    }
}
