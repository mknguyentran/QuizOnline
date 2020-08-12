/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;

/**
 *
 * @author Mk
 */
public class QuestionErrorObject implements Serializable{
    private String subjectIDError;
    boolean empty;

    public QuestionErrorObject() {
        empty = true;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public String getSubjectIDError() {
        return subjectIDError;
    }

    public void setSubjectIDError(String subjectIDError) {
        this.subjectIDError = subjectIDError;
    }
}
