package sneaker.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class SneakerStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sneakerStoreId;


    private String sneakerStoreName;
    private String sneakerStoreAddress;
    private String sneakerStoreCity;
    private String sneakerStoreState;
    private String sneakerStoreZip;
    private String sneakerStorePhone;


    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "sneakerStore", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SneakerSize> sneakersize = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "sneaker_store_customer", 
    joinColumns = @JoinColumn(name = "sneaker_store_id"), 
    inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private Set<Customer> customers = new HashSet<>();


	
}