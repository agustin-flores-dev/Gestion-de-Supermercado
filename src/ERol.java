public enum ERol {

    ADMIN("admin"), CAJERO("cajero"), REPOSITOR("repositor");

    private String nombre;

     ERol (String nombre){
        this.nombre = nombre;
    }

    public String toString (){
        return nombre;
    }
}
