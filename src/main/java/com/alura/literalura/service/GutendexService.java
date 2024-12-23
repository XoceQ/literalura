package com.alura.literalura.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class GutendexService {

    private final RestTemplate restTemplate;

    public GutendexService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String searchBooks(String query) {
        String apiUrl = "https://gutendex.com/books?search=" + query;
        try {
            return restTemplate.getForObject(apiUrl, String.class);
        } catch (HttpClientErrorException e) {
            // Manejo específico de errores HTTP
            return "Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        } catch (RestClientException e) {
            // Manejo de otros errores del cliente
            return "Error al conectar con la API: " + e.getMessage();
        }
    }
}