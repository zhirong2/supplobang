package supplobang.services;

import java.util.List;

import supplobang.dto.FlavourDto;
import supplobang.entities.Flavour;
import supplobang.entities.Product;

public interface FlavourService {

    Flavour getFlavourById(Long id);
    Flavour getFlavourByIdOrNull(Long id);

    Flavour createFlavour(FlavourDto flavourDto, Product product);
    List<Flavour> createFlavours(List<FlavourDto> flavourDtos, Product product);
    
    void updateFlavours(List<FlavourDto> flavourDtos, Product product);

}
