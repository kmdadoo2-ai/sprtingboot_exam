package com.study.springboot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class BuyAndLogService 
{
	private final BuyTicketService buyTicket;

	private final LogWriteService logWrite;

	private final TransactionTemplate transactionTemplate;

    public int buy(String consumerId, int amount, String error) 
    {
        try {
            transactionTemplate.executeWithoutResult(status -> {   
                buyTicket.buy(consumerId, amount, error);
                
                // 의도적 에러 발생
                if (error.equals("2")) { int n = 10 / 0; }

                logWrite.write(consumerId, amount);
            });

            return 1;
        } catch (Exception e) {
            System.out.println("[Transaction Propagation #1] Rollback");
            return 0;
        }
    }
}
