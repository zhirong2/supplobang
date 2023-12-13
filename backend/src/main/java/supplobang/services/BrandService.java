package supplobang.services;

import java.util.List;

import supplobang.dto.BrandDto;
import supplobang.entities.Brand;

public interface BrandService {
    List<BrandDto> getAllBrand();
    Brand getBrandById(Long id);
    Brand getBrandByName(String brandName);
    Brand createBrand(BrandDto brandDto);
    Brand updateBrand(Long brand_id, BrandDto brand);
    String deleteBrandById(Long brand_id);
    void validateBrandExists(String brandName);
}
