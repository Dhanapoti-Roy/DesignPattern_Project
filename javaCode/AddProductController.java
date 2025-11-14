package com.example.demo2;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import com.example.demo2.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddProductController {

    @FXML private TextField idField, nameField, categoryField, buyingPriceField, sellingPriceField, quantityField;
    @FXML private DatePicker addingDatePicker, expireDatePicker;
    @FXML private Label statusLabel;
    @FXML

    private void handleAddProduct() {
        String idText = idField.getText().trim();
        String name = nameField.getText().trim();
        String category = categoryField.getText().trim();
        String buyingPriceText = buyingPriceField.getText().trim();
        String sellingPriceText = sellingPriceField.getText().trim();
        String quantityText = quantityField.getText().trim();
        LocalDate addingDate = addingDatePicker.getValue();
        LocalDate expireDate = expireDatePicker.getValue();

        if (idText.isEmpty() || name.isEmpty() || category.isEmpty() ||
                buyingPriceText.isEmpty() || sellingPriceText.isEmpty() ||
                quantityText.isEmpty() || addingDate == null || expireDate == null) {
            statusLabel.setText("All fields are required.");
            return;
        }

        int id, quantity;
        double buyingPrice, sellingPrice;
        try {
            id = Integer.parseInt(idText);
            buyingPrice = Double.parseDouble(buyingPriceText);
            sellingPrice = Double.parseDouble(sellingPriceText);
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            statusLabel.setText("ID, Quantity, and Prices must be numbers.");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO products (id, name, category, buying_price, selling_price, quantity, adding_date, expiry_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            );
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, category);
            stmt.setDouble(4, buyingPrice);
            stmt.setDouble(5, sellingPrice);
            stmt.setInt(6, quantity);
            stmt.setString(7, addingDate.toString());
            stmt.setString(8, expireDate.toString());
            stmt.executeUpdate();

            statusLabel.setText("Product added successfully!");

            idField.clear();
            nameField.clear();
            categoryField.clear();
            buyingPriceField.clear();
            sellingPriceField.clear();
            quantityField.clear();
            addingDatePicker.setValue(null);
            expireDatePicker.setValue(null);

        } catch (Exception e) {

            System.err.println("Exception occurred while adding product: " + e.getMessage());

            statusLabel.setText("Error: " + e.getMessage());
        }
    }
}
