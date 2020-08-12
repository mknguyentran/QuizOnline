/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.SubjectDTO;
import java.util.ArrayList;
import java.util.List;
import utils.DBConnection;

/**
 *
 * @author Mk
 */
public class SubjectDAO extends DAO {

    public static final String TABLE_NAME = "Subject";
    private final int STEP = 5;

    public List<SubjectDTO> getSubjectList() throws Exception {
        List<SubjectDTO> subjectList = null;
        SubjectDTO subject = null;
        String id, name;
        String sql = "Select ID, Name From " + TABLE_NAME;
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            subjectList = new ArrayList();
            while (rs.next()) {
                id = rs.getString("ID");
                name = rs.getString("Name");
                subject = new SubjectDTO(id, name);
                subjectList.add(subject);
            }
        } finally {
            closeConnection();
        }
        return subjectList;
    }

    public List<SubjectDTO> getSubjectDetailedList() throws Exception {
        List<SubjectDTO> subjectList = null;
        SubjectDTO subject = null;
        String id, name;
        int questionAmount, quizDuration;
        String sql = "Select ID, Name, QuizDuration, QuestionAmount From " + TABLE_NAME;
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            subjectList = new ArrayList();
            while (rs.next()) {
                id = rs.getString("ID");
                name = rs.getString("Name");
                questionAmount = rs.getInt("QuestionAmount");
                quizDuration = rs.getInt("QuizDuration");
                subject = new SubjectDTO(id, name, quizDuration, questionAmount);
                subjectList.add(subject);
            }
        } finally {
            closeConnection();
        }
        return subjectList;
    }
    
    public int getPagesAmount(String search, String searchBy) throws Exception {
        double pagesAmount = 0;
        String sql = "Select Count(ID) As Count From " + TABLE_NAME + " Where " + searchBy + " LIKE ?";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            rs = ps.executeQuery();
            if (rs.next()) {
                pagesAmount = rs.getInt("Count");
                pagesAmount = Math.ceil(pagesAmount / STEP);
            }
        } finally {
            closeConnection();
        }
        return (int) pagesAmount;
    }

    public List<SubjectDTO> searchSubject(String search, String searchBy, int page) throws Exception {
        List<SubjectDTO> subjectList = null;
        SubjectDTO subject = null;
        String id, name;
        int questionAmount, quizDuration;
        String sql = "Select ID, Name, QuizDuration, QuestionAmount From " + TABLE_NAME + " Where "+searchBy+" LIKE ? ORDER BY Name ASC OFFSET ? ROWS FETCH NEXT " + STEP + " ROWS ONLY";
        try {
            conn = DBConnection.getConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            ps.setInt(2, (page - 1) * STEP);
            rs = ps.executeQuery();
            subjectList = new ArrayList();
            while (rs.next()) {
                id = rs.getString("ID");
                name = rs.getString("Name");
                questionAmount = rs.getInt("QuestionAmount");
                quizDuration = rs.getInt("QuizDuration");
                subject = new SubjectDTO(id, name, quizDuration, questionAmount);
                subjectList.add(subject);
            }
        } finally {
            closeConnection();
        }
        return subjectList;
    }
    
    public List<String> getCategoryList() throws Exception{
        List<String> result = null;
        String category;
        String sql = "Select DISTINCT Category From " + TABLE_NAME;
        try {
            conn=DBConnection.getConnection();
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            result = new ArrayList<>();
            while (rs.next()){
                category=rs.getString("Category");
                result.add(category);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
