import java.util.Set;
import java.util.HashSet;

public final class RepositorioGenerico<T> {
    private HashSet<T> elementos;

    public RepositorioGenerico(HashSet<T> elementos) {
        this.elementos = new HashSet<>();
    }

    public boolean agregar(T elemento) {
        return this.elementos.add(elemento);
    }

    public boolean eliminar(T elemento) {
        return this.elementos.remove(elemento);
    }

    public HashSet<T> obtenerTodos() {
        return this.elementos;
    }

    public boolean estaVacio() {
        return this.elementos.isEmpty();
    }
}
