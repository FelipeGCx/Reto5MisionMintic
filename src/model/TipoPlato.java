package model;

//& $$$$$$$$$$$$$$$$$$$$$$$ &\\
//* ## @AUTHOR FelipeGcz ## *\\
//& $$$$$$$$$$$$$$$$$$$$$$$ &\\

public class TipoPlato {
    private int id;
    private String nombre;

    // ^|||||||||||||||||||||||||||||||||^ \\
    // ^---------- CONSTRUCTOR ----------^ \\
    // ^|||||||||||||||||||||||||||||||||^ \\

    public TipoPlato(String nombre) {
        this.nombre = nombre;
    }

    public TipoPlato(int id, String nombre) {
        this.setId(id);
        this.nombre = nombre;
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

    // ^|||||||||||||||||||||||||||||||||^ \\
    // ^------------ SETTERS ------------^ \\
    // ^|||||||||||||||||||||||||||||||||^ \\

    // ~ @param SET NOMBRE ▼
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // ~ @param SET ID ▼
    public void setId(int id) {
        this.id = id;
    }
    
    // ^|||||||||||||||||||||||||||||||||^ \\
    // ^------------- METODS ------------^ \\
    // ^|||||||||||||||||||||||||||||||||^ \\

    public Object[] toArray(){
        Object[] data = {id, nombre};
        return data;
    }
}
