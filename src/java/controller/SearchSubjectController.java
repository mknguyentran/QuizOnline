/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.SubjectDAO;
import dto.SubjectDTO;
import java.io.IOException;
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
public class SearchSubjectController extends HttpServlet {
    
    private static final Logger logger = Logger.getLogger(SearchSubjectController.class);
    private static final String SUCCESS = "student/studentHome.jsp";

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
        String url = SUCCESS;
        String search = request.getParameter("search");
        String searchBy = request.getParameter("searchBy");
        String p = request.getParameter("page");
        int page = 1, pagesAmount;
        if (search == null) {
            search = "";
        }
        if (searchBy == null) {
            searchBy = "Name";
        } else if (searchBy.isEmpty()) {
            searchBy = "Name";
        }
        
        if (searchBy.equals("Name")) {
            request.setAttribute("searchTerm", search);
        }
        if (p != null) {
            page = Integer.parseInt(p);
        }
        System.out.println("page = " + page);
        System.out.println("p = " + p);
        try {
            SubjectDAO sDAO = new SubjectDAO();
            List<SubjectDTO> subjectList = sDAO.searchSubject(search, searchBy, page);
            request.setAttribute("SUBJECT_LIST", subjectList);
            List<String> categoryList = sDAO.getCategoryList();
            request.setAttribute("CATEGORY_LIST", categoryList);
            pagesAmount = sDAO.getPagesAmount(search, searchBy);
            request.setAttribute("PAGES_AMOUNT", pagesAmount);
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
