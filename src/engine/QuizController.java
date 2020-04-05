package engine;

import engine.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    @Autowired
    private QuizRepository quizRepository;

    public QuizController() {
    }

    @PostMapping
    public Quiz create(@Valid @RequestBody Quiz quiz) {
        return quizRepository.save(quiz);
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
