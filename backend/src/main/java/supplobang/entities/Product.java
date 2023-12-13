package supplobang.entities;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.Data;
import supplobang.dto.FlavourDto;
import supplobang.dto.ProductDto;

@Entity
@Data
@Table(name = "product")
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
    
    @Column(unique = true)
    private String productName;

    private String description;

    @Min(value = 0)
    private int totalQuantity;

    @JsonIgnore
    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Flavour> flavours;

    public ProductDto getProductDto(){
        ProductDto productDto = new ProductDto();
        productDto.setId(id);
        productDto.setCategory(category.getCategoryName());
        productDto.setBrand(brand.getBrandName());
        productDto.setProductName(productName);
        productDto.setDescription(description);
        productDto.setTotalQuantity(totalQuantity);
        List<FlavourDto> flavourDtos = flavours.stream().map(flavours -> flavours.getFlavourDto()).collect(Collectors.toList());
        productDto.setFlavourDtos(flavourDtos);
        
        return productDto;
    }
}
