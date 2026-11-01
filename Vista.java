
import java.util.ArrayList;

public class Vista {
    //menu principal con println 

    public void mostrarMenu() {
        System.out.println("\n--- MENU DE SERVICIOS ---");
        System.out.println("1. Listar servicios activos (estado de conexión)");
        System.out.println("2. Listar todos los servicios ordenados por latencia");
        System.out.println("3. Buscar servicio por ID");
        System.out.println("4. Ver operaciones disponibles de un servicio");
        System.out.println("5. Realizar reintegro");
        System.out.println("6. Salir");
        System.out.print("Seleccione: ");
    }

    // recorre la coleccion de servicios pasados por parametro con un bucle for-each y los imprime 
    // aprovechando el polimorfismo del metodo obtenerResumen para saber que tipo de servicio es
    public void mostrarLista(ArrayList<Servicio> lista) {
        System.out.println("\n--- SERVICIOS ---");
        for (Servicio s : lista) {
            System.out.println("ID: " + s.getId() + " | Estado: " + s.getEstado() + " | Latencia: " + s.getLatencia() + "ms [" + s.obtenerResumen() + "]");
        }
    }

    // igual que mostrarLista pero este enseña el estado de conexion real: si el servicio es consultable (instanceof) hace el cast
    // y usa su consultarEstado(), que cambia segun el tipo de servicio, y si no lo es pues solo muestra el estado normal.
    public void mostrarActivos(ArrayList<Servicio> lista) {
        System.out.println("\n--- SERVICIOS ACTIVOS ---");
        for (Servicio s : lista) {
            String estado = (s instanceof Consultable) ? ((Consultable) s).consultarEstado() : s.getEstado();
            System.out.println("ID: " + s.getId() + " | Estado: " + estado + " [" + s.obtenerResumen() + "]");
        }
    }

    // imprime a detalle todas las propiedades del servicio encontrado, ahora llamando a obtenerDetalle() que cada clase 
    // reescribe, asi tmb salen los datos propios (comision, cobertura, vehiculo, autorizacion) sin que la vista tenga que saber el tipo.
    public void mostrarDetalle(Servicio s) {
        System.out.println("\n--- DETALLES ---");
        System.out.println(s.obtenerDetalle());
    }

    // imprime el nombre del servicio y las operaciones que si puede hacer, numeradas para que el usuario escoja una por su numero
    public void mostrarOperaciones(Servicio s, ArrayList<String> ops) {
        System.out.println("\n--- OPERACIONES DE " + s.getId() + " [" + s.obtenerResumen() + "] ---");
        for (int i = 0; i < ops.size(); i++) {
            System.out.println((i + 1) + ". " + ops.get(i));
        }
        System.out.println("0. Volver");
    }

    // imprime el historial de la bitacora enumerado, si viene null es que no es auditable y si viene vacio es que todavia no hay eventos
    public void mostrarHistorial(ArrayList<String> historial) {
        if (historial == null) {
            System.out.println("Operación no disponible para este servicio.");
        } else if (historial.isEmpty()) {
            System.out.println("La bitácora todavía no tiene eventos.");
        } else {
            System.out.println("\n--- HISTORIAL DE EVENTOS ---");
            for (int i = 0; i < historial.size(); i++) {
                System.out.println((i + 1) + ". " + historial.get(i));
            }
        }
    }

    // imprime cualquier aviso o texto rapido hacia afuera usando System.out.println para no repetir codigo de impresion
    public void mostrarMensaje(String msg) {
        System.out.println(msg);
    }
}