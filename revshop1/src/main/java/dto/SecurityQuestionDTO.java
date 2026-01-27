package dto;

public class SecurityQuestionDTO {

    private int questionId;
    private String questionText;

    public SecurityQuestionDTO() {}

    public SecurityQuestionDTO(int questionId, String questionText) {
        this.questionId = questionId;
        this.questionText = questionText;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
}
