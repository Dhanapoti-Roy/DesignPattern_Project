package com.example.demo2;

import java.util.List;

public interface ReportFormat {
    void generateReport(List<Product> products, String filePath) throws Exception;
}