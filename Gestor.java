
import java.util.ArrayList;
import java.util.Collections;

public class Gestor {
    private ArrayList<Servicio> lista;

    public Gestor() {
        this.lista = new ArrayList<>();
        inicializar();
    }

    public ArrayList<Servicio> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Servicio> lista) {
        this.lista = lista;
    }

    // mete de golpe los 10 servicios al arrancar para que la lista no nazca vacia
    public void inicializar() {
        lista.add(new ServicioPago("pago01", "ACTIVO", 45.5, "Visa", 0.03, new ArrayList<>()));
        lista.add(new ServicioLogistica("logi1", "ACTIVO", 120.0, "Pollo Campero", "Nacional", "Camión"));
        lista.add(new ServicioCertificacion("certi1", "ACTIVO", 30.0, "SAT", "SAT-99", new ArrayList<>()));
        lista.add(new ServicioPago("pago02", "INACTIVO", 15.0, "PayPal", 0.04, new ArrayList<>()));
        lista.add(new ServicioLogistica("logi02", "ACTIVO", 80.5, "UPS", "Regional", "Moto"));
        lista.add(new ServicioCertificacion("certi02", "ACTIVO", 45.5, "SAT", "SAT-88", new ArrayList<>()));
        lista.add(new ServicioPago("pago03", "ACTIVO", 200.0, "Mastercard", 0.025, new ArrayList<>()));
        lista.add(new ServicioLogistica("logi03", "ACTIVO", 60.0, "Fedex", "Internacional", "Avión"));
        lista.add(new ServicioCertificacion("certi03", "ACTIVO", 95.0, "SAT", "SAT-77", new ArrayList<>()));
        lista.add(new ServicioPago("pago04", "ACTIVO", 45.5, "Visa", 0.01, new ArrayList<>()));
    }

    // saca una copia del arraylist para ordenarla por latencia y id sin afectar el orden original de la lista principal, 
    // usando Collections.sort que se apoya en el compareTo que armamos en la clase servicio
    public ArrayList<Servicio> obtenerOrdenados() {
        ArrayList<Servicio> copia = new ArrayList<>(lista);
        Collections.sort(copia);
        return copia;
    }

    // arma una lista nueva solo con los servicios que tienen el estado activo y recorriendo la lista principal con un for-each
    // y usando equalsIgnoreCase para que no importe si esta en mayusculas o minusculas. no toca la lista original.
    public ArrayList<Servicio> obtenerActivos() {
        ArrayList<Servicio> activos = new ArrayList<>();
        for (Servicio s : lista) {
            if (s.getEstado().equalsIgnoreCase("ACTIVO")) {
                activos.add(s);
            }
        }
        return activos;
    }

    // recorre toda la lista buscando el servicio que coincida con el id ingresado usando un bucle for-each 
    // y el equalsIgnoreCase para que no importe si escriben mayusculas o minusculas
    public Servicio buscarPorId(String id) {
        for (Servicio s : lista) {
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    // revisa que servicios implementa el objeto con instanceof y va metiendo en una lista el nombre de cada operacion que si puede hacer,
    // asi el menu solo le muestra al usuario lo que ese servicio realmente ofrece 
    public ArrayList<String> operacionesDisponibles(Servicio s) {
        ArrayList<String> ops = new ArrayList<>();
        if (s instanceof Consultable) {
            ops.add("Consultar estado");
            ops.add("Cotizar");
        }
        if (s instanceof Transaccional) {
            ops.add("Cobrar");
            ops.add("Reintegrar");
        }
        if (s instanceof Documentable) {
            ops.add("Generar documento");
        }
        if (s instanceof Auditable) {
            ops.add("Registrar evento");
            ops.add("Ver historial");
        }
        return ops;
    }

    //  revisa las reglas que comparten el cobro y el reintegro: que el servicio este activo y que el monto sea mayor a 0.
    // si todo esta bien devuelve null, y si algo esta mal devuelve el mensaje de error para mostrarselo al usuario.
    public String validarOperacionFinanciera(Servicio s, double monto) {
        if (!s.getEstado().equalsIgnoreCase("ACTIVO")) {
            return "Servicio inactivo";
        }
        if (monto <= 0) {
            return "Monto inválido, debe ser mayor a 0";
        }
        return null;
    }

    // revisa si el servicio existe y usa un instanceof para checar si aguanta dinero antes de hacer el cast seguro 
    // y ejecutar el reintegro, evitando asi que truene si le tiran un objeto que no es transaccional.
    // ahora tmb valida que este activo y que el monto sea bueno, y si el servicio es auditable deja el evento anotado en su bitacora.
    public String operarReintegro(String id, double monto) {
        Servicio s = buscarPorId(id);
        if (s == null) {
            return "Error: No existe el servicio.";
        }
        if (s instanceof Transaccional) {
            String error = validarOperacionFinanciera(s, monto);
            if (error != null) {
                return error;
            }
            boolean res = ((Transaccional) s).reintegrar(monto);
            if (res && s instanceof Auditable) {
                ((Auditable) s).registrarEvento("Reintegro de Q" + monto);
            }
            return res ? "Reintegro exitoso" : "Fallo en el reintegro";
        } else {
            return "Operación no disponible para este servicio.";
        }
    }

    // funciona igual que el reintegro pero para cobrar: busca el servicio, checa con instanceof que sea transaccional,
    // valida activo y monto, hace el cast para cobrar y si es auditable anota el evento en la bitacora.
    public String operarCobro(String id, double monto) {
        Servicio s = buscarPorId(id);
        if (s == null) {
            return "Error: No existe el servicio.";
        }
        if (s instanceof Transaccional) {
            String error = validarOperacionFinanciera(s, monto);
            if (error != null) {
                return error;
            }
            boolean res = ((Transaccional) s).cobrar(monto);
            if (res && s instanceof Auditable) {
                ((Auditable) s).registrarEvento("Cobro de Q" + monto);
            }
            return res ? "Cobro exitoso" : "Fallo en el cobro";
        } else {
            return "Operación no disponible para este servicio.";
        }
    }

    // busca el servicio y si es consultable (instanceof) hace el cast y devuelve su estado real, que cambia segun la clase
    public String consultarEstado(String id) {
        Servicio s = buscarPorId(id);
        if (s == null) {
            return "Error: No existe el servicio.";
        }
        if (s instanceof Consultable) {
            return "Estado: " + ((Consultable) s).consultarEstado();
        }
        return "Operación no disponible para este servicio.";
    }

    // busca el servicio y si es consultable le manda el valor base a cotizar con el cast seguro,
    // cada clase calcula distinto (pago suma comision, logistica le suma el 15% y certificacion lo deja igual).
    // antes de cotizar revisa que el valor base sea mayor a 0, esta validacion vive aqui para que el main no tenga que hacerla.
    public String cotizar(String id, double valorBase) {
        Servicio s = buscarPorId(id);
        if (s == null) {
            return "Error: No existe el servicio.";
        }
        if (valorBase <= 0) {
            return "Valor base inválido, debe ser mayor a 0";
        }
        if (s instanceof Consultable) {
            return "Cotización: Q" + ((Consultable) s).cotizar(valorBase);
        }
        return "Operación no disponible para este servicio.";
    }

    // busca el servicio y si es documentable (instanceof) hace el cast y devuelve el documento que genera,
    // ya sea la guia de envio o el documento certificado segun el tipo de servicio.
    public String generarDocumento(String id) {
        Servicio s = buscarPorId(id);
        if (s == null) {
            return "Error: No existe el servicio.";
        }
        if (s instanceof Documentable) {
            return ((Documentable) s).generarDocumento();
        }
        return "Operación no disponible para este servicio.";
    }

    // busca el servicio y si es auditable (instanceof) hace el cast y le agrega la descripcion a su bitacora.
    public String registrarEvento(String id, String descripcion) {
        Servicio s = buscarPorId(id);
        if (s == null) {
            return "Error: No existe el servicio.";
        }
        if (s instanceof Auditable) {
            ((Auditable) s).registrarEvento(descripcion);
            return "Evento registrado";
        }
        return "Operación no disponible para este servicio.";
    }

    // busca el servicio y si es auditable devuelve su historial completo de eventos,
    // si no existe o no es auditable devuelve null y quien lo llame ve que hacer con eso.
    public ArrayList<String> consultarHistorial(String id) {
        Servicio s = buscarPorId(id);
        if (s instanceof Auditable) {
            return ((Auditable) s).consultarHistorialEventos();
        }
        return null;
    }
}