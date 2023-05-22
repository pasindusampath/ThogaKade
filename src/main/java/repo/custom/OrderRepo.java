package repo.custom;

import entity.Orders;
import org.hibernate.Session;
import repo.SuperRepo;

public interface OrderRepo extends SuperRepo<Orders , String> {
    public String getLastId(Session session) throws Exception;
}
