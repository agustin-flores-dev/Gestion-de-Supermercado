import java.util.Objects;

public final class Fecha {

    private int mes;
    private int anio;

    public Fecha(int mes, int anio) {
        this.mes = mes;
        this.anio = anio;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Fecha fecha = (Fecha) o;
        return anio == fecha.anio && mes == fecha.mes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(anio, mes);
    }

    @Override
    public String toString() {
        return "year: " + this.anio + " mes: " + this.mes;
    }
}
