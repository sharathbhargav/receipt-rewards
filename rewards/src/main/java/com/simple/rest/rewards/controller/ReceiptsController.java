package com.simple.rest.rewards.controller;


import com.simple.rest.rewards.entity.Items;
import com.simple.rest.rewards.entity.Receipt;
import com.simple.rest.rewards.entity.ReceiptDTO;
import com.simple.rest.rewards.exceptions.ReceiptNotFoundException;
import com.simple.rest.rewards.repository.ReceiptRepository;
import com.simple.rest.rewards.service.ReceiptService;
import com.simple.rest.rewards.service.ReceiptServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/receipts")
@Slf4j
public class ReceiptsController {

    @Autowired
    public ReceiptServiceImpl receiptServiceImpl;

    @PostMapping("/process")
    public ResponseEntity<Long> processReceipt(@RequestBody ReceiptDTO receiptDto) {
        Receipt convertedReceipt = convertToEntity(receiptDto);
        long savedReceiptId = receiptServiceImpl.addReceipt(convertedReceipt);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReceiptId);
    }


    private Receipt convertToEntity(ReceiptDTO receiptDTO) {
        Receipt receipt = new Receipt();
        receipt.setRetailer(receiptDTO.getRetailer());

        receipt.setPurchaseDateTime(combineDateTime(receiptDTO.getPurchaseDate(),receiptDTO.getPurchaseTime()));
        receipt.setPoints(receiptDTO.getPoints());

        List<Items> items = new ArrayList<>();
        for (Items itemDTO : receiptDTO.getItems()) {
            Items item = new Items();
            item.setShortDescription(itemDTO.getShortDescription());
            item.setPrice(itemDTO.getPrice());
            items.add(item);
        }
        receipt.setItems(items);

        receipt.setTotal(receiptDTO.getTotal());

        return receipt;
    }

    private LocalDateTime combineDateTime(LocalDate date, LocalTime time) {
        // Get the date components from the LocalDate
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        // Get the time components from the LocalTime
        int hour = time.getHour();
        int minute = time.getMinute();
        int second = time.getSecond();
        int nanosecond = time.getNano();

        // Create a new LocalDateTime object with the combined date and time
        return LocalDateTime.of(year, month, day, hour, minute, second, nanosecond);
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<Double> fetchPoints(@PathVariable("id") Long id) {
        try {
            double points = receiptServiceImpl.getPoints(id);
            return ResponseEntity.status(HttpStatus.OK).body(points);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0.0);
        }
    }


}
