public enum ERol {

    ADMIN("Admin"), CAJERO("Cajero"), REPOSITOR("Repositor");

    private String nombre;

     ERol (String nombre){
        this.nombre = nombre;
    }

    public String toString (){
        return nombre;
    }
}
