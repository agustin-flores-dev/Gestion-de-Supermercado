// TP GRUPAL - GESTION SUPERMERCADO
// Flores, Agustin; Vicchio, Dylan; Lezcano, Juan Cruz

public static final String NOMBRE_SUPERMERCADO = "MARDEL";
public static final ETurno TURNO_DEFAULT = ETurno.MANIANA;
public static final int ROL_DEFAULT = 1;
public static Persona usuario = null;

void main() {

    // Variables
    Scanner sc = new Scanner(System.in);
    boolean salirApp = false;
    int opcionSocio = 0;

    HashSet<Producto> stockSupermercado = new HashSet<>();
    RepositorioGenerico<Empleados> repoEmpleados = new RepositorioGenerico<>();
    HashSet<Socio> sociosSuper = new HashSet<>();
    GestoraEmpleados gestoraGeneral = new GestoraEmpleados(repoEmpleados.obtenerTodos());
    ArrayList<Double> dineroDelDia = new ArrayList<>();
    HashMap<Fecha, Double> dineroMes = new HashMap<>();

    //////////////////////////////////////////////////////////////////////////////////////////
    //  Conexión con la DB.
    try {
        ConexionAdmin.crearTablas();

        for (Admin ad : ConexionAdmin.listarAdmin(gestoraGeneral)) {
            repoEmpleados.agregar(ad);
        }
        for (Repositor repo : ConexionAdmin.listarRepositor()){
            repoEmpleados.agregar(repo);
        }
        for (Cajero caj : ConexionAdmin.listarCajeros()){
            repoEmpleados.agregar(caj);
        }

        sociosSuper.addAll(ConexionAdmin.listarSocio());
        stockSupermercado.addAll(ConexionAdmin.listarProductos());
        dineroDelDia.addAll(ConexionAdmin.listarGananciasDiarias());
        dineroMes.putAll(ConexionAdmin.listarGananciasMensual());


    } catch (ListarException e){
        System.out.println(e.getMessage());
    }
    //////////////////////////////////////////////////////////////////////////////////////////

    for (Empleados m : repoEmpleados.obtenerTodos()){
        try {
            gestoraGeneral.agregarEmpleados(m);
        } catch ( ElementoVacioException | EEstadoYaIngresado e) {
            System.out.println(e.getMessage());
        }
    }


    Supermercado mardel = new Supermercado(NOMBRE_SUPERMERCADO, 1, dineroDelDia, dineroMes,
            repoEmpleados, sociosSuper, stockSupermercado);
    Admin admin1 = new Admin("43.404.643", "Agustín Flores", "Laboratorio 5", "123",
            2.50, ETurno.MANIANA, gestoraGeneral);
    Admin admin2 = new Admin("43.404.644", "Dylan Vicchio", "Laboratorio 5", "123",
            2.50, ETurno.TARDE, gestoraGeneral);
    Admin admin3 = new Admin("43.404.645", "Juan Cruz Lezcano", "Laboratorio 5", "123",
            2.50, ETurno.NOCHE, gestoraGeneral);
    Repositor repositor1 = new Repositor("30.589.986", "Benjamin Burnley", "Matheu 4500", "123",
            100000, ETurno.TARDE);
    Cajero cajero1 = new Cajero("34.469.231","Keith Wallen", "Perú 250","123",
            100000, ETurno.MANIANA);
    Socio socio1 = new Socio( "30.501.458", "Aaron Bruch", "Ricardo Rojas 356", "123", true);
    sociosSuper.add(socio1);

    LocalDate hoy = LocalDate.now();
    Fecha fechaActual = new Fecha(hoy.getMonthValue(), hoy.getYear());
    Fecha fecha1 = new Fecha(7, 11, 2026);
    Fecha fecha2 = new Fecha(6, 10, 2026);

    Producto producto1 = new Producto("Cassancrem Light", "Queso blanco untable bajo en grasas.",
            fecha1, 10, 3500);
    Producto producto2 = new Producto("Dulce de Leche", "Producto lácteo concentrado azucarado.",
            fecha2, 15, 4000);

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

    saltarLinea(20);
    while (!salirApp) {
        // Ciclo Aplicación.
        while (usuario == null && !salirApp) {
            saltarLinea(1);
            int opcionInicio = menuIncio(sc);
            saltarLinea(1);
            switch (opcionInicio) {
                case 0: {
                    mensajeDeCarga(0, "> Cerrando aplicación", 2, true);
                    System.out.println("< Aplicación cerrada con éxito >");
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
                            System.out.println("[¡Bienvenido/a, " +usuario.getNombre() +"!]");
                            System.out.println("[Rol detectado: " +usuario.getClass().getSimpleName() +"]");

                            mensajeDeCarga(1, "> Iniciando aplicación", 2, true);
                            limpiarPantalla(sc, "< Aplicación cargada correctamente >");
                        } else {
                            limpiarPantalla(sc, "\n[!] Contraseña / ID incorrectos...");
                        }

                    } catch (BusquedaFallidaException e) {
                        limpiarPantalla(sc, "\n[!] No se encontró ningún empleado con ese ID...");
                    } catch (InputMismatchException e) {
                        limpiarPantalla(sc, "\n[!] Entrada inválida...");
                    }

                    if(validacionRol(Socio.class)) {
                        opcionSocio = -1;
                    } // Comprobar Socio.
                    break;
                } // Iniciar sesión.
                case 2: {
                    // Menú Socios.
                    do {
                        saltarLinea(20);
                        opcionSocio = menuSocios(sc);
                        switch (opcionSocio) {
                            case 0: {
                                break;
                            } // Salir.
                            case 1: {
                                // Registrar nuevo socio.
                                System.out.println("<< REGISTRAR SOCIO NUEVO >>");
                                System.out.print("- Ingrese DNI: ");
                                String dni = sc.nextLine();
                                System.out.print("- Ingrese su Apellido: ");
                                String apellido = sc.nextLine();
                                System.out.print("- Ingrese su Nombre: ");
                                String nombre = sc.nextLine() + " " + apellido;
                                System.out.print("- Ingrese Dirección: ");
                                String direccion = sc.nextLine();
                                System.out.print("- Ingrese Contraseña: ");
                                String contrasenia = sc.nextLine();

                                mensajeDeCarga(1, "Cargando", 1, true);
                                Socio nuevoSocio = new Socio(dni, nombre, direccion, contrasenia, true);
                                try {
                                    ConexionAdmin.insertarSocio(nuevoSocio);
                                    sociosSuper.add(nuevoSocio);
                                } catch (Exception e) {
                                    System.out.println("[!] " +e.getMessage());
                                }
                                System.out.println("> ¡Registro exitoso! Ya forma parte de nuestra comunidad de socios.");
                                break;
                            } // Registrar nuevo socio.
                            case 2: {
                                // Desactivar cuenta.
                                System.out.println("<< DARSE DE BAJA >>");
                                System.out.print("- Ingrese su DNI: ");
                                String dni = sc.nextLine();
                                System.out.print("- Ingrese su Contraseña: ");
                                String contrasenia = sc.nextLine();

                                Socio socioEncontrado = null;
                                for (Socio s : sociosSuper) {
                                    if (s.getDni().equals(dni) && s.contrasenia.equals(contrasenia)) {
                                        socioEncontrado = s;
                                        break;
                                    }
                                }

                                mensajeDeCarga(1, "> Cargando", 1, true);
                                if (socioEncontrado != null) {
                                    try {
                                        socioEncontrado.desactivarEstado();
                                        sociosSuper.remove(socioEncontrado);
                                        System.out.println("> Su cuenta ha sido dada de baja correctamente. ¡Lamentamos verle partir!");
                                    } catch (EEstadoYaIngresado e) {
                                        System.out.println("[!] " + e.getMessage());
                                    }
                                } else {
                                    System.out.println("[!] No se encontró un socio activo con esos datos...");
                                }
                                break;
                            } // Desactivar cuenta.
                            default: {
                                System.out.println("! OPCIÓN INVÁLIDA !");
                                break;
                            } // Error.
                        }

                        if (opcionSocio != 0) {
                            limpiarPantalla(sc, "\n< Presione Enter para continuar... >");
                        } // Limpiar pantalla.
                    }while (opcionSocio != 0) ; // Ciclo Menú Socios.
                    break;
                } // Menú Socios.
                default: {
                    limpiarPantalla(sc, "! OPCIÓN INVÁLIDA !");
                    break;
                } // Error.
            } // Menú Socios.

        } // Inicio de sesión.

        if(salirApp) break;

        if(usuario instanceof Empleados) {
            // Verificación Empleado.
            int opcionPrincipal;
            do {
                // Ciclo Menú principal.
                saltarLinea(1);
                opcionPrincipal = menuPrincipal(sc);
                switch (opcionPrincipal) {
                    case 0: {
                        usuario = null;
                        mensajeDeCarga(0,"> Cerrando sesión", 3, true);
                        System.out.println("< Sesión cerrada correctamente >");
                        break;
                    } // Cerrar sesión.
                    case 1: {
                        // Menú Empleados - (Admin).
                        if(!validacionRol(Admin.class)) {
                            System.out.println("[!] ACCESO DENEGADO: No posees los permisos para realizar esta acción.");
                            break;
                        } // Validación General

                        saltarLinea(20);
                        int opcionEmpleados;
                        do {
                            opcionEmpleados = menuAdmin(sc);
                            saltarLinea(1);
                            switch (opcionEmpleados) {
                                case 0: {
                                    break;
                                } // Salir.
                                case 1: {
                                    // Mostrar lista Empleados.
                                    System.out.println("<< Listado de Empleados >>");
                                    int opcion = menuSeleccionarRol(sc);

                                    Class<?> emp = switch(opcion) {
                                        case 1 -> Cajero.class;
                                        case 2 -> Repositor.class;
                                        case 3 -> Admin.class;
                                        default -> null;
                                    };

                                    mensajeDeCarga(0, "> Consiguendo Empleados", 1, true);

                                    try {
                                        Set<Empleados> lista = gestoraGeneral.obtenerEmpleados();
                                        mostrarEmpleados(lista, emp);
                                    } catch (ListaVaciaException e) {
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                                } // Mostrar lista Empleados.
                                case 2: {
                                    // Buscar Empleados.
                                    Admin admin = (Admin) usuario;

                                    System.out.println("<< Buscar Empleado por ID >>");
                                    System.out.print("- Ingrese el ID a buscar: ");
                                    try {
                                        int id = sc.nextInt();
                                        sc.nextLine();
                                        Empleados emp = admin.buscarEmpleadoPorID(id);

                                        mensajeDeCarga(0, "> Buscando Empleado", 1, true);

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
                                    Admin admin = (Admin) usuario;

                                    System.out.println("<< Agregar Empleado Nuevo >>");
                                    System.out.print("- DNI (Con puntos): ");
                                    String dni = sc.nextLine();
                                    System.out.print("- Nombre: ");
                                    String nombre = sc.nextLine();
                                    System.out.print("- Dirección: ");
                                    String direccion = sc.nextLine();
                                    System.out.print("- Contraseña: ");
                                    String contrasenia = sc.nextLine();
                                    System.out.print("- Sueldo: ");
                                    double sueldo = validacionDouble(sc);

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
                                        case 2 -> new Repositor(dni, nombre, direccion, contrasenia, sueldo, turno);
                                        case 3 -> new Admin(dni, nombre, direccion, contrasenia, sueldo, turno, gestoraGeneral);
                                        default -> new Cajero(dni, nombre, direccion, contrasenia, sueldo, turno);
                                    };

                                    mensajeDeCarga(1, "> Agregando Empleado", 1, true);
                                    if (admin.agregarEmpleado(nuevo)) {
                                        System.out.println("> El Empleado fue agregado al sistema.");
                                    } else {
                                        System.out.println("[!] No se pudo completar la operación...");
                                    }
                                    break;
                                } // Agregar Empleado nuevo.
                                case 4: {
                                    // Despedir Empleado.
                                    Admin admin = (Admin) usuario;

                                    System.out.println("<< Despedir Empleado >>");
                                    System.out.print("- Ingrese el ID del empleado a despedir: ");
                                    try {
                                        int id = sc.nextInt();
                                        sc.nextLine();
                                        Empleados emp = admin.buscarEmpleadoPorID(id);

                                        mensajeDeCarga(1, "> Despidiendo Empleado", 1, true);
                                        if (emp != null) {
                                            if (admin.despedirEmpleado(emp)) {
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
                                case 5: {
                                    // Mostrar lista Socios.
                                    System.out.println("<< Listado de Socios >>");
                                    mensajeDeCarga(0, "> Consiguendo Socios", 1, true);

                                    if(!mostrarSocios(sociosSuper)) {
                                        System.out.println("[!] No hay socios en la lista...");
                                    }
                                    break;
                                } // Mostrar lista Socios.
                                default: {
                                    System.out.println("! OPCIÓN INVÁLIDA !");
                                    break;
                                } // Error.
                            }
                            if (opcionEmpleados != 0) {
                                limpiarPantalla(sc, "\n< Volviendo al menú principal... >");
                            } // Limpiar pantalla.
                        } while (opcionEmpleados != 0); // Ciclo Menú Empleados.
                        break;
                    } // Menu Empleados - (Admin).
                    case 2: {
                        // Menú Supermercado - (Repositor).
                        if(!validacionRol(Admin.class) && !validacionRol(Repositor.class)) {
                            System.out.println("[!] ACCESO DENEGADO: No posees los permisos para realizar esta acción.");
                            break;
                        } // Validación General

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
                                    // Calcular plata del día  - (Admin).
                                    if(!validacionRol(Admin.class)) {
                                        System.out.println("[!] ACCESO DENEGADO: No posees los permisos para realizar esta acción.");
                                        break;
                                    } // Validacion

                                    System.out.println("<< Calcular Plata del Día >>");
                                    double recaudadoDia = mardel.calcularPlataDia();

                                    mensajeDeCarga(1, "> Calculando", 1, true);
                                    System.out.println("> Recaudación total del día registrada: $" +recaudadoDia);
                                    break;
                                } // Calcular plata del día - (Admin).
                                case 2: {
                                    // Calcular plata del mes  - (Admin).
                                    if(!validacionRol(Admin.class)) {
                                        System.out.println("[!] ACCESO DENEGADO: No posees los permisos para realizar esta acción.");
                                        break;
                                    } // Validacion.

                                    System.out.println("<< Calcular Plata del Mes >>");
                                    double recaudadoMes = mardel.calcularPlataMes();

                                    mensajeDeCarga(1, "> Calculando", 1, true);
                                    System.out.println("< Cierre mensual registrado >: $" + recaudadoMes);
                                    break;
                                } // Calcular plata del mes - (Admin).
                                case 3: {
                                    // Borrar productos vencidos.
                                    System.out.println("<< Borrar Productos Vencidos >>");
                                    mensajeDeCarga(1, "> Buscando", 1, true);

                                    try {
                                        if (mardel.sacarVencido()) {
                                            System.out.println("> Se eliminaron los productos vencidos del stock.");
                                        } else {
                                            System.out.println("[!] No hubo productos vencidos para eliminar...");
                                        }
                                    } catch (IllegalArgumentException | ActualizacionUpdateException e) {
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                                } // Borrar productos vencidos.
                                default: {
                                    System.out.println("! OPCIÓN INVÁLIDA !");
                                    break;
                                } // Error.
                            }
                            if (opcionSuper != 0) {
                                limpiarPantalla(sc, "\n< Volviendo al menú principal... >");
                            } // Limpiar pantalla.
                        } while (opcionSuper != 0); // Ciclo Menú Supermercado.
                        break;
                    } // Menu Supermercado - (Repositor).
                    case 3: {
                        // Menú Productos y Stock - (Cajero/Repositor).
                        // No hay validación, hay acciones que puede hacer cualquier Empleado.

                        int opcionProd;
                        do {
                            opcionProd = menuProductos(sc);
                            switch (opcionProd) {
                                case 0: {
                                    break;
                                } // Salir.
                                case 1: { // Buscar Producto.
                                    System.out.print("- Ingrese ID del producto a buscar: ");
                                    int idProd = validacionInt(sc);

                                    mensajeDeCarga(1, "Cargando", 1, true);
                                    try {
                                        Producto p = mardel.buscarProductoPorID(idProd);
                                        System.out.println("> Producto encontrado: ");
                                        System.out.println(p);
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                                } // Buscar Producto.
                                case 2: { // Buscar Producto por Nombre.
                                    System.out.print("- Ingrese el nombre del producto a buscar: ");
                                    String nombreProd = sc.nextLine();

                                    mensajeDeCarga(1, "> Buscando", 1, true);
                                    try {
                                        Producto p = mardel.buscarProductoPorNombre(nombreProd);
                                        System.out.println("> Producto encontrado: ");
                                        System.out.println(p);
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                                } // Buscar por Nombre.
                                case 3: {
                                    // Agregar stock - (Repositor).
                                    if(!validacionRol(Admin.class) && !validacionRol(Repositor.class)) {
                                        System.out.println("[!] ACCESO DENEGADO: No posees los permisos para realizar esta acción.");
                                        break;
                                    } // Validación

                                    System.out.print("- Ingrese ID del producto: ");
                                    int id = validacionInt(sc);
                                    System.out.print("- Cantidad a sumar al stock: ");
                                    int cant = validacionInt(sc);

                                    mensajeDeCarga(1, "Cargando", 1, true);
                                    try {
                                        if(mardel.aumentarStockID(id, cant)) {
                                            System.out.println("> Stock actualizado con éxito.");
                                        }
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                                } // Agregar stock - (Repositor).
                                case 4: {
                                    // Agregar producto nuevo - (Repositor).
                                    if(!validacionRol(Admin.class) && !validacionRol(Repositor.class)) {
                                        System.out.println("[!] ACCESO DENEGADO: No posees los permisos para realizar esta acción.");
                                        break;
                                    } // Validación

                                    System.out.println("<< Agregar Producto Nuevo >>");
                                    System.out.print("- Nombre del producto: ");
                                    String nombre = sc.nextLine();
                                    System.out.print("- Descripción: ");
                                    String descripcion = sc.nextLine();

                                    System.out.println("> Ingrese la fecha de vencimiento:");
                                    System.out.print("- Día: ");
                                    int dia = validacionInt(sc);
                                    System.out.print("- Mes: ");
                                    int mes = validacionInt(sc);
                                    System.out.print("- Año: ");
                                    int anio = validacionInt(sc);
                                    Fecha fechaVenc = new Fecha(dia, mes, anio);

                                    System.out.print("- Stock inicial: ");
                                    int stock = validacionInt(sc);
                                    System.out.print("- Precio unitario: $");
                                    double precio = validacionDouble(sc);

                                    Producto nuevoProd = new Producto(nombre, descripcion, fechaVenc, stock, precio);

                                    mensajeDeCarga(1, "> Guardando", 1, true);
                                    try {
                                        if(mardel.agregarProducto(nuevoProd)) {
                                            System.out.println("> Producto agregado con éxito al stock.");
                                        }
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                                } // Agregar producto nuevo - (Repositor).
                                default: {
                                    System.out.println("! OPCIÓN INVÁLIDA !");
                                    break;
                                } // Error.
                            }
                            if (opcionProd != 0) limpiarPantalla(sc, "\n< Presione Enter para continuar... >");
                        } while (opcionProd != 0);
                        break;
                    } // Menú Productos y Stock - (Cajero/Repositor).
                    case 4: {
                        // Menú Caja y Ventas - (Cajero).
                        if(!validacionRol(Admin.class) && !validacionRol(Cajero.class)) {
                            System.out.println("[!] ACCESO DENEGADO: No posees los permisos para realizar esta acción.");
                            break;
                        } // Validación.

                        int opcionVentas;
                        do {
                            saltarLinea(20);
                            opcionVentas = menuVentas(sc);
                            switch (opcionVentas) {
                                case 0: {
                                    break;
                                } // Salir.
                                case 1: {
                                    // Chequear precio de un producto.
                                    System.out.print("- Ingrese el ID del producto a consultar: ");
                                    int idProd = validacionInt(sc);

                                    mensajeDeCarga(1, "> Consultando", 1, true);
                                    try {
                                        Producto p = mardel.buscarProductoPorID(idProd);
                                        double precio = mardel.chequearPrecios(p);
                                        System.out.println("> El precio de [" +p.getNombre() +"] es: $" +precio);
                                    } catch (Exception e) {
                                        System.out.println("[!] " +e.getMessage());
                                    }
                                    break;
                                } // Chequear precio de un producto.
                                case 2: {
                                    // Realizar venta.
                                    System.out.println("<< REGISTRAR VENTA >>");
                                    System.out.print("- Ingrese su ID de empleado: ");
                                    int id = validacionInt(sc);

                                    ArrayList<Producto> productosVenta = new ArrayList<>();
                                    ArrayList<Integer> cantidadesVenta = new ArrayList<>();
                                    boolean seguirAgregando = true;

                                    while (seguirAgregando) {
                                        System.out.print("- Ingrese ID del producto a vender: ");
                                        int idProd = validacionInt(sc);
                                        try {
                                            Producto p = mardel.buscarProductoPorID(idProd);
                                            productosVenta.add(p);

                                            System.out.print("- Ingrese la cantidad: ");
                                            int cant = validacionInt(sc);
                                            cantidadesVenta.add(cant);

                                            System.out.print("¿Agregar otro producto? (1: Sí / 0: No): ");
                                            int rta = validacionInt(sc);
                                            if (rta == 0) seguirAgregando = false;
                                        } catch (Exception e) {
                                            System.out.println("[!] Producto no encontrado o error en ID.");
                                        }
                                    }

                                    mensajeDeCarga(1, "> Procesando venta", 1, true);
                                    try {
                                        if (mardel.realizarVenta(id, productosVenta, cantidadesVenta)) {
                                            System.out.println("> ¡Venta realizada con éxito! Stock y caja actualizados.");
                                        } else {
                                            System.out.println("[!] No se pudo procesar la venta...");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("[!] Error en la venta: " +e.getMessage());
                                    }
                                    break;
                                } // Realizar venta.
                                default: {
                                    System.out.println("! OPCIÓN INVÁLIDA !");
                                    break;
                                } // Error.
                            }
                            if (opcionVentas != 0) {
                                limpiarPantalla(sc, "\n< Presione Enter para continuar... >");
                            } // Limpiar pantalla.
                        } while (opcionVentas != 0);
                        break;
                    } // Menú Caja y Ventas - (Cajero).
                    default: {
                        System.out.println("! INGRESE UNA OPCIÓN VÁLIDA !");
                        break;
                    } // Error.
                }

                if(opcionPrincipal != 0) {
                    limpiarPantalla(sc, "\n< Volviendo al Menú Principal >");
                } else {
                    limpiarPantalla(sc, "\n< Volviendo al Inicio de sesión >");
                } // Limpiar pantalla.
            } while (opcionPrincipal != 0 && usuario != null); // Ciclo Menú principal.
        } // Verificación Empleado.
    } // Ciclo Aplicación.
    sc.close();
}

// Metodos
public static int menuIncio(Scanner sc) {
    System.out.printf("[ GESTION SUPERMERCADO %s ]\n", NOMBRE_SUPERMERCADO);
    separador(20);
    System.out.println("[0.] SALIR");
    System.out.println("[1.] Iniciar sesión (Empleados).");
    System.out.println("[2.] Menú Socios.");
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
    System.out.println("[1.] Menu Empleados - (Admin)");
    System.out.println("[2.] Menu Supermercado - (Repositor)");
    System.out.println("[3.] Menu Productos y Stock - (Cajero/Repositor)");
    System.out.println("[4.] (!) Menu Caja y Ventas (Cajero)");
    separador(20);
    System.out.print("- Ingrese una opción: ");

    return validacionInt(sc);
}
public static int menuAdmin(Scanner sc) {
    System.out.println("[ MENU EMPLEADOS ]");
    separador(20);
    System.out.println("[0.] Volver al Menú Principal.");
    System.out.println("[1.] Mostrar lista de Empleados.");
    System.out.println("[2.] Buscar Empleado por ID.");
    System.out.println("[3.] Agregar Empleado nuevo.");
    System.out.println("[4.] Despedir Empleado.");
    System.out.println("[5.] Mostrar lista de Socios.");
    separador(20);
    System.out.print("- Ingrese una opción: ");

    return validacionInt(sc);
}
public static int menuSupermercado(Scanner sc) {
    System.out.println("[ MENU SUPERMERCADO ]");
    separador(20);
    System.out.println("[0.] Volver al Menú Principal.");
    System.out.println("[1.] Calcular la plata del día - [Admin]");
    System.out.println("[2.] Calcular la plata del mes - [Admin]");
    System.out.println("[3.] Borrar productos vencidos - [Repositor]");
    separador(20);
    System.out.print("- Ingrese una opción: ");

    return validacionInt(sc);
}
public static int menuProductos(Scanner sc) {
    System.out.println("[ MENU PRODUCTOS Y STOCK ]");
    separador(20);
    System.out.println("[0.] Volver al Menú Principal.");
    System.out.println("[1.] Buscar producto por ID.");
    System.out.println("[2.] Buscar producto por Nombre.");
    System.out.println("[3.] Aumentar stock de un producto - [Repositor]");
    System.out.println("[4.] Agregar producto nuevo - [Repositor]");
    separador(20);
    System.out.print("- Ingrese una opción: ");
    return validacionInt(sc);
}
public static int menuVentas(Scanner sc) {
    System.out.println("[ MENU CAJA Y VENTAS ]");
    separador(20);
    System.out.println("[0.] Volver al Menú Principal.");
    System.out.println("[1.] Chequear precio de un producto.");
    System.out.println("[2.] Realizar venta.");
    separador(20);
    System.out.print("- Ingrese una opción: ");
    return validacionInt(sc);
}
public static int menuSocios(Scanner sc) {
    System.out.println("[ MENU SOCIOS ]");
    separador(20);
    System.out.println("[0.] Volver al Menú Principal.");
    System.out.println("[1.] Registrarse como nuevo Socio.");
    System.out.println("[2.] Desactivar cuenta.");
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
public static boolean mostrarEmpleados(Set<Empleados> listaEmpleados, Class<?> rol) {
    if(listaEmpleados == null || listaEmpleados.isEmpty()) {
        return false;
    }

    if(rol == null) {
        for(Empleados e : listaEmpleados) {
            System.out.println(e);
            separador(20);
        }
        return true;
    }

    for(Empleados e : listaEmpleados) {
        if(rol.isInstance(e)) {
            System.out.println(e);
            separador(20);
        }
    }
    return true;
}
public static boolean mostrarSocios(Set<Socio> listaSocios) {
    if(listaSocios == null || listaSocios.isEmpty()) {
        return false;
    }

    for (Socio s : listaSocios) {
        System.out.println(s);
        separador(10);
    }
    return true;
}

// Metodos Auxiliares
public static void limpiarPantalla(Scanner sc, String mensaje) {
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
public static void mensajeDeCarga(int saltos ,String mensaje, int vueltas, boolean eliminar) {
    final String[] ESTADOS = {".   ", "..  ", "... ", "    "};
    final int TIEMPO_ANIMACION = 500;

    saltarLinea(saltos);
    try {
        for(int j = 0; j<vueltas; j++) {
            for (int i = 0; i < 4; i++) {
                System.out.print("\r" +mensaje +ESTADOS[i]);
                Thread.sleep(TIEMPO_ANIMACION);
                System.out.flush();
            }
        }
        if(eliminar) System.out.print("\r");
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
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