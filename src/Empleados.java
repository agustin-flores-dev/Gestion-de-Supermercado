import java.util.Objects;

public abstract class Empleados {
    private int id_empleado;
    private double sueldo;
    private ETurno turno;
    private static int contadorId=0;

    public Empleados(double sueldo, ETurno turno){
        this.sueldo = sueldo;
        this.turno = turno;
        this.id_empleado = contadorId++;
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
