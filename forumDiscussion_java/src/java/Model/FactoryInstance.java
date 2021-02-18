package Model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryInstance {

    private static SessionFactory factoryObject;

    private FactoryInstance() {
        factoryObject = new Configuration().configure().buildSessionFactory();
    }

    public static Session getSession() {
        init();
        return factoryObject.openSession();
    }

    @SuppressWarnings("InstantiationOfUtilityClass")
    public static void init() {
        if (factoryObject == null) {
            new FactoryInstance();
        }
    }

}
