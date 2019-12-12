package com.scholanova.projectstore.services;

import com.scholanova.projectstore.exceptions.ModelNotFoundException;
import com.scholanova.projectstore.exceptions.StoreNameCannotBeEmptyException;
import com.scholanova.projectstore.models.Store;
import com.scholanova.projectstore.repositories.StoreRepository;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    private StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }
    public Store getByID(Integer id) throws ModelNotFoundException
    {
    	return this.storeRepository.getById(id);
    }
    
    public Store create(Store store) throws StoreNameCannotBeEmptyException {

        if (isNameMissing(store)) {
            throw new StoreNameCannotBeEmptyException();
        }

        return storeRepository.create(store);
    }

    private boolean isNameMissing(Store store) {
        return store.getName() == null ||
                store.getName().trim().length() == 0;
    }
    
    public boolean deleteStore(Integer id) throws DataAccessException, ModelNotFoundException {
    	return storeRepository.deletebyid(id);
    }
    
    public Store update(Store store) throws DataAccessException, ModelNotFoundException
    {
    	return this.storeRepository.update(store.getId(), store.getName());
    }
}
