/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AccountDAO;
import dto.AccountDTO;
import dto.RegistrationErrorObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Mk
 */
public class InsertAccountController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(InsertAccountController.class);
    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "index.jsp";
    private static final String INVALID = "account/insertAccount.jsp";

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
        try {
            String email = request.getParameter("email");
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");

            RegistrationErrorObject errorObj = new RegistrationErrorObject();
            boolean valid = true;
            if (email.length() == 0) {
                errorObj.setEmailError("Please enter your email");
                valid = false;
            } else if (!email.matches("[\\w]+@[\\w]+(.[\\w]+)+")) {
                errorObj.setEmailError("Invalid email");
                valid = false;
            } else {
                AccountDAO dao = new AccountDAO();
                if (dao.isExisted(email)) {
                    errorObj.setEmailError("Account existed!");
                    valid = false;
                }
            }
            if (name.length() == 0) {
                errorObj.setNameError("Please enter your name");
                valid = false;
            }
            if (password.length() == 0) {
                errorObj.setPasswordError("Password can't be blank");
                valid = false;
            } else if (!confirmPassword.equals(password)) {
                errorObj.setConfirmError("Confirm Password mismatch");
                valid = false;
            }

            if (valid) {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
                password = utils.Conversion.toHex(hashedPassword);
                AccountDTO dto = new AccountDTO(email, name, password);
                AccountDAO dao = new AccountDAO();
                if (dao.addAccount(dto)) {
                    url = SUCCESS;
                    request.setAttribute("ALERT", "Account created successfully! Please verify your email to login!");
                } else {
                    request.setAttribute("ERROR", "Insert failed");
                }
            } else {
                url = INVALID;
                request.setAttribute("ERROROBJ", errorObj);
            }

        } catch (Exception e) {
            String error = "";
            for (int i = 0; i < e.getStackTrace().length; i++) {
                error += (e.getStackTrace()[i] + "\n");
            }
            logger.error(error);
            if (e.getMessage().contains("duplicate")) {
                url = INVALID;
                RegistrationErrorObject errorObj = new RegistrationErrorObject();
                errorObj.setEmailError("Account existed");
                request.setAttribute("ERROROBJ", errorObj);
            }
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
