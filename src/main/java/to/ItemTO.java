package to;

import entity.OrderItem;

import java.util.List;

public class ItemTO {
    private String itemCode;
    private String description;
    private int qty;
    private double price;



    public ItemTO(String itemCode, String description, int qty, double price) {
        this.itemCode = itemCode;
        this.description = description;
        this.qty = qty;
        this.price = price;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


}
