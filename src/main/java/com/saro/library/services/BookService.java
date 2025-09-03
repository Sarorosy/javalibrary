package com.saro.library.services;

import com.saro.library.models.Book;
import com.saro.library.repository.BookRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class BookService {
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Book reduceAvailableCount(Long id) {
        return bookRepository.findById(id).map(book -> {
                    if (book.getAvailableCopies() != null && book.getAvailableCopies() > 0) {
                        book.setAvailableCopies(book.getAvailableCopies() - 1);
                    }
                    if (book.getAvailableCopies() == 0) {
                        book.setIsAvailable(false);
                    }
                    return bookRepository.save(book);
                }).orElseThrow(()->new IllegalArgumentException("Book Not Available with id " + id));
    }
}
