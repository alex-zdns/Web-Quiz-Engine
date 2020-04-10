package engine.repository;

import engine.entity.CompletedQuiz;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CompletedQuizRepository extends JpaRepository<CompletedQuiz, Long>, CrudRepository<CompletedQuiz, Long> {
    public Slice<CompletedQuiz> findByUsername(String username, Pageable pageable);
}
