package com.study.springboot.service;

import org.springframework.stereotype.Service;

import com.study.springboot.dao.ITransaction3Dao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor  
public class LogWriteService 
{
	private final ITransaction3Dao transaction3;

    public int write(String consumerId, int amount) 
    {
        try {
            transaction3.pay(consumerId, amount);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}
