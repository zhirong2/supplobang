package supplobang.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import supplobang.entities.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Long>{
    Optional<Brand> findByBrandName(String brand);
}
