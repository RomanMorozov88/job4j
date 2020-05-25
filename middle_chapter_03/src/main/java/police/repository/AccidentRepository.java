package police.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import police.model.Accident;

import java.util.List;

/**
 * Класс для работы с базой данных
 */
public class AccidentRepository implements RepoInterface {

    private static final AccidentRepository INSTANCE = new AccidentRepository();
    private static final SessionFactory FACTORY = new Configuration()
            .configure()
            .buildSessionFactory();

    private AccidentRepository() {
    }

    public static AccidentRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Accident> getAccidents() {
        Session session = FACTORY.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            List<Accident> result = session.createQuery("from Accident").list();
            transaction.commit();
            return result;
        } catch (final Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void close() {
        FACTORY.close();
    }
}