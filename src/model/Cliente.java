package model;

//& $$$$$$$$$$$$$$$$$$$$$$$ &\\
//* ## @AUTHOR FelipeGcz ## *\\
//& $$$$$$$$$$$$$$$$$$$$$$$ &\\

public class Cliente extends Persona{
    private int id;

    // ^|||||||||||||||||||||||||||||||||^\\
    // ^---------- CONNECTORS -----------^\\
    // ^|||||||||||||||||||||||||||||||||^\\

    public Cliente(String nombres, String apellidos, String documento, String telefono) {
        super(nombres, apellidos, documento, telefono);
    }

    public Cliente(int id, String nombres, String apellidos, String documento, String telefono) {
        super(nombres, apellidos, documento, telefono);
        this.id = id;
    }
    // ^|||||||||||||||||||||||||||||||||^ \\
    // ^------------ GETTERS ------------^ \\
    // ^|||||||||||||||||||||||||||||||||^ \\

    // ~ @return GET ID ▼
    public int getId() {
        return id;
    }

    // ~ @return GET NOMBRE ▼
    public String getNombres() {
        return super.getNombres();
    }

    // ~ @return GET APELLIDO ▼
    public String getApellidos() {
        return super.getApellidos();
    }

    // ~ @return GET DOCUMENTO ▼
    public String getDocumento() {
        return super.getDocumento();
    }

    // ~ @return GET TELEFONO ▼
    public String getTelefono() {
        return super.getTelefono();
    }

    // ^|||||||||||||||||||||||||||||||||^ \\
    // ^------------ SETTERS ------------^ \\
    // ^|||||||||||||||||||||||||||||||||^ \\

    // ~ @param SET NOMBRE ▼
    public void setNombres(String nombres) {
        super.setNombres(nombres);
    }

    // ~ @param SET APELLIDO ▼
    public void setApellidos(String apellidos) {
        super.setApellidos(apellidos);
    }

    // ~ @param SET DOCUMENTO ▼
    public void setDocumento(String documento) {
        super.setDocumento(documento);
    }

    // ~ @param SET TELEFONO ▼
    public void setTelefono(String telefono) {
        super.setTelefono(telefono);
    }
    public Object[] toArray(){
        Object[] data = {id, super.getNombres(), super.getApellidos(), super.getDocumento(),super.getTelefono()};
        return data;
    }
}
