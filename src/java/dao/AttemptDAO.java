/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.AttemptDTO;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import utils.DBConnection;

/**
 *
 * @author Mk
 */
public class AttemptDAO extends DAO {

    public static final String TABLE_NAME = "Attempt";

    public int newAttempt(AttemptDTO attempt) throws Exception {
        int generatedKey = 0;
        String sql = "Insert into " + TABLE_NAME + "(AccountID, SubmittedAt, SubjectID) values (?,?,?)";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, attempt.getAccountID());
            ps.setTimestamp(2, attempt.getSubmittedAt());
            ps.setString(3, attempt.getSubjectID());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
        } finally {
            closeConnection();
        }
        return generatedKey;
    }

    public List<AttemptDTO> loadAttempt(String accountID, String subjectID) throws Exception {
        List<AttemptDTO> result = null;
        int correctAmount;
        float grade;
        Timestamp submittedAt;
        AttemptDTO attempt;
        String sql = "Select SubmittedAt, CorrectAmount, Grade From " + TABLE_NAME + " Where AccountID = ? AND SubjectID = ? ORDER BY SubmittedAt DESC";
        try {
            conn=DBConnection.getConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1, accountID);
            ps.setString(2, subjectID);
            rs=ps.executeQuery();
            result = new ArrayList<>();
            while(rs.next()){
                submittedAt=rs.getTimestamp("SubmittedAt");
                correctAmount=rs.getInt("CorrectAmount");
                grade=rs.getFloat("Grade");
                attempt = new AttemptDTO(correctAmount, grade, submittedAt);
                result.add(attempt);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
