package com.saro.library.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BorrowBook {
    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private Long bookId;

    @NotNull
    private LocalDate borrowDate;

    @NotNull
    @Max(value = 15)
    @Min(value = 1)
    private Integer borrowDays;


    private LocalDate returnDate;


    private Boolean isReturned;

}
