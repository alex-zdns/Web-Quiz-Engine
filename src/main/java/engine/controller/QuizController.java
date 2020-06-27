package engine.controller;

import engine.entity.AnswerEntity;
import engine.entity.CompletedQuiz;
import engine.entity.Quiz;
import engine.entity.QuizResult;
import engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("{id}/solve")
    public QuizResult checkSolutions(@PathVariable long id, @RequestBody AnswerEntity userAnswer) {
        Quiz quiz = quizService.getQuiz(id);
        return new QuizResult(
                quizService.isCorrectAnswer(id, userAnswer.getAnswer())
        );

    }

    @PostMapping
    public Quiz createQuiz(@Valid @RequestBody Quiz quiz) {
        return quizService.saveQuiz(quiz);
    }

    @GetMapping("{id}")
    public Quiz getQuiz(@PathVariable long id) {
        return quizService.getQuiz(id);
    }

    @GetMapping
    public Page<Quiz> getAllQuizzes(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {

        Page<Quiz> pages = quizService.getAllQuizzes(page, pageSize, sortBy);

        return pages;
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deleteQuiz(@PathVariable long id) {
        quizService.deleteQuiz(id);
    }

    @GetMapping("completed")
    public Slice<CompletedQuiz> getAllCompletedQuizzes(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "completedAt") String sortBy) {

        return quizService.getAllCompletedQuizForCurrentUser(page, pageSize, sortBy);
    }
}
