package supplobang.controller.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;
import supplobang.dto.BrandDto;
import supplobang.exceptions.BrandNotFoundException;
import supplobang.services.BrandService;

import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("brands")
@RequiredArgsConstructor
public class UserBrandController {
    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<List<BrandDto>> getAllbrand(){
        return ResponseEntity.ok(brandService.getAllBrand());
    }

    @GetMapping("{brandName}/products")
    public ResponseEntity<?> getAllProductByBrand(@PathVariable("brandName") String brandName) {
        try{
            return ResponseEntity.ok(brandService.getAllProductByBrand(brandName));
        } catch (BrandNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    
}
