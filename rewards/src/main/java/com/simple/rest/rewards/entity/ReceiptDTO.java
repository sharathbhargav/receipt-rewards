package com.simple.rest.rewards.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDTO {

    private Items[] items;

    private String retailer;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchaseDate;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime purchaseTime;

    private double total;

    public double getPoints() {
        int points = calculateAlphanumericPoints(retailer);
        points += calculateRoundDollarPoints(total);
        points += calculateMultipleOfQuarterPoints(total);
        points += calculateItemPoints(items.length);
        points += calculateItemDescriptionPoints(items.length);
        points += calculateOddDayPoints(purchaseDate);
        points += calculatePurchaseTimePoints(purchaseTime);

        return points;
    }

    private  int calculateAlphanumericPoints(String retailer) {
        int alphanumericCount = retailer.replaceAll("[^a-zA-Z0-9]", "").length();
        return alphanumericCount;
    }

    private  int calculateRoundDollarPoints(double total) {
        if (Math.floor(total)== Math.ceil(total)) {
            return 50;
        }
        return 0;
    }

    private  int calculateMultipleOfQuarterPoints(double total) {
        if (total %0.25 == 0) {
            return 25;
        }
        return 0;
    }

    private  int calculateItemPoints(int itemCount) {
        return itemCount / 2 * 5;
    }

    private  int calculateItemDescriptionPoints(int itemCount) {
        int points = 0;
        for (int i = 0; i < itemCount; i++) {
            String itemDescription = getItemDescriptionFromReceipt(i); // Replace with your logic to get the item description
            int trimmedLength = itemDescription.trim().length();
            if (trimmedLength % 3 == 0) {
                double price = getItemPriceFromReceipt(i); // Replace with your logic to get the item price
                int itemPoints =(int)( Math.ceil(price*0.2));
                points += itemPoints;
            }
        }
        return points;
    }

    private  int calculateOddDayPoints(LocalDate purchaseDate) {
        if (purchaseDate.getDayOfMonth() % 2 != 0) {
            return 6;
        }
        return 0;
    }

    private  int calculatePurchaseTimePoints(LocalTime purchaseTime) {
        LocalTime startTime = LocalTime.of(14, 0); // 2:00 PM
        LocalTime endTime = LocalTime.of(16, 0); // 4:00 PM

        if (purchaseTime.isAfter(startTime) && purchaseTime.isBefore(endTime)) {
            return 10;
        }
        return 0;
    }

    // Replace with your logic to retrieve item description from the receipt
    private  String getItemDescriptionFromReceipt(int index) {
        // Placeholder implementation
        return items[index].getShortDescription();
    }

    // Replace with your logic to retrieve item price from the receipt
    private  double getItemPriceFromReceipt(int index) {
        // Placeholder implementation
        return items[index].getPrice();
    }
}
