package com.example.demo2;

import java.util.List;

public class ReportGenerator {
    private ReportFormat format;

    public ReportGenerator(ReportFormat format) {
        this.format = format;
    }

    public void generate(List<Product> products, String filePath) throws Exception {
        format.generateReport(products, filePath);
    }
}
