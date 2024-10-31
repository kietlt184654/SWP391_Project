package com.example.swp391.entity;



import jakarta.persistence.*;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ServiceCartEntity {
    private Map<DesignEntity, Integer> selectedServices = new HashMap<>();

    public void addService(DesignEntity service, int quantity) {
        selectedServices.put(service, selectedServices.getOrDefault(service, 0) + quantity);
    }

    public void removeService(DesignEntity service) {
        selectedServices.remove(service);
    }

    public double calculateTotalCost() {
        return selectedServices.entrySet()
                .stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }
}

