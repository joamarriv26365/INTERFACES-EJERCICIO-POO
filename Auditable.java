
import java.util.ArrayList;
//Interfaz con los métodos para mantener una bitácora.
public interface Auditable {
    //Registra una descripcion de un evento dentro de la bitácora.
    void registrarEvento(String descripcion);
    //guarda todos los eventos en un historial y los devuelve todos (pues los devuelve, eso hace el método.)
    ArrayList<String> consultarHistorialEventos();
}