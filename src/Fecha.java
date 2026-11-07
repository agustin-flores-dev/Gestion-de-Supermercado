import java.util.Objects;

public final class Fecha {

    private int dia;
    private int mes;
    private int anio;

    public Fecha(int dia, int mes, int anio) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    public Fecha(int mes, int anio) {
        this.dia = 1;
        this.mes = mes;
        this.anio = anio;
    }

    public int getDia() {
        return dia;
    }
    public void setDia(int dia) {
        this.dia = dia;
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
        return dia == fecha.dia && mes == fecha.mes && anio == fecha.anio;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dia, mes, anio);
    }

    @Override
    public String toString() {
        return dia+ "/" +mes + "/" +anio;
    }

    public boolean vencido(Fecha otra) {
        if (this.anio != otra.anio) {
            return this.anio < otra.anio;
        }
        if (this.mes != otra.mes) {
            return this.mes < otra.mes;
        }
        return this.dia <= otra.dia;
    }
}
