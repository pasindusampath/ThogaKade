package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Orders {
    @Id
    private String id;
    private String date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customerId")
    @ToString.Exclude
    private Customer customer;

    @OneToMany(mappedBy = "orders",targetEntity = OrderItem.class)
    @ToString.Exclude
    private List<OrderItem> orderItems;
}
