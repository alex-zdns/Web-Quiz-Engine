package engine.service;

import engine.entity.CompletedQuiz;
import engine.entity.Quiz;
import engine.entity.User;
import engine.exceptions.ForbiddenException;
import engine.exceptions.NotFoundException;
import engine.repository.CompletedQuizRepository;
import engine.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class QuizService {
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    CompletedQuizRepository completedQuizRepository;

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

    public Page<Quiz> getAllQuizzes(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        return quizRepository.findAll(paging);
    }

    public boolean isCorrectAnswer(long id, int[] answer) {
        Quiz quiz = getQuiz(id);
        boolean isCorrect = quiz.isCorrectAnswer(answer);

        if (isCorrect) {
            User user = userService.getCurrentUser();
            CompletedQuiz completedQuiz = new CompletedQuiz(quiz, user);

            completedQuizRepository.save(completedQuiz);

            quiz.getCompletedQuizList().add(completedQuiz);
            user.getCompletedQuizList().add(completedQuiz);
        }

        return isCorrect;
    }

    public Slice<CompletedQuiz> getAllCompletedQuizForCurrentUser(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        User user = userService.getCurrentUser();
        return completedQuizRepository.findByUser(user, paging);
    }
}
