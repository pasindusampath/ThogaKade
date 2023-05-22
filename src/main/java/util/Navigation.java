package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {

    public static void navigate(Route route, AnchorPane pane) throws IOException {
        switch (route){
            case ITEM:setStage("ManageItem",pane);break;
            case CUSTOMER:setStage("ManageCustomer",pane);break;
            case PLACEORDER:setStage("PlaceOrder",pane);break;
            case DASHBOARD:setStage("DashBoard",pane);break;
        }
    }


    private static void setStage(String url, AnchorPane pane) throws IOException {
        String u = "/view/"+url+"Form.fxml";
        Parent load = FXMLLoader.load(Navigation.class.getResource(u));
        pane.getChildren().clear();
        pane.getChildren().add(load);
    }
}
