package es.alber.dao;

import es.alber.entity.Fabricante;
import es.alber.entity.Producto;
import es.alber.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FabricanteDAO {
    /*Métodos de Acceso a la bbdd
    1. Guardar nuevo fabricante
    2. Listar fabricantes
    */
    public static void guardar(Fabricante fabricante) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(fabricante);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    public List<Fabricante> listar() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT f FROM Fabricante f ORDER BY f.codigo ASC", Fabricante.class)
                    .getResultList();
        }
    }

    public void borrar(int id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Fabricante fabricante = session.get(Fabricante.class, id);

            if (fabricante != null) {

                session.remove(fabricante);
                System.out.println("Fabricante eliminado.");
            } else {
                System.out.println("No existe un fabricante con ese ID.");
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void actualizar(Fabricante fabricante) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(fabricante);

            tx.commit();
            System.out.println("Fabricante actualizado correctamente.");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println(e.getMessage());
        }
    }

    public Fabricante buscarPorId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Fabricante.class, id);
        }
    }
    public static Fabricante buscarPorNombre(String nombreBusqueda) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Fabricante f WHERE f.nombre = :nombre", Fabricante.class)
                    .setParameter("nombre", nombreBusqueda)
                    .uniqueResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Fabricante buscarPorNombreConProductos(String nombreBusqueda) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT f FROM Fabricante f LEFT JOIN FETCH f.listaProductos WHERE LOWER(f.nombre) = LOWER(:nombre)";
            return session.createQuery(hql, Fabricante.class)
                    .setParameter("nombre", nombreBusqueda)
                    .uniqueResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}



