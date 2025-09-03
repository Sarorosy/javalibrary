package com.saro.library.repository;

import com.saro.library.models.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {

    Optional<Borrow> findByUserIdAndIsReturnedFalse(Long userId);

    Optional<Borrow> findByUserIdAndBookId(Long userId, Long bookId);
}
