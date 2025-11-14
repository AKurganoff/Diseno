/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.DAO;

import java.util.List;

import isi.deso.Modelo.DireccionDTO;

/**
 * Interfaz del DAO para direcciones.
 * <p>
 * Define la base del DAO que se encargara
 * de el CRUD de direcciones en el sistema.
 * </p>
 */
public interface DireccionDAO {
    void crearDireccion(DireccionDTO d);
    void modificarDireccion(String calle, String numero, String departamento, String piso, String codigoPostal);
    void eliminarDireccion(DireccionDTO d);
    DireccionDTO obtenerDireccion(String calle, String numero, String departamento, String piso, String codigoPostal);
    List<DireccionDTO> obtenerTodos();
    void registrarDireccionBD(DireccionDTO d);
    List<DireccionDTO> buscarDireccionBDxPais(String pais);
    List<DireccionDTO> buscarDireccionBDxLocalidad(String localidad);
    List<DireccionDTO> buscarDireccionBDxCalle(String calle);
}
