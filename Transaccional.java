
//Esta interfaz define las operaciones (a través de métodos) de las operaciones relacionadas con dinero que se 
//pueden hacer en servicios que lidian con pagos.

public interface Transaccional {
    //Procesara un cobo por una cantidad específica de dinero.
    boolean cobrar(double monto);
    //Se encarga de ver que onda cuando se le devuelve el dinero a alguien.
    boolean reintegrar(double monto);
}