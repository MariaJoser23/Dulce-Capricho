public abstract class ProductoBase implements Producto {
    private String nombre;
    private double precio;

    public ProductoBase(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public double getPrecio() {
        return precio;
    }
}

