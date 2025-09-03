package com.saro.library.services;

import com.saro.library.models.Borrow;
import com.saro.library.repository.BookRepository;
import com.saro.library.repository.BorrowRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

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
}
