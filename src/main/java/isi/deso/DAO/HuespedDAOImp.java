package isi.deso.DAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import isi.deso.Modelo.DireccionDTO;
import isi.deso.Modelo.HuespedDTO;
import isi.deso.domain.ConexionBD;
import isi.deso.domain.Huesped;
import isi.deso.domain.PosicionIVA;
import isi.deso.domain.TipoDocumento;

/**
 * Implementacion de HuespedDAO que utiliza archivos de texto como
 * medio de persistencia.
 * <p>
 * Se encarga de implementar el CRUD de huespedes trabajando
 * con el archivo huespedesCargados.txt
 * </p>
 * 
 * @see isi.deso.DAO.HuespedDAO
 * @see isi.deso.domain.Huesped
 */
public class HuespedDAOImp implements HuespedDAO{
    
    private static final String ARCHIVO = "huespedesCargados.txt";
    private static final String SEPARADOR = ";";
    private static HuespedDAOImp instancia; 

    /**
     * Almacena un huesped en el archivo
     *
     * @param h {@link isi.deso.domain.Huesped} datos del huesped
     */
    

    public HuespedDAOImp getInstance() {
        if (instancia == null) {
            instancia = new HuespedDAOImp();
        }
        return instancia;
    } 

    @Override
    public void crearHuesped(Huesped h) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {

            StringBuilder sb = new StringBuilder();
            sb.append(h.getApellido()).append(SEPARADOR)
              .append(h.getNombres()).append(SEPARADOR)
              .append(h.getTipoDocumento()).append(SEPARADOR)
              .append(h.getNumeroDocumento()).append(SEPARADOR)
              .append(h.getCuit() != null ? h.getCuit() : "").append(SEPARADOR)
              .append(h.getPosicionIVA()).append(SEPARADOR)
              .append(h.getFechaNacimiento()).append(SEPARADOR);

            DireccionDTO d = h.getDireccion();
            sb.append(d.getCalle()).append(SEPARADOR)
              .append(d.getNumero()).append(SEPARADOR)
              .append(d.getDepartamento()).append(SEPARADOR)
              .append(d.getPiso()).append(SEPARADOR)
              .append(d.getCodigoPostal()).append(SEPARADOR)
              .append(d.getLocalidad()).append(SEPARADOR)
              .append(d.getProvincia()).append(SEPARADOR)
              .append(d.getPais()).append(SEPARADOR);

            sb.append(h.getTelefono()).append(SEPARADOR)
              .append(h.getEmail() != null ? h.getEmail() : "").append(SEPARADOR)
              .append(h.getOcupacion()).append(SEPARADOR)
              .append(h.getNacionalidad());

            bw.write(sb.toString());
            bw.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void crearHuespedconDTO(HuespedDTO h) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {

            StringBuilder sb = new StringBuilder();
            sb.append(h.getApellido()).append(SEPARADOR)
              .append(h.getNombres()).append(SEPARADOR)
              .append(h.getTipoDocumento()).append(SEPARADOR)
              .append(h.getNumeroDocumento()).append(SEPARADOR)
              .append(h.getCuit() != null ? h.getCuit() : "").append(SEPARADOR)
              .append(h.getPosicion()).append(SEPARADOR)
              .append(h.getFechaNacimiento()).append(SEPARADOR);

            DireccionDTO d = h.getDireccion();
            sb.append(d.getCalle()).append(SEPARADOR)
              .append(d.getNumero()).append(SEPARADOR)
              .append(d.getDepartamento()).append(SEPARADOR)
              .append(d.getPiso()).append(SEPARADOR)
              .append(d.getCodigoPostal()).append(SEPARADOR)
              .append(d.getLocalidad()).append(SEPARADOR)
              .append(d.getProvincia()).append(SEPARADOR)
              .append(d.getPais()).append(SEPARADOR);

            sb.append(h.getTelefono()).append(SEPARADOR)
              .append(h.getEmail() != null ? h.getEmail() : "").append(SEPARADOR)
              .append(h.getOcupacion()).append(SEPARADOR)
              .append(h.getNacionalidad());

            bw.write(sb.toString());
            bw.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Devuelve una lista con los huespedes leidos en el archivo.
     *
     * @return coleccion con los huespedes leidos
     */
    @Override
    public List<Huesped> obtenerTodos() {
        List<Huesped> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(SEPARADOR, -1); // -1 para conservar vacíos
                if (datos.length >= 19) {
                    // Reconstrucción del objeto
                    DireccionDTO dir = new DireccionDTO(
                        datos[7], datos[8], datos[9], datos[10],
                        datos[11], datos[12], datos[13], datos[14]
                    );

                    Huesped h = new Huesped(
                        datos[1], // nombre
                        datos[0], // apellido
                        TipoDocumento.valueOf(datos[2].toUpperCase()), // tipo doc
                        datos[3], // nro doc
                        datos[4], // cuit
                        PosicionIVA.valueOf(datos[5]), // pos IVA
                        LocalDate.parse(datos[6]), // fecha nacimiento
                        dir,
                        datos[15], // telefono
                        datos[16], // email
                        datos[17], // ocupacion
                        datos[18]  // nacionalidad
                    );

                    lista.add(h);
                }
            }
        } catch (FileNotFoundException e) {
            // Si el archivo no existe, se crea vacío al guardar el primero
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Busca un huesped con el mismo tipo y numero de documento que recibe por parametros.
     *
     * @param tipo {@link isi.deso.domain.TipoDocumento} del huesped
     * @param num numero del documento
     * @return huesped de existir, caso contrario devuelve {@code null}
     */
    @Override
    public Huesped obtenerHuesped(TipoDocumento tipo, String num) {
        return obtenerTodos().stream()
                .filter(h -> h.getTipoDocumento() == tipo
                        && h.getNumeroDocumento().equalsIgnoreCase(num))
                .findFirst()
                .orElse(null);
    }

    /**
     * Elimina el huesped recibido por parametro del archivo
     *
     * @param h huesped a eliminar {@link isi.deso.domain.Huesped}
     * @return huesped de existir, caso contrario devuelve {@code null}
     */
    @Override
    public void eliminarHuesped(Huesped h) {
        File archivo = new File("huespedesCargados.txt");
        File archivoTemp = new File("huespedesCargados_temp.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(archivo));
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivoTemp))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";", -1); 
                if (datos.length >= 19) { // cantidad real de campos
                    String tipoDoc = datos[2];
                    String numDoc  = datos[3];

                    if (!(tipoDoc.equalsIgnoreCase(h.getTipoDocumento().toString()) &&
                        numDoc.equalsIgnoreCase(h.getNumeroDocumento()))) {
                        bw.write(linea);
                        bw.newLine();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // reemplazar archivo
        if (!archivo.delete()) System.out.println("No se pudo borrar el archivo original.");
        if (!archivoTemp.renameTo(archivo)) System.out.println("No se pudo renombrar el archivo temporal.");
    }

    /**
     * Modifica el huesped almacenado en el archivo
     *
     * @param tipoOriginal {@link isi.deso.domain.TipoDocumento} del huesped en el archivo
     * @param numOriginal numero del documento en el archivo
     * @param hActualizado nuevos datos del huesped
     */
    @Override
    public void modificarHuesped(TipoDocumento tipoOriginal, String numOriginal, Huesped hActualizado) {
        File archivo = new File(ARCHIVO);
        File archivoTemp = new File("huespedesCargados_temp.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(archivo));
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivoTemp))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(SEPARADOR, -1);
                if (datos.length >= 19) {
                    String tipoDoc = datos[2];
                    String numDoc  = datos[3];

                    // Si coincide con el huésped a modificar
                    if (tipoDoc.equalsIgnoreCase(tipoOriginal.toString()) &&
                        numDoc.equalsIgnoreCase(numOriginal)) {

                        // Reconstruir línea con los datos actualizados
                        StringBuilder sb = new StringBuilder();
                        sb.append(hActualizado.getApellido()).append(SEPARADOR)
                        .append(hActualizado.getNombres()).append(SEPARADOR)
                        .append(hActualizado.getTipoDocumento()).append(SEPARADOR)
                        .append(hActualizado.getNumeroDocumento()).append(SEPARADOR)
                        .append(hActualizado.getCuit() != null ? hActualizado.getCuit() : "").append(SEPARADOR)
                        .append(hActualizado.getPosicionIVA()).append(SEPARADOR)
                        .append(hActualizado.getFechaNacimiento()).append(SEPARADOR);

                        var d = hActualizado.getDireccion();
                        sb.append(d.getCalle()).append(SEPARADOR)
                        .append(d.getNumero()).append(SEPARADOR)
                        .append(d.getDepartamento()).append(SEPARADOR)
                        .append(d.getPiso()).append(SEPARADOR)
                        .append(d.getCodigoPostal()).append(SEPARADOR)
                        .append(d.getLocalidad()).append(SEPARADOR)
                        .append(d.getProvincia()).append(SEPARADOR)
                        .append(d.getPais()).append(SEPARADOR);

                        sb.append(hActualizado.getTelefono()).append(SEPARADOR)
                        .append(hActualizado.getEmail() != null ? hActualizado.getEmail() : "").append(SEPARADOR)
                        .append(hActualizado.getOcupacion()).append(SEPARADOR)
                        .append(hActualizado.getNacionalidad());

                        linea = sb.toString(); // reemplaza la línea
                    }

                    // Escribimos la línea (modificada o no) en el archivo temporal
                    bw.write(linea);
                    bw.newLine();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Reemplazamos el archivo original por el temporal
        if (!archivo.delete()) {
            System.out.println("No se pudo borrar el archivo original.");
        }
        if (!archivoTemp.renameTo(archivo)) {
            System.out.println("No se pudo renombrar el archivo temporal.");
        }
    }

    @Override
    public void registrarHuespedBD(HuespedDTO h){
        String  dni, nombre, apellido, ocupacion, mail, calle, numero, departamento, piso, codigoPostal;
        TipoDocumento tipodni;
        PosicionIVA pos_iva;
        int edad;
        LocalDate fecha_nacimiento;

        dni = h.getNumeroDocumento();
        nombre = h.getNombres();
        apellido = h.getApellido();
        fecha_nacimiento = h.getFechaNacimiento();
        ocupacion = h.getOcupacion();
        tipodni = h.getTipoDocumento();
        mail = h.getEmail();
        pos_iva = h.getPosicion();
        calle = h.getDireccion().getCalle();
        numero = h.getDireccion().getNumero();
        departamento = h.getDireccion().getDepartamento();
        piso = h.getDireccion().getPiso();
        codigoPostal = h.getDireccion().getCodigoPostal();
        
        //Calculo la edad
        LocalDate fechaActual = LocalDate.now();
        Period periodo = Period.between(fecha_nacimiento, fechaActual);
        edad = periodo.getYears();

        //Parseo LocalDate a Date
        LocalDateTime f_nac = fecha_nacimiento.atStartOfDay();
        ZonedDateTime zone_date = f_nac.atZone(ZoneId.systemDefault());
        Date f_nacDate = Date.from(zone_date.toInstant());
        
        long milisegundos = f_nacDate.getTime();
        java.sql.Date fecha_nacSQL = new java.sql.Date(milisegundos);



        String sql = "INSERT INTO HUESPED(dni, nombre, apellido, fecha_nacimiento, ocupacion, tipodni, mail, pos_iva, calle, numero, departamento, piso, codigo_postal) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dni);
            pstmt.setString(2, nombre);
            pstmt.setString(3, apellido);
            pstmt.setInt(4, edad);
            pstmt.setDate(5, fecha_nacSQL);
            pstmt.setString(6, ocupacion);
            pstmt.setString(7, tipodni.toString());
            pstmt.setString(8, mail);
            pstmt.setString(9, pos_iva.toString());
            pstmt.setString(10, calle);
            pstmt.setString(11, numero);
            pstmt.setString(12, departamento);
            pstmt.setString(13, piso);
            pstmt.setString(14, codigoPostal);
            

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PosicionIVA convertirStringAPos(String pos){
        PosicionIVA posIVA;
        switch (pos) {
            case "Responsable Inscripto":
                return posIVA = PosicionIVA.ResponsableInscripto;
                
            case "Monotributista":
                return posIVA = PosicionIVA.Monotributista;
                
            case "Exento":
                return posIVA = PosicionIVA.Exento;
                
            case "Consumidor Final":
                return posIVA = PosicionIVA.ConsumidorFinal;
            
            default:
                return posIVA = PosicionIVA.ConsumidorFinal;
        }
    }

    public TipoDocumento pasarStringATipoDoc(String tipoDoc){
        TipoDocumento td;
        switch (tipoDoc) { 
            case "DNI":
                td = TipoDocumento.DNI;
                break;
            case "LE":
                td = TipoDocumento.LE;
                break;
            case "LC" :
                td = TipoDocumento.LC;
                break;
            case "Pasaporte":
                td = TipoDocumento.PASAPORTE;
                break;
            default:
                td = TipoDocumento.OTRO;
        }
        return td;
    }

    @Override
    public List<HuespedDTO> buscarBDxNombre(String pnombre){
        List<HuespedDTO> listaxNombre = new ArrayList<>();
        String sql = "SELECT * FROM HUESPED WHERE nombre = ?";
        try(Connection conn = ConexionBD.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1, pnombre);

                ResultSet rs = pstmt.executeQuery();

                while(rs.next()){
                    HuespedDTO h = new HuespedDTO();
                    h.setNombre(rs.getString("nombre"));
                    h.setApellido(rs.getString("apellido"));
                    h.setTipoDocumento(pasarStringATipoDoc(rs.getString("tipoDocumento")));
                    h.setNumeroDocumento(rs.getString("dni"));
                    h.setCuit(rs.getString("cuit"));
                    h.setPosicion(convertirStringAPos(rs.getString("pos_iva")));
                    h.setfNac(rs.getDate("fecha_nacimineto").toLocalDate());
                    h.setEmail(rs.getString("mail"));
                    h.setOcupacion(rs.getString("ocupacion"));
                    
                    DireccionDTO d = new DireccionDTO();
                    d.setCalle(rs.getString("calle"));
                    d.setNumero(rs.getString("numero"));
                    d.setDepartamento(rs.getString("departamento"));
                    d.setPiso(rs.getString("piso"));
                    d.setCodigoPostal(rs.getString("cod_postal"));

                    h.setDir(d);
                    h.setNacionalidad(d.getPais());

                    listaxNombre.add(h);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return listaxNombre;
    }

    @Override
    public List<HuespedDTO> buscarBDxApellido(String papellido){
        List<HuespedDTO> listaxApellido = new ArrayList<>();
        String sql = "SELECT * FROM HUESPED WHERE apellido = ?";
        try(Connection conn = ConexionBD.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1, papellido);

                ResultSet rs = pstmt.executeQuery();

                while(rs.next()){
                    HuespedDTO h = new HuespedDTO();
                    h.setNombre(rs.getString("nombre"));
                    h.setApellido(rs.getString("apellido"));
                    h.setTipoDocumento(pasarStringATipoDoc(rs.getString("tipoDocumento")));
                    h.setNumeroDocumento(rs.getString("dni"));
                    h.setCuit(rs.getString("cuit"));
                    h.setPosicion(convertirStringAPos(rs.getString("pos_iva")));
                    h.setfNac(rs.getDate("fecha_nacimineto").toLocalDate());
                    h.setEmail(rs.getString("mail"));
                    h.setOcupacion(rs.getString("ocupacion"));
                    
                    DireccionDTO d = new DireccionDTO();
                    d.setCalle(rs.getString("calle"));
                    d.setNumero(rs.getString("numero"));
                    d.setDepartamento(rs.getString("departamento"));
                    d.setPiso(rs.getString("piso"));
                    d.setCodigoPostal(rs.getString("cod_postal"));

                    h.setDir(d);
                    h.setNacionalidad(d.getPais());

                    listaxApellido.add(h);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return listaxApellido;
    }
    
    @Override
    public HuespedDTO buscarBDxDNI(String pdni){
        HuespedDTO hxDNI = new HuespedDTO();
        String sql = "SELECT * FROM HUESPED WHERE dni = ?";
        try(Connection conn = ConexionBD.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1, pdni);

                ResultSet rs = pstmt.executeQuery();
                hxDNI.setNombre(rs.getString("nombre"));
                hxDNI.setApellido(rs.getString("apellido"));
                hxDNI.setTipoDocumento(pasarStringATipoDoc(rs.getString("tipoDocumento")));
                hxDNI.setNumeroDocumento(rs.getString("dni"));
                hxDNI.setCuit(rs.getString("cuit"));
                hxDNI.setPosicion(convertirStringAPos(rs.getString("pos_iva")));
                hxDNI.setfNac(rs.getDate("fecha_nacimineto").toLocalDate());
                hxDNI.setEmail(rs.getString("mail"));
                hxDNI.setOcupacion(rs.getString("ocupacion"));
                    
                DireccionDTO d = new DireccionDTO();
                d.setCalle(rs.getString("calle"));
                d.setNumero(rs.getString("numero"));
                d.setDepartamento(rs.getString("departamento"));
                d.setPiso(rs.getString("piso"));
                d.setCodigoPostal(rs.getString("cod_postal"));

                hxDNI.setDir(d);
                hxDNI.setNacionalidad(d.getPais());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return hxDNI;
    }

}
