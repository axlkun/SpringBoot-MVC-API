package com.example.springmvcfullproject.controllers;

import com.example.springmvcfullproject.models.Category;
import com.example.springmvcfullproject.models.CategoryDto;
import com.example.springmvcfullproject.repositories.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryApiController {

    @Autowired
    private CategoryRepository repo;

    @GetMapping
    public List<Category> getCategories(){
        return repo.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Category> getCategory(@PathVariable int id){
        Category category = repo.findById(id).orElse(null);

        if(category == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<Object> createCategory(
            @Valid @RequestBody CategoryDto categoryDto,
            BindingResult result
            ){

        if(result.hasErrors()){
            var errorList = result.getAllErrors();
            var errorMap = new HashMap<String, String>();

            for(int i=0; i < errorList.size(); i++){
                var error = (FieldError) errorList.get(i);
                errorMap.put(error.getField(),error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errorMap);
        }

        Category category = new Category();

        category.setName(categoryDto.getName());

        repo.save(category);

        return ResponseEntity.ok(category);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateCategory(
            @PathVariable int id,
            @Valid @RequestBody CategoryDto categoryDto,
            BindingResult result
    ){
        Category category = repo.findById(id).orElse(null);
        if(category == null){
            return ResponseEntity.notFound().build();
        }

        if(result.hasErrors()){
            var errorList = result.getAllErrors();
            var errorMap = new HashMap<String, String>();

            for(int i=0; i < errorList.size(); i++){
                var error = (FieldError) errorList.get(i);
                errorMap.put(error.getField(),error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errorMap);
        }

        category.setName(categoryDto.getName());

        repo.save(category);

        return ResponseEntity.ok(category);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable int id){
        Category category = repo.findById(id).orElse(null);

        if(category == null){
            return ResponseEntity.notFound().build();
        }

        repo.delete(category);

        return ResponseEntity.ok().build();
    }
}
