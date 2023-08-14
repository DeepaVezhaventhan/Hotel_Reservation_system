package HotelDAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction; // Import Transaction class

import Model.Reservation;

import java.util.List;

public class ReservationDAOImpl implements GenericDAO<Reservation, Long> {
    private SessionFactory sessionFactory;

    public ReservationDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Reservation getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Reservation.class, id);
        }
    }

    @Override
    public List<Reservation> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Reservation", Reservation.class).getResultList();
        }
    }

    @Override
    public void save(Reservation reservation) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(reservation);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void update(Reservation reservation) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(reservation);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Reservation reservation) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(reservation);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
