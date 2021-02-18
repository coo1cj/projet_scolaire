package Model;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;

public abstract class PersistenceBase {

    public boolean persist() {
        Session session = FactoryInstance.getSession();
        Transaction transaction;

        try {
            transaction = session.beginTransaction();
        } catch (HibernateException e) {
            return false;
        }

        try {
            session.persist(this);
            transaction.commit();
        } catch (PersistenceException e) {
            if(transaction != null) transaction.rollback();
            return false;
        }
        finally {
            session.close();
        }

        return true;
    }

    public boolean merge() {
        Session session = FactoryInstance.getSession();
        Transaction transaction;

        try {
            transaction = session.beginTransaction();
        } catch (HibernateException e) {
            session.close();
            return false;
        }

        try {
            session.merge(this);
            transaction.commit();
        } catch (PersistenceException e) {
            if(transaction != null) transaction.rollback();
            return false;
        } finally {
            session.close();
        }

        return true;
    }

    public void delete() {
        Session session = FactoryInstance.getSession();
        Transaction transaction;

        try {
            transaction = session.beginTransaction();
        } catch (HibernateException e) {
            session.close();
            return;
        }

        try {
            session.delete(this);
            transaction.commit();
        } catch (PersistenceException e) {
            if(transaction != null) transaction.rollback();
        } finally {
            session.close();
        }
    }

}
