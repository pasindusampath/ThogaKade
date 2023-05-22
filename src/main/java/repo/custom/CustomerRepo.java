package repo.custom;

import entity.Customer;
import org.hibernate.Session;
import repo.SuperRepo;

public interface CustomerRepo extends SuperRepo<Customer,String> {
     String getNewId(Session session) throws Exception;
}
