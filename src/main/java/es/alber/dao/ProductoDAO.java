package es.alber.dao;

import es.alber.entity.Producto;
import es.alber.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProductoDAO {
    public void guardar(Producto producto) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            session.persist(producto);

            tx.commit();
            System.out.println("Producto guardado correctamente.");
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Error real: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public List<Producto> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Producto", Producto.class).getResultList();
        }
    }
}