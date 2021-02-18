package Model;

import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@IdClass(SubscriptionsPK.class)
public class Subscriptions extends PersistenceBase {
    private String user;
    private int forum;

    @Id
    @Column(name = "user", nullable = false, length = 100)
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Id
    @Column(name = "forum", nullable = false)
    public int getForum() {
        return forum;
    }

    public void setForum(int forum) {
        this.forum = forum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscriptions that = (Subscriptions) o;
        return forum == that.forum &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, forum);
    }

    @SuppressWarnings("unchecked")
    public static List<Forums> getSubscriptions(String user) {
        if(user == null) {
            return null;
        }

        Session session = FactoryInstance.getSession();
        String hql = "SELECT F FROM Subscriptions S INNER JOIN Forums F ON S.forum = F.id WHERE S.user = :user";
        Query query = session.createQuery(hql);

        query.setParameter("user", user);
        return (List<Forums>) query.getResultList();
    }


    public static Subscriptions get(SubscriptionsPK key) {
        Session session = FactoryInstance.getSession();
        Subscriptions sub = session.get(Subscriptions.class, key);
        session.close();
        return sub;
    }

    public boolean deleteSub() {
        Session session = FactoryInstance.getSession();
        String hql = "DELETE Subscriptions WHERE user = :user AND forum = :forum";
        Query query = session.createQuery(hql);
        query.setParameter("user", this.user);
        query.setParameter("forum", this.forum);

        Transaction transaction = session.beginTransaction();
        try {
            query.executeUpdate();
            transaction.commit();
        } catch (PersistenceException e) {
            transaction.rollback();
            return false;
        }

        return true;
    }
}
