package com.saro.library.controllers;

//import com.github.andrewoma.dexx.collection.HashMap;
//import com.github.andrewoma.dexx.collection.Map;
import com.saro.library.dto.BorrowBook;
import com.saro.library.models.Borrow;
import com.saro.library.models.User;
import com.saro.library.services.BookService;
import com.saro.library.services.BorrowService;
import com.saro.library.services.UserService;
import com.saro.library.utils.JwtUtil;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@Data
@RequestMapping("/borrow")
public class BorrowController {
    private BorrowService borrowService;
    private BookService bookService;
    private UserService userService;
    private JwtUtil jwtUtil;

    public BorrowController(BookService bookService, BorrowService borrowService, JwtUtil jwtUtil, UserService userService){
        this.bookService = bookService;
        this.borrowService= borrowService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/get")
    public ResponseEntity<?> borrowBook(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody BorrowBook borrowBook) {

        String token = authHeader.replace("Bearer ","").trim();
        String email = jwtUtil.extractEmail(token);

        Map<String, Object> response = new HashMap<>();

        User user = userService.getUserByEmail(email);
        if(borrowService.isAlreadyBorrowed(user.getId())){


            response.put("status", false);
            response.put("message", "User Book return pending.");

            return ResponseEntity.badRequest().body(response);
        }
        Long userId = user.getId();

        Long bookId = borrowBook.getBookId();
        LocalDate borrowDate = borrowBook.getBorrowDate();
        Integer borrowDays = borrowBook.getBorrowDays();


        if (bookId == null || borrowDate == null || borrowDays == null) {
            response.put("status", false);
            response.put("message", "bookId, borrowDate and borrowDays cannot be null");
            return ResponseEntity.badRequest().body(response);
        }

        Borrow borrow = Borrow.builder()
                .bookId(bookId)
                .userId(userId)
                .borrowDate(borrowDate)
                .borrowDays(borrowDays)
                .isReturned(false)
                .build();

        bookService.reduceAvailableCount(bookId);
        Borrow borrowed = borrowService.borrowBook(borrow);

        response.put("status", true);
        response.put("message", "Book Borrowed Successfully");
        response.put("data", borrowed);

        return ResponseEntity.ok(response);  // ✅ always return the map
    }

    @PostMapping("/returnBook")
    public ResponseEntity<?> returnBook(@RequestHeader("Authorization") String authHeader, @RequestBody BorrowBook borrowBookDto){
        String token = authHeader.replace("Bearer ", "").trim();

        String email = jwtUtil.extractEmail(token);

        User user = userService.getUserByEmail(email);

        Long bookId = borrowBookDto.getBookId();

        Map<String, Object> response = new HashMap<>();

        if(bookId == null){
            response.put("status", false);
            response.put("message", "bookId is required");
            return ResponseEntity.badRequest().body(response);
        }


        Borrow returned = borrowService.returnBook(user.getId(), bookId);

        if(returned == null){
            response.put("status", false);
            response.put("message", "bookId, User Doesn't borrowed any Books");
            return ResponseEntity.badRequest().body(response);
        }else{
            response.put("status", true);
            response.put("message", "Book returned Successfully");
            return ResponseEntity.ok(response);
        }


    }


}
