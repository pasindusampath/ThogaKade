package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import service.CustomerService;
import to.CustomerTo;

import java.util.Optional;

public class ManageCustomerFormController {

    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerContact;
    public TableView<CustomerTo> tblCustomer;
    public TableColumn<CustomerTo,String> colCustomerName;
    public TableColumn<CustomerTo,String> colCustomerAddress;
    public TableColumn<CustomerTo,String> colMobileNo;
    public Label lblMain;

    CustomerService service = new CustomerService();

    public void initialize(){
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMobileNo.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        setTable();
    }

    public void btnAddCustomerOnAction(ActionEvent actionEvent) {
        CustomerTo customer = collectData();
        boolean b = service.addCustomer(customer);
        if(b){
            new Alert(Alert.AlertType.INFORMATION,"Customer Added").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Customer Adding Failed").show();
        }
    }

    public void btnUpdateCustomerOnAction(ActionEvent actionEvent) {
        boolean b = service.updateCustomer(collectData());
        if(b){
            new Alert(Alert.AlertType.INFORMATION,"Update Success").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Update Failed").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        CustomerTo customerTo = service.searchItem(txtCustomerId.getText());
        if(customerTo==null){
            new Alert(Alert.AlertType.ERROR,"Item Not Found").show();
            return;
        }
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.WARNING, "Do You Want To Delete : " + customerTo.getId() + " : " + customerTo.getName() +
                " From The System", ButtonType.YES, ButtonType.NO).showAndWait();
        if(buttonType.get().getText().equalsIgnoreCase("no")){
            new Alert(Alert.AlertType.INFORMATION,"Item Not Deleted").show();
            return;
        }
        if(service.deleteCustomer(customerTo)){
            new Alert(Alert.AlertType.INFORMATION,"Item Deleted Success").show();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        txtCustomerId.clear();
        txtCustomerName.clear();
        txtCustomerContact.clear();
        txtCustomerAddress.clear();
    }

    public void btnCustomerIdRefreshOnAction(ActionEvent actionEvent) {
        txtCustomerId.setText(service.getNewId());
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        CustomerTo customerTo = service.searchItem(txtCustomerId.getText());
        if(customerTo==null){
            new Alert(Alert.AlertType.ERROR,"Customer Not Found").show();
            return;
        }
        setData(customerTo);
    }

    public void setTable(){
        Thread t1 = new Thread(){
          public void run(){
              ObservableList<CustomerTo> customers = FXCollections.observableArrayList(service.getAll());
              tblCustomer.setItems(customers);
          }
        };
        t1.start();


    }

    public CustomerTo collectData(){
        return new CustomerTo(txtCustomerId.getText(),txtCustomerName.getText(),txtCustomerContact.getText(),txtCustomerAddress.getText());
    }

    public void setData(CustomerTo customer){
        txtCustomerId.setText(customer.getId());
        txtCustomerName.setText(customer.getName());
        txtCustomerContact.setText(customer.getContact());
        txtCustomerAddress.setText(customer.getAddress());
    }

    public void tblCustomersOnMouseClickAction(MouseEvent mouseEvent) {
        if(tblCustomer.getSelectionModel().getSelectedIndex()==-1)return;
        setData(tblCustomer.getSelectionModel().getSelectedItem());
    }
}
