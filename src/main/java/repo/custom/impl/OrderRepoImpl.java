package repo.custom.impl;

import entity.Orders;
import org.hibernate.Session;
import repo.custom.OrderRepo;

import java.util.List;

public class OrderRepoImpl implements OrderRepo {

    @Override
    public Orders add(Orders obj, Session session) throws Exception {
        obj.setId((String) session.save(obj));
        return obj;
    }

    @Override
    public Orders update(Orders obj, Session session) throws Exception {
        session.update(obj);
        return obj;
    }

    @Override
    public boolean delete(Orders obj, Session session) throws Exception {
        session.delete(obj);
        return true;
    }

    @Override
    public Orders search(String s, Session session) throws Exception {
        return session.get(Orders.class,s);
    }

    @Override
    public List<Orders> getAll(Session session) throws Exception {
        return session.createCriteria(Orders.class).list();
    }

    @Override
    public String getLastId(Session session) throws Exception{
        return session.createSQLQuery("SELECT id from orders order by id desc limit 1").getSingleResult().toString();
    }
}
