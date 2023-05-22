package to;



public class OrderItemTO {

    private OrdersTo orders;
    private ItemTO item;
    private int qty;

    public OrderItemTO(OrdersTo orders, ItemTO item, int qty) {

        this.orders = orders;
        this.item = item;
        this.qty = qty;
    }

    public OrdersTo getOrders() {
        return orders;
    }

    public void setOrders(OrdersTo orders) {
        this.orders = orders;
    }

    public ItemTO getItem() {
        return item;
    }

    public void setItem(ItemTO item) {
        this.item = item;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
