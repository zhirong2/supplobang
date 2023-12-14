package supplobang.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "order")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String orderDescription;

    private Date date;

    private double totalAmount;

    private String payment;

    private double finalAmount;

    // private UUID trackindId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
