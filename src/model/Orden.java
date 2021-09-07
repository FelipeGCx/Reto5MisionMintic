package model;

//& $$$$$$$$$$$$$$$$$$$$$$$ &\\
//* ## @AUTHOR FelipeGcz ## *\\
//& $$$$$$$$$$$$$$$$$$$$$$$ &\\

public class Orden {
    private int id;
    private String fechaServicio;
    private Object idPlatoFK;
    private Object idClienteFK;
    private Object idMeseroFK;

    // ^|||||||||||||||||||||||||||||||||^ \\
    // ^---------- CONSTRUCTOR ----------^ \\
    // ^|||||||||||||||||||||||||||||||||^ \\
    
    public Orden(String fechaServicio, String idClienteFK,String idPlatoFK, String idMeseroFK) {
        this.fechaServicio = fechaServicio;
        this.idClienteFK = idClienteFK;
        this.idPlatoFK = idPlatoFK;
        this.idMeseroFK = idMeseroFK;
    }
    
    public Orden(int id,String fechaServicio, String idClienteFK,String idPlatoFK, String idMeseroFK) {
        this.id = id;
        this.fechaServicio = fechaServicio;
        this.idClienteFK = idClienteFK;
        this.idPlatoFK = idPlatoFK;
        this.idMeseroFK = idMeseroFK;
    }

    public Orden(int id, String fechaServicio, int idClienteFK, int idPlatoFK, int idMeseroFK) {
        this.id = id;
        this.fechaServicio = fechaServicio;
        this.idClienteFK = idClienteFK;
        this.idPlatoFK = idPlatoFK;
        this.idMeseroFK = idMeseroFK;
    }
    
    public Orden(String fechaServicio, int idClienteFK, int idPlatoFK, int idMeseroFK) {
        this.fechaServicio = fechaServicio;
        this.idClienteFK = idClienteFK;
        this.idPlatoFK = idPlatoFK;
        this.idMeseroFK = idMeseroFK;
    }
    
    // ^|||||||||||||||||||||||||||||||||^ \\
    // ^------------ GETTERS ------------^ \\
    // ^|||||||||||||||||||||||||||||||||^ \\

    // ~ @return GET ID ▼
    public int getId() {
        return id;
    }

    // ~ @return GET FECHASERVICIO ▼
    public String getFechaServicio() {
        return fechaServicio;
    }

    // ~ @return GET PLATO-FK ▼
    public int getIdPlatoFK() {
        return (int) idPlatoFK;
    }

    // ~ @return GET CLIENTE-FK ▼
    public int getIdClienteFK() {
        return (int) idClienteFK;
    }

    // ~ @return GET MESERO-FK ▼
    public int getIdMeseroFK() {
        return (int) idMeseroFK;
    }

    // ^|||||||||||||||||||||||||||||||||^ \\
    // ^------------ SETTERS ------------^ \\
    // ^|||||||||||||||||||||||||||||||||^ \\
   
    // ~ @param SET FECHASERVICIO ▼
    public void setFechaServicio(String fechaServicio) {
        this.fechaServicio = fechaServicio;
    }

    // ~ @param SET PLATO-FK ▼
    public void setIdPlatoFK(int idPlatoFK) {
        this.idPlatoFK = idPlatoFK;
    }

    // ~ @param SET CLIENTE-FK ▼
    public void setIdClienteFK(int idClienteFK) {
        this.idClienteFK = idClienteFK;
    }

    // ~ @param SET MESERO-FK ▼
    public void setIdMeseroFK(int idMeseroFK) {
        this.idMeseroFK = idMeseroFK;
    }

    public Object[] toArray(){
        Object[] data = {id, fechaServicio, idClienteFK, idPlatoFK, idMeseroFK};
        return data;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s",id, fechaServicio, idClienteFK, idPlatoFK, idMeseroFK);
    }
}