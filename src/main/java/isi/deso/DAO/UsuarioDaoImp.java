/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import isi.deso.Modelo.DireccionDTO;
import isi.deso.domain.ConexionBD;
import isi.deso.domain.Usuario;

/**
 * Implementacion de UsuarioDAO que almacena
 * usuarios en memoria
 * <p>
 * Implementa el patron Singleton, solo puede existir una instancia
 * de esta clase durante la ejecucion del programa.
 * Mantiene un mapa interno de usuarios registrados durante la ejecucion.
 * Los datos no se guardan de forma persistente.
 * </p>
 * 
 * @see isi.deso.DAO.UsuarioDAO
 * @see isi.deso.domain.Usuario
 */
public class UsuarioDAOImp implements UsuarioDAO{
    private Map<String, Usuario> usuarios; //key: ID
    private static UsuarioDAOImp instancia;
    
    /**
     * Crea una nueva instancia de UsuarioDaoImp con su mapa.
     */
    private UsuarioDAOImp (){
        this.usuarios = new HashMap<String, Usuario>();
    }
    
    /**
     * Devuelve la instancia unica de {@code UsuarioDaoImp}.
     *
     * @return instancia unica del {@code UsuarioDaoImp}
     */
    public static UsuarioDAOImp getInstance() {
	if (instancia == null) {
            instancia = new UsuarioDAOImp();
	}
        return instancia;
    }
    
    /**
     * Devuelve una lista con todos los usuarios registrados en el mapa.
     *
     * @return coleccion de usuarios
     */
    @Override
    public List<Usuario> listaCompUser() {
	    return new ArrayList<>(this.usuarios.values());
    }

    /**
     * Agrega un nuevo usuario al mapa.
     *
     * @param usuario usuario a registrar
     */
    @Override
    public void guardarUsuario(Usuario usuario) {
	    this.usuarios.put(usuario.getNUsuario(), usuario);
    }

    @Override
    public boolean validarUsuarioBD(String nombreUsuario, String contrasena) {
        String sql = "SELECT FROM usuario WHERE nombre = ? AND psw = ?";
        try(Connection conn = ConexionBD.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1, nombreUsuario);
                pstmt.setString(2, contrasena);

                ResultSet rs = pstmt.executeQuery();

                if(rs.next()){
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
    }

}
