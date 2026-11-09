// TP GRUPAL - GESTION SUPERMERCADO
// Flores, Agustin; Vicchio, Dylan; Lezcano, Juan Cruz

public static final String NOMBRE_SUPERMERCADO = "MARDEL";
public static final ETurno TURNO_DEFAULT = ETurno.MANIANA;
public static final int ROL_DEFAULT = 1;

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
        opcion = menuPrincipal(sc);
        saltarLinea(1);
        switch (opcion) {
            case 0: {
                System.out.println("< Cerrando programa... >");
                break;
            } // Salir
            case 1: {
                saltarLinea(20);
                int opcionEmpleados;
                do {
                    opcionEmpleados = menuEmpleados(sc);
                    saltarLinea(1);
                    switch (opcionEmpleados) {
                        case 0: {
                            break;
                        } // Salir
                        case 1: {
                            System.out.println("<< Listado de Empleados >>");
                            try {
                                Set<Empleados> lista = gestoraGeneral.obtenerEmpleados();
                                mostrarEmpleados(lista);
                            } catch (ListaVaciaException e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        } // Mostrar Empleados.
                        case 2: {
                            System.out.println("<< Buscar Empleado por ID >>");
                            System.out.print("- Ingrese el ID a buscar: ");
                            try {
                                int id = sc.nextInt();
                                sc.nextLine();
                                Empleados emp = admin1.buscarEmpleadoPorID(id);

                                if (emp != null) {
                                    System.out.println("< Empleado Encontrado >");
                                    saltarLinea(1);
                                    System.out.println(emp);
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("[!] ID no fue encontrado / es inválido...]");
                                sc.next();
                            }
                            break;
                        } // Buscar Empleado.
                        case 3: {
                            System.out.println("<< Agregar Empleado Nuevo >>");
                            System.out.print("- Nombre: ");
                            String nombre = sc.nextLine();
                            System.out.print("- DNI (Con puntos): ");
                            String dni = sc.nextLine();
                            System.out.print("- Dirección: ");
                            String direccion = sc.nextLine();
                            System.out.print("- Sueldo: ");
                            double sueldo = sc.nextDouble();
                            sc.nextLine();

                            saltarLinea(1);
                            int opcionTurno = menuSeleccionarTurno(sc);
                            ETurno turno = switch (opcionTurno) {
                                case 1 -> ETurno.MANIANA;
                                case 2 -> ETurno.TARDE;
                                case 3 -> ETurno.NOCHE;
                                default -> TURNO_DEFAULT;
                            };

                            saltarLinea(1);
                            int opcionRol = menuSeleccionarRol(sc);


                            saltarLinea(1);
                            if (opcionTurno < 1 || opcionTurno > 3) {
                                System.out.println("[!] Opción inválida. Se asignará el Turno por defecto...");
                            }
                            if (opcionRol < 1 || opcionRol > 3) {
                                System.out.println("[!] Opción inválida. Se asignará el Rol por defecto...");
                                opcionRol = ROL_DEFAULT;
                            }

                            Empleados nuevo = switch (opcionRol) {
                                case 2 -> new Repositor(nombre, dni, direccion, sueldo, turno);
                                case 3 -> new Admin(nombre, dni, direccion, sueldo, turno, gestoraGeneral);
                                default -> new Cajero(nombre, dni, direccion, sueldo, turno);
                            };

                            if (admin1.agregarEmpleado(nuevo)) {
                                System.out.println("[!] El Empleado fue agregado al sistema.");
                            } else {
                                System.out.println("[!] No se pudo completar la operación...");
                            }

                            break;
                        } // Agregar Empleado nuevo.
                        case 4: {
                            System.out.println("<< Despedir Empleado >>");
                            System.out.print("- Ingrese el ID del empleado a despedir: ");
                            try {
                                int id = sc.nextInt();
                                sc.nextLine();
                                Empleados emp = admin1.buscarEmpleadoPorID(id);
                                if (emp != null) {
                                    if (admin1.despedirEmpleado(emp)) {
                                        System.out.println("[!] Empleado despedido correctamente.");
                                    }
                                    else {
                                        System.out.println("[!] No se pudo completar la operación...");
                                    }
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("[!] El ID no fue encontrado / es inválido...]");
                                sc.next();
                            }
                            break;
                        } // Despedir Empleado.
                        default: {
                            System.out.println("! OPCIÓN INVÁLIDA EN MENÚ EMPLEADOS !");
                            break;
                        } // Error
                    }
                    if (opcionEmpleados != 0) {
                        limpiarPantalla(sc);
                    } // Limpiar pantalla
                } while (opcionEmpleados != 0);
                break;
            } // Menu Empleados
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

// Metodos
public static int menuPrincipal(Scanner sc) {
    System.out.printf("[ GESTION SUPERMERCADO %s ]\n", NOMBRE_SUPERMERCADO);
    separador(20);
    System.out.println("[0.] SALIR");
    System.out.println("[1.] Menu Empleados.");
    System.out.println("[2.] (!) Menu Supermercado.");
    separador(20);
    System.out.print("- Ingrese una opción: ");

    return validacionInt(sc);
}
public static int menuEmpleados(Scanner sc) {
    System.out.println("[ MENU EMPLEADOS ]");
    separador(20);
    System.out.println("[0.] Volver al Menú Principal.");
    System.out.println("[1.] Mostrar lista de Empleados.");
    System.out.println("[2.] Buscar Empleado por ID.");
    System.out.println("[3.] Agregar Empleado nuevo (vía Admin).");
    System.out.println("[4.] Despedir Empleado (vía Admin).");
    separador(20);
    System.out.print("- Ingrese una opción: ");

    return validacionInt(sc);
}
public static int menuSeleccionarTurno(Scanner sc) {
    System.out.println("[ Seleccione el Turno ]");
    System.out.println("[1] Mañana");
    System.out.println("[2] Tarde");
    System.out.println("[3] Noche");
    saltarLinea(1);
    System.out.print("- Ingrese una opción: ");

    return validacionInt(sc);
}
public static int menuSeleccionarRol(Scanner sc) {
    System.out.println("[ Seleccione el Rol del Empleado ]");
    System.out.println("[1] Cajero");
    System.out.println("[2] Repositor");
    System.out.println("[3] Administrador");
    saltarLinea(1);
    System.out.print("- Ingrese una opción: ");

    return validacionInt(sc);
}
public static void mostrarEmpleados(Set<Empleados> listaEmpleados) {
    for(Empleados e : listaEmpleados) {
        System.out.println(e);
        separador(20);
    }
}

// Metodos Auxiliares
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
public static int validacionInt(Scanner sc) {

    int opcion = -1;
    try {
        opcion = sc.nextInt();
    } catch (InputMismatchException e) {
        System.out.println("[!] Por favor, ingrese un número válido...");
        sc.next();
    }
    sc.nextLine();
    return opcion;
}