package com.example.demo2;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.demo2.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderController {

    @FXML private TextField customerNameField, phoneField, addressField, nidField;
    @FXML private ComboBox<String> paymentMethodBox;
    @FXML private TextField productNameField, typeField, colorField, quantityField;
    @FXML private Label statusLabel;

    @FXML
    private void handleSubmitOrder() {
        String cname = customerNameField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();
        String nid = nidField.getText().trim();
        String payment = paymentMethodBox.getValue();

        String pname = productNameField.getText().trim();
        String type = typeField.getText().trim();
        String color = colorField.getText().trim();
        int quantity;

        try {
            quantity = Integer.parseInt(quantityField.getText().trim());
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid quantity.");
            return;
        }

        if (cname.isEmpty() || phone.isEmpty() || address.isEmpty() || nid.isEmpty() ||
                payment == null || pname.isEmpty() || type.isEmpty() || color.isEmpty()) {
            statusLabel.setText("Please fill in all fields.");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();

            // Check if product exists and has enough stock
            PreparedStatement psCheck = conn.prepareStatement(
                    "SELECT id, quantity FROM products WHERE name = ? AND type = ? AND color = ?"
            );
            psCheck.setString(1, pname);
            psCheck.setString(2, type);
            psCheck.setString(3, color);

            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                int productId = rs.getInt("id");
                int currentStock = rs.getInt("quantity");

                if (currentStock < quantity) {
                    statusLabel.setText("Not enough stock!");
                    return;
                }

                // Insert customer
                PreparedStatement insertCustomer = conn.prepareStatement(
                        "INSERT INTO customers (name, phone, address, nid) VALUES (?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                insertCustomer.setString(1, cname);
                insertCustomer.setString(2, phone);
                insertCustomer.setString(3, address);
                insertCustomer.setString(4, nid);
                insertCustomer.executeUpdate();

                ResultSet custKeys = insertCustomer.getGeneratedKeys();
                int customerId = custKeys.next() ? custKeys.getInt(1) : -1;

                // Insert order
                String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                PreparedStatement insertOrder = conn.prepareStatement(
                        "INSERT INTO orders (customer_id, product_id, quantity, payment_method, order_time) VALUES (?, ?, ?, ?, ?)"
                );
                insertOrder.setInt(1, customerId);
                insertOrder.setInt(2, productId);
                insertOrder.setInt(3, quantity);
                insertOrder.setString(4, payment);
                insertOrder.setString(5, time);
                insertOrder.executeUpdate();

                // Update stock
                PreparedStatement updateStock = conn.prepareStatement(
                        "UPDATE products SET quantity = quantity - ? WHERE id = ?"
                );
                updateStock.setInt(1, quantity);
                updateStock.setInt(2, productId);
                updateStock.executeUpdate();

                statusLabel.setText("Order submitted successfully!");

            } else {
                statusLabel.setText("Product not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Error: " + e.getMessage());
        }
    }
}

