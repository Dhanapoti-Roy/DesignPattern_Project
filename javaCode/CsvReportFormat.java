package com.example.demo2;

import java.io.FileWriter;
import java.util.List;

public class CsvReportFormat implements ReportFormat {
    @Override
    public void generateReport(List<Product> products, String filePath) throws Exception {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("ID,Name,Type,Color,Quantity,Price,ExpiryDate\n");
            for (Product p : products) {
                writer.write(p.getId() + "," + p.getName() + "," + p.getType() + "," +
                        p.getColor() + "," + p.getQuantity() + "," + p.getPrice() + "," +
                        (p.getExpiryDate() != null ? p.getExpiryDate() : "") + "\n");
            }
        }
    }
}