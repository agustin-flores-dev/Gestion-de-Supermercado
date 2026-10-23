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

    public String toString(){
        return "Id :"+id_empleado+", Nombre :"+nombre+", Apellido :" +""+apellido+", Direccion :"
                +direccion+ ", DNI :"+dni+", Sueldo :"+sueldo+" , Turno :"+turno.getTurno_nombre();
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getDni() {
        return dni;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public ETurno getTurno() {
        return turno;
    }

    public void setTurno(ETurno turno) {
        this.turno = turno;
    }


}
