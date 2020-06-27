package engine.repository;

import engine.entity.CompletedQuiz;
import engine.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompletedQuizRepository extends JpaRepository<CompletedQuiz, Long> {
    public Slice<CompletedQuiz> findByUser(User user, Pageable pageable);
}
