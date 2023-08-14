package HotelDAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction; // Import Transaction class

import Model.Room;

import java.util.List;

public class RoomDAOImpl implements GenericDAO<Room, Long>{
    private SessionFactory sessionFactory;

    public RoomDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Room getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Room.class, id);
        }
    }

    @Override
    public List<Room> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Room", Room.class).getResultList();
        }
    }

    @Override
    public void save(Room room) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(room);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void update(Room room) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(room);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Room room) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(room);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
