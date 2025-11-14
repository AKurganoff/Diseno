/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.DAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

import isi.deso.Modelo.DireccionDTO;
import isi.deso.domain.ConexionBD;

/**
 * Implementacion de DireccionDAO que utiliza archivos de texto como
 * medio de persistencia.
 * <p>
 * Se encarga de implementar el CRUD de direcciones trabajando
 * con el archivo direccionesCargadas.txt
 * </p>
 * 
 * @see isi.deso.DAO.DireccionDAO
 * @see isi.deso.Modelo.DireccionDTO
 */
public class DireccionDAOImp implements DireccionDAO{
    private static final String ARCHIVO = "direccionesCargadas.txt";
    private static final String SEPARADOR = ";";

    /**
     * Almacena una direccion en el archivo
     *
     * @param d {@link isi.deso.Modelo.DireccionDTO} datos de la direccion
     */
    @Override
    public void crearDireccion(DireccionDTO d) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {

            StringBuilder sb = new StringBuilder();
            sb.append(d.getCalle()).append(SEPARADOR)
              .append(d.getNumero()).append(SEPARADOR)
              .append(d.getDepartamento()).append(SEPARADOR)
              .append(d.getPiso()).append(SEPARADOR)
              .append(d.getCodigoPostal()).append(SEPARADOR)
              .append(d.getLocalidad()).append(SEPARADOR)
              .append(d.getProvincia()).append(SEPARADOR)
              .append(d.getPais()).append(SEPARADOR);
            bw.write(sb.toString());
            bw.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Devuelve una lista con todas las direcciones leidas en el archivo.
     *
     * @return coleccion con las direcciones leidos
     */
    @Override
    public List<DireccionDTO> obtenerTodos() {
        List<DireccionDTO> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(SEPARADOR, -1); // -1 para conservar vacíos
                DireccionDTO dir = new DireccionDTO(
                    datos[0], datos[1], datos[2], datos[3],
                    datos[4], datos[5], datos[6], datos[7]
                );
                lista.add(dir);
            }
        } catch (FileNotFoundException e) {
            // Si el archivo no existe, se crea vacío al guardar el primero
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Busca una direccion con la que correspondan los datos recibidos por parametro.
     *
     * @param calle nombre de la calle
     * @param numero numero de la calle
     * @param departamento numero de departamento
     * @param piso numero de piso
     * @param codigoPostal codigo postal de la direccion
     * @return direccion de existir, caso contrario devuelve {@code null}
     */
    @Override
    public DireccionDTO obtenerDireccion(String calle, String numero, String departamento, String piso, String codigoPostal) {
        return obtenerTodos().stream()
                .filter(d -> d.getCalle().equalsIgnoreCase(calle)
                        && d.getNumero().equalsIgnoreCase(numero)
                        && d.getDepartamento().equalsIgnoreCase(departamento)
                        && d.getPiso().equalsIgnoreCase(piso)
                        && d.getCodigoPostal().equalsIgnoreCase(codigoPostal))
                .findFirst()
                .orElse(null);
    }
    
    /** Modifica la direccion almacenada en el archivo */
    @Override
    public void modificarDireccion(String calle, String numero, String departamento, String piso, String codigoPostal) {
        throw new UnsupportedOperationException("modificarDireccion pendiente (no requerido en CU01/CU02)");
    }

    /** Elimina la direccion almacenada en el archivo */
    @Override
    public void eliminarDireccion(DireccionDTO d) {
        throw new UnsupportedOperationException("eliminarDireccion pendiente (no requerido en CU01/CU02)");
    }

    @Override
    public void registrarDireccionBD(DireccionDTO d) {
        String calle, numero, departamento, piso, codigoPostal, localidad, provincia, pais;
        calle = d.getCalle();
        numero = d.getNumero();
        departamento = d.getDepartamento();
        piso = d.getPiso();
        codigoPostal = d.getCodigoPostal();
        localidad = d.getLocalidad();
        provincia = d.getProvincia();
        pais = d.getPais();
        String sql = "INSERT INTO DIRECCION(calle, numero, departamento, piso, codigo_postal, localidad, provincia, pais) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, calle);
            pstmt.setString(2, numero);
            pstmt.setString(3, departamento);
            pstmt.setString(4, piso);
            pstmt.setString(5, codigoPostal);
            pstmt.setString(6, localidad);
            pstmt.setString(7, provincia);
            pstmt.setString(8, pais);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<DireccionDTO> buscarDireccionBDxPais(String ppais){
        List<DireccionDTO> listaxPais = new ArrayList<DireccionDTO>();
        String sql = "SELECT * FROM DIRECCION WHERE pais = ?";
        try(Connection conn = ConexionBD.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1, ppais);

                ResultSet rs = pstmt.executeQuery();

                while(rs.next()){
                    DireccionDTO d = new DireccionDTO();
                    d.setCalle(rs.getString("calle"));
                    d.setNumero(rs.getString("numero"));
                    d.setDepartamento(rs.getString("departamento"));
                    d.setPiso(rs.getString("piso"));
                    d.setCodigoPostal(rs.getString("cod_postal"));
                    d.setLocalidad(rs.getString("localidad"));
                    d.setProvincia(rs.getString("provincia"));
                    d.setPais(rs.getString("pais"));
                    listaxPais.add(d);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return listaxPais;
    }
    @Override
    public List<DireccionDTO> buscarDireccionBDxCalle(String pcalle){
        List<DireccionDTO> listaxCalle = new ArrayList<DireccionDTO>();
        String sql = "SELECT * FROM DIRECCION WHERE calle = ?";
        try(Connection conn = ConexionBD.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1, pcalle);

                ResultSet rs = pstmt.executeQuery();

                while(rs.next()){
                    DireccionDTO d = new DireccionDTO();
                    d.setCalle(rs.getString("calle"));
                    d.setNumero(rs.getString("numero"));
                    d.setDepartamento(rs.getString("departamento"));
                    d.setPiso(rs.getString("piso"));
                    d.setCodigoPostal(rs.getString("cod_postal"));
                    d.setLocalidad(rs.getString("localidad"));
                    d.setProvincia(rs.getString("provincia"));
                    d.setPais(rs.getString("pais"));
                    listaxCalle.add(d);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return listaxCalle;
    }
    @Override
    public List<DireccionDTO> buscarDireccionBDxLocalidad(String ploc){
        List<DireccionDTO> listaxLoc = new ArrayList<DireccionDTO>();
        String sql = "SELECT * FROM DIRECCION WHERE localidad = ?";
        try(Connection conn = ConexionBD.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1, ploc);

                ResultSet rs = pstmt.executeQuery();

                while(rs.next()){
                    DireccionDTO d = new DireccionDTO();
                    d.setCalle(rs.getString("calle"));
                    d.setNumero(rs.getString("numero"));
                    d.setDepartamento(rs.getString("departamento"));
                    d.setPiso(rs.getString("piso"));
                    d.setCodigoPostal(rs.getString("cod_postal"));
                    d.setLocalidad(rs.getString("localidad"));
                    d.setProvincia(rs.getString("provincia"));
                    d.setPais(rs.getString("pais"));
                    listaxLoc.add(d);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return listaxLoc;
    }
}
