package engine.controller;

import engine.entity.Quiz;
import engine.entity.QuizResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @PostMapping
    public QuizResult checkSolutions(@RequestParam(value = "answer") int answer) {
        return new QuizResult(answer == 2);
    }

    @GetMapping
    public Quiz getQuiz() {
        String title = "The Java Logo";
        String text = "What is depicted on the Java logo?";
        String[] options = {"Robot","Tea leaf","Cup of coffee","Bug"};
        return new Quiz(title, text, options, 2);
    }
}
