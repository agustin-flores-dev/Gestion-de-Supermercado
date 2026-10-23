public enum ETurno {
    MANIANA("Mañana"),
    TARDE("Tarde"),
    NOCHE("Noche");

    private String turno_nombre;

    ETurno(String turno_nombre){
        this.turno_nombre = turno_nombre;
    }

    public String getTurno_nombre() {
        return turno_nombre;
    }

    public void setTurno_nombre(String turno_nombre) {
        this.turno_nombre = turno_nombre;
    }
}
