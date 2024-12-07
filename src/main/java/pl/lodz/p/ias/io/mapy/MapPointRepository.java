package pl.lodz.p.ias.io.mapy;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class MapPointRepository {
    SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    public void add(MapPoint mapPoint) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(mapPoint);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void remove(MapPoint mapPoint) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(mapPoint);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void update(MapPoint mapPoint) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(mapPoint);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void changeStatus(MapPoint mapPoint, boolean status) {
        mapPoint.setActive(status);
        update(mapPoint);
    }

    public MapPoint findById(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(MapPoint.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<MapPoint> getClients() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            List<MapPoint> points = session.createQuery("FROM MapPoint", MapPoint.class).getResultList();

            transaction.commit();
            return points;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return List.of();
    }
}
