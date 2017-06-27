/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dusa.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import org.dusan.user.User;
import org.dusan.user.UserWS_Service;

/**
 *
 * @author dusanmatejka
 */
@WebServlet(name = "UserServlet", urlPatterns = {"/UserServlet"})
public class UserServlet extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/UserWS/UserWS.wsdl")
    private UserWS_Service service;

    protected void processRequestGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserServlet</title>");
            out.println("</head>");
            out.println("<body>");
            List<User> users = getUsers();
            for(User user: users){
               out.println(user.getFirst() + " " + user.getLast() + "<br>");
            }
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    protected void processRequestPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserServlet</title>");
            out.println("</head>");
            out.println("<body>");
            if (saveUser(request)) {
                out.println("Saved");
            } else {
                out.println("You need to post \"first\" and \"last\" both java.langString");
            }
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequestGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequestPost(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private java.util.List<org.dusan.user.User> getUsers() {
        org.dusan.user.UserWS port = service.getUserWSPort();
        return port.getUsers();
    }

    private boolean saveUser(HttpServletRequest request) {
        String first = request.getParameter("first");
        String last = request.getParameter("last");
        if (first != null  && last != null){
            addUser((String) first, (String) last);
            return true;
        }
        return false;
    }

    private void addUser(java.lang.String first, java.lang.String last) {
        org.dusan.user.UserWS port = service.getUserWSPort();
        port.addUser(first, last);
    }


}
