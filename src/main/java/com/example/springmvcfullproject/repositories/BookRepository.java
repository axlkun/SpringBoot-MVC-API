package com.example.springmvcfullproject.repositories;

import com.example.springmvcfullproject.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {

    @Query(value = "SELECT * FROM books WHERE autor = ?1", nativeQuery = true)
    List<Book> findByAutor(String autor);
}
