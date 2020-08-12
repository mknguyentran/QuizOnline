/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 *
 * @author Mk
 */
public class AttemptDTO {

    private int id, correctAmount;
    private float grade;
    private String accountID, subjectID;
    private Timestamp submittedAt;

    public AttemptDTO(String accountID, String subjectID, Timestamp submittedAt) {
        this.accountID = accountID;
        this.subjectID = subjectID;
        this.submittedAt = submittedAt;
    }

    public AttemptDTO(int correctAmount, float grade, Timestamp submittedAt) {
        this.correctAmount = correctAmount;
        this.grade = grade;
        this.submittedAt = submittedAt;
    }
    
    public String getSimpleSubmittedDate(){
        LocalDateTime date = submittedAt.toLocalDateTime();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy kk:mm:ss").withLocale(Locale.US).withZone(ZoneId.of("UTC+7"));
        String dateString = date.format(format);
        return dateString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCorrectAmount() {
        return correctAmount;
    }

    public void setCorrectAmount(int correctAmount) {
        this.correctAmount = correctAmount;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public Timestamp getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Timestamp submittedAt) {
        this.submittedAt = submittedAt;
    }

}
