package com.example.demo2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class RecordPurchaseController {

    @FXML private ComboBox<Product> productComboBox;
    @FXML private TextField quantityField;
    @FXML private Label statusLabel;

    private ObservableList<Product> productList = FXCollections.observableArrayList();
    private LocalDate expiry;

    @FXML
    private void initialize() {
        loadProducts();

        productComboBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Product item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getName() + " (" + item.getType() + ", " + item.getColor() + ")");
            }
        });

        productComboBox.setButtonCell(productComboBox.getCellFactory().call(null));
    }

    private void loadProducts() {
        try {
            Connection conn = DBConnection.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM products");
            productList.clear();

            while (rs.next()) {
                productList.add(new Product(
                        rs.getInt("id"), rs.getString("name"),
                        rs.getString("type"),
                        rs.getString("color"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"), // Assuming price is also a field in the Product class
                        expiry));
            }

            productComboBox.setItems(productList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRecordPurchase() {
        Product selected = productComboBox.getValue();
        if (selected == null) {
            statusLabel.setText("Please select a product.");
            return;
        }

        int addedQuantity;
        try {
            addedQuantity = Integer.parseInt(quantityField.getText().trim());
            if (addedQuantity <= 0) {
                statusLabel.setText("Quantity must be positive.");
                return;
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid quantity.");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();

            int newQuantity = selected.getQuantity() + addedQuantity;

            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE products SET quantity = ? WHERE name = ? AND type = ? AND color = ?"
            );
            ps.setInt(1, newQuantity);
            ps.setString(2, selected.getName());
            ps.setString(3, selected.getType());
            ps.setString(4, selected.getColor());

            int updated = ps.executeUpdate();
            if (updated > 0) {
                statusLabel.setText("Purchase recorded. Stock updated!");
                loadProducts();  // Refresh
            } else {
                statusLabel.setText("Update failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error: " + e.getMessage());
        }
    }
}
