package com.example.demo2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class AdminController {

    @FXML
    private void handleViewCustomers() {
        openPage("/com/example/demo2/customers.fxml", "Customer List");
    }

    @FXML
    private void handleCheckStock() {
        openPage("/com/example/demo2/stock.fxml", "Stock Info");
    }

    @FXML
    private void handleAddProduct() {
        openPage("/com/example/demo2/add_product.fxml", "Add Product");
    }

    @FXML
    private void handleEditStock() {
        openPage("/com/example/demo2/stock.fxml", "Edit Stock");
    }

    @FXML
    private void handleRecordPurchase() {
        openPage("/com/example/demo2/record_purchase.fxml", "Record Purchase");
    }

    // New: Generate CSV report
    @FXML
    private void handleGenerateCsvReport() {
        List<Product> products = fetchProducts();
        try {
            ReportGenerator csvGenerator = new ReportGenerator(new CsvReportFormat());
            csvGenerator.generate(products, "products.csv");
            // Optionally show a message to the user
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // New: Generate PDF report
    @FXML
    private void handleGeneratePdfReport() {
        List<Product> products = fetchProducts();
        try {
            ReportGenerator pdfGenerator = new ReportGenerator(new PdfReportFormat());
            pdfGenerator.generate(products, "products.pdf");
            // Optionally show a message to the user
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper: Fetch products from DB
    private List<Product> fetchProducts() {
        List<Product> products = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM products");
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getString("color"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getDate("expiry_date") != null ? rs.getDate("expiry_date").toLocalDate() : null
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    private void openPage(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}