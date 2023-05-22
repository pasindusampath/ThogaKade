package repo.custom.impl;

import entity.Customer;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import repo.custom.CustomerRepo;



import java.util.List;

public class CustomerRepoImpl implements CustomerRepo {

    @Override
    public Customer add(Customer obj, Session session) throws Exception {
        String save = (String) session.save(obj);
        obj.setId(save);
        return obj;
    }

    @Override
    public Customer update(Customer obj, Session session) throws Exception {
        session.update(obj);
        return obj;
    }

    @Override
    public boolean delete(Customer s, Session session) throws Exception {
        session.delete(s);
        return true;
    }

    @Override
    public Customer search(String id, Session session) throws Exception {
        return session.get(Customer.class, id);
    }

    @SuppressWarnings("Unchecked")
    @Override
    public List<Customer> getAll(Session session) throws Exception {
        return session.createCriteria(Customer.class).list();
    }

    @Override
    public String getNewId(Session session) throws Exception {
        try {
            String lastId = getLastId(session);
            if (lastId == null) return "C-001";
            String[] split = lastId.split("[C][-]");
            int i = Integer.parseInt(split[1]);
            System.out.println(i);
            return String.format("C-%03d", ++i);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    private String getLastId(Session session) throws Exception {
        try {
            NativeQuery sqlQuery = session.createSQLQuery("select id from Customer ORDER BY id DESC LIMIT 1");
            Object singleResult = sqlQuery.getSingleResult();
            return singleResult.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
