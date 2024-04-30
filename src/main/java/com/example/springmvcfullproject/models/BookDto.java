package com.example.springmvcfullproject.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class BookDto {

    @NotEmpty(message = "The title is required")
    private String title;

    @NotEmpty(message = "The author is required")
    private String autor;

    @Min(0)
    private double price;

    @NotNull(message = "The published date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishedDate;

    //@NotNull(message = "The category ID is required")
    private Integer categoryId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }
}
