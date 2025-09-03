package com.saro.library.services;

import com.saro.library.models.Borrow;
import com.saro.library.repository.BookRepository;
import com.saro.library.repository.BorrowRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Data
@Service
public class BorrowService {
    private BorrowRepository borrowRepository;
    private BookRepository bookRepository;

    public BorrowService(BorrowRepository borrowRepository, BookRepository bookRepository){
        this.borrowRepository = borrowRepository;
        this.bookRepository = bookRepository;
    }



    public Borrow borrowBook(Borrow borrow){
        return borrowRepository.save(borrow);
    }

    public Boolean isAlreadyBorrowed(Long userId) {
        return borrowRepository.findByUserIdAndIsReturnedFalse(userId).isPresent();
    }

    public Borrow returnBook(Long userId, Long bookId){
        return borrowRepository.findByUserIdAndBookId(userId, bookId).map(borrow ->{
            borrow.setIsReturned(true);
            borrow.setReturnDate(LocalDate.now());
            return borrowRepository.save(borrow);
        }).orElse(null);
    }

}
