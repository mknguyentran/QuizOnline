/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AccountDAO;
import dto.RegistrationErrorObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author Mk
 */
public class LoginController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(LoginController.class);
    private static final String ERROR = "error.jsp";
    private static final String ADMIN = "SearchQuestionController";
    private static final String STUDENT = "SearchSubjectController";
    private static final String INVALID = "index.jsp";
    private static final String LOGOUT = "index.jsp";

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
            String action = request.getParameter("action");
            if (action.equals("Login")) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                RegistrationErrorObject errorObj = new RegistrationErrorObject();
                boolean valid = true;
                if (email.length() == 0) {
                    errorObj.setEmailError("Please enter your email");
                    valid = false;
                }
                if (password.length() == 0) {
                    errorObj.setPasswordError("Please enter your password");
                    valid = false;
                }
                if (valid) {
                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
                    password = utils.Conversion.toHex(hashedPassword);
                    String role;
                    AccountDAO dao = new AccountDAO();
                    role = dao.checkLogin(email, password);
                    if (role.equals("invalid")) {
                        request.setAttribute("ALERT", "Username or Password is incorrect");
                        url = INVALID;
                    } else {
                        String status = dao.getAccountStatus(email);
                        if (status.equals("active")) {
                            String name = dao.getAccountName(email);
                            HttpSession session = request.getSession();
                            session.setAttribute("EMAIL", email);
                            session.setAttribute("NAME", name);
                            session.setAttribute("ROLE", role);
                            if (role.equals("admin")) {
                                logger.info("admin " + email + " login successfully");
                                url = ADMIN;
                            } else if (role.equals("student")) {
                                logger.info("student " + email + " login successfully");
                                url = STUDENT;
                            } else {
                                logger.error("User with invalid role tried to log in (" + email + ")");
                                request.setAttribute("ERROR", "Your role is invalid");
                            }
                        } else if (status.equals("new")) {
                            logger.info("User " + email + " login failed because their email has not been verified");
                            request.setAttribute("ALERT", "Please verify your email before loging in!");
                            url = INVALID;
                        }
                    }
                } else {
                    url = INVALID;
                    request.setAttribute("INVALID", errorObj);
                }
            } else if (action.equals("Logout")) {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                }
                request.setAttribute("ALERT", "See you again!");
                url = LOGOUT;
            } else {
                request.setAttribute("ERROR", "Invalid Action");
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
