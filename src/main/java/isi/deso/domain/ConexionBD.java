package isi.deso.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static Connection con = null;
    private static final String driver = "org.postgresql.Driver";
    private static final String user = "postgres";
    private static final String password = "alenachosantiagu";
    private static final String url = "jdbc:postgresql://localhost:5432/postgres";

    public ConexionBD() {}

    public void connect() {
        try {
            Class.forName(driver);
            con = (Connection) DriverManager.getConnection(url,user,password);
            if(con != null){
                System.out.println("Conexion exitosa");
            }
        }
        catch (ClassNotFoundException | SQLException e){
            System.out.println("Error de conexion " + e);
        }
    }

    public static Connection getConnection() throws SQLException{
        con = (Connection) DriverManager.getConnection(url, user, password);
        return con;
    }
}
