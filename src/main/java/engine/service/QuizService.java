package engine.service;

import engine.entity.Quiz;
import engine.entity.User;
import engine.exceptions.ForbiddenException;
import engine.exceptions.NotFoundException;
import engine.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    UserService userService;

    public QuizService() {

    }

    public Quiz saveQuiz(Quiz quiz) {
        if (quiz.getAnswer() == null) {
            quiz.setAnswer(new int[0]);
        }

        quiz.setAuthor(userService.getCurrentUser());
        quiz.getAuthor().getQuizList().add(quiz);
        return quizRepository.save(quiz);
    }

    public Quiz getQuiz(long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Quiz not found for this id :: " + id));
    }

    public void deleteQuiz(long id) {
        Quiz quiz = getQuiz(id);

        User currentUser = userService.getCurrentUser();
        if (quiz.getAuthor() != currentUser) {
            throw new ForbiddenException();
        }

        quizRepository.delete(quiz);
    }

    public List<Quiz> getAllQuizzes() {
        return (List<Quiz>) quizRepository.findAll();
    }

    public boolean isCorrectAnswer(long id, int[] answer) {
        Quiz quiz = getQuiz(id);
        return quiz.isCorrectAnswer(answer);
    }
}
