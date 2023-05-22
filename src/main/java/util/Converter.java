package util;

import entity.Customer;
import entity.Item;
import entity.OrderItem;
import entity.Orders;
import org.hibernate.criterion.Order;
import to.CustomerTo;
import to.ItemTO;
import to.OrderItemTO;
import to.OrdersTo;

import java.util.ArrayList;
import java.util.List;

public class Converter {
    private static Converter converter;

    private Converter(){

    }

    public static Converter getInstance(){
        if(converter==null)converter=new Converter();
        return converter;
    }

    public Item toItem(ItemTO itemTO){
        Item item = toOnlyItem(itemTO);
        return item;

    }


    public ItemTO fromItem(Item item){
        ItemTO itemTO = fromOnlyItem(item);
        return itemTO;

    }



    public Customer toCustomer(CustomerTo customer){
        Customer customer1 = toOnlyCustomer(customer);
        return customer1;

    }




    public CustomerTo fromCustomer(Customer customer){
        CustomerTo customerTo = fromOnlyCustomer(customer);
        return customerTo;
    }



    public Orders toOrder(OrdersTo orders){
        Orders orders1 = toOnlyOrder(orders);
        return orders1;
    }



    public OrdersTo fromOrder(Orders orders){
        OrdersTo orders1 = fromOnlyOrder(orders);
        return orders1;
    }





    public OrderItemTO fromOrderItem(OrderItem orderItem){
        OrderItemTO orderItemTO = fromOnlyOrderItem(orderItem);
        return orderItemTO;
    }



    public OrderItem toOrderItem(OrderItemTO orderItem){
        OrderItem orderItem1 = toOnlyOrderItem(orderItem);
        return orderItem1;

    }




    public List<OrderItemTO> fromOrderItem(List<OrderItem> list){
        ArrayList<OrderItemTO> orderItems = new ArrayList<>();
        for(OrderItem ob : list){
            orderItems.add(fromOrderItem(ob));
        }
        return orderItems;
    }

    public List<OrderItem> toOrderItem(List<OrderItemTO> list){
        ArrayList<OrderItem> items = new ArrayList<>();
        for(OrderItemTO ob: list){
            OrderItem orderItem = toOnlyOrderItem(ob);
            Item item = toOnlyItem(ob.getItem());
            Orders orders = toOnlyOrder(ob.getOrders());


            orders.setOrderItems(items);
            orderItem.setItem(item);
            orderItem.setOrders(orders);
            items.add(orderItem);
        }
        return items;
    }

    public List<Orders> toOrdersList(List<OrdersTo> orders){
        ArrayList<Orders> list = new ArrayList<>();
        for(OrdersTo item : orders){
            list.add(toOrder(item));
        }
        return list;
    }

    public List<OrdersTo> fromOrdersList(List<Orders> orders){
        ArrayList<OrdersTo> list = new ArrayList<>();
        for(Orders item : orders){
            list.add(fromOrder(item));
        }
        return list;
    }

    //ijiujioyyygydytr5r6yuy7t7iui8yytftiy7f6tyhy6yiuiftrfyuhbiuhvtcvuhbuyfryhutgfugygtyihguhyuftyhiygyugtyyuy8y8

    public List<Item> orderItemListToItemList(List<OrderItem> orderItems){
        ArrayList<Item> items = new ArrayList<>();
        for(OrderItem ob:orderItems){
            items.add(ob.getItem());
        }
        return items;
    }

    @SuppressWarnings("Dont Touch")
    private Item toOnlyItem(ItemTO itemTO){
        return new Item(itemTO.getItemCode(), itemTO.getDescription(), itemTO.getQty(), itemTO.getPrice(),null);
    }

    @SuppressWarnings("Dont Touch")
    private ItemTO fromOnlyItem(Item item){
        return new ItemTO(item.getItemCode(),item.getDescription(), item.getQty(), item.getPrice());
    }



    @SuppressWarnings("Dont Touch")
    private Customer toOnlyCustomer(CustomerTo customer){
        return new Customer(customer.getId(),customer.getName(), customer.getContact(), customer.getAddress(),null);
    }

    @SuppressWarnings("Dont Touch")
    private CustomerTo fromOnlyCustomer(Customer customer){
        return new CustomerTo(customer.getId(),customer.getName(), customer.getContact(), customer.getAddress());
    }

    @SuppressWarnings("Dont Touch")
    private Orders toOnlyOrder(OrdersTo orders){
        return new Orders(orders.getId(), orders.getDate(), toOnlyCustomer(orders.getCustomer()),null);
    }

    @SuppressWarnings("Dont Touch")
    private OrdersTo fromOnlyOrder(Orders orders){

        return new OrdersTo(orders.getId(),orders.getDate(), fromCustomer(orders.getCustomer()));
    }

    @SuppressWarnings("Dont Touch")
    private OrderItemTO fromOnlyOrderItem(OrderItem orderItem){
        ItemTO item = fromOnlyItem(orderItem.getItem());
        OrdersTo orders = fromOnlyOrder(orderItem.getOrders());
        return new OrderItemTO(orders,item,orderItem.getQty());
    }

    @SuppressWarnings("Dont Touch")
    private OrderItem toOnlyOrderItem(OrderItemTO orderItem){
        return  new OrderItem(null,null,orderItem.getQty());
    }

}
