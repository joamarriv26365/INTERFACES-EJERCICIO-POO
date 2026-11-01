
//Interfaz que sirve para definir las capacidades necesarias para consultar el estado (si esta activo o no)
//Y tmb para cotizar el costo de algún servicio. 
public interface Consultable {
    //Todavia no esta con parametros ni operaciones pero en teoría va a devolver un string con el estado de la conexión.
    String consultarEstado();
    //Todavia no tiene que hacer pero en teoría cotiza usando los costos relacionados con los servicios.
    double cotizar(double valorBase);
}