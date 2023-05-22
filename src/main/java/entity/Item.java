package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Item {
    @Id
    private String itemCode;
    private String description;
    private int qty;
    private double price;


    @OneToMany(mappedBy = "item", targetEntity = OrderItem.class)
    @ToString.Exclude
    private List<OrderItem> orderItems;

}
