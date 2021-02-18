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
import java.util.Map;

@WebServlet(name = "Register", urlPatterns = {"/register"})
public class Register extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> params = request.getParameterMap();

        Users U = new Users();
        try {
            U.setLogin(params.get("login")[0]);
            U.setPwd(params.get("password")[0]);
            U.setFirstname(params.get("firstname")[0]);
            U.setLastname(params.get("lastname")[0]);
            U.setGender(params.get("gender")[0]);
        } catch (NullPointerException e) {
            response.sendRedirect(request.getContextPath() + "/register");
        }

        U.setRole((byte)(-1));
        if(U.check() && U.persist()) {
            response.sendRedirect(request.getContextPath() + "/connexion?registered");
        } else {
            response.sendRedirect(request.getContextPath() + "/register?failed");

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/register.jsp");
        dispatcher.forward(request, response);
    }
}
