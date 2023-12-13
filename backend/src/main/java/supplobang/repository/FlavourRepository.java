package supplobang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import supplobang.entities.Flavour;

@Repository
public interface FlavourRepository extends JpaRepository<Flavour, Long>{

}
