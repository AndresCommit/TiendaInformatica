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

    public void listar(Fabricante fabricante1, Producto producto) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            /*Comenzar la transacción*/
            tx = session.beginTransaction();
            /*Guardar el objeto en la BBDD*/
            List<Fabricante> fabricantes =
                    session.createQuery("SELECT f FROM Fabricante f", Fabricante.class)
                            .getResultList();
            for (Fabricante fabricante : fabricantes) {
                System.out.println("----------------------------");
                System.out.println("ID Del Fabricante: "+fabricante.getCodigo()+"\nFabricante:" + fabricante.getNombre());
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
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

    public static Fabricante buscar(Fabricante fabricante) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            /*Comenzar la transacción*/
            tx = session.beginTransaction();
            /*Guardar el objeto en la BBDD*/
            session.createQuery("SELECT f FROM Fabricante f WHERE f.nombre = :nombre").setParameter("nombre", fabricante.getNombre()).getResultList();
            System.out.println("ID del fabricante con nombre: "+fabricante.getNombre()+", "+fabricante.getCodigo());
            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return fabricante;
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
    public Fabricante buscarFabricantePorNombreProducto(String nombreProducto) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String query = "SELECT f FROM Fabricante f JOIN f.listaProductos p WHERE LOWER(p.nombre) = LOWER(:nombreProd)";
            session.createQuery(query, Fabricante.class)
                    .setParameter("nombreProd", nombreProducto)
                    .uniqueResult();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}



