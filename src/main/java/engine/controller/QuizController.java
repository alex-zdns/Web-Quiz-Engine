package engine.controller;

import engine.Service.QuizService;
import engine.entity.Quiz;
import engine.entity.QuizResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("{id}/solve")
    public QuizResult checkSolutions(@PathVariable int id, @RequestParam(value = "answer") int answer) {
        Quiz quiz = quizService.getQuiz(id);
        return new QuizResult(
                quiz.isCorrectAnswer(answer)
        );
    }

    @PostMapping
    public Quiz createQuiz(@RequestBody Quiz quiz) {
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
