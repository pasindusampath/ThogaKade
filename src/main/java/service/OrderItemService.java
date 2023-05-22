package service;

import entity.Item;
import entity.OrderItem;
import entity.Orders;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repo.custom.ItemRepo;
import repo.custom.OrderItemRepo;
import repo.custom.OrderRepo;
import repo.custom.impl.ItemRepoImpl;
import repo.custom.impl.OrderItemRepoImpl;
import repo.custom.impl.OrderRepoImpl;
import to.OrderItemTO;
import to.OrdersTo;
import util.Converter;
import util.FactoryConfiguration;

import java.util.ArrayList;
import java.util.List;

public class OrderItemService {
    OrderRepo orderRepo = new OrderRepoImpl();
    OrderItemRepo repo = new OrderItemRepoImpl();
    ItemRepo itemRepo = new ItemRepoImpl();

    public boolean placeOrder(OrdersTo orders, ArrayList<OrderItemTO> list){
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Orders orders1 = Converter.getInstance().toOrder(orders);
            List<OrderItem> orderItems = Converter.getInstance().toOrderItem(list);
            List<Item> items = Converter.getInstance().orderItemListToItemList(orderItems);

            orders1.setOrderItems(orderItems);
            Orders add1 = orderRepo.add(orders1, session);
            if(add1!=null){
                boolean add = repo.add(orderItems, session);
                if(add){
                    boolean update = itemRepo.update(items, session);
                    if(update){
                        transaction.commit();
                        return true;
                    }
                }
            }
            transaction.rollback();
            return false;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }finally {
            session.close();
        }
    }
}
