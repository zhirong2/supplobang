package supplobang.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import supplobang.dto.FlavourDto;
import supplobang.entities.Flavour;
import supplobang.entities.Product;
import supplobang.repository.FlavourRepository;
import supplobang.services.FlavourService;

@Service
@Transactional
@RequiredArgsConstructor
public class FlavourServiceImpl implements FlavourService{
    private final FlavourRepository flavourRepository;

    //create and save new flavours 
    private Flavour createFlavour(FlavourDto flavourDto, Product product){
        Flavour flavour = new Flavour();
        flavour.setFlavourName(flavourDto.getFlavourName());
        flavour.setFlavourQuantity(flavourDto.getFlavourQuantity());
        flavour.setPrice(flavourDto.getPrice());
        flavour.setProduct(product);
        return flavourRepository.save(flavour);
    }

    public List<Flavour> createFlavours(List<FlavourDto> flavourDtos, Product product){
        return flavourDtos.stream()
                .map(flavourDto -> {
                    return createFlavour(flavourDto, product);
                })
                .collect(Collectors.toList());
    }

    public List<Flavour> updateFlavours(List<FlavourDto> flavourDtos, Product product) {
        List<Flavour> existingFlavours = product.getFlavours();
        List<Flavour> updatedFlavours = new ArrayList<>();
    
        // Add New Flavors and Update Existing Flavors
        for (FlavourDto flavourDto : flavourDtos) {
            Flavour existingFlavour = getExistingFlavourById(existingFlavours, flavourDto.getId());
    
            if (existingFlavour == null) {
                // Flavor doesn't exist, create and add a new one
                updatedFlavours.add(createFlavour(flavourDto, product));
            } else {
                // Flavor exists, update its details
                existingFlavour.setFlavourName(flavourDto.getFlavourName());
                existingFlavour.setFlavourQuantity(flavourDto.getFlavourQuantity());
                existingFlavour.setPrice(flavourDto.getPrice());
                updatedFlavours.add(flavourRepository.save(existingFlavour));
            }
        }
    
        // Delete Removed Flavors
        List<Flavour> flavoursToDelete = existingFlavours.stream()
                .filter(flavour -> flavourDtos.stream().noneMatch(dto -> dto.getId() == flavour.getId()))
                .collect(Collectors.toList());
    
        flavourRepository.deleteAll(flavoursToDelete);
        
        // Return the updated list of flavours
        return updatedFlavours;
    }
    
    //check if flavour exist in the product
    private Flavour getExistingFlavourById(List<Flavour> existingFlavours, Long flavour_id){
        return existingFlavours.stream()
                .filter(flavour -> flavour.getId() == flavour_id)
                .findFirst()
                .orElse(null);
    }
}
