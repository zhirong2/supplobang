package supplobang.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import supplobang.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    Optional<Product> findByProductName(String productName);
    void deleteById(long product_id);
    List<Product> findAllByProductNameContaining(String productName);
}
