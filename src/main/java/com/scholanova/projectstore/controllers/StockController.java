package com.scholanova.projectstore.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.scholanova.projectstore.exceptions.StoreNameCannotBeEmptyException;
import com.scholanova.projectstore.models.Stock;
import com.scholanova.projectstore.models.Store;
import com.scholanova.projectstore.services.StockService;

@RestController
public class StockController {
	private final StockService stockService;

	public StockController(StockService stockService) {
		this.stockService = stockService;
	}
	
    @PostMapping(path = "/stock")
    public ResponseEntity createStock(@RequestBody Stock stock) throws StoreNameCannotBeEmptyException{
    	if(this.isNameNull(stock))
        	return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(stockService.create(stock), HttpStatus.OK);
    }
    
    boolean isIdNull (Stock stock)
    {
    	return stock.getId() == null;
    }
    
    boolean isNameNull (Stock stock)
    {
    	return stock.getName() == null || stock.getName().trim().length() == 0;
    }

}
