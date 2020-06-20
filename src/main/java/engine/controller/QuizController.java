package engine.controller;

import engine.service.QuizService;
import engine.entity.AnswerEntity;
import engine.entity.Quiz;
import engine.entity.QuizResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public List<Quiz> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deleteQuiz(@PathVariable long id) {
        quizService.deleteQuiz(id);
    }
}
