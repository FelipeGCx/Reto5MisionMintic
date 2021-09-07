package access;

//? Imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import model.Mesero;
import utils.ConnectionDB;

//& $$$$$$$$$$$$$$$$$$$$$$$ &\\
//* ## @AUTHOR FelipeGcz ## *\\
//& $$$$$$$$$$$$$$$$$$$$$$$ &\\

public class MeseroDAO {
    private Connection conn = null;

    // ^|||||||||||||||||||||||||||||||||^\\
    // ^-------- INSERT QUERIES ---------^\\
    // ^|||||||||||||||||||||||||||||||||^\\

    public void insertMesero(Mesero objectMesero) {
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "INSERT INTO Mesero (Me_Nombres,Me_Apellidos,Me_Documento,Me_Correo,Me_Telefono) VALUES (?,?,?,?,?);";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, objectMesero.getNombres());
            statement.setString(2, objectMesero.getApellidos());
            statement.setString(3, objectMesero.getDocumento());
            statement.setString(4, objectMesero.getCorreo());
            statement.setString(5, objectMesero.getTelefono());

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

    public void deleteMesero(int id) {
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "DELETE FROM Mesero WHERE Me_Id = ?";
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
    
    public void updateMesero(Mesero objectMesero) {
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();
            //Me_Nombres,Me_Apellidos,Me_Documento,Me_Correo,Me_Telefono
            String sql = "UPDATE Mesero SET Me_Nombres = ?,Me_Apellidos = ?,Me_Documento = ?,Me_Correo = ?,Me_Telefono = ? WHERE Me_Id = ? ;";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, objectMesero.getNombres());
            statement.setString(2, objectMesero.getApellidos());
            statement.setString(3, objectMesero.getDocumento());
            statement.setString(4, objectMesero.getCorreo());
            statement.setString(5, objectMesero.getTelefono());
            statement.setInt(6, objectMesero.getId());

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

    public ArrayList<Mesero> getAllMeseros() {
        ArrayList<Mesero> arrayMesero = new ArrayList<>();
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "SELECT * FROM Mesero ORDER BY Me_Id";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                Mesero objectMesero = new Mesero(result.getInt(1),result.getString(2),result.getString(3),result.getString(4),result.getString(5),result.getString(6));
                arrayMesero.add(objectMesero);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : " + ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
        return arrayMesero;
    }

    public ArrayList<Mesero> getSearchMesero(Mesero objectMesero) {
        ArrayList<Mesero> arrayMesero = new ArrayList<>();
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "SELECT * FROM Mesero WHERE Me_Id = ? Or Me_Nombres = ? Or Me_Apellidos = ? Or Me_Documento = ? Or Me_Correo = ? Or Me_Telefono = ?;";

            PreparedStatement statement = conn.prepareStatement(sql);
            System.out.println(sql);
            statement.setInt(1,objectMesero.getId());
            statement.setString(2,objectMesero.getNombres());
            statement.setString(3,objectMesero.getApellidos());
            statement.setString(4,objectMesero.getDocumento());
            statement.setString(5,objectMesero.getCorreo());
            statement.setString(6,objectMesero.getTelefono());
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                Mesero object = new Mesero(result.getString(1),result.getString(2),result.getString(3),result.getString(4),result.getString(5));
                arrayMesero.add(object);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : " + ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
        return arrayMesero;
    }
}
