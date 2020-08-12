/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AnswerDAO;
import dao.AttemptDAO;
import dto.AnswerDTO;
import dto.AttemptDTO;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Mk
 */
public class SubmitQuizController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SubmitQuizController.class);
    private static final String SUCCESS = "LoadQuizDetailController";
    private static final String ERROR = "error.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int questionAmount, attemptID;
        String subjectID, accountID;
        AnswerDTO a;
        List<AnswerDTO> answerList;
        String url = ERROR;
        try {
            subjectID = request.getParameter("subjectID");
            accountID = (String) request.getSession(false).getAttribute("EMAIL");
            Timestamp now = new Timestamp(System.currentTimeMillis());
            AttemptDTO attempt = new AttemptDTO(accountID, subjectID, now);
            AttemptDAO atDAO = new AttemptDAO();
            if ((attemptID = atDAO.newAttempt(attempt)) > 0) {
                questionAmount = Integer.parseInt(request.getParameter("questionAmount"));
                answerList = new ArrayList<AnswerDTO>();
                for (int i = 1; i <= questionAmount; i++) {
                    int questionID, answer;
                    questionID = Integer.parseInt(request.getParameter("q" + i));
                    String answerData = request.getParameter("a" + i);
                    if (answerData != null) {
                        answer = Integer.parseInt(answerData);
                    } else {
                        answer = 0;
                    }
                    a = new AnswerDTO(questionID, answer);
                    answerList.add(a);
                }
                AnswerDAO anDAO = new AnswerDAO();
                if (anDAO.submitAnswers(attemptID, answerList)) {
                    url = SUCCESS;
                    request.setAttribute("ALERT", "Quiz submitted successfully");
                } else {
                    request.setAttribute("ERROR", "Error occured while submitting the quiz");
                }
            } else {
                request.setAttribute("ERROR", "Error occured while recording your attempt");
            }
        } catch (Exception e) {
            String error = "";
            for (int i = 0; i < e.getStackTrace().length; i++) {
                error += (e.getStackTrace()[i] + "\n");
            }
            logger.error(error);
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
