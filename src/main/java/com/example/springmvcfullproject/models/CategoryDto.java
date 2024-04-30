package com.example.springmvcfullproject.models;

import jakarta.validation.constraints.NotEmpty;

public class CategoryDto {

    @NotEmpty(message = "The title is required")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
