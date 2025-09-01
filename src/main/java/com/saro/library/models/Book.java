package com.saro.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String bookName;


    private String authorName;

    private String publisher;   // Publisher/Company name

    private String category;    // Fiction, Science, History, etc.

    private Double price;

    private String imageUrl;    // Cover image path / URL

    private Boolean isAvailable = true; // Availability status

    private Integer totalCopies;   // Total copies in library

    private Integer availableCopies; // How many are currently available
}

