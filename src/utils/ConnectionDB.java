package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import org.json.simple.*;
import org.json.simple.parser.*;

public class ConnectionDB {
    
    //Get JSON Simple: https://code.google.com/archive/p/json-simple/downloads
    
    public static Connection getConnection() {
        JSONParser parser = new JSONParser();
        Connection conn = null;
        // conectar
        try {
            String credentials_path = System.getProperty("user.dir") + "/src/utils/db_credentials.json";
            JSONObject jsonObject = (JSONObject)parser.parse(new FileReader(credentials_path));
            
            String host     = (String)jsonObject.get("db_ip");
            String port     = (String)jsonObject.get("dp_port");
            String username = (String)jsonObject.get("db_user");
            String password = (String) jsonObject.get("db_pssword");
            String nameDB = "restaurante";
            String dbURL = String.format("jdbc:mysql://%s:%s/%s",host,port,nameDB) ;
            
            conn = DriverManager.getConnection(dbURL, username, password);
            if( conn != null ) 
                System.out.println ( "Conectado" );
        } 
        catch( SQLException | FileNotFoundException ex ) {
            ex.printStackTrace();
        } 
        catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }
        
        return conn;
    }
    
    public static void main(String[] args){
        getConnection();
    }
}


/* String sql = "INSERT  INTO  libro(libId ,libNombre ,libPub ,ediId ,autId ,libPrecio) VALUES( VALUES (?,?, ?, ?, ?, ?)";PreparedStatement  statement = conn.prepareStatement(sql)statement.setInt(1,1010);
statement.setString(2,"La  Hojarasca");
statement.setInt(3,1955);
statement.setInt(4,1);
statement.setInt(5,1);
statement.setDouble(6,95000.0);
int  rowsInserted = statement.executeUpdate ();
if(rowsInserted>0){
    System.out.println("Inserci ́on exitosa!");
} */

/* String sql = "SELECT * FROM  libro";
Statement statement = conn.createStatement();
ResultSet result = statement.executeQuery(sql);
int  count = 0;
while(result.next()){
    String  titulo = result.getString (2);
    Int  pub = result.getInt (3);
    Double  costo = result.getDouble (6);

    System.out.println("T ́ıtulo: " +titulo + "A~no publicaci ́on: "   + pub +" Costo: " + costo);
} */


