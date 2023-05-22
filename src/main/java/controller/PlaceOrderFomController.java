package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;
import lombok.SneakyThrows;
import service.CustomerService;
import service.ItemService;
import service.OrderItemService;
import service.OrderService;
import tm.CartTM;
import to.CustomerTo;
import to.ItemTO;
import to.OrderItemTO;
import to.OrdersTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class PlaceOrderFomController {
    public TableView<CartTM> tblCart;
    public TableColumn<CartTM,String> colItemName;
    public TableColumn<CartTM,Double> colPrice;
    public TableColumn<CartTM,Integer> colQty;
    public TableColumn<CartTM,Double> colTotal;
    public JFXComboBox<CustomerTo> cbCustomers;
    public JFXComboBox<ItemTO> cbItems;
    public JFXTextField txtCustName;
    public JFXTextField txtMobileName;
    public JFXTextField txtItemCode;
    public JFXTextField txtAvailableQty;
    public JFXTextField txtQty;
    public JFXTextField txtOrderId;
    public JFXTextField txtItemDescription;
    public JFXTextField txtItemPrice;
    public JFXTextField txtCustomerAddress;

    private CustomerService customerService;
    private ItemService itemService;
    private OrderService orderService;
    private OrderItemService place;

    public void initialize(){
        txtOrderId.setEditable(false);
        txtQty.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtQty.setText(newValue.replaceAll("\\D", "")); // force the text field to be numeric only
            }
        });

        //txtItemCode.setText();

        customerService = new CustomerService();
        itemService=new ItemService();
        orderService = new OrderService();
        place = new OrderItemService();

        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));


        setComboBoxes();
        setOrderId();

    }



    public void setComboBoxes(){
        Thread t1 = new Thread(() -> {
            cbCustomers.setItems(FXCollections.observableArrayList(customerService.getAll()));
            cbCustomers.setConverter(new StringConverter<CustomerTo>() {
                @Override
                public String toString(CustomerTo customer) {
                    return customer.getName();
                }

                @Override
                public CustomerTo fromString(String s) {
                    return null;
                }
            });


            cbItems.setItems(FXCollections.observableArrayList(itemService.getAll()));
            cbItems.setConverter(new StringConverter<ItemTO>() {
                @Override
                public String toString(ItemTO item) {
                    return item.getDescription();
                }

                @Override
                public ItemTO fromString(String s) {
                    return null;
                }
            });
        });
        t1.start();

    }

    public void txtKeyPressedOnAction(KeyEvent keyEvent) {
        /*if(!keyEvent.getCode().isKeypadKey()){

            txtQty.setText(txtQty.getText(0,txtQty.getText().length()-1));
            txtQty.selectRange(txtQty.getText().length(),txtQty.getText().length());

        }*/
    }



    public void btnRemoveFromCartOnAction(ActionEvent actionEvent) {
        CartTM selectedItem = tblCart.getSelectionModel().getSelectedItem();
        ObservableList<ItemTO> items = cbItems.getItems();
        for (ItemTO item : items){
            if(selectedItem.getItemCode().equals(item.getItemCode())){
                item.setQty(item.getQty()+selectedItem.getQty());
                tblCart.getItems().remove(selectedItem);
                tblCart.refresh();

            }
        }
    }



    public void cbCustomersOnAction(ActionEvent actionEvent) {
        CustomerTo selectedItem = cbCustomers.getSelectionModel().getSelectedItem();
        setCustomerData(selectedItem);
    }

    public void setCustomerData(CustomerTo customer){
        txtCustName.setText(customer.getName());
        txtMobileName.setText(customer.getContact());
        txtCustomerAddress.setText(customer.getAddress());
    }

    public void cbItemsOnAction(ActionEvent actionEvent) {
        if(cbItems.getSelectionModel().getSelectedIndex()==-1)return;
        setItemData(cbItems.getSelectionModel().getSelectedItem());
    }

    public void setItemData(ItemTO item){
        txtItemCode.setText(item.getItemCode());
        txtAvailableQty.setText(String.valueOf(item.getQty()));
        txtItemDescription.setText(item.getDescription());
        txtItemPrice.setText(String.valueOf(item.getPrice()));
    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        ItemTO item = cbItems.getSelectionModel().getSelectedItem();
        CustomerTo customer = cbCustomers.getSelectionModel().getSelectedItem();
        int qty = Integer.parseInt(txtQty.getText());
        item.setQty(item.getQty()-qty);
        clearItemData();
        for (CartTM cart : tblCart.getItems()) {
            if(cart.getItemCode().equals(item.getItemCode())){
                cart.setQty(cart.getQty()+qty);
                cart.setTotal(cart.getTotal()+(qty*item.getPrice()));
                tblCart.refresh();
                return;
            }
        }
        CartTM cartTM = new CartTM(item.getItemCode(), item.getDescription(), item.getPrice(), qty, item.getPrice() * qty);
        tblCart.getItems().add(cartTM);
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        ObservableList<CartTM> items = tblCart.getItems();
        ArrayList<OrderItemTO> orderItemTOS = new ArrayList<>();
        OrdersTo orders = new OrdersTo(txtOrderId.getText(), LocalDate.now().toString(),
                cbCustomers.getSelectionModel().getSelectedItem());
        int i =0;
        for(CartTM cart : items){
            ItemTO item = null;
            for(ItemTO ob:cbItems.getItems()){
                if(cart.getItemCode().equals(ob.getItemCode())){
                    item=ob;
                }
            }
            orderItemTOS.add(new OrderItemTO(orders,item,cart.getQty()));
        }
        boolean b = place.placeOrder(orders,orderItemTOS);
        if(b){
            new Alert(Alert.AlertType.INFORMATION,"Order Placed").show();
            tblCart.getItems().clear();
            tblCart.refresh();
            setOrderId();
        }
    }

    public void clearItemData(){
        cbItems.getSelectionModel().select(null);
        txtItemCode.clear();
        txtItemPrice.clear();
        txtItemDescription.clear();
        txtAvailableQty.clear();
        txtQty.clear();
    }

    public void setOrderId(){
        Thread t1 = new Thread(){
            public void run(){
                txtOrderId.setText(orderService.getNewOrderId());
            }
        };
        t1.start();
    }

}
