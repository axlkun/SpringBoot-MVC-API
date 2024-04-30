package com.example.springmvcfullproject.controllers;

import com.example.springmvcfullproject.models.Book;
import com.example.springmvcfullproject.models.Category;
import com.example.springmvcfullproject.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("api/books")
public class BookApiController {

    @Autowired
    private BookRepository repo;

    private final String API_BASE_URL = "https://pokeapi.co/api/v2/pokemon/";

    @GetMapping
    @RequestMapping("autor/{autor}")
    public ResponseEntity<List<Book>> getByAutor(@PathVariable String autor){
        List<Book> books = repo.findByAutor(autor);

        if(books == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(books);
    }

    @GetMapping("pokemon/{pokemonName}")
    public ResponseEntity<String> getPokemonData(@PathVariable String pokemonName) {
        // Construye la URL completa para la solicitud a la API
        String apiUrl = API_BASE_URL + pokemonName;

        // Crea una instancia de RestTemplate para realizar la solicitud HTTP
        RestTemplate restTemplate = new RestTemplate();

        // Realiza la solicitud GET a la API externa y obtiene la respuesta como String
        String response = restTemplate.getForObject(apiUrl, String.class);

        // Verifica si la respuesta no está vacía
        if (response != null) {
            // Devuelve la respuesta de la API externa en formato JSON
            return ResponseEntity.ok(response);
        } else {
            // Si la respuesta está vacía, devuelve una respuesta de error
            return ResponseEntity.notFound().build();
        }
    }
}
