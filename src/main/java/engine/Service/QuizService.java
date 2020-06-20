package engine.Service;

import engine.entity.Quiz;
import engine.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {
    private static final List<Quiz> quizzes = new ArrayList<>();

    public QuizService() {

    }

    public Quiz saveQuiz(Quiz quiz) {
        int id = quizzes.size();
        quiz.setId(id);

        if (quiz.getAnswer() == null) {
            quiz.setAnswer(new int[0]);
        }

        quizzes.add(quiz);
        return quiz;
    }

    public Quiz getQuiz(int id) {
        if (quizzes.size() > id) {
            return quizzes.get(id);
        } else {
            throw new NotFoundException();
        }
    }

    public List<Quiz> getAllQuizzes() {
        return quizzes;
    }

    public boolean isCorrectAnswer(int id, int[] answer) {
        Quiz quiz = getQuiz(id);
        return quiz.isCorrectAnswer(answer);
    }
}
