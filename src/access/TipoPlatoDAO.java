package access;

//? Imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import model.TipoPlato;
import utils.ConnectionDB;

//& $$$$$$$$$$$$$$$$$$$$$$$ &\\
//* ## @AUTHOR FelipeGcz ## *\\
//& $$$$$$$$$$$$$$$$$$$$$$$ &\\

public class TipoPlatoDAO {
    private Connection conn = null;

    // ^|||||||||||||||||||||||||||||||||^\\
    // ^-------- INSERT QUERIES ---------^\\
    // ^|||||||||||||||||||||||||||||||||^\\

    public void insertTipoPlato(TipoPlato objectTipoPlato) {
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "INSERT INTO tipoplato(TP_Nombre) VALUES(?);";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, objectTipoPlato.getNombre());

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

    public void deleteTipoPlato(int id) {
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "DELETE FROM TipoPlato WHERE TP_Id = ?";
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
    
    public void updateTipoPlato(TipoPlato objectTipoPlato) {
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "UPDATE TipoPlato SET TP_Nombre = ? WHERE TP_Id = ? ;";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, objectTipoPlato.getNombre());
            statement.setInt(2, objectTipoPlato.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0)
                JOptionPane.showMessageDialog(null, "El registro fue actualizado exitosamente !");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : " + ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
    }
    
    // ^|||||||||||||||||||||||||||||||||^\\
    // ^--------- SELECT QUERIES --------^\\
    // ^|||||||||||||||||||||||||||||||||^\\

    public ArrayList<TipoPlato> getAllTipoPlatos() {
        ArrayList<TipoPlato> arrayTipoPlato = new ArrayList<>();
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "SELECT * FROM tipoplato ORDER BY TP_Id";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                TipoPlato objecTipoPlato = new TipoPlato(result.getInt(1), result.getString(2));
                arrayTipoPlato.add(objecTipoPlato);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : " + ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
        return arrayTipoPlato;
    }

    public ArrayList<TipoPlato> getSearchTipoPlato(TipoPlato objectPlato) {
        ArrayList<TipoPlato> arrayTipoPlato = new ArrayList<>();
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "SELECT * FROM tipoplato WHERE TP_Id = ? OR TP_Nombre = ? ";

            PreparedStatement statement = conn.prepareStatement(sql);
            System.out.println(sql);
            statement.setInt(1,objectPlato.getId());
            statement.setString(2,objectPlato.getNombre());
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                TipoPlato objecTipoPlato = new TipoPlato(result.getInt(1), result.getString(2));
                arrayTipoPlato.add(objecTipoPlato);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : " + ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
        return arrayTipoPlato;
    }
}
