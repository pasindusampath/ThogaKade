package to;

import entity.Customer;
import entity.OrderItem;

import java.util.List;

public class OrdersTo {
    private String id;
    private String date;
    private CustomerTo customer;

    public OrdersTo(String id, String date, CustomerTo customer) {
        this.id = id;
        this.date = date;
        this.customer = customer;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public CustomerTo getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerTo customer) {
        this.customer = customer;
    }

}
