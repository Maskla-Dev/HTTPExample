import java.io.*;
import java.util.*;

public class Catalogue {
    private static final String ARCHIVO_CATALOGO = "catalogoProductos.ser";
    List<Producto> catalogue = null;

    public void addItem(Producto producto) {
        catalogue.add(producto);
        guardarCatalogo(this.catalogue);
    }

    public Producto getItem(int id) {
        for (Producto producto : catalogue) {
            if (producto.ID == id) {
                return producto;
            }
        }
        return null;
    }

    public void updateItem(Producto producto) {
        for (int i = 0; i < catalogue.size(); i++) {
            if (catalogue.get(i).ID == producto.ID) {
                catalogue.set(i, producto);
                break;
            }
        }
        guardarCatalogo(this.catalogue);
    }

    public void deleteItem(int id) {
        catalogue.removeIf(producto -> producto.ID == id);
        guardarCatalogo(this.catalogue);
    }

    Catalogue() throws IOException, ClassNotFoundException {
        Catalogue.generarCatalogo();
        this.cargarCatalogo();
    }

    private void cargarCatalogo() {
        try (FileInputStream fileIn = new FileInputStream(ARCHIVO_CATALOGO);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            catalogue = (List<Producto>) in.readObject();
            System.out.println("Catálogo de productos cargado desde " + ARCHIVO_CATALOGO);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar el catálogo de productos: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void generarCatalogo() {
        File file = new File(ARCHIVO_CATALOGO);
        if (!file.exists()) {
            List<Producto> catalogo = new ArrayList<>();
            catalogo.add(new Producto("Barra chocolate 100gr", 10.2, 10011, "Abarrotes", 100));
            catalogo.add(new Producto("Pan integral 200gr", 20.5, 10012, "Abarrotes", 200));
            catalogo.add(new Producto("1kg de Huevo", 30.6, 10013, "Abarrotes", 300));
            catalogo.add(new Producto("1kg de Jamon", 60.5, 10014, "Abarrotes", 300));
            catalogo.add(new Producto("1kg de Jamon ahumado", 65.2, 10015, "Abarrotes", 300));
            catalogo.add(new Producto("1Lt Leche", 20.99, 10016, "Abarrotes", 300));
            catalogo.add(new Producto("Agua embotellada 100ml", 50.99, 10017, "Abarrotes", 300));
            catalogo.add(new Producto("Lapiz 1pz", 20.1, 10018, "Papeleria", 300));
            catalogo.add(new Producto("Plumas 10pz", 25.13, 10019, "Papeleria", 300));
            catalogo.add(new Producto("Goma", 89.24, 10020, "Papeleria", 300));
            catalogo.add(new Producto("Sacapuntas", 90.1, 10021, "Papeleria", 300));
            catalogo.add(new Producto("Juguete niño", 90.2, 10022, "Jugueteria", 300));
            catalogo.add(new Producto("Aspersor de agua", 100, 10023, "Jardineria", 300));
            catalogo.add(new Producto("Juego utensilios de cocina", 2000, 10024, "Linea blanca", 300));
            catalogo.add(new Producto("Silla", 5000, 10025, "Linea Blanca", 300));
            guardarCatalogo(catalogo);
        }
    }

    private static void guardarCatalogo(List<Producto> catalogo) {
        try (FileOutputStream fileOut = new FileOutputStream(ARCHIVO_CATALOGO);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(catalogo);
            System.out.println("Catalogo de productos guardado en " + ARCHIVO_CATALOGO);
        } catch (IOException e) {
            System.err.println("Error al guardar el catalogo de productos: " + e.getMessage());
        }
    }
}
