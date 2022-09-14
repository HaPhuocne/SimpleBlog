/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuochg.controllers.accounts;

import java.io.IOException;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import phuochg.account.AccountDAO;
import phuochg.account.AccountDTO;
import phuochg.users.UserDAO;
import phuochg.users.UserDTO;
import phuochg.utils.encrypted;

/**
 *
 * @author Phước Hà
 */
public class CreateAccountServlet extends HttpServlet {

    private static final String CREATE_PAGE = "registerPage";
    private static final String CONFIRM_CODE = "confirmCodePage";
    private static final String LOGIN_PAGE = "loginPage";

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
        String url = CREATE_PAGE;

        ServletContext context = request.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITE_MAP");
        url = (String) siteMap.get(url);
        try {
            boolean check = true;
            String msg = "";
            String email = request.getParameter("txtEmail");
            String password = request.getParameter("txtPassword");
            String rePassword = request.getParameter("txtRePassword");
            String name = request.getParameter("txtName");
            String phone = request.getParameter("txtPhoneNumber");
            HttpSession session = request.getSession();

            AccountDAO accDao = new AccountDAO();
            UserDAO userDao = new UserDAO();

            if (!password.equals(rePassword)) {
                check = false;
                msg = "The Password and Repassword must match!";
            }
            if (accDao.checkExistEmail(email)) {
                check = false;
                msg = "The Account Existed, Choose new Account!";
            }
            if (check) {
                AccountDTO acc = new AccountDTO(email, encrypted.encryptedPassword(password), "US", "Active");
                UserDTO user = new UserDTO(email, name, phone);
                accDao.insertAccount(acc);
                userDao.insertUser(user);
                msg = "Create success, Please Login !";
                url = (String) siteMap.get(LOGIN_PAGE);
            } else {
                url = (String) siteMap.get(CREATE_PAGE);
            }
            request.setAttribute("CREATEACCOUNT_MSG", msg);

        } catch (Exception e) {
            log("Error at CreateAccountServlet:" + e.toString());
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
