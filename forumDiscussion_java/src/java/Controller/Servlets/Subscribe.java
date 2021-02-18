package Controller.Servlets;

import Model.Subscriptions;
import Model.SubscriptionsPK;
import Model.Users;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Subscribe", urlPatterns = {"/auth/subscribe", "/auth/unsubscribe"})
public class Subscribe extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/plain");

        if(request.getParameter("id") == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().flush();
            return;
        }

        int id;

        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().flush();
            return;
        }

        HttpSession session = request.getSession();
        Users U = (Users)session.getAttribute("user");

        String uri = request.getRequestURI();
        uri = uri.substring(request.getContextPath().length());

        if(uri.compareTo("/auth/subscribe") == 0) { // subscribing
            Subscriptions sub = new Subscriptions();
            sub.setUser(U.getLogin());
            sub.setForum(id);

            if(sub.persist()) {
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

        } else { // unsubscribing
            SubscriptionsPK pk = new SubscriptionsPK();
            pk.setUser(U.getLogin());
            pk.setForum(id);
            Subscriptions sub = Subscriptions.get(pk);
            if(sub != null) {
                sub.deleteSub();
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        response.getWriter().flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }
}
