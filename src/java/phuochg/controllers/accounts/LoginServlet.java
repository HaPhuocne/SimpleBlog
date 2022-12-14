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
import phuochg.utils.encrypted;

/**
 *
 * @author Phước Hà
 */
public class LoginServlet extends HttpServlet {

    private static final String LOGIN_PAGE = "loginPage";
    private static final String HOME_PAGE = "";

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

        String url = LOGIN_PAGE;
        ServletContext context = request.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITE_MAP");
        url = (String) siteMap.get(url);
        try {

            String Username = request.getParameter("Username");
            String Password = request.getParameter("Password");

            AccountDAO AccDao = new AccountDAO();

            AccountDTO acc = AccDao.login(Username, encrypted.encryptedPassword(Password));
            HttpSession session = request.getSession();
            String msg = "";
            if (acc == null) {
                msg = "Email and Password not match";
            } else {
                if (acc.getStatus().equals("New")) {
                    msg = "The account not Active!";
                    url = (String) siteMap.get(LOGIN_PAGE);
                } else {

                    if (session.getAttribute("LOAD_URL") != null) {
                        session.setAttribute("ACC", acc);
                        url = (String) session.getAttribute("LOAD_URL");
                    } else {
                        session.setAttribute("ACC", acc);
                        url = (String) siteMap.get(HOME_PAGE);
                    }

                }
            }
            request.setAttribute("LOGIN_MSG", msg);
            //get MAP

        } catch (Exception e) {
            log("Error at LoginServlet:" + e.toString());
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
