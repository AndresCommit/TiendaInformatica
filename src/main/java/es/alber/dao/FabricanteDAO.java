package es.alber.dao;

import es.alber.entity.Fabricante;
import es.alber.entity.Producto;
import es.alber.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class FabricanteDAO {
    /*Métodos de Acceso a la bbdd
    1. Guardar nuevo fabricante
    2. Listar fabricantes
    */
    public static void guardar(Fabricante fabricante) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            /*Comenzar la transacción*/
            tx = session.beginTransaction();
            /*Guardar el objeto en la BBDD*/
            session.persist(fabricante);
            session.createQuery("INSERT INTO Fabricante (nombre) VALUES (:nombre)",Fabricante.class).setParameter("nombre",fabricante.getNombre()).executeUpdate();
            tx.commit();
            System.out.println("Fabricante agregado correctamente...");
        }
        catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        }

        public void listar(Fabricante fabricante1, Producto producto) {
            Transaction tx = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                /*Comenzar la transacción*/
                tx = session.beginTransaction();
                /*Guardar el objeto en la BBDD*/
                List<Fabricante> fabricantes =
                        session.createQuery("SELECT f FROM Fabricante f",Fabricante.class)
                                .getResultList();
                for (Fabricante fabricante : fabricantes) {
                    System.out.println("Fabricante:\n"+fabricante.getNombre()+"\nProductos:");
                    System.out.println(producto.getNombre()+"\n");
                }
            }catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
            }
        }
        public void borrar(Fabricante fabricante) {
            Transaction tx = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                /*Comenzar la transacción*/
                tx = session.beginTransaction();
                /*Guardar el objeto en la BBDD*/
                session.persist(fabricante);
                tx.commit();
                session.createQuery("DELETE FROM Fabricante f WHERE f.codigo = :codigo").setParameter("codigo",fabricante.getCodigo()).executeUpdate();
                tx.commit();

            }catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
            }
        }
        public void actualizar(Fabricante fabricante) {
            Transaction tx = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                /*Comenzar la transacción*/
                tx = session.beginTransaction();
                /*Guardar el objeto en la BBDD*/
                session.persist(fabricante);
                session.createQuery("UPDATE Fabricante f SET f.nombre = :nombre WHERE f.codigo = :codigo").setParameter("nombre",fabricante.getNombre()).setParameter("codigo",fabricante.getCodigo()).executeUpdate();
                tx.commit();

            }catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
            }
        }
        public void buscar(Fabricante fabricante) {
            Transaction tx = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                /*Comenzar la transacción*/
                tx = session.beginTransaction();
                /*Guardar el objeto en la BBDD*/
                session.persist(fabricante);
                session.createQuery("INSERT INTO Fabricante (nombre) VALUES (:nombre)",Fabricante.class).setParameter("nombre",fabricante.getNombre()).executeUpdate();
                tx.commit();

            }catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
            }
        }
    }



