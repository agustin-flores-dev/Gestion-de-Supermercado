import java.util.Objects;

public abstract class Empleados extends Persona{
    private static int contadorId = 1;
    private int id_empleado;
    protected String contrasenia;
    private double sueldo;
    private ETurno turno;

    public Empleados(String dni, String nombre, String direccion,String contrasenia, double sueldo, ETurno turno) {
        super(dni, nombre, direccion);
        if (turno == null) throw new IllegalArgumentException("[!] Turno inválido...");

        this.id_empleado = contadorId++;
        this.contrasenia = contrasenia;
        this.sueldo = sueldo;
        this.turno = turno;
    }

    public static int getContadorId() {
        return contadorId;
    }
    public String getContrasenia() {
        return contrasenia;
    }
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
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

    @Override
    public String toString() {
        return super.toString()+"[ID]: "+id_empleado+"\n"
                +"[Sueldo]: $" +sueldo +"\n"
                +"[Turno]: " +turno.getTurno_nombre();
    }

    public boolean equals(Object o){
        if (!(o instanceof Empleados emp))return false;
        return id_empleado == emp.id_empleado;
    }

    public int hashCode(){
        return Objects.hashCode(id_empleado);
    }

    public int getId_empleado() {
        return id_empleado;
    }
}
