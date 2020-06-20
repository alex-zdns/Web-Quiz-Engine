package engine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Arrays;

public class Quiz {
    private long id;

    @NotBlank(message = "title is mandatory")
    private String title;

    @NotBlank(message = "text is mandatory")
    private String text;

    @Size(min = 2)
    @NotEmpty
    private String[] options;

    private int[] answer;

    public Quiz() {
    }

    public long getId() {
        return id;
    }

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

    @JsonIgnore
    public int[] getAnswer() {
        return answer;
    }

    @JsonProperty
    public void setAnswer(int[] answer) {
        this.answer = answer;
    }
}
