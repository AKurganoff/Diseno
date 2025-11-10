package isi.deso.Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
    private static Connection con;
    private static final String driver = "com.postgresql.cj.jdbc.Driver";
    private static final String user = "admin@admin.com";
    private static final String password = "admin";
    private static final String url = "http://localhost:8080/browser/";


    public void connect() {
        con = null;
        try {
            Class.forName(driver);
            con = (Connection) DriverManager.getConnection(url,user,password);
            if(con != null){
                System.out.println("Conexion exitosa");
            }
        }
        catch (ClassNotFoundException | SQLException e){
            System.out.println("Error de conexion" + e);
        }
}
