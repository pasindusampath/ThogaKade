package repo.custom.impl;

import entity.Item;
import entity.OrderItem;
import entity.Orders;
import org.hibernate.Session;
import org.hibernate.query.Query;
import repo.custom.OrderItemRepo;

import java.util.List;

public class OrderItemRepoImpl implements OrderItemRepo {
    @Override
    public OrderItem add(OrderItem obj, Session session) throws Exception {
        //System.out.println("repo inside");
        session.save(obj);
        return obj;
    }

    @Override
    public OrderItem update(OrderItem obj, Session session) throws Exception {
        session.update(obj);
        return obj;
    }

    @Override
    public boolean delete(OrderItem obj, Session session) throws Exception {
        session.delete(obj);
        return true;
    }

    @Override
    public OrderItem search(String s, Session session) throws Exception {
        return session.get(OrderItem.class,s);
    }

    @Override
    public List<OrderItem> getAll(Session session) throws Exception {
        return session.createCriteria(OrderItem.class).list();
    }

    public OrderItem merge(OrderItem obj, Session session) throws Exception {
        //obj.setId((int) session.save(obj));
        return obj;
    }

    @Override
    public boolean add(List<OrderItem> obj, Session session) throws Exception {
        for (OrderItem ob : obj){
            if(add(ob,session)==null){
                return false;
            }
        }
        return true;
    }

    public List<OrderItem> get( Orders order,Session session){
        Query<OrderItem> query = session.createQuery("FROM OrderItem WHERE OrderItem.orders.id='"+order.getId()+"'", OrderItem.class);
        System.out.println(query);
        return query.list();
    }
}
