package com.saro.library.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateBook {
    @NotNull(message = "Book name is required")
    private String bookName;

    @NotNull(message = "Author name is required")
    private String authorName;

    @NotNull
    private String publisher;

    @NotNull
    private String category;

    @Min(value = 0, message = "Price must be non-negative")
    private Double price;

    @NotNull
    private String imageUrl;    // Cover image path / URL

    private Boolean isAvailable = true; // Availability status

    @NotNull(message = "Total Copies is required")
    @Min(value = 1, message = "must be minimum 1")
    private Integer totalCopies;   // Total copies in library

    @NotNull(message = "Available Copies is required")
    @Min(value = 0, message = "value should not be negative")
    private Integer availableCopies; // How many are currently available
}
