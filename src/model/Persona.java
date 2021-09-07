package model;

//& $$$$$$$$$$$$$$$$$$$$$$$ &\\
//* ## @AUTHOR FelipeGcz ## *\\
//& $$$$$$$$$$$$$$$$$$$$$$$ &\\

public class Persona {
    private String nombres;
    private String apellidos;
    private String documento;
    private String telefono;

    // ^|||||||||||||||||||||||||||||||||^ \\
    // ^---------- CONSTRUCTOR ----------^ \\
    // ^|||||||||||||||||||||||||||||||||^ \\

    public Persona(String nombres, String apellidos, String documento, String telefono) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.documento = documento;
        this.telefono = telefono;
    }

    // ^|||||||||||||||||||||||||||||||||^ \\
    // ^------------ GETTERS ------------^ \\
    // ^|||||||||||||||||||||||||||||||||^ \\

    // ~ @return GET NOMBRE ▼
    public String getNombres() {
        return nombres;
    }

    // ~ @return GET APELLIDO ▼
    public String getApellidos() {
        return apellidos;
    }

    // ~ @return GET DOCUMENTO ▼
    public String getDocumento() {
        return documento;
    }

    // ~ @return GET TELEFONO ▼
    public String getTelefono() {
        return telefono;
    }

    // ^|||||||||||||||||||||||||||||||||^ \\
    // ^------------ SETTERS ------------^ \\
    // ^|||||||||||||||||||||||||||||||||^ \\

    // ~ @param SET NOMBRE ▼
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    // ~ @param SET APELLIDO ▼
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    // ~ @param SET DOCUMENTO ▼
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    // ~ @param SET TELEFONO ▼
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}