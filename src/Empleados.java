import java.util.Objects;

public abstract class Empleados extends Persona{
    private int id_empleado;
    private double sueldo;
    private ETurno turno;
    private static int contadorId=0;

    public Empleados(String nombre, String dni, String direccion, double sueldo, ETurno turno) {
        super(nombre, dni, direccion);
        this.sueldo = sueldo;
        this.turno = turno;
        this.id_empleado = contadorId++;
    }

    public Empleados(String dni, int id_empleado) {
        super(dni);
        this.id_empleado = id_empleado;
        this.sueldo = 0;
        this.turno = null;
    }

    public String toString(){
        return "Id :"+id_empleado+", Sueldo :"+sueldo+" , Turno :"+turno.getTurno_nombre();
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
