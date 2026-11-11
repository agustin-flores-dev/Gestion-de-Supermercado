// TP GRUPAL - GESTION SUPERMERCADO
// Flores, Agustin; Vicchio, Dylan; Lezcano, Juan Cruz

public static final String NOMBRE_SUPERMERCADO = "MARDEL";
public static final ETurno TURNO_DEFAULT = ETurno.MANIANA;
public static final int ROL_DEFAULT = 1;
public static Empleados usuario = null;

void main() {

    // Variables
    Scanner sc = new Scanner(System.in);
    boolean salirApp = false;

    HashSet<Producto> stockSupermercado = new HashSet<>();
    RepositorioGenerico<Empleados> repoEmpleados = new RepositorioGenerico<>();
    GestoraEmpleados gestoraGeneral = new GestoraEmpleados(repoEmpleados.obtenerTodos());
    ArrayList<Double> dineroDelDia = new ArrayList<>();
    HashMap<Fecha, Double> dineroMes = new HashMap<>();
    HashSet<Socio> sociosSuper = new HashSet<>();
    HashSet<Producto> productos = new HashSet<>();

    Supermercado mardel = new Supermercado(NOMBRE_SUPERMERCADO, 1, dineroDelDia, dineroMes, repoEmpleados, sociosSuper, productos);
    Admin admin1 = new Admin("Agustin Flores", "43.404.643", "Laboratorio 5", "123", 2.50, ETurno.MANIANA, gestoraGeneral);
    Admin admin2 = new Admin("Dylan Vicchio", "43.404.644", "Laboratorio 6", "123",2.50, ETurno.TARDE, gestoraGeneral);
    Admin admin3 = new Admin("Juan Cruz Lezcano", "43.404.645", "Laboratorio 7", "123",2.50, ETurno.NOCHE, gestoraGeneral);
    Repositor repositor1 = new Repositor("Benjamin Burnley", "30.345.670", "123", "Matheu 4500", 100000, ETurno.TARDE);
    Cajero cajero1 = new Cajero("34.469.231","Keith Wallen", "123","Perú 250", 100000, ETurno.MANIANA);
    Socio socio1 = new Socio( "30.501.458", "Pepe", "123", "Ricardo Rojas 356", true);

    LocalDate hoy = LocalDate.now();
    Fecha fechaActual = new Fecha(hoy.getMonthValue(), hoy.getYear());
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
    while (!salirApp) {

        while (usuario == null && !salirApp) {
            saltarLinea(1);
            int opcionInicio = menuIncio(sc);
            saltarLinea(1);
            switch (opcionInicio) {
                case 0: {
                    System.out.println("< Cerrando programa... >");
                    salirApp = true;
                    break;
                } // Salir.
                case 1: {
                    System.out.println("<< INICIAR SESIÓN >>");
                    System.out.print("- Ingrese su ID de empleado: ");
                    try {
                        int idLogin = validacionInt(sc);
                        Empleados emp = gestoraGeneral.buscarPorID(idLogin);
                        System.out.print("- Ingrese su contraseña: ");
                        String contrasenia = sc.nextLine();
                        if (emp.getContrasenia().equals(contrasenia)) {
                            usuario = emp;
                            saltarLinea(1);
                            System.out.println("< Sesión iniciada con éxito >");
                            System.out.println("[¡Bienvenido/a, " + usuario.getNombre() + "!]");
                            System.out.println("[Rol detectado: " + usuario.getClass().getSimpleName() + "]");
                        } else {
                            System.out.println("[!] Contraseña incorrecta...");
                        }

                    } catch (BusquedaFallidaException e) {
                        System.out.println("[!] No se encontró ningún empleado con ese ID...");
                    } catch (InputMismatchException e) {
                        System.out.println("[!] Entrada inválida...");
                    }
                    break;
                } // Iniciar sesión.
                default: {
                    System.out.println("! OPCIÓN INVÁLIDA !");
                    break;
                } // Error.
            }

            if(opcionInicio != 0) limpiarPantalla(sc, "< Abriendo aplicación >"); // Limpiar pantalla.
        } // Inicio de sesión.

        if (salirApp) break; // Salir.

        int opcionPrincipal;
        do {
            saltarLinea(1);
            opcionPrincipal = menuPrincipal(sc);
            saltarLinea(1);
            switch (opcionPrincipal) {
                case 0: {
                    usuario = null;
                    System.out.println("< Sesión cerrada con éxito >");
                    break;
                } // Cerrar sesión.
                case 1: {
                    // Menú Empleados.
                    saltarLinea(20);
                    int opcionEmpleados;
                    do {
                        opcionEmpleados = menuEmpleados(sc);
                        saltarLinea(1);
                        switch (opcionEmpleados) {
                            case 0: {
                                break;
                            } // Salir.
                            case 1: {
                                // Mostrar lista Empleados.
                                System.out.println("<< Listado de Empleados >>");
                                try {
                                    Set<Empleados> lista = gestoraGeneral.obtenerEmpleados();
                                    mostrarEmpleados(lista);
                                } catch (ListaVaciaException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            } // Mostrar lista Empleados.
                            case 2: {
                                // Buscar Empleados.
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
                                    System.out.println("[!] ID no fue encontrado / es inválido...");
                                    sc.next();
                                }
                                break;
                            } // Buscar Empleados.
                            case 3: {
                                // Agregar Empleado nuevo.
                                if(!validacionRol(Admin.class)) {
                                    System.out.println("[!] ACCESO DENEGADO: No posees los permisos para realizar esta acción.");
                                    break;
                                } // Validacion

                                System.out.println("<< Agregar Empleado Nuevo >>");
                                System.out.print("- Nombre: ");
                                String nombre = sc.nextLine();
                                System.out.print("- DNI (Con puntos): ");
                                String dni = sc.nextLine();
                                System.out.print("- Contraseña: ");
                                String contrasenia = sc.nextLine();
                                System.out.print("- Dirección: ");
                                String direccion = sc.nextLine();
                                System.out.print("- Sueldo: ");
                                double sueldo = validacionDouble(sc);
                                sc.nextLine();

                                int opcionTurno = menuSeleccionarTurno(sc);
                                ETurno turno = switch (opcionTurno) {
                                    case 1 -> ETurno.MANIANA;
                                    case 2 -> ETurno.TARDE;
                                    case 3 -> ETurno.NOCHE;
                                    default -> TURNO_DEFAULT;
                                };

                                int opcionRol = menuSeleccionarRol(sc);
                                if (opcionRol < 1 || opcionRol > 3) {
                                    opcionRol = ROL_DEFAULT;
                                }

                                Empleados nuevo = switch (opcionRol) {
                                    case 2 -> new Repositor(nombre, dni, direccion, contrasenia, sueldo, turno);
                                    case 3 -> new Admin(nombre, dni, contrasenia, direccion, sueldo, turno, gestoraGeneral);
                                    default -> new Cajero(nombre, dni, contrasenia, direccion, sueldo, turno);
                                };

                                if (admin1.agregarEmpleado(nuevo)) {
                                    System.out.println("> El Empleado fue agregado al sistema.");
                                } else {
                                    System.out.println("[!] No se pudo completar la operación...");
                                }
                                break;
                            } // Agregar Empleado nuevo.
                            case 4: {
                                // Despedir Empleado.
                                if(!validacionRol(Admin.class)) {
                                    System.out.println("[!] ACCESO DENEGADO: No posees los permisos para realizar esta acción.");
                                    break;
                                } // Validacion

                                System.out.println("<< Despedir Empleado >>");
                                System.out.print("- Ingrese el ID del empleado a despedir: ");
                                try {
                                    int id = sc.nextInt();
                                    sc.nextLine();
                                    Empleados emp = admin1.buscarEmpleadoPorID(id);
                                    if (emp != null) {
                                        if (admin1.despedirEmpleado(emp)) {
                                            System.out.println("> Empleado despedido correctamente.");
                                        } else {
                                            System.out.println("[!] No se pudo completar la operación...");
                                        }
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("[!] El ID no fue encontrado / es inválido...");
                                    sc.next();
                                }
                                break;
                            } // Despedir Empleado.
                            default: {
                                System.out.println("! OPCIÓN INVÁLIDA EN MENÚ EMPLEADOS !");
                                break;
                            } // Error.
                        }
                        if (opcionEmpleados != 0) {
                            limpiarPantalla(sc, "< Volviendo al menú principal... >");
                        } // Limpiar pantalla.
                    } while (opcionEmpleados != 0); // Ciclo Menú Empleados.
                    break;
                } // Menu Empleados.
                case 2: {
                    // Menú Supermercado.
                    saltarLinea(20);
                    int opcionSuper;
                    do {
                        opcionSuper = menuSupermercado(sc);
                        saltarLinea(1);
                        switch (opcionSuper) {
                            case 0: {
                                break;
                            } // Salir.
                            case 1: {
                                // Calcular plata del día.
                                if(!validacionRol(Admin.class)) {
                                    System.out.println("[!] ACCESO DENEGADO: No posees los permisos para realizar esta acción.");
                                    break;
                                } // Validacion
                                System.out.println("<< Calcular Plata del Día >>");
                                double recaudadoDia = mardel.calcularPlataDia();
                                System.out.println("> Recaudación total del día registrada: $" + recaudadoDia);
                                break;
                            } // Calcular plata del día.
                            case 2: {
                                // Calcular plata del mes.
                                if(!validacionRol(Admin.class)) {
                                    System.out.println("[!] ACCESO DENEGADO: No posees los permisos para realizar esta acción.");
                                    break;
                                } // Validacion.
                                System.out.println("<< Calcular Plata del Mes >>");
                                double recaudadoMes = mardel.calcularPlataMes();
                                System.out.println("< Cierre mensual registrado >: $" + recaudadoMes);
                                break;
                            } // Calcular plata del mes.
                            case 3: {
                                // Borrar productos vencidos.
                                if(!validacionRol(Admin.class) && !validacionRol(Repositor.class)) {
                                    System.out.println("[!] ACCESO DENEGADO: No posees los permisos para realizar esta acción.");
                                    break;
                                } // Validacion

                                System.out.println("<< Borrar Productos Vencidos >>");
                                if (repositor1.sacarVencido(fechaActual, stockSupermercado)) {
                                    System.out.println("> Se eliminaron los productos vencidos del stock.");
                                } else {
                                    System.out.println("[!] No hubo productos vencidos para eliminar...");
                                }
                                break;
                            } // Borrar productos vencidos.
                            default: {
                                System.out.println("! OPCIÓN INVÁLIDA EN MENÚ SUPERMERCADO !");
                                break;
                            } // Error.
                        }
                        if (opcionSuper != 0) {
                            limpiarPantalla(sc, "< Volviendo al menú principal... >");
                        } // Limpiar pantalla.
                    } while (opcionSuper != 0); // Ciclo Menú Supermercado.
                    break;
                } // Menu Supermercado.
                default: {
                    System.out.println("! INGRESE UNA OPCIÓN VÁLIDA !");
                    break;
                } // Error.
            }

            if(opcionPrincipal != 0) {
                limpiarPantalla(sc, "< Volviendo al Menú Principal >");
            } else {
                limpiarPantalla(sc, "< Volviendo al Inicio de sesión >");
            } // Limpiar pantalla.
        } while (opcionPrincipal != 0 && usuario != null); // Ciclo Menú principal.
    }
    sc.close();
}

// Metodos
public static int menuIncio(Scanner sc) {
    System.out.printf("[ GESTION SUPERMERCADO %s ]\n", NOMBRE_SUPERMERCADO);
    separador(20);
    System.out.println("[0.] SALIR");
    System.out.println("[1.] Iniciar sesión.");
    separador(20);
    System.out.print("- Ingrese una opción: ");

    return validacionInt(sc);
}
public static int menuPrincipal(Scanner sc) {
    if (usuario != null)
        System.out.println("[ Sesión activa: " +usuario.getNombre() +" (" +usuario.getClass().getSimpleName() +") ]");

    System.out.printf("[ GESTION SUPERMERCADO %s ]\n", NOMBRE_SUPERMERCADO);
    separador(20);
    System.out.println("[0.] Cerrar sesión.");
    System.out.println("[1.] Menu Empleados.");
    System.out.println("[2.] Menu Supermercado.");
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
    System.out.println("[3.] Agregar Empleado nuevo [Admin].");
    System.out.println("[4.] Despedir Empleado [Admin].");
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
public static int menuSupermercado(Scanner sc) {
    System.out.println("[ MENU SUPERMERCADO ]");
    separador(20);
    System.out.println("[0.] Volver al Menú Principal.");
    System.out.println("[1.] Calcular la plata del día. [Admin]");
    System.out.println("[2.] Calcular la plata del mes. [Admin]");
    System.out.println("[3.] Borrar productos vencidos [Repositor].");
    separador(20);
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
public static void limpiarPantalla(Scanner sc, String mensaje) {
    saltarLinea(1);
    System.out.println(mensaje);
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
    boolean entradaValida = false;

    while (!entradaValida) {
        try {
            opcion = sc.nextInt();
            entradaValida = true;
        } catch (InputMismatchException e) {
            System.out.print("[!] Por favor, ingrese un número válido: ");
            sc.next();
        }
    }
    sc.nextLine();
    return opcion;
}
public static double validacionDouble(Scanner sc) {
    double opcion = 0.0;
    boolean entradaValida = false;

    while (!entradaValida) {
        try {
            opcion = sc.nextDouble();
            entradaValida = true;
        } catch (InputMismatchException e) {
            System.out.print("[!] Por favor, ingrese un número válido: ");
            sc.next();
        }
    }
    sc.nextLine();
    return opcion;
}
public static boolean validacionUsuario() {
    return usuario != null;
}
public static boolean validacionRol(Class<?> rol) {
    return rol.isInstance(usuario);
}