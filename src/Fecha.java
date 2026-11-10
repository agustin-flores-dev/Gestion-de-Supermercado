import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Objects;

public final class Fecha {

    private final int dia;
    private final int mes;
    private final int anio;

    public Fecha(int dia, int mes, int anio) {
        try {
            LocalDate.of(anio, mes, dia);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("[!] Fecha inválida");
        }

        this.dia = dia;
        this.mes = mes;
        this.anio = anio;

    }

    public Fecha(int mes, int anio) {
        this.dia = 1;
        try {
            LocalDate.of(anio, mes, dia);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("[!] Fecha inválida");
        }

        this.mes = mes;
        this.anio = anio;
    }

    public int getDia() {
        return dia;
    }
    public int getMes() {
        return mes;
    }
    public int getAnio() {
        return anio;
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
        return dia +"/" +mes +"/" +anio;
    }

    public boolean vencido(Fecha otra) {
        if (this.anio != otra.anio) {
            return this.anio < otra.anio;
        }
        if (this.mes != otra.mes) {
            return this.mes < otra.mes;
        }
        return this.dia < otra.dia;
    }
}
