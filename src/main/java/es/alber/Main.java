package es.alber;

import es.alber.dao.FabricanteDAO;
import es.alber.dao.ProductoDAO;
import es.alber.entity.Fabricante;
import es.alber.entity.Producto;

import java.util.Scanner;

public class Main {
    static FabricanteDAO fabricanteDAO = new FabricanteDAO();
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        int opcion = -1;

        do {
            System.out.println("\n=== MENU TIENDA INFORMATICA ===");
            System.out.println("1. Fabricantes");
            System.out.println("2. Productos");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            try {
                opcion = entrada.nextInt();
            } catch (Exception e) {
                opcion = -1;
            }
            switch (opcion) {
                case 1:
                    int opcionFabricante = 0;
                    menuFabricantes(entrada);
                    break;

                case 2:
                    menuProductos(entrada);
                    break;

                case 0:
                    System.out.println("Gracias por usar la Tienda Informatica");
                    break;

                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
        } while (opcion != 0);

        entrada.close();
    }

    private static void menuProductos(Scanner entrada) {
        int opcionProd;
        ProductoDAO productoDAO = new ProductoDAO();
        do {
            System.out.println("\n   === GESTIÓN DE PRODUCTOS ===");
            System.out.println("   1. Crear");
            System.out.println("   2. Leer / Buscar");
            System.out.println("   3. Actualizar Precio");
            System.out.println("   4. Borrar");
            System.out.println("   9. Volver");
            System.out.print("   Elige: ");
            opcionProd = entrada.nextInt();

            switch (opcionProd) {
                case 1:
                    crearProducto(entrada, productoDAO);
                    break;
                case 2:
                    for (Producto p : productoDAO.listarTodos()) {
                        System.out.println(p.getCodigo() + ": " + p.getNombre() + " | " + p.getPrecio() + " | " + p.getFabricante().getNombre());
                    }
                    break;
                case 3:
                    // Mostramos la lista antes de pedir el ID
                    for (Producto p : productoDAO.listarTodos()) {
                        System.out.println(p.getCodigo() + ": " + p.getNombre() + " " + p.getPrecio() + " " + p.getFabricante().getNombre());
                    }
                    System.out.print("Introduce el ID del producto a modificar: ");
                    int idActualizar = entrada.nextInt();
                    System.out.print("Introduce el nuevo precio: ");
                    double nuevoPrecio = entrada.nextDouble();
                    productoDAO.actualizarPrecio(idActualizar, nuevoPrecio);
                    break;
                case 4:
                    System.out.print("Introduce el ID del producto a borrar: ");
                    int idBorrar = entrada.nextInt();
                    productoDAO.borrar(idBorrar);
                    break;
            }
        } while (opcionProd != 9);
    }

    private static void crearProducto(Scanner entrada, ProductoDAO dao) {
        entrada.nextLine();
        System.out.println("CREAR PRODUCTO");
        System.out.print("Nombre del producto: ");
        String nombre = entrada.nextLine();
        System.out.print("Precio: ");
        double precio = entrada.nextDouble();
        entrada.nextLine();

        System.out.println("Fabricantes disponibles:");
        for(Fabricante f : fabricanteDAO.listar()) {
            System.out.println(f.getNombre());
        }

        System.out.print("Nombre del fabricante (si no existe, se creará): ");
        String nombreFab = entrada.nextLine().trim();

        Fabricante fab = FabricanteDAO.buscarPorNombre(nombreFab);

        if (fab == null) {
            fab = new Fabricante(nombreFab);
            FabricanteDAO.guardar(fab);
        }

        Producto p = new Producto(nombre, precio);
        p.setFabricante(fab);
        dao.guardar(p);
        System.out.println("Operación finalizada con éxito.");
    }


    private static void crearFabricante() {
        Scanner entrada = new Scanner(System.in);
        System.out.print("Introduce el nombre del fabricante: ");
        String nombre = entrada.nextLine().trim();

        if (nombre.isEmpty()) {
            System.out.println("El nombre está vacío.");
            return;
        }
        Fabricante existente = FabricanteDAO.buscarPorNombre(nombre);
        if (existente != null) {
            System.out.println("El fabricante '" + nombre + "' ya existe con el ID: " + existente.getCodigo());

        }else {
        Fabricante nuevo = new Fabricante(nombre);
        FabricanteDAO.guardar(nuevo);
        System.out.println("Fabricante creado correctamente");}
    }

    private static void buscarFabricantePorNombre() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Introduce el nombre del fabricante: ");
        String nombre = entrada.nextLine();

        Fabricante encontrado = FabricanteDAO.buscarPorNombre(nombre);

        if (encontrado != null) {
            System.out.println("Fabricante encontrado: ID " + encontrado.getCodigo() + ", " + encontrado.getNombre());
        } else {
            System.out.println("No existe ningún fabricante con ese nombre.");
        }
    }
    private static void listarFabricantes() {
        Fabricante fabricante = new Fabricante();
        fabricanteDAO.listar();
    }
    private static void buscarFabricanteDeUnProducto() {
        Scanner entrada = new Scanner(System.in);
        System.out.print("Introduce el nombre del producto para saber su fabricante: ");
        String nombreProd = entrada.nextLine().trim();
        System.out.println(nombreProd);

        FabricanteDAO dao = new FabricanteDAO();
        Fabricante f = dao.buscarFabricantePorNombreProducto(nombreProd);

        if (f != null) {
            System.out.println("El producto '" + nombreProd + "' es fabricado por: " + f.getNombre());
        } else {
            System.out.println("No se encontró ningún fabricante para ese producto.");
        }
    }

    private static void actualizarFabricante() {
        Fabricante fabricante = new Fabricante();
        Scanner entrada = new Scanner(System.in);
        System.out.println("Introduce el ID del fabricante a actualizar: ");
        int id = entrada.nextInt();
        fabricante.setCodigo(id);
        fabricanteDAO.actualizar(fabricante);
        System.out.println("Fabricante actualizado correctamente.");

    }
    private static void buscarProductoPorNombreFabricante() {
        Scanner entrada = new Scanner(System.in);
        entrada.nextLine();
        System.out.println("Introduce el nombre del fabricante para conocer sus productos: ");
        String nombFab = entrada.nextLine();
        Fabricante fab = FabricanteDAO.buscarPorNombre(nombFab);
        if(fab != null && fab.getListaProductos() != null) {
            System.out.println("Se han encontrado " + fab.getListaProductos().size() + " Asociados al fabricante: " + fab.getNombre());
            for(Producto p : fab.getListaProductos()) {
                System.out.println("-> ID: " + p.getCodigo() + " Producto: " + p.getNombre());
            }
        } else {
            System.out.println("No se encontraron productos o el fabricante no existe.");
        }
    }
    private static void borrarFabricante() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("BORRAR FABRICANTE");
        System.out.print("Introduce el ID del fabricante a borrar: ");
        int id = entrada.nextInt();

        Fabricante fab = fabricanteDAO.buscarPorId(id);
        if(fab != null) {
            System.out.println("VAS A BORRAR A: " + fab.getNombre());
            System.out.println("¿Estás seguro? (s/n): ");
            String confirmacion = entrada.next();
            if(confirmacion.equalsIgnoreCase("s")) {
                fabricanteDAO.borrar(id);
                System.out.println("ÉXITO: Fabricante eliminado.");
            }
        } else {
            System.out.println("Error: No existe el ID " + id);
        }
    }
    private static void menuFabricantes(Scanner entrada) {
        int opcionFabricante;
        do {
            System.out.println("\n   === Menú Gestión Fabricantes ===");
            System.out.println("   1. Crear Fabricante");
            System.out.println("   2. Listar Fabricantes");
            System.out.println("   3. Actualizar Fabricante");
            System.out.println("   4. Eliminar Fabricante");
            System.out.println("   9. Volver atrás <---");
            System.out.print("   Elige una opción de fabricantes: ");

            try {
                opcionFabricante = entrada.nextInt();
            } catch (Exception exc) {
                opcionFabricante = -1;
            }

            switch (opcionFabricante) {
                case 1:
                    crearFabricante();
                    System.out.println("   >> Creando fabricante...");
                    break;
                case 2:
                    menuBuscarFabricante();
                case 3:
                    actualizarFabricante();
                    System.out.println("   >> Actualizando fabricante...");
                    break;
                case 4:
                    borrarFabricante();
                    break;
                case 9:
                    System.out.println("   >> Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("   >> Opción de fabricante no válida");
                    break;
            }
        } while (opcionFabricante != 9);

    }
    private static void menuBuscarFabricante() {
        Scanner entrada = new Scanner(System.in);
        int opcion = -1;
        do {
           System.out.println("   <<--- BUSCAR FABRICANTES --->> ");
           System.out.println(">> 1. Ver Todos los Fabricantes");
           System.out.println(">> 2. Buscar el ID por NOMBRE de Fabricante");
           System.out.println(">> 3. Buscar el Fabricante por NOMBRE de Producto");
           System.out.println(">> 4. Buscar por NOMBRE de Fabricante y obtener productos asociados");
            System.out.println(">> 9. Volver atrás");
            opcion = entrada.nextInt();

           switch (opcion) {
               case 1:
                   listarFabricantes();
                   break;
               case 2:
                   buscarFabricantePorNombre();
                   break;
               case 3:
                    buscarFabricanteDeUnProducto();
                   break;
               case 4:
                    buscarProductoPorNombreFabricante();
                   break;
               case 9:
                   break;
               default:
           }
       } while (opcion != 9);
    }
}