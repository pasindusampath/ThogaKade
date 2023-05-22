package repo.custom;

import entity.Item;
import org.hibernate.Session;
import repo.SuperRepo;

import java.util.List;

public interface ItemRepo extends SuperRepo<Item,String> {
    String generateNewId(Session session) throws Exception;
    boolean update(List<Item> obj, Session session) throws Exception;

}
