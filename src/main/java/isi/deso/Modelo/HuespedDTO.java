package isi.deso.Modelo;

import java.time.LocalDate;

import isi.deso.domain.PosicionIVA;
import isi.deso.domain.TipoDocumento;
    /**
     * Crea una instancia de HuespedDTO con todos sus atributos esenciales.
     * 
     * @param nombre nombre del huesped
     * @param apellido apellido del huesped
     * @param tipo {@link isi.deso.domain.TipoDocumento} del huesped
     * @param numero numero del documento
     * @param direccion {@link isi.deso.Modelo.DireccionDTO} del huesped
     * @param telefono numero de telefono del huesped
     * @param email correo electronico del huesped
     * @param posicionIVA {@link isi.deso.domain.PosicionIVA} del huesped
    */
public class HuespedDTO {
    private String nombres;
    private String apellido;
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
    private String cuit;
    private PosicionIVA posicion;
    private LocalDate fNac;
    private DireccionDTO dir;
    private String telefono;
    private String email;
    private String ocupacion;
    private String nacionalidad;

    public HuespedDTO(String nombres, String apellido, TipoDocumento tipoDocumento, String numeroDocumento, String cuit,
            PosicionIVA posicion, LocalDate fNac, DireccionDTO dir, String telefono, String email,
            String ocupacion, String nacionalidad) {
            this.nombres = nombres;
            this.apellido = apellido;
            this.tipoDocumento = tipoDocumento;
            this.numeroDocumento = numeroDocumento;
            this.cuit = cuit;
            this.posicion = posicion;
            this.fNac = fNac;
            this.dir = dir;
            this.telefono = telefono;
            this.email = email;
            this.ocupacion = ocupacion;
            this.nacionalidad = nacionalidad;
        }

    public String getNombres() {
        return nombres;
    }
    public String getApellido() {
        return apellido;
    }
    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }
    public String getNumeroDocumento() {
        return numeroDocumento;
    }
    public String getCuit() {
        return cuit;
    }
    public PosicionIVA getPosicion() {
        return posicion;
    }
    public LocalDate getFechaNacimiento() {
        return fNac;
    }
    public DireccionDTO getDireccion() {
        return dir;
    }
    public String getTelefono() {
        return telefono;
    }
    public String getEmail() {
        return email;
    }
    public String getOcupacion() {
        return ocupacion;
    }
    public String getNacionalidad() {
        return nacionalidad;
    }
    
    public void setNombre(String nombre) {
        this.nombres=nombre;
    }
    public void setApellido(String apellido) {
        this.apellido=apellido;
    }
    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento=tipoDocumento;
    }
    public void setNumeroDocumento(String nroDoc) {
        this.numeroDocumento=nroDoc;
    }
    public void setCuit(String cuit) {
        this.cuit=cuit;
    }
    public void setPosicion(PosicionIVA pos) {
        this.posicion=pos;
    }
    public void setfNac(LocalDate fNac) {
        this.fNac=fNac;
    }
    public void setDir(DireccionDTO dir) {
        this.dir=dir;
    }
    public void setTelefono(String telefono) {
        this.telefono=telefono;
    }
    public void setEmail(String email) {
        this.email=email;
    }
    public void setOcupacion(String ocupacion) {
        this.ocupacion=ocupacion;
    }
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad=nacionalidad;
    }
}
