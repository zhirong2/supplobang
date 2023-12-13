package supplobang.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;
import supplobang.dto.BrandDto;
import supplobang.exceptions.BrandAlreadyExistException;
import supplobang.exceptions.BrandNotFoundException;
import supplobang.services.BrandService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<List<BrandDto>> getAllbrand(){
        return ResponseEntity.ok(brandService.getAllBrand());
    }

    @PostMapping("/admin")
    public ResponseEntity<?> createBrand(@RequestBody BrandDto brandDto) {
        try {
            return ResponseEntity.ok(brandService.createBrand(brandDto));
        } catch (BrandAlreadyExistException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/admin/{brand_id}")
    public ResponseEntity<?> updatebrand(@PathVariable("brand_id") Long brand_id, @RequestBody BrandDto brandDto) {
        try {
            return ResponseEntity.ok(brandService.updateBrand(brand_id, brandDto));
        } catch (BrandNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/admin/{brand_id}")
    public ResponseEntity<?> deletebrand(@PathVariable("brand_id") Long brand_id) {
        try {
            String brandName = brandService.deleteBrandById(brand_id);
            return ResponseEntity.ok(brandName + " has been deleted.");
        } catch (BrandNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
}
