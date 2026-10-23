public abstract class Empleados {
    private int id_empleado;
    private String nombre;
    private String apellido;
    private String direccion;
    private int dni;
    private double sueldo;
    private ETurno turno;
    private static int contadorId=1;

    public Empleados(String nombre, String apellido, String direccion, int dni, double sueldo, ETurno turno){
        this.nombre =  nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.dni = dni;
        this.sueldo = sueldo;
        this.turno = turno;
        this.id_empleado = contadorId++;
    }

    

}
