// TP GRUPAL - GESTION SUPERMERCADO
// Flores, Agustin; Vicchio, Dylan; Lezcano, Juan Cruz

void main() {

    // Variables
    Scanner sc = new Scanner(System.in);
    int opcion;

    HashSet<Producto> stockSupermercado = new HashSet<>();
    GestoraEmpleados gestoraGeneral = new GestoraEmpleados(new HashSet<>());

    Admin admin1 = new Admin("Agustin Flores", "43.404.643", "Laboratorio 5", 2.50, ETurno.MANIANA, gestoraGeneral);
    Admin admin2 = new Admin("Dylan Vicchio", "43.404.644", "Laboratorio 6", 2.50, ETurno.TARDE, gestoraGeneral);
    Admin admin3 = new Admin("Juan Cruz Lezcano", "43.404.645", "Laboratorio 7", 2.50, ETurno.NOCHE, gestoraGeneral);
    Repositor repositor1 = new Repositor("Benjamin Burnley", "30.345.670", "Matheu 4500", 100000, ETurno.TARDE);
    Cajero cajero1 = new Cajero("Keith Wallen", "34.469.231", "Perú 250", 100000, ETurno.MANIANA);

    Fecha fecha1 = new Fecha(7, 11, 2026);
    Fecha fecha2 = new Fecha(6, 10, 2026);
    Fecha fecha3 = new Fecha(11, 2026);
    Producto producto1 = new Producto("Cassancrem Light", "Queso blanco untable bajo en grasas.", fecha1, 10, 3500);
    Producto producto2 = new Producto("Dulce de Leche", "Producto lácteo concentrado azucarado.", fecha2, 15, 4000);

    try {
        gestoraGeneral.agregarEmpleados(admin1);
        gestoraGeneral.agregarEmpleados(admin2);
        gestoraGeneral.agregarEmpleados(admin3);
        gestoraGeneral.agregarEmpleados(repositor1);
        gestoraGeneral.agregarEmpleados(cajero1);
    } catch (ElementoVacioException | EEstadoYaIngresado e) {
        System.out.println(e.getMessage());
    } // Instancia de Empleados.

    try {
        repositor1.agregarProducto(stockSupermercado, producto1);
        repositor1.agregarProducto(stockSupermercado, producto2);
    } catch (ElementoVacioException e) {
        System.out.println(e.getMessage());
    } // Instancia de Productos

    saltarLinea(1);
    do {
        opcion = menu(sc);
        saltarLinea(1);
        switch (opcion) {
            case 0: {
                System.out.println("< Cerrando programa... >");
                break;
            } // Salir
            case 1: {
                System.out.println("<< Empleados >>");
                try {
                    Set<Empleados> lista = gestoraGeneral.obtenerEmpleados();
                    mostrarEmpleados(lista);
                } catch (ListaVaciaException e) {
                    System.out.println(e.getMessage());
                }
                break;
            }
            case 2: {
                Fecha hoy = new Fecha(7, 11, 2026);
                if(repositor1.sacarVencido(hoy, stockSupermercado)) {
                    System.out.println("Se eliminaron los productos vencidos.");
                }
                else {
                    System.out.println("No hubo productos vencidos para eliminar.");
                }
                break;
            }
            default: {
                System.out.println("! INGRESE UNA OPCIÓN VÁLIDA !");
                break;
            } // Error
        }
        if (opcion != 0) {
            limpiarPantalla(sc);
        } // Limpiar pantalla
    } while (opcion != 0); // Ciclo Menu
    sc.close();
}

// Métodos
public static int menu(Scanner sc) {
    System.out.println("[ GESTION SUPERMERCADO ´NOMBRE ?´ ]");
    separador(20);
    System.out.println("[0.] SALIR");
    System.out.println("[1.] Mostrar Empleados.");
    System.out.println("[2.] Prueba.");
    separador(20);
    System.out.print("- Ingrese una opción: ");

    int opcion = -1;
    try {
        opcion = sc.nextInt();
    } catch (InputMismatchException e) {
        System.out.println("! Por favor, ingrese un número válido !");
        sc.next();
    }
    sc.nextLine();
    return opcion;
}
public static void mostrarEmpleados(Set<Empleados> listaEmpleados) {
    for(Empleados e : listaEmpleados) {
        System.out.println(e);
        separador(20);
    }
}

// Auxiliares
public static void limpiarPantalla(Scanner sc) {
    saltarLinea(1);
    System.out.println("< Volviendo al menu principal >");
    System.out.print("> Presione Enter para continuar...");
    sc.nextLine();
    saltarLinea(15);
}
public static void saltarLinea(int num) {
    for (int i = 0; i < num; i++) {
        System.out.println();
    }
}
public static void separador(int num) {
    for (int i = 0; i < num; i++) {
        System.out.print("- ");
    }
    System.out.println();
}