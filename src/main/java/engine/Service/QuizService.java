package engine.Service;

import engine.entity.Quiz;
import engine.exceptions.NotFoundException;
import engine.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    @Autowired
    QuizRepository quizRepository;

    public QuizService() {

    }

    public Quiz saveQuiz(Quiz quiz) {
        if (quiz.getAnswer() == null) {
            quiz.setAnswer(new int[0]);
        }

        return quizRepository.save(quiz);
    }

    public Quiz getQuiz(long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Quiz not found for this id :: " + id));
    }

    public List<Quiz> getAllQuizzes() {
        return (List<Quiz>) quizRepository.findAll();
    }

    public boolean isCorrectAnswer(int id, int[] answer) {
        Quiz quiz = getQuiz(id);
        return quiz.isCorrectAnswer(answer);
    }
}
