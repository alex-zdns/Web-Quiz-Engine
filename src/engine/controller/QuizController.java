package engine.controller;

import engine.entity.Answer;
import engine.entity.CompletedQuiz;
import engine.entity.Quiz;
import engine.entity.RequestAnswerObject;
import engine.exceptions.ForbiddenException;
import engine.exceptions.ResourceNotFoundException;
import engine.repository.CompletedQuizRepository;
import engine.repository.QuizRepository;
import engine.service.CompletedQuizService;
import engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private CompletedQuizRepository completedQuizRepository;

    @Autowired
    QuizService quizService;

    @Autowired
    CompletedQuizService completedQuizService;

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

        if (quiz.getAnswer() == null && answer.getAnswer().length == 0) {
            isCorrect = true;
        }



        if (isCorrect) {
            String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
            completedQuizRepository.save(new CompletedQuiz(id, currentUserName));
        }

        return new RequestAnswerObject(isCorrect);
    }

    @GetMapping
    public ResponseEntity<Page<Quiz>> getAllQuizzes(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy)
    {

        Page<Quiz> pages = quizService.getAllQuizzes(page, pageSize, sortBy);

        return new ResponseEntity<>(pages, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("completed")
    public ResponseEntity<Slice<CompletedQuiz>> getAllCompletedQuizzes(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy)
    {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Slice<CompletedQuiz> pages = completedQuizService.getCompletedQuizzes(currentUserName, page, pageSize, sortBy);

        return new ResponseEntity<>(pages, new HttpHeaders(), HttpStatus.OK);
    }

}
