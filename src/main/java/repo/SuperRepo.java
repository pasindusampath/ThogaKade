package repo;

import org.hibernate.Session;
import util.FactoryConfiguration;

import java.util.List;

public interface SuperRepo<T,ID> {
    T add(T obj,Session session) throws Exception;
    T update(T obj,Session session) throws Exception;
    boolean delete(T obj,Session session) throws Exception;
    T search(ID id,Session session) throws Exception;
    List<T> getAll(Session session) throws Exception;
}
