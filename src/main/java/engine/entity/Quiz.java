package engine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "quizzes")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private long id;

    @Column
    @NotBlank(message = "title is mandatory")
    private String title;

    @Column
    @NotBlank(message = "text is mandatory")
    private String text;

    @Column
    @Size(min = 2)
    @NotEmpty
    private String[] options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column
    private int[] answer;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId")
    private User author;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<CompletedQuiz> completedQuizList = new ArrayList<>();

    public Quiz() {
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(long id) {
        this.id = id;
    }

    public boolean isCorrectAnswer(int[] answer) {
        return Arrays.equals(answer, this.answer);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<CompletedQuiz> getCompletedQuizList() {
        return completedQuizList;
    }

    public void setCompletedQuizList(List<CompletedQuiz> completedQuizList) {
        this.completedQuizList = completedQuizList;
    }
}
