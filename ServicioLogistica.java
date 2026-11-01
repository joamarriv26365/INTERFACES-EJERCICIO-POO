

//servicio puntual de logística, usa los métodos de consultable y documentable

public class ServicioLogistica extends Servicio implements Consultable, Documentable {
    private String cobertura;// hasta donde llega el envío
    private String vehiculo;//tipo de transporte

    public ServicioLogistica(String id, String estado, double latencia, String configuracion, String cobertura, String vehiculo) {
        super(id, estado, latencia, configuracion);
        this.cobertura = cobertura;
        this.vehiculo = vehiculo;
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    //devuelve el estado real del servicio y enseña que zona cubre
    @Override
    public String consultarEstado() {
        return getEstado() + " - Cobertura: " + cobertura;
    }

    //saca el calculo de cuanto sale cotizarle sumandole el extra de envio
    @Override
    public double cotizar(double valorBase) {
        return valorBase * 1.15;
    }

    //arma el documento del envio con el vehiculo asignado
    @Override
    public String generarDocumento() {
        return "Guía generada para: " + vehiculo;
    }

    //devuelve la palabra logistica para saber que tipo de servicio es
    @Override
    public String obtenerResumen() {
        return "Logística";
    }

    //agarra el detalle general de la clase servicio y le pega la cobertura y el vehiculo que son lo propio de logistica.
    @Override
    public String obtenerDetalle() {
        return super.obtenerDetalle() + "\nCobertura: " + cobertura + "\nVehículo: " + vehiculo;
    }
}