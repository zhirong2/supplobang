package supplobang.services;

import java.util.List;

import supplobang.dto.FlavourDto;
import supplobang.entities.Flavour;
import supplobang.entities.Product;

public interface FlavourService {
    List<Flavour> createFlavours(List<FlavourDto> flavourDtos, Product product);
    List<Flavour> updateFlavours(List<FlavourDto> flavourDtos, Product product);
}
