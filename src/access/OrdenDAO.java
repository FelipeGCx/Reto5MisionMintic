package access;

//? Imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import model.Orden;
import utils.ConnectionDB;

//& $$$$$$$$$$$$$$$$$$$$$$$ &\\
//* ## @AUTHOR FelipeGcz ## *\\
//& $$$$$$$$$$$$$$$$$$$$$$$ &\\

public class OrdenDAO {
    private Connection conn = null;

    // ^|||||||||||||||||||||||||||||||||^\\
    // ^--------- INSERT QUERY ----------^\\
    // ^|||||||||||||||||||||||||||||||||^\\

    public void insertOrden(Orden objectOrden) {
        System.out.println("llega al DAO");
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "INSERT INTO orden (Or_FechaServicio,Or_IdPlato,Or_IdCliente,Or_IdMesero) VALUES (?,?,?,?);";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, objectOrden.getFechaServicio());
            statement.setInt(2, objectOrden.getIdPlatoFK());
            statement.setInt(3, objectOrden.getIdClienteFK());
            statement.setInt(4, objectOrden.getIdMeseroFK());

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

    public void deleteOrden(int id) {
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "DELETE FROM Orden WHERE Or_Id = ?";
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
    
    public void updateOrden(Orden objectOrden) {
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "UPDATE Orden SET Or_FechaServicio = ? , Or_IdPlato = ? , Or_IdCliente = ? , Or_IdMesero = ? WHERE Or_Id = ? ;";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, objectOrden.getFechaServicio());
            statement.setInt(2, objectOrden.getIdPlatoFK());
            statement.setInt(3, objectOrden.getIdClienteFK());
            statement.setInt(4, objectOrden.getIdMeseroFK());
            statement.setInt(5, objectOrden.getId());

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

    //& OBTIENE TODAS LAS ORDENES, USA JOIN PARA MOSTRAR PARAMETROS ENTENDIBLES AL USUARIO
    public ArrayList<Orden> getAllOrdens() {
        ArrayList<Orden> arrayOrden = new ArrayList<>();
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();
            String sql = "SELECT Or_Id,Or_FechaServicio,concat(Cl_Nombres,concat(\" \",Cl_Apellidos)) as NombreCliente ,Pl_Nombre,concat(Me_Nombres,concat(\" \",Me_Apellidos)) as NombreMesero FROM Orden inner join cliente on Or_IdCliente =  Cl_id inner join plato on Or_IdPlato = Pl_id  inner join mesero on Or_IdMesero = Me_id ORDER BY Or_Id;";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                Orden objectOrden = new Orden(result.getInt(1), result.getString(2), result.getString(3),
                        result.getString(4), result.getString(5));
                arrayOrden.add(objectOrden);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : " + ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
        return arrayOrden;
    }

    //& BUSCA UNA ORDEN ESPECIFICA 
    public ArrayList<Orden> getSearchOrden(Orden objectOrden, String filter,ArrayList<Integer> pos) {
        ArrayList<Orden> arrayOrden = new ArrayList<>();
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();
            
            String sql = "SELECT Or_Id,Or_FechaServicio,concat(Cl_Nombres,concat(\" \",Cl_Apellidos)) as NombreCliente ,Pl_Nombre,concat(Me_Nombres,concat(\" \",Me_Apellidos)) as NombreMesero FROM Orden inner join cliente on Or_IdCliente =  Cl_id inner join plato on Or_IdPlato = Pl_id  inner join mesero on Or_IdMesero = Me_id WHERE "+ filter;

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(pos.get(0), objectOrden.getId());
            statement.setString(pos.get(1), objectOrden.getFechaServicio());
            statement.setInt(pos.get(2), objectOrden.getIdClienteFK());            
            statement.setInt(pos.get(3), objectOrden.getIdPlatoFK());
            statement.setInt(pos.get(4), objectOrden.getIdMeseroFK());
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Orden object = new Orden(result.getInt(1), result.getString(2), result.getString(3),
                        result.getString(4), result.getString(5));
                arrayOrden.add(object);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : " + ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
        return arrayOrden;
    }
    
    //& TRANSFORMA LA INFORMACION NO COMPRENDIBLE PARA EL USUARIO A UNA QUE PUEDE SER SELECCIONADA EN UN COMBOBOX
    public ArrayList<String> getMeseros() {
        ArrayList<String> arrayOrden = new ArrayList<>();
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "select concat(Me_Nombres,concat(\" \",Me_Apellidos)) as Nombre from Mesero order by Me_Id;";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                arrayOrden.add(result.getString(1));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : " + ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
        return arrayOrden;
    }

    //& TRANSFORMA LA INFORMACION NO COMPRENDIBLE PARA EL USUARIO A UNA QUE PUEDE SER SELECCIONADA EN UN COMBOBOX
    public ArrayList<String> getClientes() {
        ArrayList<String> arrayOrden = new ArrayList<>();
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "select concat(Cl_Nombres,concat(\" \",Cl_Apellidos)) as Nombre from Cliente order by Cl_Id;";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                arrayOrden.add(result.getString(1));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : " + ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
        return arrayOrden;
    }

    //& TRANSFORMA LA INFORMACION NO COMPRENDIBLE PARA EL USUARIO A UNA QUE PUEDE SER SELECCIONADA EN UN COMBOBOX
    public ArrayList<String> getPlatos() {
        ArrayList<String> arrayOrden = new ArrayList<>();
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "select Pl_Nombre from Plato order by Pl_Id;";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                arrayOrden.add(result.getString(1));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : " + ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
        return arrayOrden;
    }

    //& TRANSFORMA LA INFORMACION VISIBLE Y COMPRENDIBLE MOSTRADA AL USUARIO EN INFORMACION VALIDAD PARA INSERTAR
    public int getIdMesero(String nombre) {
        int idMesero = 0;
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "select Me_Id from Mesero Where concat(Me_Nombres,concat(\" \",Me_Apellidos)) = '" + nombre
                    + "'";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                idMesero = result.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : " + ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
        return idMesero;
    }
    
    //& TRANSFORMA LA INFORMACION VISIBLE Y COMPRENDIBLE MOSTRADA AL USUARIO EN INFORMACION VALIDAD PARA INSERTAR
    public int getIdCliente(String nombre) {
        int idCliente = 0;
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "select Cl_id from Cliente Where concat(Cl_Nombres,concat(\" \",Cl_Apellidos)) = '" + nombre
                    + "'";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                idCliente = result.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : " + ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
        return idCliente;
    }
    
    //& TRANSFORMA LA INFORMACION VISIBLE Y COMPRENDIBLE MOSTRADA AL USUARIO EN INFORMACION VALIDAD PARA INSERTAR
    public int getIdPlato(String nombre) {
        int idPlato = 0;
        try {
            if (conn == null)
                conn = ConnectionDB.getConnection();

            String sql = "select Pl_id from Plato Where Pl_nombre = '"+nombre+"'";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                idPlato = result.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : " + ex.getErrorCode() + "\nError :" + ex.getMessage());
        }
        return idPlato;
    }
}
