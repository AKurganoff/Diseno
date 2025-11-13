/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.Gestor;

import isi.deso.DAO.DireccionDAO;
import isi.deso.DAO.HuespedDAO;
import isi.deso.Modelo.DireccionDTO;
import isi.deso.Modelo.HuespedDTO;
import isi.deso.Strategy.Validacion;
import isi.deso.domain.Huesped;

/**
 * Se encarga principalmente de realizar validaciones
 * y el proceso de registro para huespedes nuevos.
 * 
 * @see isi.deso.Strategy
 * @see isi.deso.DAO.DireccionDAO
 * @see isi.deso.DAO.HuespedDAO
 */
public class GestorHuesped {
    private DireccionDAO ddao;
    private HuespedDAO dao;
    private Validacion validaciones;
    private Validacion valid;
    
    /**
     * Crea una instancia de GestorHuesped con las instancias necesarias
     * para validar y registrar un huesped nuevo.
     * 
     * @param ddao instancia de {@link isi.deso.DAO.DireccionDAO}
     * @param dao instancia de {@link isi.deso.DAO.HuespedDAO}
     * @param validaciones instancia de {@link isi.deso.Strategy.Validacion}
     * @param valid instancia de un segundo tipo de {@link isi.deso.Strategy.Validacion}
     */
    public GestorHuesped(DireccionDAO ddao,HuespedDAO dao,Validacion validaciones, Validacion valid) {
        this.ddao = ddao;
        this.dao = dao;
        this.validaciones = validaciones;
        this.valid = valid;
    }
    public boolean ValidarCampos(HuespedDTO h){
        return validaciones.validar(h);
    }
    public boolean ValidacionDocumentoUnico(HuespedDTO h){
        return valid.validar(h);
    }
    /**
     * Registra un nuevo huesped en el sistema.
     *
     * @param h huesped a registrar
     */
    public void crearHuesped(HuespedDTO h){
        dao.crearHuespedconDTO(h);
    }

    /**
     * Registra una nueva direccion en el sistema.
     *
     * @param d direccion a registrar
     */
    public void crearDireccion(DireccionDTO d){
        ddao.crearDireccion(d);
    }

    public Huesped pasarDTOaHuesped(HuespedDTO h){
        Huesped nuevo = new Huesped(
            h.getNombres(),
            h.getApellido(),
            h.getTipoDocumento(),
            h.getNumeroDocumento(),
            h.getCuit(),
            h.getPosicion(),
            h.getFechaNacimiento(),
            h.getDireccion(),
            h.getTelefono(),
            h.getEmail(),
            h.getOcupacion(),
            h.getNacionalidad()
        );
        return nuevo;
    }
}
