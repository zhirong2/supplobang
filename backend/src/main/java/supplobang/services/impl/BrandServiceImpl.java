package supplobang.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import supplobang.dto.BrandDto;
import supplobang.entities.Brand;
import supplobang.exceptions.BrandAlreadyExistException;
import supplobang.exceptions.BrandNotFoundException;
import supplobang.repository.BrandRepository;
import supplobang.services.BrandService;

@Service
@Transactional
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    public List<BrandDto> getAllBrand() {
        List<Brand> brands = brandRepository.findAll();

        return brands.stream()
                .map(brand -> {
            return brand.convertToBrandDto();
        
        })
        .collect(Collectors.toList());
    }

    @Override
    public Brand createBrand(BrandDto brandDto){
        String brandName = brandDto.getBrandName();

        validateBrandExists(brandName);

        Brand brand = new Brand();
        brand.setBrandName(brandName);
        return brandRepository.save(brand);
    }

    @Override
    public Brand updateBrand(Long brand_id, BrandDto brand){
        String brandName = brand.getBrandName();

        validateBrandExists(brandName);

        Brand exisitingBrand = getBrandById(brand_id);
        exisitingBrand.setBrandName(brandName);
        return brandRepository.save(exisitingBrand);
    }

    @Override
    public String deleteBrandById(Long brand_id){
        Brand exisitingBrand = getBrandById(brand_id);
        String brandName = exisitingBrand.getBrandName();
        brandRepository.deleteById(brand_id);
        return brandName;
    }

    @Override
    public void validateBrandExists(String brandName){
        if(brandRepository.findByBrandName(brandName).isPresent()){
            throw new BrandAlreadyExistException(brandName + " already exists");
        }
    }

    @Override
    public Brand getBrandById(Long id){
        return brandRepository.findById(id)
                .orElseThrow(() -> new BrandNotFoundException("brand with " + id + "is not found"));
    }

    @Override
    public Brand getBrandByName(String brandName){
        return brandRepository.findByBrandName(brandName)
                .orElseThrow(() -> new BrandNotFoundException(brandName + "is not found"));
    }


}
