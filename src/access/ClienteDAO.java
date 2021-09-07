package access;

//? Imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import model.Cliente;
import utils.ConnectionDB;

//& $$$$$$$$$$$$$$$$$$$$$$$ &\\
//* ## @AUTHOR FelipeGcz ## *\\
//& $$$$$$$$$$$$$$$$$$$$$$$ &\\

public class ClienteDAO {
    private Connection conn = null;

    // ^|||||||||||||||||||||||||||||||||^\\
    // ^-------- INSERT QUERIES ---------^\\
    // ^|||||||||||||||||||||||||||||||||^\\

    public void insertCliente(Cliente objectCliente) {
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "INSERT INTO Cliente (Cl_Nombres,Cl_Apellidos,Cl_Documento,Cl_Telefono) VALUES (?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, objectCliente.getNombres());
            statement.setString(2, objectCliente.getApellidos());
            statement.setString(3, objectCliente.getDocumento());
            statement.setString(4, objectCliente.getTelefono());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0)
                JOptionPane.showMessageDialog(null, "El registro fue agregado exitosamente !");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : " + ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
    }

    // ^|||||||||||||||||||||||||||||||||^\\
    // ^--------- DELETE QUERY ----------^\\
    // ^|||||||||||||||||||||||||||||||||^\\

    public void deleteCliente(int id) {
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "DELETE FROM Cliente WHERE Cl_Id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0)
                JOptionPane.showMessageDialog(null, "El registro fue eliminado exitosamente !");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : " + ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
    }

    // ^|||||||||||||||||||||||||||||||||^\\
    // ^--------- UPDATE QUERY ----------^\\
    // ^|||||||||||||||||||||||||||||||||^\\
    
    public void updateCliente(Cliente objectCliente) {
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "UPDATE Cliente SET Cl_Nombres = ? , Cl_Apellidos = ? , Cl_Documento = ? , Cl_Telefono = ? WHERE Cl_Id = ? ;";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, objectCliente.getNombres());
            statement.setString(2, objectCliente.getApellidos());
            statement.setString(3, objectCliente.getDocumento());
            statement.setString(4, objectCliente.getTelefono());
            statement.setInt(5, objectCliente.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0)
                JOptionPane.showMessageDialog(null, "El registro fue actualizado exitosamente !");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : " + ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
    }

    // ^|||||||||||||||||||||||||||||||||^\\
    // ^-------- SELECTT QUERIES --------^\\
    // ^|||||||||||||||||||||||||||||||||^\\

    public ArrayList<Cliente> getAllClientes() {
        ArrayList<Cliente> arrayCliente = new ArrayList<>();
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "SELECT * FROM Cliente ORDER BY Cl_Id";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                Cliente objectCliente = new Cliente(result.getInt(1),result.getString(2),result.getString(3),result.getString(4),result.getString(5));
                arrayCliente.add(objectCliente);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : " + ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
        return arrayCliente;
    }

    public ArrayList<Cliente> getSearchCliente(Cliente objectCliente) {
        ArrayList<Cliente> arrayCliente = new ArrayList<>();
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "SELECT * FROM Cliente WHERE Cl_Id = ? Or Cl_Nombres = ? Or Cl_Apellidos = ? Or Cl_Documento = ? Or Cl_Telefono = ?;";

            PreparedStatement statement = conn.prepareStatement(sql);
            System.out.println(sql);
            statement.setInt(1,objectCliente.getId());
            statement.setString(2,objectCliente.getNombres());
            statement.setString(3,objectCliente.getApellidos());
            statement.setString(4,objectCliente.getDocumento());
            statement.setString(5,objectCliente.getTelefono());
            System.out.println(statement.toString());
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                Cliente object = new Cliente(result.getInt(1),result.getString(2),result.getString(3),result.getString(4),result.getString(5));
                arrayCliente.add(object);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : " + ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
        return arrayCliente;
    }
}
