package com.saro.library.controllers;

import com.saro.library.dto.CreateBook;
import com.saro.library.models.Book;
import com.saro.library.services.BookService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/books")
@Data
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBooks(){
        List<Book> books = bookService.getBooks();
        Map<String, Object> response = new HashMap<>();

        response.put("status" , true);
        response.put("message" , "Books Fetched successfully");
        response.put("data" , books);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id){
        Optional<Book> book = bookService.getBookById(id);
        if(book.isPresent()){
            Map<String, Object> response = new HashMap<>();

            response.put("status" , true);
            response.put("message" , "Book Details Fetched succesfully");
            response.put("data" , book);
            return ResponseEntity.ok(response);
        }else{
            Map<String, Object> response = new HashMap<>();

            response.put("status" , false);
            response.put("message" , "Book Not Found");
            response.put("data" , null);
            return ResponseEntity.status(404).body(response);
        }

    }

    @GetMapping("/get/{id}")
    public Book getBookByIdNew(@PathVariable Long id) {
        return bookService.getBookById(id).orElse(null);
    }


    @PostMapping("/create")
    public ResponseEntity<?> createBook(@RequestBody CreateBook createBook){
        String bookName = createBook.getBookName();
        String authorName = createBook.getAuthorName();
        String publisherName = createBook.getPublisher();
        Double price = createBook.getPrice();
        String category = createBook.getCategory();
        String imageUrl = createBook.getImageUrl();
        Boolean isAvailable = createBook.getIsAvailable();
        Integer totalCopies = createBook.getTotalCopies();
        Integer availableCopies = createBook.getAvailableCopies();



        Map<String,Object> response = new HashMap<>();

        if(bookName == null || bookName.isEmpty()){
            response.put("status", false);
            response.put("message", "Book name is required");
            return ResponseEntity.badRequest().body(response);
        }

        if(authorName == null || authorName.isEmpty()){
            response.put("status", false);
            response.put("message", "authorName is required");
            return ResponseEntity.badRequest().body(response);
        }

        if(publisherName == null || publisherName.isEmpty()){
            response.put("status", false);
            response.put("message", "publisherName is required");
            return ResponseEntity.badRequest().body(response);
        }

        if(price == null){
            response.put("status", false);
            response.put("message", "Price is required");
            return ResponseEntity.badRequest().body(response);
        }

        if(category == null || category.isEmpty()){
            response.put("status", false);
            response.put("message", "Category is required");
            return ResponseEntity.badRequest().body(response);
        }

        if(imageUrl == null || imageUrl.isEmpty()){
            response.put("status", false);
            response.put("message", "Image URL is required");
            return ResponseEntity.badRequest().body(response);
        }

        if(isAvailable == null){
            response.put("status", false);
            response.put("message", "Availability status is required");
            return ResponseEntity.badRequest().body(response);
        }

        if(totalCopies == null){
            response.put("status", false);
            response.put("message", "Total copies is required");
            return ResponseEntity.badRequest().body(response);
        }

        if(availableCopies == null){
            response.put("status", false);
            response.put("message", "Available copies is required");
            return ResponseEntity.badRequest().body(response);
        }

        Book book = Book.builder()
                .bookName(bookName)
                .authorName(authorName)
                .publisher(publisherName)
                .price(price)
                .category(category)
                .availableCopies(availableCopies)
                .imageUrl(imageUrl)
                .isAvailable(isAvailable)
                .totalCopies(totalCopies).build();
        Book savedBook = bookService.saveBook(book);


        response.put("status" , true);
        response.put("message" , "Books Created successfully");
        response.put("data" , savedBook);
        return ResponseEntity.ok(response);
    }
}
