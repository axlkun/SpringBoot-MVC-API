package com.example.springmvcfullproject.controllers;

import com.example.springmvcfullproject.models.Book;
import com.example.springmvcfullproject.models.BookDto;
import com.example.springmvcfullproject.models.Category;
import com.example.springmvcfullproject.repositories.BookRepository;
import com.example.springmvcfullproject.repositories.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository repo;

    @Autowired
    private CategoryRepository category_repo;

    @GetMapping({"", "/"})
    public String showBooksList(Model model){
        List<Book> books = repo.findAll((Sort.by(Sort.Direction.DESC,"id")));
        model.addAttribute("books",books);
        return "books/index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model){
        BookDto bookDto = new BookDto();
        List<Category> categories = category_repo.findAll();
        model.addAttribute("bookDto",bookDto);
        model.addAttribute("categories",categories);
        return "books/createBook";
    }

    @PostMapping("/create")
    public String createProduct(
            @Valid @ModelAttribute BookDto bookDto,
            BindingResult result,
            Model model
    ){
        Category category = null;

        if(bookDto.getCategoryId() == null){
            result.addError(new FieldError("bookDto","categoryId","Invalid category"));
        }else{
            // Obtener la entidad Category correspondiente al categoryId del DTO
            category = category_repo.findById(bookDto.getCategoryId()).orElse(null);
        }

        if(result.hasErrors()){
            List<Category> categories = category_repo.findAll();
            model.addAttribute("categories",categories);
            return "books/createBook";
        }

        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAutor(bookDto.getAutor());
        book.setCategory(category);
        book.setPrice(bookDto.getPrice());
        book.setPublishedDate(bookDto.getPublishedDate());

        repo.save(book);

        return "redirect:/books";
    }


    @GetMapping("/edit")
    public String showEditPage(
            Model model,
            @RequestParam int id
    ){
        try{

            Book book = repo.findById(id).get();
            model.addAttribute("book", book);

            // Obtener todas las categorías disponibles
            List<Category> categories = category_repo.findAll();

            // Obtener la categoría seleccionada del libro
            Category selectedCategory = book.getCategory();

            BookDto bookDto = new BookDto();
            bookDto.setTitle(book.getTitle());
            bookDto.setAutor(book.getAutor());
            bookDto.setCategoryId(selectedCategory.getId());
            bookDto.setPrice(book.getPrice());
            bookDto.setPublishedDate(book.getPublishedDate());

            model.addAttribute("bookDto",bookDto);
            model.addAttribute("categories", categories);

        }catch(Exception ex){
            System.out.println("Exception: " + ex);
            return "redirect:/books";
        }

        return "books/editBook";
    }

    @PostMapping("/edit")
    public String updatebook(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute BookDto bookDto,
            BindingResult result
    ){

        try{

            Book book = repo.findById(id).get();
            model.addAttribute("book", book);

            Category category = null;

            if(bookDto.getCategoryId() == null){
                result.addError(new FieldError("bookDto","categoryId","Invalid category"));
            }else{
                // Obtener la entidad Category correspondiente al categoryId del DTO
                category = category_repo.findById(bookDto.getCategoryId()).orElse(null);
            }

            if(result.hasErrors()){
                List<Category> categories = category_repo.findAll();
                model.addAttribute("categories",categories);
                return "books/editBook";
            }

            book.setTitle(bookDto.getTitle());
            book.setAutor(bookDto.getAutor());
            book.setPrice(bookDto.getPrice());
            book.setCategory(category);
            book.setPublishedDate(bookDto.getPublishedDate());

            repo.save(book);

        }catch(Exception ex){
            System.out.println("Exception: " + ex);
            return "redirect:/books";
        }

        return "redirect:/books";
    }

    @GetMapping("/delete")
    public String deleteBook(
            @RequestParam int id
    ){

        try{
            Book book = repo.findById(id).get();

            repo.delete(book);
        }
        catch(Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }

        return "redirect:/books";
    }
}
