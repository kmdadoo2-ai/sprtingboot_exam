package com.study.springboot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.study.springboot.dao.ITransaction1Dao;
import com.study.springboot.dao.ITransaction2Dao;

import lombok.RequiredArgsConstructor;

@Service   
@RequiredArgsConstructor    
public class BuyTicketService implements IBuyTicketService 
{
    private final ITransaction1Dao transaction1;   
    private final ITransaction2Dao transaction2;   

//    private final PlatformTransactionManager transactionManager;
    private final TransactionTemplate transactionTemplate;

    @Override
    public int buy(String consumerId, int amount, String error) 
    {
//		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
//		TransactionStatus status = transactionManager.getTransaction(definition);
    	try {
    		transactionTemplate.executeWithoutResult(status -> {
	             transaction1.pay(consumerId, amount);
	
                 // 의도적 에러 발생
                 if (error.equals("1")) {int n = 10 / 0;}
                 
                 transaction2.pay(consumerId, amount);
    		}); 
    	    
//            transactionManager.commit(status);
            return 1;
        } catch (Exception e) {
        	System.out.println("[TransactionTemplate] Rollback");
//            transactionManager.rollback(status);
            return 0;
        }
    }
}
