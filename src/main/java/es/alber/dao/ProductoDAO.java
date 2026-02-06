package es.alber.dao;

import es.alber.entity.Fabricante;
import es.alber.entity.Producto;
import es.alber.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProductoDAO {
    public static void guardar(Producto producto, String nombreFabricante) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            /*Comenzar la transacción*/
            tx = session.beginTransaction();
            /*Guardar el objeto en la BBDD*/
            session.persist(producto);
            session.createQuery("INSERT INTO Producto (nombre) VALUES (:nombre)", Producto.class)
                    .setParameter("nombre", producto.getNombre()).executeUpdate();
            session.createQuery("INSERT INTO Producto (precio) VALUES (:precio)", Producto.class)
                    .setParameter("precio", producto.getPrecio()).executeUpdate();

            tx.commit();
            System.out.println("Fabricante agregado correctamente...");
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }
    public void actualizarPrecio(Producto producto) {
        Producto producto1 = new Producto();


    }
    public void guardarProductoJuntoFabricante(Producto producto, String nombreFabricante) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            String consulta = "SELECT f FROM Fabricante f WHERE LOWER(f.nombre) = LOWER(:nombre)";
            FabricanteDAO.buscar(new Fabricante());
            List<Fabricante> fabricantes = session.createQuery(consulta, Fabricante.class)
                    .setParameter("nom", nombreFabricante)
                    .getResultList();
            Fabricante fabricante;

            if (fabricantes.isEmpty()) {
                fabricante = fabricantes.get(0);
            } else {
                fabricante = new Fabricante(nombreFabricante);
                session.persist(fabricante);
            }
        }
    }
}


