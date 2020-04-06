package UserManager;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Manager")
public class Manager extends HttpServlet {
    private static Hashtable<String, User> userTable = new Hashtable<>();
    private static final long serialVersionUID = 1L;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        getServletContext().setAttribute("users", userTable);
    }

    protected User getUser(HttpServletRequest request) {

        String fn = request.getParameter("firstname");
        String ln = request.getParameter("lastname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String gender = request.getParameter("gender");
        boolean admin = (request.getParameter("admin") != null);


        return new User(fn, ln, email, password, gender, admin);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/addUser.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User u = getUser(request);
        String email = u.getemail();
        System.out.println(u.toString());


        if (!u.isValid()) {
            response.sendRedirect("Register");
        } else if (userTable.containsKey(u.getemail()) && request.getParameter("enforce") == null) {
            request.setAttribute("fname", userTable.get(email).getfname());
            request.setAttribute("lname", userTable.get(email).getlname());
            request.setAttribute("email", userTable.get(email).getemail());
            request.setAttribute("gender", userTable.get(email).getgender());

            request.setAttribute("ufname", u.getfname());
            request.setAttribute("ulname", u.getlname());
            request.setAttribute("uemail", u.getemail());
            request.setAttribute("upass", u.getpassword());

            if (!u.isAdmin()) {
                request.setAttribute("uadmin", "disabled");
            }
            if (u.getgender().compareTo("Homme") == 0) {
                request.setAttribute("ismale", "checked");
                request.setAttribute("isfemale", "");
            } else {
                request.setAttribute("ismale", "");
                request.setAttribute("isfemale", "checked");
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/recap.jsp");
            dispatcher.forward(request, response);
        } else {
            userTable.put(email, u);
            response.sendRedirect("Connexion");
        }
    }
}
