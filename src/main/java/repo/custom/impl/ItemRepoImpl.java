package repo.custom.impl;

import entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repo.custom.ItemRepo;
import util.FactoryConfiguration;

import java.util.List;

public class ItemRepoImpl implements ItemRepo {


    @Override
    public Item add(Item obj, Session session) throws Exception {
        String save = (String) session.save(obj);
        obj.setItemCode(save);
        return obj;
    }

    @Override
    public Item update(Item obj, Session session) throws Exception {
        session.update(obj);
        return obj;
    }

    @Override
    public boolean delete(Item s, Session session) throws Exception {
        session.delete(s);
        return true;
    }

    @Override
    public Item search(String s, Session session) throws Exception {
        try {
            Item item = session.get(Item.class, s);
            return item;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    @SuppressWarnings("Unchecked")
    public List<Item> getAll(Session session) throws Exception {
        return session.createCriteria(Item.class).list();
    }

    @Override
    public String generateNewId(Session session) throws Exception {
        String lastId = getLastId(session);
        String[] split = lastId.split("[I][T][-]");
        int i = Integer.parseInt(split[1]);
        return String.format("IT-%03d", ++i);
    }

    @Override
    public boolean update(List<Item> obj, Session session) throws Exception {
        for(Item item:obj){
            if(update(item,session)==null)return false;
        }
        return true;
    }


    public String getLastId(Session session) {
        Query query = session.createNativeQuery("select itemCode from Item ORDER BY itemCode DESC LIMIT  1");
        Object singleResult = query.getSingleResult();
        return singleResult.toString();
    }
}
