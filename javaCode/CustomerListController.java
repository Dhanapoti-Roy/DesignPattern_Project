package com.example.demo2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.demo2.CustomerListController;
import com.example.demo2.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CustomerListController {

    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, Integer> idCol;
    @FXML private TableColumn<Customer, String> nameCol;
    @FXML private TableColumn<Customer, String> phoneCol;
    @FXML private TableColumn<Customer, String> addressCol;
    @FXML private TableColumn<Customer, String> nidCol;

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        nidCol.setCellValueFactory(new PropertyValueFactory<>("nid"));

        loadCustomers();
    }

    private void loadCustomers() {
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM customers");

            customerList.clear();

            while (rs.next()) {
                customerList.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("nid")
                ));
            }

            customerTable.setItems(customerList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
