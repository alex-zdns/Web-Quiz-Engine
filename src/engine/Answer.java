package engine;

import java.util.Arrays;

public class Answer {
    private int[] answer;

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
        Arrays.sort(this.answer);
    }
}
