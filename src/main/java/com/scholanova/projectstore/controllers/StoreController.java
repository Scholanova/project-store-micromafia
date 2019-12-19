package com.scholanova.projectstore.controllers;

import com.scholanova.projectstore.exceptions.ModelNotFoundException;
import com.scholanova.projectstore.exceptions.StoreNameCannotBeEmptyException;
import com.scholanova.projectstore.models.Store;
import com.scholanova.projectstore.services.StoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
@RestController
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping(path = "/stores/{id}")
    public Store getStation(@PathVariable("id") Integer id) throws ModelNotFoundException {
        return this.storeService.getByID(id);
    }

    @PostMapping(path = "/stores")
    public ResponseEntity createStore(@RequestBody Store store) throws StoreNameCannotBeEmptyException {
    	if(this.isNameNull(store))
        	return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(storeService.create(store), HttpStatus.OK);
    }
    
    @DeleteMapping(path = "/stores/{id}")
    public ResponseEntity deleteStation(@PathVariable("id") Integer id) {
			try {
				return new ResponseEntity(this.storeService.deleteStore(id), HttpStatus.NO_CONTENT);
			} catch (DataAccessException e) {
				return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
			} catch (ModelNotFoundException e) {
				return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		
    }
    
    @PutMapping(path = "/stores")
    public ResponseEntity updateStore(@RequestBody Store store) throws StoreNameCannotBeEmptyException, DataAccessException, ModelNotFoundException {
        if(this.isIdNull(store) || this.isNameNull(store))
        	return new ResponseEntity(HttpStatus.BAD_REQUEST);
    	return new ResponseEntity(this.storeService.update(store), HttpStatus.OK);
    }
    
    @GetMapping(path = "/stores/{id}/inventory_value")
    public ResponseEntity value_inventory(@PathVariable("id") Integer id) throws StoreNameCannotBeEmptyException, DataAccessException, ModelNotFoundException {
        if(id == null)
        	return new ResponseEntity(HttpStatus.BAD_REQUEST);
    	return new ResponseEntity(this.storeService.calculate_inventory(id), HttpStatus.OK);
    }
    
    boolean isIdNull (Store store)
    {
    	return store.getId() == null;
    }
    
    boolean isNameNull (Store store)
    {
    	return store.getName() == null || store.getName().trim().length() == 0;
    }
}
