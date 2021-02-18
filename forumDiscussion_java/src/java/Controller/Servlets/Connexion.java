package Controller.Servlets;

import Model.Users;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Connexion", urlPatterns = {"/connexion"})
public class Connexion extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String email = request.getParameter("login");
        String pwd = request.getParameter("password");
        if(email == null || pwd == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/connexion.jsp");
            dispatcher.forward(request, response);
        } else {
            Users logged = Users.getUser(request.getParameter("login"), request.getParameter("password"));
            if(logged != null) {
                session.setAttribute("user", logged);
                response.sendRedirect(request.getContextPath() + "/auth/index");
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/connexion.jsp");
                request.setAttribute("failure", true);
                dispatcher.forward(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/connexion.jsp");
        dispatcher.forward(request, response);
    }

}
