package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private void handleCreateOrder() {
        loadFXML("/com/example/demo2/order.fxml", "Create Order");
    }

    @FXML
    private void handleViewCustomers() {
        loadFXML("/com/example/demo2/customers.fxml", "Customer List");
    }

    @FXML
    private void handleCheckStock() {
        loadFXML("/com/example/demo2/stock.fxml", "Stock Info");
    }

    /*@FXML
    private void handleAddProduct() {
        loadFXML("/com/example/demo2/add_product.fxml", "Add New Product");
    }

    @FXML
    private void handleEditStock() {
        loadFXML("/com/example/demo2/stock.fxml", "Edit Stock");
    }

    @FXML
    private void handleRecordPurchase() {
        loadFXML("/com/example/demo2/record_purchase.fxml", "Record Purchase");
    }*/

    private void loadFXML(String path, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
