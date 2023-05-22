package repo.custom;

import entity.OrderItem;
import org.hibernate.Session;
import repo.SuperRepo;

import java.util.List;

public interface OrderItemRepo extends SuperRepo<OrderItem,String > {
    boolean add(List<OrderItem> obj, Session session) throws Exception;
}
