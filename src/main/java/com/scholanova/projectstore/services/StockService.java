package com.scholanova.projectstore.services;

import org.springframework.stereotype.Service;

import com.scholanova.projectstore.exceptions.ModelNotFoundException;
import com.scholanova.projectstore.exceptions.StoreNameCannotBeEmptyException;
import com.scholanova.projectstore.models.Stock;
import com.scholanova.projectstore.models.Store;
import com.scholanova.projectstore.repositories.StockRepository;
import com.scholanova.projectstore.repositories.StoreRepository;

@Service
public class StockService {
	
    private StockRepository stockRepository;


    public StockService(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	public Stock getByID(Integer id) throws ModelNotFoundException
    {
    	return this.stockRepository.getById(id);
    }
    
    public Stock create(Stock stock) throws StoreNameCannotBeEmptyException {

        if (isNameMissing(stock)) {
            throw new StoreNameCannotBeEmptyException();
        }

        return stockRepository.create(stock);
    }
    
    private boolean isNameMissing(Stock stock) {
        return stock.getName() == null ||
                stock.getName().trim().length() == 0;
    }

}
