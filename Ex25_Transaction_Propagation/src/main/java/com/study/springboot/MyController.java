package com.study.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springboot.service.BuyAndLogService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor 
public class MyController 
{
//	private final IBuyTicketService buyTicket;
    private final BuyAndLogService buyTicketLog;

    @GetMapping("/")
    @ResponseBody
    public String root() throws Exception 
    {
    	return "Transaction Propagation (4)";
    }

    @GetMapping("/buy_ticket")
    public String buy_ticket() 
    {
        return "buy_ticket";
    }

    @GetMapping("/buy_ticket_card")
    public String buy_ticket_card(@RequestParam("consumerId") String consumerId,
                                 @RequestParam("amount") String amount,
                                 @RequestParam("error") String error,
                                 Model model) 
    {
//        int nResult = buyTicket.buy(consumerId, Integer.parseInt(amount), error);
    	int nResult = buyTicketLog.buy(consumerId, Integer.parseInt(amount), error);

        model.addAttribute("consumerId", consumerId);
        model.addAttribute("amount", amount);
        if (nResult == 1) {
            return "buy_ticket_end";
        } else {
            return "buy_ticket_error";
        }
    }
}
