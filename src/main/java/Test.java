import entity.OrderItem;
import entity.Orders;
import repo.custom.impl.OrderItemRepoImpl;
import util.FactoryConfiguration;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        OrderItemRepoImpl repo = new OrderItemRepoImpl();
        Orders orders = new Orders();
        orders.setId("O-001");
        List<OrderItem> orderItems = repo.get(orders, FactoryConfiguration.getInstance().getSession());
        System.out.println(orderItems);
    }
}
