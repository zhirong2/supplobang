package supplobang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import supplobang.entities.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
