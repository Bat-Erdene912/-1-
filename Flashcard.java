package org.example;

public class FlashCard {
    private final String question;
    private final String answer;
    private int correctCount;
    private int wrongCount;

    public FlashCard(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.correctCount = 0;
        this.wrongCount = 0;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void markCorrect() {
        correctCount++;
    }

    public void markWrong() {
        wrongCount++;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public int getWrongCount() {
        return wrongCount;
    }
}
