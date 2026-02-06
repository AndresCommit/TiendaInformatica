package es.alber;

import es.alber.dao.FabricanteDAO;
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
                    System.out.println("\n=== Menú Productos ===");
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
        fabricanteDAO.listar(fabricante, new Producto());
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
    private static void borrarFabricante() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Introduce el ID del fabricante a borrar: ");
        listarFabricantes();
        int id = entrada.nextInt();
        fabricanteDAO.borrar(id);
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
                    System.out.println("   >> Eliminando fabricante...");
                    break;
                case 9:
                    System.out.println("   >> Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("   >> Opción de fabricante no válida.");
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
                   break;
               case 9:
                   break;
               default:
           }
       } while (opcion != 9);
    }
}