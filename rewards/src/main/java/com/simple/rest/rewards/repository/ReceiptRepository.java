package com.simple.rest.rewards.repository;

import com.simple.rest.rewards.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface ReceiptRepository extends JpaRepository<Receipt,Long> {

}
