package com.example.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.time.LocalDate;

public class StockController implements ProductObserver {

    @FXML private TableView<Product> stockTable;

    @FXML private TableColumn<Product, String> nameCol;
    @FXML private TableColumn<Product, String> typeCol;
    @FXML private TableColumn<Product, String> colorCol;
    @FXML private TableColumn<Product, Integer> quantityCol;
    @FXML private TableColumn<Product, Double> priceCol;
    @FXML private TableColumn<Product, LocalDate> expiryCol;
    @FXML private Label alertLabel;

    private final ObservableList<Product> stockList = FXCollections.observableArrayList();
    private final ProductSubject subject = new ProductSubject();

    @FXML
    private void initialize() {

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        expiryCol.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
            //new add
        expiryCol.setCellFactory(column -> new TableCell<Product, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString()); // তুমি চাইলে DateTimeFormatter ব্যবহার করতে পারো
                }
            }
        });

        //
        stockTable.setItems(stockList);

        subject.addObserver(this);
        loadStock();
    }

    private void loadStock() {
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM products");

            stockList.clear();
            while (rs.next()) {
                String dateStr = rs.getString("expiry_date");
                LocalDate expiry = (dateStr != null && !dateStr.isEmpty())
                        ? LocalDate.parse(dateStr)
                        : null;

                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getString("color"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        expiry
                );
                stockList.add(product);
                subject.checkProduct(product);
            }
            stockTable.setItems(stockList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onProductExpired(Product product) {
        alertLabel.setText("Alert: Product expired - " + product.getName());
    }

    @Override
    public void onProductOutOfStock(Product product) {
        alertLabel.setText("Alert: Product out of stock - " + product.getName());
    }
}