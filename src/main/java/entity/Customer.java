package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    private String id;
    private String name;
    private String contact;
    private String address;

    @OneToMany(mappedBy = "customer",targetEntity = Orders.class)
    private List<Orders> orders;
}
