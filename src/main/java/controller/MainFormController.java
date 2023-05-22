package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import util.Navigation;
import util.Route;

import java.io.IOException;

public class MainFormController {
    public AnchorPane navigationalContext;

    @FXML
    public void btnHomeOnAction(ActionEvent actionEvent) {
        navigate(Route.DASHBOARD);
    }

    @FXML
    public void btnCustomerOnAction(ActionEvent actionEvent) {
        navigate(Route.CUSTOMER);
        /*navigationalContext.getChildren().clear();
        try {
            Parent load = FXMLLoader.load(getClass().getResource("/view/ManageCustomerForm.fxml"));
            navigationalContext.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @FXML
    public void btnStockOnAction(ActionEvent actionEvent) {
        navigate(Route.ITEM);
        /*navigationalContext.getChildren().clear();
        try {
            Parent load = FXMLLoader.load(getClass().getResource("/view/ManageItemForm.fxml"));
            navigationalContext.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @FXML
    public void btnBillingOnAction(ActionEvent actionEvent) {
        navigate(Route.PLACEORDER);
        /*navigationalContext.getChildren().clear();
        try {
            Parent load = FXMLLoader.load(getClass().getResource("/view/PlaceOrderForm.fxml"));
            navigationalContext.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private void navigate(Route route){
        try {
            Navigation.navigate(route,navigationalContext);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
