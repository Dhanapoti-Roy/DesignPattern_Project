package com.example.demo2;

import java.util.List;

// Use a simple placeholder for PDF generation. Replace with a real library if needed.
public class PdfReportFormat implements ReportFormat {
    @Override
    public void generateReport(List<Product> products, String filePath) throws Exception {
        // For demonstration, just write plain text as "PDF"
        try (java.io.FileWriter writer = new java.io.FileWriter(filePath)) {
            writer.write("Product Report (PDF)\n\n");
            for (Product p : products) {
                writer.write("ID: " + p.getId() + ", Name: " + p.getName() +
                        ", Type: " + p.getType() + ", Color: " + p.getColor() +
                        ", Quantity: " + p.getQuantity() + ", Price: " + p.getPrice() +
                        ", Expiry: " + (p.getExpiryDate() != null ? p.getExpiryDate() : "") + "\n");
            }
        }
    }
}
