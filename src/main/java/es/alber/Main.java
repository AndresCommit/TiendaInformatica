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
                    menuFabricantes(opcionFabricante, entrada);
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
        String nombre = entrada.nextLine();
        /*Validaciones Interesantes a realizar:


        Comprobar que el usuario está vacio
        */
        if (nombre.isEmpty()) {
            System.out.println("El nombre está vacio, inténtalo de nuevo.");
            return;
        }

        /*Buscar en la bbdd si existe el fabricante que el usuario quiere crear*/
        try {
            Fabricante nuevo = new Fabricante(nombre);
            if (nuevo.getNombre() != null){
            /*Guardaremos a partir del DAO de fabricante*/
            fabricanteDAO.guardar(nuevo);

            }else {
                System.out.println("El fabricante ya existe");
            }

        }catch (Exception e) {
            System.out.println("Error al crear el fabricante: " + e.getMessage());
        }
        Fabricante fabricante = new Fabricante(nombre);
    }
    private static void listarFabricantes() {
        Fabricante fabricante = new Fabricante();
        fabricanteDAO.listar(fabricante, new Producto());
    }
    private static void menuFabricantes(int opcionFabricante, Scanner entrada) {
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
                    listarFabricantes();
                    System.out.println("   >> Listando fabricantes...");
                    break;
                case 3:
                    System.out.println("   >> Actualizando fabricante...");
                    break;
                case 4:
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
}