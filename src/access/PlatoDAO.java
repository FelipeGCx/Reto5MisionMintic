package access;

//? Imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import model.Plato;
import utils.ConnectionDB;

//& $$$$$$$$$$$$$$$$$$$$$$$ &\\
//* ## @AUTHOR FelipeGcz ## *\\
//& $$$$$$$$$$$$$$$$$$$$$$$ &\\

public class PlatoDAO {
    private Connection conn = null;

    // ^|||||||||||||||||||||||||||||||||^\\
    // ^-------- INSERT QUERIES ---------^\\
    // ^|||||||||||||||||||||||||||||||||^\\

    public void insertPlato(Plato objectPlato) {
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "INSERT INTO plato (Pl_Nombre,Pl_Precio,Pl_TipoPlato) VALUES(?,?,?);";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, objectPlato.getNombre());
            statement.setDouble(2, objectPlato.getPrecio());
            statement.setInt(3, objectPlato.getTipoPlatoFK());

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

    public void deletePlato(int id) {
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "DELETE FROM Plato WHERE Pl_Id = ?";
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
    
    public void updatePlato(Plato objectPlato) {
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "UPDATE Plato SET Pl_Nombre = ? , Pl_Precio = ? Pl_TipoPlato = ? WHERE Or_Id = ? ;";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, objectPlato.getNombre());
            statement.setDouble(2, objectPlato.getPrecio());
            statement.setInt(3, objectPlato.getTipoPlatoFK());
            statement.setInt(4, objectPlato.getId());

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

    public ArrayList<Plato> getAllPlatos() {
        ArrayList<Plato> arrayPlato = new ArrayList<>();
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "SELECT Pl_Id,Pl_Nombre,Pl_Precio, TP_Nombre FROM Plato inner join TipoPlato on Pl_TipoPlato =  TP_id ORDER BY Pl_Id;";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                Plato objectPlato = new Plato(result.getInt(1), result.getString(2), result.getDouble(3),
                        result.getString(4));
                arrayPlato.add(objectPlato);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : " + ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
        return arrayPlato;
    }

    //& BUSCA UNA ORDEN ESPECIFICA 
    public ArrayList<Plato> getSearchPlato(Plato objectOrden) {
        ArrayList<Plato> arrayOrden = new ArrayList<>();
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "SELECT Pl_Id,Pl_Nombre,Pl_Precio, TP_Nombre FROM Plato inner join TipoPlato on Pl_TipoPlato =  TP_id WHERE Pl_Id = ? Or Pl_Nombre = ? Or Pl_Precio = ? Or Pl_TipoPlato = ?;";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, objectOrden.getId());
            statement.setString(2, objectOrden.getNombre());
            statement.setDouble(3, objectOrden.getPrecio());
            statement.setInt(4, objectOrden.getTipoPlatoFK());
            
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Plato objectPlato = new Plato(result.getInt(1), result.getString(2), result.getDouble(3),result.getString(4));
                arrayOrden.add(objectPlato);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : " + ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
        return arrayOrden;
    }

    public ArrayList<String> getTipoPlato() {
        ArrayList<String> arrayTipoPlato = new ArrayList<>();
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "select TP_nombre from tipoplato order by TP_Id;";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                arrayTipoPlato.add(result.getString(1));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : " + ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
        return arrayTipoPlato;
    }

    public int getIdTipoPlato(String nombre) {
        int idTipoPlato = 0;
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "select TP_Id from TipoPlato Where TP_Nombre = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,nombre);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                idTipoPlato = result.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : " + ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
        return idTipoPlato;
    }
}
