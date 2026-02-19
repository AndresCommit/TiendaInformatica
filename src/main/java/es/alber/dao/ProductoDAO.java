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
    public void actualizarPrecio(int id, double nuevoPrecio) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Producto producto = session.get(Producto.class, id);
            if (producto != null) {
                producto.setPrecio(nuevoPrecio);
                session.merge(producto); // Actualiza el objeto en BBDD
                System.out.println("Actualizado");
            } else {
                System.out.println("Error: No existe el producto con ID " + id);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    public void borrar(int id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Producto producto = session.get(Producto.class, id);
            if (producto != null) {
                session.remove(producto);
                System.out.println("Producto eliminado.");
            } else {
                System.out.println("Error: No existe un producto con ese ID.");
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }
    public List<Producto> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Producto", Producto.class).getResultList();
        }
    }
}