package engine.entity;

public class AnswerEntity {
    private int[] answer;

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {

        if (answer == null) {
            answer = new int[0];
        }

        this.answer = answer;
    }
}
