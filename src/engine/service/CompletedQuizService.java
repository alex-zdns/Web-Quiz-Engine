package engine.service;

import engine.entity.CompletedQuiz;
import engine.repository.CompletedQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompletedQuizService {
    @Autowired
    CompletedQuizRepository repository;

    public Slice<CompletedQuiz> getCompletedQuizzes(String username, Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("completedAt").descending());

        return repository.findByUsername(username, paging);
    }
}
