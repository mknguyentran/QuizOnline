/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.AnswerDTO;
import java.util.List;
import utils.DBConnection;

/**
 *
 * @author Mk
 */
public class AnswerDAO extends DAO {

    public static final String TABLE_NAME = "Answer";

    public boolean submitAnswers(int attemptID, List<AnswerDTO> answerList) throws Exception {
        boolean successful = false;
        int[] result = null;
        String sql = "Insert into " + TABLE_NAME + "(AttemptID, QuestionID, Answer) values (?,?,?)";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < answerList.size(); i++) {
                ps.setInt(1, attemptID);
                ps.setInt(2, answerList.get(i).getQuestionID());
                ps.setInt(3, answerList.get(i).getAnswer());
                ps.addBatch();
            }
            result = ps.executeBatch();
            successful = true;
            for (int i = 0; i < result.length; i++) {
                if (result[i] != 1) {
                    successful = false;
                    break;
                }
            }
        } finally {
            closeConnection();
        }
        return successful;
    }

}
