package com.scholanova.projectstore.services;

import com.scholanova.projectstore.exceptions.StoreNameCannotBeEmptyException;
import com.scholanova.projectstore.models.Stock;
import com.scholanova.projectstore.models.Store;
import com.scholanova.projectstore.repositories.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    private StoreService storeService;

    @Mock
    private StoreRepository storeRepository;

    @BeforeEach
    void setUp() {
        storeService = new StoreService(storeRepository);
    }

    @Test
    void givenNoStoreName_whenCreated_failsWithNoEmptyStoreNameError() {
        // GIVEN
        Integer id = null;
        String name = null;

        Store emptyNameStore = new Store(id, name);

        // WHEN
        assertThrows(StoreNameCannotBeEmptyException.class, () -> {
            storeService.create(emptyNameStore);
        });

        // THEN
        verify(storeRepository, never()).create(emptyNameStore);
    }

    @Test
    void givenCorrectStore_whenCreated_savesStoreInRepository() throws Exception {
        // GIVEN
        Integer id = 1234;
        String name = "BHV";

        Store correctStore = new Store(null, name);
        Store savedStore = new Store(id, name);
        when(storeRepository.create(correctStore)).thenReturn(savedStore);

        // WHEN
        Store returnedStore = storeService.create(correctStore);

        // THEN
        verify(storeRepository, atLeastOnce()).create(correctStore);
        assertThat(returnedStore).isEqualTo(savedStore);
    }
    
    @Test
    void givenCorrectStore_whenUpdated_updateStoreInRepository() throws Exception {
        // GIVEN
        Integer id = 1234;
        String name = "BHV";

        Store correctStore = new Store(id, name);
        Store savedStore = new Store(id, name);
        when(storeRepository.update(id, name)).thenReturn(savedStore);

        // WHEN
        Store returnedStore = storeService.update(correctStore);

        // THEN
        verify(storeRepository, atLeastOnce()).update(id, name);
        assertThat(returnedStore).isEqualTo(savedStore);
    }
    
    @Test
    void givenIncorrectStore_whenUpdated_updateStoreInRepository() throws Exception {
        // GIVEN
        Integer id = 1234;
        String name = "";

        Store correctStore = new Store(id, name);
        Store savedStore = new Store(id, name);
        when(storeRepository.update(id, name)).thenReturn(savedStore);

        // WHEN
        Store returnedStore = storeService.update(correctStore);

        // THEN
        verify(storeRepository, atLeastOnce()).update(id, name);
        assertThat(returnedStore).isEqualTo(savedStore);
    }
    
    @Test
    void givenCorrectStore_whenDeleted_updateStoreInRepository() throws Exception {
        // GIVEN
        Integer id = 1234;
        
        when(storeRepository.deletebyid(id)).thenReturn(true);

        // WHEN
        Boolean returnedStore = storeService.deleteStore(id);

        // THEN
        verify(storeRepository, atLeastOnce()).deletebyid(id);
        assertThat(returnedStore).isEqualTo(true);
    }
    
    @Test
    void givenCorrectStoreId_Calculate_Give_Correct_List() throws Exception {
        // GIVEN
        Integer id = 1234;
        Stock one = new Stock(1, "nom", "type", 1, 5);
        Stock two = new Stock(2, "nom", "type", 2, 5);
        ArrayList<Stock> arr = new ArrayList();
        arr.add(one);
        arr.add(two);
        
        when(storeRepository.getByStoreId(id)).thenReturn(arr);

        // WHEN
        long returnedStore = storeService.calculate_inventory(id);
        
        // THEN
        verify(storeRepository, atLeastOnce()).getByStoreId(id);
        assertThat(returnedStore).isEqualTo(3);
    }
}