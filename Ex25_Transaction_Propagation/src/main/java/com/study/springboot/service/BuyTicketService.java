package com.study.springboot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.study.springboot.dao.ITransaction1Dao;
import com.study.springboot.dao.ITransaction2Dao;

import lombok.RequiredArgsConstructor;

@Service   
@RequiredArgsConstructor    
public class BuyTicketService
{
    private final ITransaction1Dao transaction1;   
    private final ITransaction2Dao transaction2;   

    private final TransactionTemplate transactionTemplate;
    
//    @Transactional(propagation = Propagation.REQUIRED)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int buy(String consumerId, int amount, String error) 
    {
    	try {
    		transactionTemplate.executeWithoutResult(status -> {
	             transaction1.pay(consumerId, amount);
	
                 // 의도적 에러 발생
                 if (error.equals("1")) {int n = 10 / 0;}
                 
                 transaction2.pay(consumerId, amount);
    		}); 
    	    
            return 1;
        } catch (Exception e) {
        	System.out.println("[Transaction Propagation #2] Rollback");
            return 0;
        }
    }
}
