// TP GRUPAL - GESTION SUPERMERCADO
// Flores, Agustin; Vicchio, Dylan; Lezcano, Juan Cruz

void main() {

    // Variables
    Scanner sc = new Scanner(System.in);
    int opcion;

    GestoraEmpleados gestoraGeneral = new GestoraEmpleados();
    Admin adminPrincipal = new Admin("Agustin Flores", "43.404.643", "Saturno", 2.50, ETurno.NOCHE, gestoraGeneral);
    Repositor repositor1 = new Repositor("Benjamin Burnley", "30.345.670", "Matheu 4500", 100000, ETurno.TARDE);
    Repositor repositor2 = new Repositor("Keith Wallen", "34.469.231", "Perú 250", 100000, ETurno.MANIANA);

    try {
        gestoraGeneral.agregarEmpleados(adminPrincipal);
        gestoraGeneral.agregarEmpleados(repositor1);
        gestoraGeneral.agregarEmpleados(repositor2);
    } catch (ElementoVacioException | EEstadoYaIngresado e) {
        throw new RuntimeException(e);
    }

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
                    List<Empleados> lista = gestoraGeneral.obtenerEmpleados();
                    mostrarEmpleados(lista);
                } catch (ListaVaciaException e) {
                    System.out.println(e.getMessage());
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
    System.out.println("[1.] Mostrar Empleados");
    separador(20);
    System.out.print("- Ingrese una opción: ");

    int opcion = -1;
    try {
        opcion = sc.nextInt();
    } catch (java.util.InputMismatchException e) {
        System.out.println("! Por favor, ingrese un número válido !");
        sc.next();
    }
    sc.nextLine();
    return opcion;
}
public static void mostrarEmpleados(List<Empleados> listaEmpleados) {
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