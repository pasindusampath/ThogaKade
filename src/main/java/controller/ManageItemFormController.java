package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import service.ItemService;
import to.ItemTO;

import java.util.Optional;

public class ManageItemFormController {

    public JFXTextField txtItemCode;
    public JFXTextField txtItemName;
    public JFXTextField txtPrice;
    public JFXTextField txtQty;
    public TableColumn<ItemTO,String> colDescription;
    public TableColumn<ItemTO,String> colAvailableQty;
    public TableColumn<ItemTO,String> colPrice;
    public TableView<ItemTO> tblItem;

    private ItemService itemService;

    public void initialize(){
        itemService=new ItemService();
        colDescription.setCellValueFactory(new PropertyValueFactory<ItemTO,String>("description"));
        colAvailableQty.setCellValueFactory(new PropertyValueFactory<ItemTO,String>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<ItemTO,String>("price"));
        setTable();
    }
    public void setTable(){
        Thread t1 = new Thread(){
            public void run(){
                try {
                    tblItem.setItems(FXCollections.observableArrayList(itemService.getAll()));
                    setItemCode();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();
    }

    public void setItemCode(){
        try {
            txtItemCode.setText(itemService.getNewId());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Failed").show();
            e.printStackTrace();
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        ItemTO itemTO = collectData();
        boolean b = false;
        try {
            b = itemService.addItem(itemTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(b){
            new Alert(Alert.AlertType.INFORMATION,"Item Added").show();
            setItemCode();
        }else {
            new Alert(Alert.AlertType.ERROR,"Failed").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        ItemTO item = collectData();
        try {
            boolean b = itemService.updateItem(item);
            if(b){
                new Alert(Alert.AlertType.INFORMATION,"Update Success").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION,"Update Success").show();
            e.printStackTrace();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
            ItemTO item = itemService.searchItem(txtItemCode.getText());
            if(item!=null){
                Optional<ButtonType> buttonType = new Alert(Alert.AlertType.WARNING, "Do You Want To Delete " + item.getItemCode() + " : " +
                        item.getDescription(), ButtonType.YES, ButtonType.NO).showAndWait();
                if(buttonType.get().getText().equalsIgnoreCase("yes")) {
                    boolean b = itemService.deleteItem(item);
                    if(b){
                        new Alert(Alert.AlertType.INFORMATION,"Deleted Success").show();
                    }
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Failed").show();
            e.printStackTrace();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        txtPrice.clear();
        txtQty.clear();
        txtItemName.clear();
        txtItemCode.clear();
    }



    public ItemTO collectData(){
        return new ItemTO(txtItemCode.getText(),txtItemName.getText(),Integer.parseInt(txtQty.getText())
                ,Double.parseDouble(txtPrice.getText()));
    }

    public void setData(ItemTO item){
        txtItemCode.setText(item.getItemCode());
        txtItemName.setText(item.getDescription());
        txtQty.setText(String.valueOf(item.getQty()));
        txtPrice.setText(String.valueOf(item.getPrice()));
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        try {
            setData(itemService.searchItem(txtItemCode.getText()));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Item Not Found").show();
            e.printStackTrace();
        }
    }

    public void btnItemCodeRefreshOnAction(ActionEvent actionEvent) {
        setItemCode();
    }

    public void tblItemsOnMouseClickAction(MouseEvent mouseEvent) {
        if(tblItem.getSelectionModel().getSelectedIndex()==-1)return;
        setData(tblItem.getSelectionModel().getSelectedItem());
    }
}
