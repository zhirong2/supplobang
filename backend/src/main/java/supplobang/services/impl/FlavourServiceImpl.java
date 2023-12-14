package supplobang.services.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import supplobang.repository.FlavourRepository;
import supplobang.services.FlavourService;
import supplobang.dto.FlavourDto;
import supplobang.entities.Flavour;
import supplobang.entities.Product;
import supplobang.exceptions.FlavourNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class FlavourServiceImpl implements FlavourService{
    private final FlavourRepository flavourRepository;

    @Override
    public Flavour getFlavourById(Long id){
        return flavourRepository.findById(id)
                .orElseThrow(() -> new FlavourNotFoundException("Flavour with id: " + id + " is not found"));
    }

    @Override
    public Flavour getFlavourByIdOrNull(Long id){
        return flavourRepository.findById(id)
                .orElse(null);
    }
    //create and save new flavours 
    public Flavour createFlavour(FlavourDto flavourDto, Product product){
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

    public void updateFlavours(List<FlavourDto> updatedFlavourDtos, Product product){
        List<Flavour> existingFlavours = product.getFlavours();

        //add new flavours or update existing flavour
        for(FlavourDto flavourDto : updatedFlavourDtos){

            Flavour existingFlavour = getExistingFlavour(flavourDto.getId(), existingFlavours);

            if(existingFlavour == null){
                Flavour newFlavour = createFlavour(flavourDto, product);
                existingFlavours.add(newFlavour);
            }else{
                existingFlavour.setFlavourName(flavourDto.getFlavourName());
                existingFlavour.setFlavourQuantity(flavourDto.getFlavourQuantity());
                existingFlavour.setPrice(flavourDto.getPrice());
            }
        }

        existingFlavours.removeIf(flavour -> updatedFlavourDtos.stream().noneMatch(dto -> dto.getId() == flavour.getId()));

    }
    
    private Flavour getExistingFlavour(Long flavour_id, List<Flavour> existingFlavours) {
        return existingFlavours.stream()
                .filter(flavour -> flavour.getId() == flavour_id)
                .findFirst()
                .orElse(null);
    }
    
}
