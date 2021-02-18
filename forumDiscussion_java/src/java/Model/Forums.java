package Model;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Forums extends PersistenceBase {
    private int id;
    private String title;
    private String description;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 100)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 1024)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Forums forums = (Forums) o;
        return id == forums.id &&
                Objects.equals(title, forums.title) &&
                Objects.equals(description, forums.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description);
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

        String mhql = "DELETE FROM Messages WHERE forum = :id";
        String shql = "DELETE FROM Subscriptions WHERE forum = :id";

        Query messages = session.createQuery(mhql);
        messages.setParameter("id", this.id);
        Query subscriptions = session.createQuery(shql);
        subscriptions.setParameter("id", this.id);

        try {
            messages.executeUpdate();
            subscriptions.executeUpdate();
            session.delete(this);
            transaction.commit();
        } catch (PersistenceException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public static Forums getForum(int id) {
        if(id < 0) {
            return null;
        }

        Session session = FactoryInstance.getSession();
        Forums F;

        try {
            F = session.get(Forums.class, id);
        } catch (HibernateException e) {
            return null;
        } finally {
            session.close();
        }

        return F;
    }

    @SuppressWarnings("unchecked")
    public static List<Forums> getList() {
        Session session = FactoryInstance.getSession();

        String hql = "FROM Forums";
        Query query = session.createQuery(hql);
        List<Forums> forumsList = query.getResultList();
        session.close();

        return forumsList;
    }
}
