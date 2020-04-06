package UserManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;

@WebServlet(name = "Connexion")
public class Connexion extends HttpServlet {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    protected User getUser(String login) {
        @SuppressWarnings(value = "unchecked")
        Hashtable<String, User> users = (Hashtable<String, User>) getServletContext().getAttribute("users");
        if (users == null) {
            return null;
        } else {
            return users.getOrDefault(login, null);
        }
    }

    protected String getDate(Cookie[] cookies) {
        for (Cookie c : cookies) {
            if (c.getName().compareTo("sr03") == 0) {
                return URLDecoder.decode(c.getValue(), StandardCharsets.UTF_8);
            }
        }

        return null;
    }

    protected boolean checkSession(HttpSession session) {
        String login = (String) session.getAttribute("login");
        return login != null && login.length() != 0 && getUser(login) != null;
    }

    protected void sendHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = (String) request.getSession().getAttribute("login");
        User user = getUser(login);
        request.setAttribute("firstname", user.getfname());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/home.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (checkSession(request.getSession())) {
            sendHome(request, response);
            return;
        }

        String login = request.getParameter("email");
        String pass = request.getParameter("password");

        if (login == null || login.length() == 0) {
            response.sendRedirect("Connexion");
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/login.jsp");
        User client = getUser(login);
        if (client == null) {
            System.out.println("EMAIL");
            request.setAttribute("bademail", "true");
            dispatcher.forward(request, response);
        } else {
            if (pass.compareTo(client.getpassword()) != 0) {
                request.setAttribute("badpass", "true");
                dispatcher.forward(request, response);
            } else {
                HttpSession session = request.getSession();
                String last = getDate(request.getCookies());
                session.setAttribute("last", last);
                Cookie time = new Cookie("sr03", URLEncoder.encode(LocalDateTime.now().format(formatter), StandardCharsets.UTF_8));
                response.addCookie(time);

                session.setAttribute("login", login);
                session.setAttribute("user", client);
                if (client.isAdmin()) {
                    session.setAttribute("userList", getServletContext().getAttribute("users"));
                }
                sendHome(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (checkSession(request.getSession())) {
            sendHome(request, response);
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/login.jsp");
        dispatcher.forward(request, response);
    }
}
