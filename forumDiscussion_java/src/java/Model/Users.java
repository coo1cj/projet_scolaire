package Model;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Users extends PersistenceBase {
    private String login;
    private String firstname;
    private String lastname;
    private String gender;
    private Byte role;
    private String pwd;

    public Users() {}


    public Users(String login, String firstname, String lastname, String gender, boolean role, String pwd) {
        this.login = login;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        if(role) {
            this.role = 1;
        } else {
            this.role = 0;
        }
        this.pwd = pwd;
    }

    @Id
    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login.toLowerCase();
    }

    @Basic
    @Column(name = "firstname")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "lastname")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "role")
    public Byte getRole() {
        return role;
    }

    public void setRole(Byte role) {
        this.role = role;
    }

    @Basic
    @Column(name = "pwd")
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean authenticate(String pwd) {
        return this.pwd.equals(pwd);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(login, users.login) &&
                Objects.equals(firstname, users.firstname) &&
                Objects.equals(lastname, users.lastname) &&
                Objects.equals(gender, users.gender) &&
                Objects.equals(role, users.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, firstname, lastname, gender, role);
    }

    public static Users getUser(String login) {
        if(login == null) {
            return null;
        }

        Session session = FactoryInstance.getSession();
        Users U;

        try {
            U = session.get(Users.class, login.toLowerCase());
        } catch (HibernateException e) {
            return null;
        } finally {
            session.close();
        }

        return U;
    }

    @SuppressWarnings("unchecked")
    public static List<Users> getUsers() {
        Session session = FactoryInstance.getSession();

        String hql = "FROM Users";
        Query query = session.createQuery(hql);
        List<Users> usersList = query.getResultList();
        session.close();

        return usersList;
    }

    public static Users getUser(String login, String pwd) {
        if(login == null || pwd == null) {
            return null;
        }

        Session session = FactoryInstance.getSession();
        String hql = "FROM Users U WHERE U.login = :login AND U.pwd = :pwd";
        Query query = session.createQuery(hql);

        query.setParameter("login", login.toLowerCase());
        query.setParameter("pwd", pwd);
        List<?> result = query.getResultList();
        if (result.size() < 1) {
            session.close();
            return null;
        } else {
            session.close();
            return (Users) result.get(0);
        }
    }

    public boolean admin() {
        return (role > 1);
    }

    public boolean user() {
        return (role >= 1);
    }

    public boolean check() {
        if(login == null || pwd == null || firstname == null || lastname == null || gender == null) {
            return false;
        }

        if(login.length() < 5 || pwd.length() < 6 || firstname.length() < 1 || lastname.length() < 1) {
            return false;
        }

        if(!gender.equals("Male") && !gender.equals("Female")) {
            return false;
        }

        return true;
    }

    @Override
    public void delete() {
        Session session = FactoryInstance.getSession();
        Transaction transaction;

        try {
            transaction = session.beginTransaction();
        } catch (HibernateException e) {
            session.close();
            return;
        }

        String shql = "DELETE FROM Subscriptions WHERE user = :user";

        Query subscriptions = session.createQuery(shql);
        subscriptions.setParameter("user", this.login);

        try {
            subscriptions.executeUpdate();
            session.delete(this);
            transaction.commit();
        } catch (PersistenceException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

}
