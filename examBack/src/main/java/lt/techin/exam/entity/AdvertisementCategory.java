package lt.techin.exam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "advert_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementCategory {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "category_name")
    private String categoryName;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "advert_categories",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "advert_id")
    )
    @JsonIgnore
    private List<Advertisement> advertisements;

    public AdvertisementCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public AdvertisementCategory(String categoryName, List<Advertisement> books) {
        this.categoryName = categoryName;
        this.advertisements = books;
    }

    public void addBook(Advertisement book) {
        advertisements.add(book);
        book.getCategories().add(this);
    }

    public void removeBook(Advertisement book) {
        advertisements.remove(book);
        book.getCategories().remove(this);
    }
}
