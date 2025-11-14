package com.example.demo2;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class LoginController {


        @FXML private TextField usernameField;
        @FXML private PasswordField passwordField;
        @FXML private Label errorLabel;

        @FXML
        private void handleLogin() {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Please enter username and password.");
                return;
            }

            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                conn = DBConnection.getConnection();
                if (conn == null) {
                    errorLabel.setText("Database connection failed.");
                    return;
                }
                String query = "SELECT role FROM users WHERE username=? AND password=?";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, password);
                rs = stmt.executeQuery();

                if (rs.next()) {
                    String role = rs.getString("role");
                    loadDashboard(role);
                } else {
                    errorLabel.setText("Invalid credentials.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                errorLabel.setText("Error: " + e.getMessage());
            } finally {
                try { if (rs != null) rs.close(); } catch (Exception ignored) {}
                try { if (stmt != null) stmt.close(); } catch (Exception ignored) {}
                // Do not close conn here, as it's managed by DBConnection
            }
        }

        private void loadDashboard(String role) throws IOException {
            String fxml = role.equals("admin") ? "/com/example/demo2/admin.fxml" : "/com/example/demo2/dashboard.fxml";

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(role.substring(0, 1).toUpperCase() + role.substring(1) + " Dashboard");
        }
    }
