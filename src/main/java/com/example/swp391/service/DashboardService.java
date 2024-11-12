package com.example.swp391.service;

import com.example.swp391.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;




@Service
public class DashboardService {


    @Autowired
    private PaymentRepository paymentRepository;

    public Map<String, Double> getMonthlyRevenue() {
        List<Object[]> results = paymentRepository.findMonthlyRevenue();
        Map<String, Double> monthlyRevenue = new LinkedHashMap<>();

        for (Object[] result : results) {
            int month = (int) result[0];
            int year = (int) result[1];
            double totalRevenue = (double) result[2];

            // Định dạng yyyy-MM
            String monthYear = String.format("%d-%02d", year, month);
            monthlyRevenue.put(monthYear, totalRevenue);
        }

        return monthlyRevenue;
    }
}
