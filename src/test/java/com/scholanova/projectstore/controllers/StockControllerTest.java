package com.scholanova.projectstore.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.scholanova.projectstore.models.Stock;
import com.scholanova.projectstore.services.StockService;
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StockControllerTest {

	
    @LocalServerPort //pour récupérer le port du server local et initialiser la variable port
    private int port;
    
    private TestRestTemplate template = new TestRestTemplate();

    @MockBean
    private StockService stockService; //on déclare une simulation

    @Captor
    ArgumentCaptor<Stock> createStockArgumentCaptor; //capteur de paramètre
    @Captor
    ArgumentCaptor<Stock> updateStockArgumentCaptor;
    @Captor
    ArgumentCaptor<Integer> DeleteArgumentCaptor;
    
    @Nested // signale qu'il ya des @test imbriqués
    public class Test_createStock {
        @Test
        void givenCorrectBody_whenCalled_createStock() throws Exception {
            // given
            String url = "http://localhost:{port}/stock";

            Map<String, String> urlVariables = new HashMap<>();
            urlVariables.put("port", String.valueOf(port));

            HttpHeaders headers = new HttpHeaders();
            //header de la requete http pour indiquer qu'on envoi du json 
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            //donnée json
            String requestJson = "{" +
                    "\"name\":\"orange\"," +
                    "\"type\":\"fruit\"," +
                    "\"value\":10," +
                    "\"id_store\":4" +
                    "}";
            
            //requete http
            HttpEntity<String> httpEntity = new HttpEntity<>(requestJson, headers);

            //création d'un obje stock
            Stock createdStock = new Stock(123, "orange", "fruit", 10, 4);
            
            //quand on simule la methode create() du service, on utilise createStoreArgumentCaptor.capture() pour mettre en paramètre le contenu de createdStock que l'on récup avec thenReturn()
            when(stockService.create(createStockArgumentCaptor.capture())).thenReturn(createdStock);

            //Execute la requete HTTP et récupère la réponse
            ResponseEntity responseEntity = template.exchange(url,
                    HttpMethod.POST,
                    httpEntity,
                    String.class,
                    urlVariables);

            // Then
            assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
            assertThat(responseEntity.getBody()).isEqualTo(
                    "{" +
                    		"\"id\":123," +
                            "\"name\":\"orange\"," +
                            "\"type\":\"fruit\"," +
                            "\"value\":10," +
                            "\"id_store\":4" +
                            "}"
            );
            Stock stockToCreate = createStockArgumentCaptor.getValue();
            assertThat(stockToCreate.getName()).isEqualTo("orange");
        }
    }
}
