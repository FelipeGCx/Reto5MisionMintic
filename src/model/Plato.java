package model;

//& $$$$$$$$$$$$$$$$$$$$$$$ &\\
//* ## @AUTHOR FelipeGcz ## *\\
//& $$$$$$$$$$$$$$$$$$$$$$$ &\\

public class Plato {
    private int id;
    private String nombre;
    private Double precio;
    private Object tipoPlatoFK;

    // ^|||||||||||||||||||||||||||||||||^ \\
    // ^---------- CONSTRUCTOR ----------^ \\
    // ^|||||||||||||||||||||||||||||||||^ \\

    public Plato(String nombre, Double precio, String tipoPlatoFK) {
        this.nombre = nombre;
        this.precio = precio;
        this.tipoPlatoFK = tipoPlatoFK;
    }

    public Plato(int id, String nombre, Double precio, String tipoPlatoFK) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.tipoPlatoFK = tipoPlatoFK;
    }

    public Plato(String nombre, Double precio, int tipoPlatoFK) {
        this.nombre = nombre;
        this.precio = precio;
        this.tipoPlatoFK = tipoPlatoFK;
    }

    public Plato(int id, String nombre, Double precio, int tipoPlatoFK) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.tipoPlatoFK = tipoPlatoFK;
    }

    // ^|||||||||||||||||||||||||||||||||^ \\
    // ^------------ GETTERS ------------^ \\
    // ^|||||||||||||||||||||||||||||||||^ \\

    // ~ @return GET ID ▼
    public int getId() {
        return id;
    }

    // ~ @return GET NOMBRE ▼
    public String getNombre() {
        return nombre;
    }

    // ~ @return GET PRECIO ▼
    public Double getPrecio() {
        return precio;
    }

    // ~ @return GET TIPOPLATO-FK ▼
    public int getTipoPlatoFK() {
        return (int) tipoPlatoFK;
    }

    // ^|||||||||||||||||||||||||||||||||^ \\
    // ^------------ SETTERS ------------^ \\
    // ^|||||||||||||||||||||||||||||||||^ \\

    // ~ @param SET NOMBRE ▼
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // ~ @param SET PRECIO ▼
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    // ~ @param SET TIPOPLATO-FK ▼
    public void getTipoPlatoFK(int tipoPlatoFK) {
        this.tipoPlatoFK = tipoPlatoFK;
    }
    
    public Object[] toArray(){
        Object[] data = {id, nombre, precio, tipoPlatoFK};
        return data;
    }
}
