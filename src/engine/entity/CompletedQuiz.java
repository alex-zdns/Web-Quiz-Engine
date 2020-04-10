package engine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Finished_Quizzes")
public class CompletedQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long DataBaseId;

    @Column(name = "quiz_id", nullable = false)
    private Integer id;

    @Column(name = "completedAt", nullable = false)
    private LocalDateTime completedAt;

    @Column(name = "username")
    private String username;

    public CompletedQuiz() {
    }

    public CompletedQuiz(Integer quizId, String username) {
        this.id = quizId;
        this.username = username;
        this.completedAt = LocalDateTime.now();
    }

    @JsonIgnore
    public Long getDataBaseId() {
        return DataBaseId;
    }

    @JsonIgnore
    public void setDataBaseId(Long dataBaseId) {
        this.DataBaseId = dataBaseId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    @JsonIgnore
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    public void setUsername(String username) {
        this.username = username;
    }
}
