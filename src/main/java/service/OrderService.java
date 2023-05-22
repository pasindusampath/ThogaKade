package service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import repo.custom.OrderRepo;
import repo.custom.impl.OrderRepoImpl;
import to.OrdersTo;
import util.Converter;
import util.FactoryConfiguration;

public class OrderService {
    OrderRepo orderRepo=new OrderRepoImpl();

    public OrdersTo addOrder(OrdersTo orders){
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            return Converter.getInstance().fromOrder(orderRepo.add(Converter.getInstance().toOrder(orders),session ));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteOrder(OrdersTo orders){
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            return orderRepo.delete(Converter.getInstance().toOrder(orders),session );
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public OrdersTo updateOrder(OrdersTo orders){
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            return Converter.getInstance().fromOrder(orderRepo.update(Converter.getInstance().toOrder(orders),session ));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String getNewOrderId(){
        Session session = FactoryConfiguration.getInstance().getSession();
        try{
            String lastId = getLastId(session);
            if(lastId==null) return "O-001";
            String[] split = lastId.split("[O][-]");
            int i = Integer.parseInt(split[1]);
            System.out.println(i);
            return String.format("O-%03d",++i);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            session.close();
        }
    }

    private String getLastId(Session session){
        try {
            return orderRepo.getLastId(session);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
