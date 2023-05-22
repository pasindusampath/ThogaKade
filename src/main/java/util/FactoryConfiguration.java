package util;



import entity.Customer;
import entity.Item;
import entity.OrderItem;
import entity.Orders;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;

    private SessionFactory sessionFactory;

    private FactoryConfiguration(){
        Configuration configuration = new Configuration().configure().addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Item.class).addAnnotatedClass(Orders.class)
                .addAnnotatedClass(OrderItem.class);
        sessionFactory = configuration.buildSessionFactory();
    }

    public static FactoryConfiguration getInstance(){
        if(factoryConfiguration==null)factoryConfiguration=new FactoryConfiguration();
        return factoryConfiguration;
    }

    public Session getSession(){
        return sessionFactory.openSession();
    }


}
