package supplobang.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import supplobang.dto.FlavourDto;

@Entity
@Table(name = "flavour")
@Data
public class Flavour {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String flavourName;
    private double price;
    private int flavourQuantity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public FlavourDto getFlavourDto(){
        FlavourDto flavourDto = new FlavourDto();
        
        flavourDto.setId(id);
        flavourDto.setFlavourName(flavourName);
        flavourDto.setPrice(price);
        flavourDto.setFlavourQuantity(flavourQuantity);
        flavourDto.setProductName(product.getProductName());

        return flavourDto;
    }

}