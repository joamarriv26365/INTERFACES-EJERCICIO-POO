
import java.util.ArrayList;

//servicio de certificacion, tambien implementa consultable para ver el estado real como las otras clases
public class ServicioCertificacion extends Servicio implements Consultable, Documentable, Auditable {
    private String autorizacion; //codigo o llave que le da la institucion
    private ArrayList<String> bitacora; //historial de la certificacion

    public ServicioCertificacion(String id, String estado, double latencia, String configuracion, String autorizacion, ArrayList<String> bitacora) {
        super(id, estado, latencia, configuracion);
        this.autorizacion = autorizacion;
        this.bitacora = bitacora;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public ArrayList<String> getBitacora() {
        return bitacora;
    }

    public void setBitacora(ArrayList<String> bitacora) {
        this.bitacora = bitacora;
    }

    //devuelve el estado real y la autorizacion que tiene
    @Override
    public String consultarEstado() {
        return getEstado() + " - Autorización: " + autorizacion;
    }

    //devuelve la base completa sin cambios extra
    @Override
    public double cotizar(double valorBase) {
        return valorBase;
    }

    //arma la respuesta en string con la autorizacion
    @Override
    public String generarDocumento() {
        return "Documento certificado: " + autorizacion;
    }

    //agrega un nuevo registro al listado de la bitacora
    @Override
    public void registrarEvento(String descripcion) {
        bitacora.add(descripcion);
    }

    //devuelve toda la lista de eventos guardados
    @Override
    public ArrayList<String> consultarHistorialEventos() {
        return bitacora;
    }

    //devuelve la etiqueta para saber que es de certificacion
    @Override
    public String obtenerResumen() {
        return "Certificación";
    }

    //agarra el detalle general de la clase servicio y le suma la autorizacion y cuantos eventos hay en la bitacora.
    @Override
    public String obtenerDetalle() {
        return super.obtenerDetalle() + "\nAutorización: " + autorizacion + "\nEventos en bitácora: " + bitacora.size();
    }
}