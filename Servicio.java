

// clase base para todos los servicios, no se puede crear un servicio solo servicio
//implementa el comparable para poder ordenar por latencia.


public abstract class Servicio implements Comparable<Servicio> {
    private String id;//id del servicio
    private String estado;//estado del servivio (activo o inactivo)
    private double latencia;//un numero que representa la latencia en ms
    private String configuracion;//almacena los parameteos de la API

    public Servicio(String id, String estado, double latencia, String configuracion) {
        this.id = id;
        this.estado = estado;
        this.latencia = latencia;
        this.configuracion = configuracion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getLatencia() {
        return latencia;
    }

    public void setLatencia(double latencia) {
        this.latencia = latencia;
    }

    public String getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(String configuracion) {
        this.configuracion = configuracion;
    }

    //Este método compara los objetos del tipo servicio para poder ordenarlos según latencia.
    @Override
    public int compareTo(Servicio otro) {
        //compara la latencia de un servicio con la de otro con .compare, y guarda el resultado int en una variable
        int comparacionLatencia = Double.compare(this.latencia, otro.latencia);
        //aqui ve que las latenias no sean iguales
        if (comparacionLatencia != 0) {
            //si sí son diferentes devuelve el valor conseguido
            return comparacionLatencia;
        }
        //si los valores son iguales con el compare to se comparan por alfabeto y de alli sale el "ganador"
        return this.id.compareTo(otro.getId());
    }

//otras clases deben reescribir este método para tener su propio resumen.
    public abstract String obtenerResumen();

    //arma un texto con los datos que todos los servicios tienen en comun (id, estado, latencia y configuracion).
    //las clases hijas lo reescriben llamando a super.obtenerDetalle() y le pegan al final sus datos propios,
    //asi la vista solo imprime lo que le regresen sin tener que saber de que tipo es el servicio.
    public String obtenerDetalle() {
        return "ID: " + id + "\nEstado: " + estado + "\nLatencia: " + latencia + "ms\nConfiguración: " + configuracion;
    }
}