package com.scholanova.projectstore.repositories;

import com.scholanova.projectstore.exceptions.ModelNotFoundException;
import com.scholanova.projectstore.models.Stock;
import com.scholanova.projectstore.models.Store;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

@SpringJUnitConfig(StoreRepository.class)
@JdbcTest
class StoreRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void cleanUp() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "STORES");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "STOCK");
    }

    @Nested
    class Test_getById {

        @Test
        void whenNoStoresWithThatId_thenThrowsException() throws Exception {
            // Given
            Integer id = 1000;

            // When & Then
            assertThrows(ModelNotFoundException.class, () -> {
                storeRepository.getById(id);
            });
        }

        @Test
        void whenStoreExists_thenReturnsTheStore() throws Exception {
            // Given
            Integer id = 1;
            Store store = new Store(id, "Carrefour");
            insertStore(store);

            // When
            Store extractedStore = storeRepository.getById(id);

            // Then
            assertThat(extractedStore).isEqualToComparingFieldByField(store);
        }
    }

    @Nested
    class Test_create {

        @Test
        void whenCreateStore_thenStoreIsInDatabaseWithId() {
            // Given
            String storeName = "Auchan";
            Store storeToCreate = new Store(null, storeName);

            // When
            Store createdStore = storeRepository.create(storeToCreate);

            // Then
            assertThat(createdStore.getId()).isNotNull();
            assertThat(createdStore.getName()).isEqualTo(storeName);
        }
    }

    private void insertStore(Store store) {
        String query = "INSERT INTO STORES " +
                "(ID, NAME) " +
                "VALUES ('%d', '%s')";
        jdbcTemplate.execute(
                String.format(query, store.getId(), store.getName()));
    }
    private void insertSock(Stock store) {
        String query = "INSERT INTO STOCK " +
                "(ID, NAME, TYPE, VALUE, ID_STORE) " +
                "VALUES ('%d', '%s', '%s', '%d', '%d')";
        jdbcTemplate.execute(
                String.format(query, store.getId(), store.getName(), store.getType(), store.getValue(), store.getId_store()));
    }
    @Test
    void whenStoreExists_thenReturnsTheStore() throws Exception {
        // Given
    	insertStore(new Store(1, "one"));
    	insertStore(new Store(2, "deux"));
        Integer id = 1;
        Stock stock = new Stock(1, "na", "nb", 2, 1);
        Stock stock_2 = new Stock(2, "na", "nb", 1, 1);
        Stock stock_3 = new Stock(3, "na", "nb", 2, 2);
        insertSock(stock);
        insertSock(stock_2);
        insertSock(stock_3);
        // When
        ArrayList<Stock> extractedStore = (ArrayList<Stock>) storeRepository.getByStoreId(id);

        // Then
        assertThat(extractedStore.size()).isEqualTo(2);
        assertThat(extractedStore.get(0)).isEqualTo(stock);
        assertThat(extractedStore.get(1)).isEqualTo(stock_2);
    }
}
