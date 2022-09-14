/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuochg.controllers.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import phuochg.account.AccountDTO;
import phuochg.article.ArticleDAO;

/**
 *
 * @author Phước Hà
 */
public class AcceptRequestServlet extends HttpServlet {

    private static final String HOME_PAGE_ADMIN = "homeForAdmin";
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
        String url = HOME_PAGE_ADMIN;
        ServletContext context = request.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITE_MAP");
        url = (String) siteMap.get(url);
        try {

            HttpSession session = request.getSession();
            AccountDTO acc = (AccountDTO) session.getAttribute("ACC");
            String msg = "";

            if (acc == null) {
                url = (String) siteMap.get(LOGIN_PAGE);
                msg = "You need Login To Active!";
                request.setAttribute("UPDATE_MSG", msg);
            } else {
                ArticleDAO articleDao = new ArticleDAO();
                String titleId = request.getParameter("titleId");
                if (articleDao.updateArticle("Active", titleId)) {
                    msg = "Delete Success full";
                    url = "SearchServlet?searchValue=&option=New";
                } else {
                    msg = "Delete Fail";
                }
                request.setAttribute("UPDATE_MSG", msg);
            }
        } catch (Exception e) {
            log("Error at AcceptRequestServlet: " + e.toString());
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
