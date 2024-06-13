import java.io.Serializable;

public class Producto implements Serializable {
    String name;
    String department;
    double price;
    int amount;
    int ID;
    // Otros atributos según tus necesidades

    public Producto(String nombre, double precio, int ID, String departamento, int cantidad) {
        this.name = nombre;
        this.price = precio;
        this.ID = ID;
        this.department = departamento;
        this.amount = cantidad;
    }
}
