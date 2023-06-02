package com.simple.rest.rewards.service;

import com.simple.rest.rewards.entity.Receipt;
import com.simple.rest.rewards.exceptions.ReceiptNotFoundException;
import com.simple.rest.rewards.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReceiptServiceImpl  implements ReceiptService{

    @Autowired
    private ReceiptRepository receiptRepository;
    public long addReceipt(Receipt receipt) {
        return receiptRepository.save(receipt).getId();

    }

    public double getPoints(long id) throws Exception{
        Optional<Receipt> res= receiptRepository.findById(id);

        if(res.isPresent()){
            return res.get().getPoints();
        }

        throw new ReceiptNotFoundException(id);
    }

}
