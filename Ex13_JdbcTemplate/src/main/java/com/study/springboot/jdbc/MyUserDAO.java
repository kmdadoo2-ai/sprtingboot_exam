package com.study.springboot.jdbc;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MyUserDAO 
{
    private final JdbcTemplate jdbcTemplate;

    public List<MyUserDTO> list() 
    {
        String query = "select * from myuser";
        List<MyUserDTO> list = jdbcTemplate.query(
                query, new BeanPropertyRowMapper<MyUserDTO>(MyUserDTO.class));
        	
//        for(UserDTO my : list) {
//        	System.out.pri ntln(my);
//        }      
        
        return list;
    }
}
