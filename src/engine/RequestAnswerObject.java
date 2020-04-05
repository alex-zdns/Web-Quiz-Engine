package engine;

public class RequestAnswerObject {
    private boolean success;
    private String feedback;

    public RequestAnswerObject(boolean isSuccess) {
        this.success = isSuccess;

        if (isSuccess) {
            feedback = "Congratulations, you're right!";
        } else {
            feedback = "Wrong answer! Please, try again.";
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }
}
