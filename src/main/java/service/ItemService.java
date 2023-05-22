package service;

import entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repo.custom.ItemRepo;
import repo.custom.impl.ItemRepoImpl;
import to.ItemTO;
import util.Converter;
import util.FactoryConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ItemService {
    ItemRepo itemRepo = new ItemRepoImpl();

    public boolean addItem(ItemTO item) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Item add = itemRepo.add(Converter.getInstance().toItem(item),session);
            System.out.println(add.toString());
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }finally {
            session.close();
        }
    }

    public ArrayList<ItemTO> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            List<Item> all = itemRepo.getAll(session);
            ArrayList<ItemTO> list = new ArrayList<>();
            for(Item item : all){
                list.add(Converter.getInstance().fromItem(item));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            session.close();
        }
    }

    public boolean updateItem(ItemTO item) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Item item1 = Converter.getInstance().toItem(item);
            Item update = itemRepo.update(item1,session);
            transaction.commit();
            return true;
        }catch (Exception e){
            throw new Exception(e);
        }finally {
            session.close();
        }
    }

    public String getNewId() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return itemRepo.generateNewId(session);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    public ItemTO searchItem(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return Converter.getInstance().fromItem(itemRepo.search(id,session));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    public boolean deleteItem(ItemTO item) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            itemRepo.delete(Converter.getInstance().toItem(item),session);
            transaction.commit();
            return true;
        }catch (Exception e){
            throw new Exception(e);
        }finally {
            session.close();
        }
    }
}
