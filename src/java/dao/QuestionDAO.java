/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.QuestionDTO;
import java.util.ArrayList;
import java.util.List;
import utils.DBConnection;

/**
 *
 * @author Mk
 */
public class QuestionDAO extends DAO {

    public static final String TABLE_NAME = "Question";
    private final int STEP = 20;

    public boolean addQuestion(QuestionDTO question) throws Exception {
        boolean successful = false;
        String sql = "Insert into " + TABLE_NAME + "(Question, Answer1, Answer2, Answer3, Answer4, CorrectAnswer, CreatedAt, SubjectID, Status) values (?,?,?,?,?,?,?,?,?)";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, question.getQuestion());
            ps.setString(2, question.getAnswer1());
            ps.setString(3, question.getAnswer2());
            ps.setString(4, question.getAnswer3());
            ps.setString(5, question.getAnswer4());
            ps.setInt(6, question.getCorrectAnswer());
            ps.setTimestamp(7, question.getCreatedAt());
            ps.setString(8, question.getSubjectID());
            ps.setString(9, question.getStatus());
            successful = ps.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return successful;
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

    public List<QuestionDTO> searchQuestion(String search, String searchBy, int page) throws Exception {
        List<QuestionDTO> result = null;
        QuestionDTO q;
        String question, answer1, answer2, answer3, answer4, subjectID, status;
        int correctAnswer, id;
        String sql = "Select ID, Question, Answer1, Answer2, Answer3, Answer4, CorrectAnswer, SubjectID, Status From " + TABLE_NAME + " Where " + searchBy + " LIKE ? ORDER BY Question ASC OFFSET ? ROWS FETCH NEXT " + STEP + " ROWS ONLY";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            ps.setInt(2, (page - 1) * STEP);
            rs = ps.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                id = rs.getInt("ID");
                question = rs.getString("Question");
                answer1 = rs.getString("Answer1");
                answer2 = rs.getString("Answer2");
                answer3 = rs.getString("Answer3");
                answer4 = rs.getString("Answer4");
                correctAnswer = rs.getInt("CorrectAnswer");
                subjectID = rs.getString("SubjectID");
                status = rs.getString("Status");
                q = new QuestionDTO(question, answer1, answer2, answer3, answer4, subjectID, status, correctAnswer, id);
                result.add(q);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<String> getStatusList() throws Exception {
        List<String> result = null;
        String status;
        String sql = "Select Distinct Status From " + TABLE_NAME;
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                status = rs.getString("Status");
                result.add(status);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean deactivateQuestion(int id) throws Exception {
        boolean successful = false;
        String sql = "Update " + TABLE_NAME + " Set Status = ? Where ID = ?";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "deActive");
            ps.setInt(2, id);
            successful = ps.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return successful;
    }

    public boolean activateQuestion(int id) throws Exception {
        boolean successful = false;
        String sql = "Update " + TABLE_NAME + " Set Status = ? Where ID = ?";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "active");
            ps.setInt(2, id);
            successful = ps.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return successful;
    }

    public boolean updateQuestion(QuestionDTO question) throws Exception {
        boolean successful = false;
        String sql = "Update " + TABLE_NAME + " Set Question = ?, Answer1 = ?, Answer2 = ?, Answer3 = ?, Answer4 = ?, CorrectAnswer = ?, SubjectID = ? Where ID = ?";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, question.getQuestion());
            ps.setString(2, question.getAnswer1());
            ps.setString(3, question.getAnswer2());
            ps.setString(4, question.getAnswer3());
            ps.setString(5, question.getAnswer4());
            ps.setInt(6, question.getCorrectAnswer());
            ps.setString(7, question.getSubjectID());
            ps.setInt(8, question.getId());
            successful = ps.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return successful;
    }

    public List<QuestionDTO> getRandomQuestion(int amount, String subjectID) throws Exception {
        List<QuestionDTO> result = null;
        QuestionDTO q = null;
        String question, answer1, answer2, answer3, answer4;
        int correctAnswer, id;
        String sql = "Select TOP (?) ID, Question, Answer1, Answer2, Answer3, Answer4, CorrectAnswer From " + TABLE_NAME + " Where Status = ? And SubjectID = ? ORDER BY NEWID()";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, amount);
            ps.setString(2, "active");
            ps.setString(3, subjectID);
            rs = ps.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                id = rs.getInt("ID");
                question = rs.getString("Question");
                answer1 = rs.getString("Answer1");
                answer2 = rs.getString("Answer2");
                answer3 = rs.getString("Answer3");
                answer4 = rs.getString("Answer4");
                correctAnswer = rs.getInt("CorrectAnswer");
                q = new QuestionDTO(question, answer1, answer2, answer3, answer4, correctAnswer, id);
                result.add(q);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
