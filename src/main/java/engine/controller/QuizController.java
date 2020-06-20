package engine.controller;

import engine.Service.QuizService;
import engine.entity.AnswerEntity;
import engine.entity.Quiz;
import engine.entity.QuizResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("{id}/solve")
    public QuizResult checkSolutions(@PathVariable int id, @RequestBody AnswerEntity userAnswer) {
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
    public Quiz getQuiz(@PathVariable int id) {
        return quizService.getQuiz(id);
    }

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }
}
