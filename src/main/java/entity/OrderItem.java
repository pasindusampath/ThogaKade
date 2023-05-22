package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderItem implements Serializable {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    protected Orders orders;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_code")
    protected Item item;

    private int qty;


}
