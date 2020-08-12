/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.sql.Timestamp;

/**
 *
 * @author Mk
 */
public class QuestionDTO {
    public static final String DEFAULT_STATUS = "active";
    
    private String question, answer1, answer2, answer3, answer4, subjectID, status;
    private int correctAnswer, id; 
    private Timestamp createdAt;

    public QuestionDTO(String question, String answer1, String answer2, String answer3, String answer4, String subjectID, int correctAnswer, Timestamp createdAt) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.subjectID = subjectID;
        this.correctAnswer = correctAnswer;
        this.createdAt = createdAt;
        this.status = DEFAULT_STATUS;
    }

    public QuestionDTO(String question, String answer1, String answer2, String answer3, String answer4, String subjectID, String status, int correctAnswer, int id) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.subjectID = subjectID;
        this.status = status;
        this.correctAnswer = correctAnswer;
        this.id = id;
    }

    public QuestionDTO(String question, String answer1, String answer2, String answer3, String answer4, String subjectID, int correctAnswer, int id) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.subjectID = subjectID;
        this.correctAnswer = correctAnswer;
        this.id = id;
    }

    public QuestionDTO(String question, String answer1, String answer2, String answer3, String answer4, int correctAnswer, int id) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.correctAnswer = correctAnswer;
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
