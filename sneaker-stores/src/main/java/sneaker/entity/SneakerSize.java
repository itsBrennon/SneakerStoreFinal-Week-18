package sneaker.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data

public class SneakerSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//indicates the IDs are are auto assigned
    private Long sneakerSizeId;

    private String sneakerSizeBaby;
    private String sneakerSizeKid;
    private String sneakerSizeAdult;
  
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sneaker_store_id", nullable = false)
    private SneakerStore sneakerStore;

}

