package service;

import entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repo.custom.CustomerRepo;
import repo.custom.impl.CustomerRepoImpl;
import to.CustomerTo;
import util.Converter;
import util.FactoryConfiguration;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    CustomerRepo customerRepo = new CustomerRepoImpl();

    public boolean addCustomer(CustomerTo customer) {
        Session session = FactoryConfiguration.getInstance().getSession();
    Transaction transaction = session.beginTransaction();

        try {
            Customer add = customerRepo.add(Converter.getInstance().toCustomer(customer),session);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            session.close();
        }
    }

    public boolean updateCustomer(CustomerTo customer) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Customer update = customerRepo.update(Converter.getInstance().toCustomer(customer),session);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            session.close();
        }
    }

    public CustomerTo searchItem(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return Converter.getInstance().fromCustomer(customerRepo.search(id,session));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            session.close();
        }
    }

    public boolean deleteCustomer(CustomerTo customer) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            boolean delete = customerRepo.delete(Converter.getInstance().toCustomer(customer), session);
            transaction.commit();
            return delete;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public ArrayList<CustomerTo> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            List<Customer> all = customerRepo.getAll(session);
            ArrayList<CustomerTo> list = new ArrayList<>();
            for (Customer c : all) {
                list.add(Converter.getInstance().fromCustomer(c));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            session.close();
        }
    }

    public String getNewId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return customerRepo.getNewId(session);
        } catch (Exception e) {
            return null;
        }finally {
            session.close();
        }
    }
}
