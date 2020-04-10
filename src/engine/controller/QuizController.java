package engine.controller;

import engine.entity.Answer;
import engine.entity.Quiz;
import engine.entity.RequestAnswerObject;
import engine.exceptions.ForbiddenException;
import engine.exceptions.ResourceNotFoundException;
import engine.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    @Autowired
    private QuizRepository quizRepository;

    public QuizController() {
    }

    @PostMapping
    public Quiz create(@Valid @RequestBody Quiz quiz) {

        quiz.setOwner(SecurityContextHolder.getContext().getAuthentication().getName());

        return quizRepository.save(quiz);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable int id) {
        Quiz quiz = getQuiz(id);

        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!currentUserName.equals(quiz.getOwner())) {
            throw new ForbiddenException();
        }

        quizRepository.delete(quiz);

    }

    @GetMapping("{id}")
    public Quiz getQuiz(@PathVariable int id) throws ResourceNotFoundException {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found for this id :: " + id));
        return quiz;
    }

    @PostMapping("/{id}/solve")
    public RequestAnswerObject checkAnswer(@PathVariable int id, @RequestBody Answer answer) {
        Quiz quiz = getQuiz(id);

        boolean isCorrect = Arrays.equals(quiz.getAnswer(), answer.getAnswer());
        return new RequestAnswerObject(isCorrect);
    }

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

}
