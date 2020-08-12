/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author Mk
 */
public class SubjectDTO {
    private String id, name;
    private int quizDuration, questionAmount;

    public SubjectDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public SubjectDTO(String id, String name, int quizDuration, int questionAmount) {
        this.id = id;
        this.name = name;
        this.quizDuration = quizDuration;
        this.questionAmount = questionAmount;
    }

    public int getQuizDuration() {
        return quizDuration;
    }

    public void setQuizDuration(int quizDuration) {
        this.quizDuration = quizDuration;
    }

    public int getQuestionAmount() {
        return questionAmount;
    }

    public void setQuestionAmount(int questionAmount) {
        this.questionAmount = questionAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getDisplayName(){
        return id + " - " + name;
    }
}
