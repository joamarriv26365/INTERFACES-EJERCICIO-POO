
import java.util.ArrayList;


//Ya un servicio específico que son los espacios de pago y usa los metodos de ciertas interfaces por eso los implements, con override.
public class ServicioPago extends Servicio implements Transaccional, Consultable, Auditable {
    private double comision;//% de comisión que cobra
    private ArrayList<String> bitacora;//historial de el servicio.

    public ServicioPago(String id, String estado, double latencia, String configuracion, double comision, ArrayList<String> bitacora) {
        super(id, estado, latencia, configuracion);
        this.comision = comision;
        this.bitacora = bitacora;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    public ArrayList<String> getBitacora() {
        return bitacora;
    }

    public void setBitacora(ArrayList<String> bitacora) {
        this.bitacora = bitacora;
    }
//simula el cobro viendo si el monto que se recibe es mayor a 0 sí si devuelve true.
    @Override
    public boolean cobrar(double monto) {
        return monto > 0;
    }
//simula la devolucion viendo que el monto es mayor a 0, mismo principio que el método pasado.
    @Override
    public boolean reintegrar(double monto) {
        return monto > 0;
    }
//revisa como esta el espacio de cobro usando el estado real y te da la latencia del mismo.
    @Override
    public String consultarEstado() {
        return getEstado() + " - Latencia: " + getLatencia() + "ms";
    }
//hace los cálculos de la cotizacion sumando la comosion dle espacio de cobro al valor base. 
    @Override
    public double cotizar(double valorBase) {
        return valorBase + (valorBase * comision);
    }
//le agrega un dato a la bitácora detallando la "operación" que sucedió.
    @Override
    public void registrarEvento(String descripcion) {
        bitacora.add(descripcion);
    }
//devuelve toda la bitácora.
    @Override
    public ArrayList<String> consultarHistorialEventos() {
        return bitacora;
    }
//devuelve un string que dice pago para dar a entender que es un servicio de pago
    @Override
    public String obtenerResumen() {
        return "Pago";
    }
//agarra el detalle general de la clase servicio y le suma la comision (pasada a porcentaje) y cuantos eventos hay en la bitacora.
    @Override
    public String obtenerDetalle() {
        return super.obtenerDetalle() + "\nComisión: " + String.format("%.1f", comision * 100) + "%"
                + "\nEventos en bitácora: " + bitacora.size();
    }
}