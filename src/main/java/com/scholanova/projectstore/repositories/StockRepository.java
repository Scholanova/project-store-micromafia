package com.scholanova.projectstore.repositories;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.scholanova.projectstore.exceptions.ModelNotFoundException;
import com.scholanova.projectstore.models.Stock;
import com.scholanova.projectstore.models.Store;

@Repository
public class StockRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    
    public StockRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    	
    public Stock getById(Integer id) throws ModelNotFoundException {
        String query = "SELECT ID as id, " +
                "NAME AS name, " +
        		"TYPE AS type," +
                "VALUE AS value," +
        		"ID_STORE AS id_store " +
                "FROM STOCK " +
                "WHERE ID = :id";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);

        return jdbcTemplate.query(query,
                parameters,
                new BeanPropertyRowMapper<>(Stock.class))
                .stream()
                .findFirst()
                .orElseThrow(ModelNotFoundException::new);
    }

    public Stock create(Stock stockToCreate) {
        KeyHolder holder = new GeneratedKeyHolder();

        String query = "INSERT INTO STOCK " +
                "(name,type,value,id_store) VALUES " +
                "(:name,:type,:value,:id_store)";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", stockToCreate.getName()).addValue("type", stockToCreate.getType()).addValue("value", stockToCreate.getValue()).addValue("id_store", stockToCreate.getId_store());

        jdbcTemplate.update(query, parameters, holder);

        Integer newlyCreatedId = (Integer) holder.getKeys().get("ID");
        try {
            return this.getById(newlyCreatedId);
        } catch (ModelNotFoundException e) {
            return null;
        }
    }
}
