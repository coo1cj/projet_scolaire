package Model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class MessagesPK implements Serializable {
    private int id;
    private int forum;

    @Column(name = "id", nullable = false)
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "forum", nullable = false)
    @Id
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
        MessagesPK that = (MessagesPK) o;
        return id == that.id &&
                forum == that.forum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, forum);
    }
}
