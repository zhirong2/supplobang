package supplobang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import supplobang.entities.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
