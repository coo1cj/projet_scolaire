package Model;

import org.hibernate.Session;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@IdClass(MessagesPK.class)
public class Messages extends PersistenceBase {
    private int id;
    private String content;
    private Timestamp time;
    private int forum;
    private String user;

    public static Messages get(MessagesPK pk) {
        if(pk == null || pk.getForum() < 0 || pk.getId() < 0) {
            return null;
        }

        Session session = FactoryInstance.getSession();
        Messages msg = session.get(Messages.class, pk);
        session.close();
        return msg;
    }

    @SuppressWarnings("unchecked")
    public static List<Messages> getMessages(int forum) {
        if(forum < 0) {
            return null;
        }

        Session session = FactoryInstance.getSession();
        String hql = "FROM Messages WHERE forum = :forum";
        Query query = session.createQuery(hql);

        query.setParameter("forum", forum);
        List<Messages> messagesList = (List<Messages>) query.getResultList();
        session.close();
        return messagesList;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "content", nullable = false, length = 1024)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "time", nullable = false)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
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
        Messages messages = (Messages) o;
        return id == messages.id &&
                forum == messages.forum &&
                Objects.equals(content, messages.content) &&
                Objects.equals(time, messages.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, time, forum);
    }

    @Basic
    @Column(name = "user", length = 100)
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
