
import java.util.ArrayList;
import java.util.Scanner;

// clase principal que lleva el ciclo del menu y coordina la interaccion con el usuario
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Gestor gestor = new Gestor();
        Vista vista = new Vista();
        
        int opcion = 0;
        do {
            vista.mostrarMenu();
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } else {
                vista.mostrarMensaje("Opción inválida");
                scanner.nextLine();
                continue;
            }

            switch (opcion) {
                case 1:
                    // case 1: le pide al gestor solo los servicios activos y se los manda a la vista 
                    // para que los imprima con su estado de conexion real
                    vista.mostrarActivos(gestor.obtenerActivos());
                    break;
                case 2:
                    // case 2: le pide al gestor la lista ordenada por latencia mediante collections.sort 
                    // y se la manda a la vista para que imprima el catalogo completo en la consola
                    vista.mostrarLista(gestor.obtenerOrdenados());
                    break;
                case 3:
                    // case 3: pide que escriban un ID en la consola, se lo pasa al gestor para buscar el servicio 
                    // y si lo encuentra manda a imprimir todos sus detalles completos
                    System.out.print("Ingrese ID: ");
                    String id = scanner.nextLine();
                    Servicio s = gestor.buscarPorId(id);
                    if (s != null) {
                        vista.mostrarDetalle(s);
                    } else {
                        vista.mostrarMensaje("No encontrado");
                    }
                    break;
                case 4: {
                    // case 4: pide el ID de un servicio, el gestor revisa con instanceof que operaciones ofrece (segun sus interfaces) 
                    // y la vista las enseña numeradas. luego se lee cual quiere el usuario y con un switch de strings se piden 
                    // los datos que hagan falta (monto, valor base o descripcion) para mandarle la orden al gestor.
                    // si el ID no existe, la opcion no es un numero o escogen 0 o fuera de rango, solo regresa al menu principal.
                    System.out.print("Ingrese ID: ");
                    String idOp = scanner.nextLine();
                    Servicio sOp = gestor.buscarPorId(idOp);
                    if (sOp == null) {
                        vista.mostrarMensaje("No encontrado");
                        break;
                    }
                    ArrayList<String> ops = gestor.operacionesDisponibles(sOp);
                    vista.mostrarOperaciones(sOp, ops);
                    System.out.print("Seleccione operación: ");
                    if (!scanner.hasNextInt()) {
                        vista.mostrarMensaje("Opción inválida");
                        scanner.nextLine();
                        break;
                    }
                    int sel = scanner.nextInt();
                    scanner.nextLine();
                    if (sel < 1 || sel > ops.size()) {
                        vista.mostrarMensaje("Volviendo al menú principal");
                        break;
                    }
                    String operacion = ops.get(sel - 1);
                    switch (operacion) {
                        case "Consultar estado":
                            vista.mostrarMensaje(gestor.consultarEstado(idOp));
                            break;
                        case "Cotizar":
                            System.out.print("Ingrese valor base: ");
                            if (scanner.hasNextDouble()) {
                                double base = scanner.nextDouble();
                                scanner.nextLine();
                                vista.mostrarMensaje(gestor.cotizar(idOp, base));
                            } else {
                                vista.mostrarMensaje("Valor base inválido");
                                scanner.nextLine();
                            }
                            break;
                        case "Cobrar":
                            System.out.print("Ingrese monto a cobrar: ");
                            if (scanner.hasNextDouble()) {
                                double montoC = scanner.nextDouble();
                                scanner.nextLine();
                                vista.mostrarMensaje(gestor.operarCobro(idOp, montoC));
                            } else {
                                vista.mostrarMensaje("Monto inválido");
                                scanner.nextLine();
                            }
                            break;
                        case "Reintegrar":
                            System.out.print("Ingrese monto a reintegrar: ");
                            if (scanner.hasNextDouble()) {
                                double montoR = scanner.nextDouble();
                                scanner.nextLine();
                                vista.mostrarMensaje(gestor.operarReintegro(idOp, montoR));
                            } else {
                                vista.mostrarMensaje("Monto inválido");
                                scanner.nextLine();
                            }
                            break;
                        case "Generar documento":
                            vista.mostrarMensaje(gestor.generarDocumento(idOp));
                            break;
                        case "Registrar evento":
                            System.out.print("Descripción del evento: ");
                            String desc = scanner.nextLine();
                            vista.mostrarMensaje(gestor.registrarEvento(idOp, desc));
                            break;
                        case "Ver historial":
                            vista.mostrarHistorial(gestor.consultarHistorial(idOp));
                            break;
                        default:
                            vista.mostrarMensaje("Operación no válida");
                    }
                    break;
                }
                case 5:
                    // case 5: solicita el ID y el monto a devolver, valida que sea un numero decimal correcto 
                    // y le avisa al gestor para que procese el reintegro de manera segura usando instanceof.
                    // se dejo directo en el menu principal para poder probar que pasa si se lo piden a un servicio que no lo ofrece (logistica)
                    System.out.print("Ingrese ID: ");
                    String idR = scanner.nextLine();
                    System.out.print("Ingrese monto: ");
                    if (scanner.hasNextDouble()) {
                        double monto = scanner.nextDouble();
                        scanner.nextLine();
                        vista.mostrarMensaje(gestor.operarReintegro(idR, monto));
                    } else {
                        vista.mostrarMensaje("Monto inválido");
                        scanner.nextLine();
                    }
                    break;
                case 6:
                    // case 6: imprime el mensaje de despedida para avisar que el programa se va a apagar
                    vista.mostrarMensaje("Saliendo...");
                    break;
                default:
                    // opcion por defecto si meten un numero que no esta contemplado en el menu
                    vista.mostrarMensaje("Opción no válida");
            }
        } while (opcion != 6);

        scanner.close();
    }
}