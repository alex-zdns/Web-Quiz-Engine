package engine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Finished_Quizzes")
public class CompletedQuiz {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long DataBaseId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "QuizID")
    private Quiz quiz;

    @Column(nullable = false)
    private LocalDateTime completedAt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "UserId")
    private User user;

    public CompletedQuiz() {    }

    public CompletedQuiz(Quiz quiz, User user) {
        this.quiz = quiz;
        this.user = user;
        this.completedAt = LocalDateTime.now();
    }

    public Long getDataBaseId() {
        return DataBaseId;
    }

    public void setDataBaseId(Long dataBaseId) {
        DataBaseId = dataBaseId;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty("id")
    public long getId() {
        return quiz.getId();
    }
}