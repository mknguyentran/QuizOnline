/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.QuestionDAO;
import dao.SubjectDAO;
import dto.QuestionDTO;
import dto.QuestionErrorObject;
import dto.SubjectDTO;
import java.io.IOException;
import java.sql.Timestamp;
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
public class CreateQuestionController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CreateQuestionController.class);
    private static final String PREPARE = "admin/createQuestion.jsp";
    private static final String INVALID = "admin/createQuestion.jsp";
    private static final String SUCCESS = "SearchQuestionController";
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
        String url = ERROR;
        String action = request.getParameter("action");
        try {
            if (action.equals("prepare")) {
                SubjectDAO sDAO = new SubjectDAO();
                List<SubjectDTO> subjectList = sDAO.getSubjectList();
                request.setAttribute("SUBJECT_LIST", subjectList);
                url = PREPARE;
            } else if (action.equals("Add")) {
                QuestionErrorObject error = new QuestionErrorObject();
                String question = request.getParameter("question");
                String answer1 = request.getParameter("answer1");
                String answer2 = request.getParameter("answer2");
                String answer3 = request.getParameter("answer3");
                String answer4 = request.getParameter("answer4");
                String subjectID = request.getParameter("subjectID");
                int correctAnswer = Integer.parseInt(request.getParameter("correctAnswer"));
                Timestamp createdAt = new Timestamp(System.currentTimeMillis());
                if(subjectID.isEmpty()){
                    error.setSubjectIDError("Please choose a subject");
                    error.setEmpty(false);
                    url = INVALID;
                }
                if (error.isEmpty()) {
                    QuestionDTO q = new QuestionDTO(question, answer1, answer2, answer3, answer4, subjectID, correctAnswer, createdAt);
                    QuestionDAO qDAO = new QuestionDAO();
                    if (qDAO.addQuestion(q)) {
                        url = SUCCESS;
                    }
                } else {
                    request.setAttribute("ERROR", error);
                }
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
