package model;

//& $$$$$$$$$$$$$$$$$$$$$$$ &\\
//* ## @AUTHOR FelipeGcz ## *\\
//& $$$$$$$$$$$$$$$$$$$$$$$ &\\

public class Mesero extends Persona {
    private int id;
    private String correo;

    // ^|||||||||||||||||||||||||||||||||^ \\
    // ^---------- CONSTRUCTOR ----------^ \\
    // ^|||||||||||||||||||||||||||||||||^ \\

    public Mesero(String nombres, String apellidos, String documento, String correo, String telefono) {
        super(nombres, apellidos, documento, telefono);
        this.correo = correo;
    }

    public Mesero(int id, String nombres, String apellidos, String documento, String correo, String telefono) {
        super(nombres, apellidos, documento, telefono);
        this.id = id;
        this.correo = correo;
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

    // ~ @return GET CORREO ▼
    public String getCorreo() {
        return correo;
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

    // ~ @param SET CORREO ▼
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    // ~ @param SET TELEFONO ▼
    public void setTelefono(String telefono) {
        super.setTelefono(telefono);
    }
    public Object[] toArray(){
        Object[] data = {id, super.getNombres(), super.getApellidos(), super.getDocumento(),correo,super.getTelefono()};
        return data;
    }
}
